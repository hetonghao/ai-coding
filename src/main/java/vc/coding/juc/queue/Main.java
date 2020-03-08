package vc.coding.juc.queue;

import lombok.SneakyThrows;
import vc.coding.juc.CPUMonitorCalc;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * 延时处理
 *
 * @author HeTongHao
 * @since 2020/3/6 22:58
 */
public class Main {
    private static ExecutorService executorService = new ThreadPoolExecutor(2, 2, 0
            , TimeUnit.SECONDS, new ArrayBlockingQueue<>(1)
            , Executors.privilegedThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss");

    @SneakyThrows
    public static void main(String[] args) {
        long startMemory = Runtime.getRuntime().freeMemory();
        System.out.println("开始时消耗内存" + consumeMB(startMemory));
        DelayQueue<DelayedOrder> delayedOrders = new DelayQueue<>();
        //启动消费者
        startConsumption(delayedOrders);
        //启动提供者
        startSupplier(delayedOrders);
        while (true) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("实时时消耗内存" + consumeMB(startMemory));
            System.out.println("-实时cpu占用"+ CPUMonitorCalc.getInstance().getProcessCpu());
        }
    }

    private static String consumeMB(long startMemory) {
        return (startMemory - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "MB";
    }

    /**
     * 启动消费者
     *
     * @param delayedOrders
     */
    private static void startConsumption(DelayQueue<DelayedOrder> delayedOrders) {
        executorService.execute(() -> {
            System.out.println("消费者已启动");
            while (true) {
                try {
                    DelayedOrder delayedOrder = delayedOrders.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 启动提供者
     *
     * @param delayedOrders
     */
    private static void startSupplier(DelayQueue<DelayedOrder> delayedOrders) {
        executorService.execute(() -> {
            System.out.println("提供者已启动");
            long id = 0;
            for (int i = 1; i <= 100000; i++) {
                //每个订单延迟30分钟消费
                DelayedOrder delayedOrder = new DelayedOrder(++id, 30, TimeUnit.MINUTES);
                delayedOrders.put(delayedOrder);
            }
            //模拟提供者，源源不断创建订单
            while (true) {
                //每个订单延迟30分钟消费
                DelayedOrder delayedOrder = new DelayedOrder(++id, 30, TimeUnit.MINUTES);
                delayedOrders.put(delayedOrder);
                try {
                    //毎100毫秒创建一个订单,一秒10单
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
