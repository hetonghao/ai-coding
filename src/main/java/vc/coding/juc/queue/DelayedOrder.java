package vc.coding.juc.queue;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时对象
 *
 * @author HeTongHao
 * @since 2020/3/6 22:06
 */
@Data
public class DelayedOrder implements Delayed {
    /**
     * 记录id
     */
    private Long id;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 到期时间
     */
    private LocalDateTime expireTime;

    public DelayedOrder(Long id, long delay, TimeUnit timeUnit) {
        this.id = id;
        this.createTime = LocalDateTime.now();
        this.expireTime = this.createTime.plusNanos(timeUnit.toNanos(delay));
    }

    public DelayedOrder(Long id, LocalDateTime expireTime) {
        this.id = id;
        this.createTime = LocalDateTime.now();
        this.expireTime = expireTime;
    }

    /**
     * 将LocalDateTime转为时间戳
     *
     * @param localDateTime
     * @return
     */
    public long parseToTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return parseToTimestamp(this.expireTime) - parseToTimestamp(LocalDateTime.now());
    }

    @Override
    public int compareTo(Delayed delayed) {
        return this.expireTime.compareTo(((DelayedOrder) delayed).getExpireTime());
    }
}
