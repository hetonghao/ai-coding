package vc.coding.juc;

import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author HeTongHao
 * @since 2020/3/6 02:18
 */
public class ScheduledExecutor {
    /**
     * 计划执行池
     */
    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(300
            , Executors.defaultThreadFactory(), new ScheduledThreadPoolExecutor.AbortPolicy());

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("开始cpu"+CPUMonitorCalc.getInstance().getProcessCpu());
        long startMemory = Runtime.getRuntime().freeMemory();
        System.out.println("开始时内存" + startMemory);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss");
        for (int i = 1; i <= 300; i++) {
            System.out.println("创建时消耗内存：" + consumeMB(startMemory));
            System.out.println("创建线程数：" + (Thread.activeCount() - 2));
            scheduledExecutorService.schedule(() -> {
//                try {
//                    TimeUnit.SECONDS.sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("执行完消耗内存" + consumeMB(startMemory)
                        + "时间" + dateTimeFormatter.format(LocalDateTime.now()));
            }, 100, TimeUnit.SECONDS);
        }
        while (true) {
            System.out.println("-实时消耗内存" + consumeMB(startMemory));
            System.out.println("-实时cpu占用"+CPUMonitorCalc.getInstance().getProcessCpu());
            TimeUnit.SECONDS.sleep(2);
        }
    }

    private static String consumeMB(long startMemory) {
        return (startMemory - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "MB";
    }
}
