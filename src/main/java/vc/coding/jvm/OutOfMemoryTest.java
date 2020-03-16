package vc.coding.jvm;

/**
 * 超出内存异常测试
 * -XX:MetaspaceSize=8m
 * -XX:MaxMetaspaceSize=8m
 *
 * @author HeTongHao
 * @since 2020/3/14 16:07
 */
public class OutOfMemoryTest {
    public static void main(String[] args) {
        OutOfMemoryTest ss = new OutOfMemoryTest();
    }
}
