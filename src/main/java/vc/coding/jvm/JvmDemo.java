package vc.coding.jvm;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author HeTongHao
 * @since 2020/3/11 14:25
 */
public class JvmDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(JvmDemo.class.getClassLoader());
        System.out.println(JvmDemo.class.getClassLoader().getParent());
        System.out.println(JvmDemo.class.getClassLoader().getParent());
        TimeUnit.SECONDS.sleep(1000);
    }
}

class StaticBlock {
    /**
     * 加载类的时候有确定的值
     */
    public static final String str = "123";
    /**
     * 加载类的时候未确定的值
     */
    public static final String str1 = UUID.randomUUID().toString();

    static {
        // 这句话会输出吗？
        System.out.println("MyParent04 static");
    }
}

