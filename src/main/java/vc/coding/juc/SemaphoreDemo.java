package vc.coding.juc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 需求，一段代码同时只能被3个线程执行
 *
 * @author HeTongHao
 * @since 2020/3/8 16:09
 */
public class SemaphoreDemo {
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("mm分ss秒");

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "线程抢到了执行权限" + dateTimeFormatter.format(LocalDateTime.now()));
                    //使用三秒执行权限
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "线程释放了执行权限");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
