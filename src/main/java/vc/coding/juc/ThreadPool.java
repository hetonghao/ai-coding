package vc.coding.juc;

import lombok.SneakyThrows;

import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author HeTongHao
 * @since 2020/3/5 20:51
 */
public class ThreadPool {
    /**
     * 执行工厂
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(2, 50, 100L
            , TimeUnit.SECONDS, new ArrayBlockingQueue<>(20)
            , Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    private static ExecutorService executorService1 = Executors.newFixedThreadPool(1);
    /**
     * 计划执行工厂
     */
    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10
            , (r) -> new Thread(r, "[" + new Random().nextInt(10) + "]"), new ScheduledThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {
        schedule();
        System.out.println(Thread.activeCount() - 2);
        System.out.println("main完毕");
    }

    private static void executor() {
        long startMemory = Runtime.getRuntime().freeMemory();
        ThreadPool threadPool = new ThreadPool();
        for (long i = 1; i <= 70; i++) {
            System.out.println("创建时消耗内存" + (startMemory - Runtime.getRuntime().freeMemory()));
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行完毕消耗内存" + (startMemory - Runtime.getRuntime().freeMemory()));
            });
        }
        System.out.println("-结束时消耗内存" + (startMemory - Runtime.getRuntime().freeMemory()));
    }

    @SneakyThrows
    private void sum() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss");
        TimeUnit.SECONDS.sleep(5);
//        System.out.println("线程" + Thread.currentThread().getName() + ",time:" + dateTimeFormatter.format(LocalDateTime.now()));
    }

    @SneakyThrows
    private static void schedule() {
        long startMemory = Runtime.getRuntime().freeMemory();
        System.out.println("开始时内存" + startMemory);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss");
        for (int i = 1; i <= 50; i++) {
            System.out.println("创建时消耗内存" + (startMemory - Runtime.getRuntime().freeMemory()));
            System.out.println(Thread.activeCount() - 2);
            scheduledExecutorService.schedule(() -> {
//                System.out.println("线程" + Thread.currentThread().getName() + "执行," + dateTimeFormatter.format(LocalDateTime.now()));
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行完消耗内存" + (startMemory - Runtime.getRuntime().freeMemory()));
//                System.out.println("线程" + Thread.currentThread().getName() + "执行完毕!" + dateTimeFormatter.format(LocalDateTime.now()));
            }, 2, TimeUnit.SECONDS);
            if (i == 6) {
                scheduledExecutorService.shutdown();
            }
        }
        while (true) {
            System.out.println("-结束时消耗内存" + (startMemory - Runtime.getRuntime().freeMemory()));
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
