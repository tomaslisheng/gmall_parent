package com.atguigu.gmall.filter;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.client.UserFeignClient;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * author lisheng
 * Date:2021/6/2
 * Description:
 */
@Component
public class AuthFilter implements GlobalFilter {
    @Value("${whiteList.url}")
    private String whiteList;
    @Autowired
    private UserFeignClient userFeignClient;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1,根据token查询当前token对应的用户是否存在。如果存在白名单正常访问，否则跳转至登录页面
        //2,如何未登录，部分功能 可以通过，比如购物车，会获取要给userTempId，如果登录就获取userId 和 userTempId
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String uri = request.getURI().toString();
        String path = request.getPath().toString();

        //如果是静态资源，直接放行
        if(uri.contains(".jpg")||uri.contains(".png")||uri.contains(".gif")||uri.contains(".css")||uri.contains(".js")||uri.contains(".ico")||uri.contains("passport")){
            return chain.filter(exchange);
        }
        //如果路径 是内部请求，则不允许访问。直接返回 状态信息
        if(antPathMatcher.match("/**/inner/**",path)){
            //请求不合法
            return  outMono(response,ResultCodeEnum.SECKILL_ILLEGAL);
        }
        String token = getCookieOrHeaderValue(request,"token");
        HashMap<String,Object> verifyMap = new HashMap<String,Object>();
        //如果包含，需要去校验token是否存在 ，进行身份认证
        if(!StringUtils.isEmpty(token)){
             verifyMap =userFeignClient.verify(token);
            if(verifyMap!=null&&!StringUtils.isEmpty(verifyMap.get("userId"))){
                Integer userId = (Integer)verifyMap.get("userId");
                request.mutate().header("userId",userId.toString()).build();
                exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }

        }
        else{
            //不存在token 直接获取userTempId 传递给网关之后的微服务
            String userTempId = getCookieOrHeaderValue(request,"userTempId");
            request.mutate().header("userTempId",userTempId).build();
            exchange.mutate().request(request).build();
            return chain.filter(exchange);
        }

        //2,验证失败-登录界面
        if(verifyMap==null||!"success".equals(verifyMap.get("success"))){
        //如果是白名单路径，必须校验。是否登录
        //web请求
        String[] whiteGroup = whiteList.split(",");
        for (String white : whiteGroup) {
            if(uri.contains(white)){
                    //重定向到其他页面
                    response.setStatusCode(HttpStatus.SEE_OTHER);
                    response.getHeaders().set(HttpHeaders.LOCATION,"http://passport.gmall.com/login.html?originUrl="+request.getURI());
                    Mono<Void> voidMono = response.setComplete();
                    return voidMono;
            }
        }
        }
        return chain.filter(exchange);
    }


    public  Mono<Void> outMono(ServerHttpResponse response, ResultCodeEnum resultCodeEnum){

        Result<Object> result = Result.build(Object.class,resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer wrap = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");// 设置编码格式
        //输入到页面
        Mono<Void> voidMono = response.writeWith(Mono.just(wrap));
        return voidMono;
    }

    /**
     * 由于 web 分同步和异步请求，所以，异步的时候，参数不能放入cookie
     * @param request
     * @param sessionId
     * @return
     */
    private String getCookieOrHeaderValue(ServerHttpRequest request ,String sessionId) {

        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        List<HttpCookie> httpCookies = cookies.get(sessionId);
        String tokenResult="";
        if(httpCookies!=null&&httpCookies.size()>0){
            //获取token值
             tokenResult = httpCookies.get(0).getValue();
        }
        //如果token不存在  1,没有传递token 2,异步请求，token在header里面存在
        if(StringUtils.isEmpty(tokenResult)){
            if(request.getHeaders().get(sessionId)!=null&&request.getHeaders().get(sessionId).size()>0){
                tokenResult = request.getHeaders().get(sessionId).get(0);
            }

        }
        return tokenResult;
    }

}
