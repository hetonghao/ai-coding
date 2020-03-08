package vc.coding.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 需求:5个线程全部执行完毕，main线程才结束
 *
 * @author HeTongHao
 * @since 2020/3/8 15:36
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                System.out.println("线程" + Thread.currentThread().getName() + "启动");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "线程完毕");
    }
}
