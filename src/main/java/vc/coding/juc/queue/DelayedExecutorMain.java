package vc.coding.juc.queue;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author HeTongHao
 * @since 2020/3/10 10:33
 */
public class DelayedExecutorMain {

    AtomicInteger count = new AtomicInteger();

    private final DelayedExecutor<DelayedTask<Long>> delayedExecutor = new DelayedExecutor<DelayedTask<Long>>(delayedTask -> {
        consume(delayedTask.getReference());
//        System.out.println(delayedTask.getReference() + ",设备离线");
    }).start().executeOnce(() -> {
    });

    public static void main(String[] args) {
        DelayedExecutorMain delayedExecutorMain = new DelayedExecutorMain();
        delayedExecutorMain.start();
    }

    int testCont = 1_00000;
    long startTime;

    @SneakyThrows
    private void start() {
        long id = 0;
        for (int i = 1; i <= testCont; i++) {
            delayedExecutor.putTask(new DelayedTask<>(++id, 3, TimeUnit.SECONDS));
        }
        TimeUnit.SECONDS.sleep(3);
        delayedExecutor.stop();
        TimeUnit.SECONDS.sleep(3);
        delayedExecutor.start();
    }

    private void consume(Long id) {
        if (id == 1) {
            startTime = System.currentTimeMillis();
        }
        int currentCount = count.addAndGet(1);
        System.out.println(id + ",订单被消费" + currentCount);
        if (testCont == currentCount) {
            System.out.println("总执行时间" + (System.currentTimeMillis() - startTime));
        }
    }
}
