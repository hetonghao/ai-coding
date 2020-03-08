package vc.coding.juc;

import lombok.Data;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author HeTongHao
 * @since 2020/3/3 22:17
 */
public class Volatile {
    public static void main(String[] args) {
        Demo demo = new Demo();
        Thread thread = new Thread(demo, "A");
        thread.start();
        while (true) {
            if (demo.isFlag()) {
                System.out.println("跳出循环：" + demo.isFlag());
                break;
            }
        }
    }
}

@Data
class Demo implements Runnable {
    private volatile boolean flag = false;

    @SneakyThrows
    @Override
    public void run() {
        TimeUnit.SECONDS.sleep(1);
        flag = true;
        System.out.println("线程：" + Thread.currentThread().getName() + ",flag:" + isFlag());
    }
}
