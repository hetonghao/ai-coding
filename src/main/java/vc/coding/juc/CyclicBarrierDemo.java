package vc.coding.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 需求：集齐七颗龙珠后立即召唤神龙
 *
 * @author HeTongHao
 * @since 2020/3/8 15:51
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("召唤神龙!");
        });
        for (int i = 1; i <= 7; i++) {
            final int tempI = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "收集了第" + tempI + "颗龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
