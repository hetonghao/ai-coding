package vc.coding.juc;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author HeTongHao
 * @since 2020/3/11 17:44
 */
public class Locks {
    private static java.util.concurrent.locks.Lock lock = new ReentrantLock();
    private static CopyOnWriteArraySet set = new CopyOnWriteArraySet();
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        for (long i = 1; i <= 100; i++) {
            new Thread(() -> {
                xx(1L);
            }, String.valueOf(i)).start();
            new Thread(() -> {
                xx(2L);
            }, String.valueOf(i)).start();
        }
    }

    private static void xx(Long id) {
        if (set.contains(id)) {
            System.out.println("id:" + id + ",正在操作,跳出!");
            return;
        } else {
            lock.lock();
            try {
                set.add(id);
                System.out.println("id:" + id + ",执行业务" + atomicInteger.addAndGet(1));
            } finally {
                lock.unlock();
                set.remove(id);
            }
        }
    }

}
