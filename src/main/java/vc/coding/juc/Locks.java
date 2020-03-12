package vc.coding.juc;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 需求：同一个人、同一个功能，不能同时执行，
 * 如果出现额外操作执行时直接抛出
 *
 * @author HeTongHao
 * @since 2020/3/11 17:44
 */
public class Locks {
    private static java.util.concurrent.locks.Lock lock = new ReentrantLock();
    private static CopyOnWriteArraySet<Long> set = new CopyOnWriteArraySet();
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        for (long i = 1; i <= 1000; i++) {
            new Thread(() -> {
                exec(1L);
            }, String.valueOf(i)).start();
        }
        TimeUnit.SECONDS.sleep(100);
    }

    private static void exec(Long id) {
        try {
            if (lock(() -> set.contains(id), () -> set.add(id))) {
                System.out.println("id:" + id + ",执行业务" + atomicInteger.addAndGet(1));
            }
        } finally {
            set.remove(id);
        }
    }

    /**
     * @param excludeCondition 排除条件
     * @param runnable         修改名单
     * @return
     */
    private static boolean lock(Supplier<Boolean> excludeCondition, Runnable runnable) {
        if (excludeCondition.get()) {
            return false;
        } else {
            lock.lock();
            try {
                if (excludeCondition.get()) {
                    return false;
                }
                runnable.run();
                return true;
            } finally {
                lock.unlock();
            }
        }
    }
}
