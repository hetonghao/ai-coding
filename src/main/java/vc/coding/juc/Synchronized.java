package vc.coding.juc;

import lombok.Data;

/**
 * @author HeTongHao
 * @since 2020/3/3 22:39
 */
public class Synchronized {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int count = 10000;
        Demo1 demo = new Demo1();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "D").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "E").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "F").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "G").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "H").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "I").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "J").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo.run();
            }
        }, "K").start();
        new Thread(() -> {
            while (true) {
                if (demo.getI() >= count * 10 - 10) {
                    System.out.println("time:" + (System.currentTimeMillis() - start));
                    break;
                }
            }
        }, "z").start();
    }
}

@Data
class Demo1 {
    private volatile int i = 0;

    @lombok.Synchronized
    public void run() {
        System.out.println("线程：" + Thread.currentThread().getName() + "，当前" + (i++) + ",i:" + i);
    }
}