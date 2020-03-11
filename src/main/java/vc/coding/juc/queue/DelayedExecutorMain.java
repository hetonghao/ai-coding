package vc.coding.juc.queue;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author HeTongHao
 * @since 2020/3/10 10:33
 */
public class DelayedExecutorMain {

//    AtomicInteger count = new AtomicInteger();

    private final DelayedExecutor delayedExecutor = new DelayedExecutor<DelayedTask<Long>>().start(delayedTask -> {
        consume(delayedTask.getReference());
    }).executeOnce(()->{
            System.out.println("拉起任务");
    });

    public static void main(String[] args) {
        DelayedExecutorMain delayedExecutorMain = new DelayedExecutorMain();
        delayedExecutorMain.start();
    }

    @SneakyThrows
    private void start() {
        long id = 0;
        for (int i = 1; i <= 1000; i++) {
            delayedExecutor.putTask(new DelayedTask<>(++id, 3, TimeUnit.SECONDS));
        }
        TimeUnit.SECONDS.sleep(3);
        delayedExecutor.stop();
    }

    private void consume(Long id) {
        System.out.println(id + ",订单被消费");
    }
}
