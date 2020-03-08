package vc.coding.juc;

import lombok.Data;
import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程之间精准唤醒
 * 题目：三个线程分别执行三个方法分别将str追加 A 、B 、C
 *
 * @author HeTongHao
 * @since 2020/3/4 01:34
 */
public class WakeUpPrecisely {
    public static void main(String[] args) {
        Demo3 demo3 = new Demo3();
        int count = 10;
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo3.addA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo3.addB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i <= count; i++) {
                demo3.addC();
            }
        }, "C").start();
    }
}

@Data
class Demo3 {
    private String str = "";
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    @SneakyThrows
    public void addA() {
        lock.lock();
        try {
            while (!"".equals(str) && !str.endsWith("C")) {
                condition1.await();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + ",处理后："
                    + (str = str + "A"));
            condition2.signal();
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public void addB() {
        lock.lock();
        try {
            while (!str.endsWith("A")) {
                condition2.await();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + ",处理后："
                    + (str = str + "B"));
            condition3.signal();
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public void addC() {
        lock.lock();
        try {
            while (!str.endsWith("B")) {
                condition3.await();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + ",处理后："
                    + (str = str + "C"));
            condition1.signal();
        } finally {
            lock.unlock();
        }
    }
}
