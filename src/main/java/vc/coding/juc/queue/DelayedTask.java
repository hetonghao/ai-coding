package vc.coding.juc.queue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时任务
 *
 * @author HeTongHao
 * @since 2020/3/6 22:06
 */
public class DelayedTask<T> implements Delayed {
    /**
     * 引用
     */
    private T reference;
    /**
     * 到期时间
     */
    private long expireTime;

    public DelayedTask(T reference, long delay, TimeUnit timeUnit) {
        this.reference = reference;
        this.expireTime = System.currentTimeMillis() + timeUnit.toMillis(delay);
    }

    public DelayedTask(T reference, LocalDateTime expireTime) {
        this.reference = reference;
        this.expireTime = expireTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public T getReference() {
        return reference;
    }

    public void setReference(T reference) {
        this.reference = reference;
    }

    public long getExpireTime() {
        return expireTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return this.expireTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed delayed) {
        return Long.compare(this.expireTime, ((DelayedTask) delayed).getExpireTime());
    }
}
