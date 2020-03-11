package vc.coding.juc.queue;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * 延时执行者
 *
 * @author HeTongHao
 * @since 2020/3/10 10:27
 */
@Data
@Accessors(chain = true)
public class DelayedExecutor<Task extends DelayedTask> {
    private ExecutorService executorService;
    private DelayQueue<Task> delayedTasks;
    private Condition condition = new ReentrantLock().newCondition();
    private boolean isStart;
    private AtomicInteger startTimes = new AtomicInteger();

    private DelayedExecutor(ExecutorService executorService) {
        this.delayedTasks = new DelayQueue<>();
        this.executorService = executorService;
    }

    /**
     * 默认构造
     * 2个线程
     * A线程持续取任务，B线程负责消费。
     * 如果B线程处理不过来交给A线程处理,并发较高的情况下请传入线程池参数自定义线程池
     */
    public DelayedExecutor() {
        this(new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS
                , new ArrayBlockingQueue<>(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy()));
    }

    /**
     * 自定义线程池
     *
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   线程空闲时间后关闭
     * @param unit            时间单位
     * @param workQueue       阻塞队列
     * @param threadFactory   线程工厂
     */
    public DelayedExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime
            , TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        this(new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue
                , threadFactory, new ThreadPoolExecutor.CallerRunsPolicy()));
    }

    /**
     * 启动延时任务执行器
     * 持续等待延时任务到期，到期立刻执行
     *
     * @param execute 执行过程
     */
    public DelayedExecutor<Task> start(Consumer<Task> execute) {
        isStart = true;
        if(startTimes.addAndGet(1)==1){
            executorService.execute(() -> {
                do {
                    try {
                        if (!isStart) {
                            condition.await();
                        }
                        Task task = delayedTasks.take();
                        executorService.execute(() -> execute.accept(task));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            });
        }
        return this;
    }

    /**
     * 停止
     *
     * @return
     */
    public DelayedExecutor<Task> stop() {
        isStart = false;
        return this;
    }

    /**
     * 放置一个任务
     *
     * @param task 延時任务
     */
    public DelayedExecutor<Task> putTask(Task task) {
        delayedTasks.put(task);
        return this;
    }

    /**
     * 执行一次
     *
     * @param execute 执行过程
     * @return
     */
    public DelayedExecutor<Task> executeOnce(Runnable execute) {
        execute.run();
        return this;
    }
}
