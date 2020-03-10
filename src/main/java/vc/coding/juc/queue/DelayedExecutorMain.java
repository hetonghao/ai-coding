package vc.coding.juc.queue;

import java.util.concurrent.TimeUnit;

/**
 * @author HeTongHao
 * @since 2020/3/10 10:33
 */
public class DelayedExecutorMain {

    public static void main(String[] args) {
        DelayedExecutorMain delayedExecutorMain = new DelayedExecutorMain();
        delayedExecutorMain.start();
    }

    private void start() {
        DelayedExecutor<DelayedTask<Long>> delayedExecutor = new DelayedExecutor<>(delayedTask -> {
            consume(delayedTask.getReference());
        });
        long id = 0;
        delayedExecutor.putTask(new DelayedTask<>(++id, 3, TimeUnit.SECONDS));
        delayedExecutor.putTask(new DelayedTask<>(++id, 3, TimeUnit.SECONDS));
        delayedExecutor.putTask(new DelayedTask<>(++id, 5, TimeUnit.SECONDS));
    }

    private void consume(Long id) {
        System.out.println(id + ",订单被消费");
    }
}
