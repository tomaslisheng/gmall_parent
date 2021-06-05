import com.atguigu.gmall.ServiceWebAllApplication;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * author lisheng
 * Date:2021/5/21
 * Description:
 */
@Data
@Component
@SpringBootTest(classes = ServiceWebAllApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    private String port;
    private String host;
    private String username;
    @Test
    public void test01() {
        redisTemplate.opsForValue().set("雷先生的妻子们", "value");
    }
}
