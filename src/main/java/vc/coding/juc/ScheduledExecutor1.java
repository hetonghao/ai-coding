package vc.coding.juc;

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
public class ScheduledExecutor1 {
    /**
     * 计划执行池
     */
    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(3
            , Executors.defaultThreadFactory(), new ScheduledThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss");
            scheduledExecutorService.schedule(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行的时间" + dateTimeFormatter.format(LocalDateTime.now()));
            }, 2, TimeUnit.SECONDS);
        }
    }
}
