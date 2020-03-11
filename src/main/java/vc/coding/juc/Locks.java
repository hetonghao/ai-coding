package vc.coding.juc;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author HeTongHao
 * @since 2020/3/11 17:44
 */
public class Locks {
    private static java.util.concurrent.locks.Lock lock = new ReentrantLock();
    private static CopyOnWriteArraySet<Long> set = new CopyOnWriteArraySet();
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        for (long i = 1; i <= 100; i++) {
            new Thread(() -> {
                exec(1L);
            }, String.valueOf(i)).start();
        }
    }

    private static void exec(Long id) {
        try {
            if (lock(cId -> set.contains(cId), () -> set.add(id), id)) {
                System.out.println("id:" + id + ",执行业务");
            }
        } finally {
            set.remove(id);
        }
    }

    /**
     * @param excludeCondition 排除条件
     * @param runnable         修改名单
     * @param id
     * @return
     */
    private static boolean lock(Predicate<Long> excludeCondition, Runnable runnable, Long id) {
        //
        if (excludeCondition.test(id)) {
            return false;
        } else {
            lock.lock();
            try {
                if (excludeCondition.test(id)) {
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
