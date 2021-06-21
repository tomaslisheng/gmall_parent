import java.math.BigDecimal;

/**
 * author lisheng
 * Date:2021/6/7
 * Description:
 */
public class TestBigDecimall {
    public static void main(String[] args) {

        BigDecimal  num= new BigDecimal(5);
        BigDecimal add = num.add(BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(3)));
        System.out.println(add);
    }
}
