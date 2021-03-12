import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;

/**
 * @Description:
 * @Author: lsw
 * @Date: 2021-03-11 10:03
 * @Version v1.0
 **/
public class Demo {
    public static void main(String[] args) {
        ConcurrencyTester tester = ThreadUtil.concurrencyTest(5, () -> {
//            HttpUtil.get("http://127.0.0.1:8001/fanout");
            HttpUtil.get("http://127.0.0.1:8001/pay/success");
        });

        System.out.println(tester.getInterval());
    }
}
