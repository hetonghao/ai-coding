package vc.coding.jvm;

/**
 * -Xmx8m 最大内存8m
 * -Xms8m 初始内存8m
 * -XX:+PrintGCDetails 打印GC详情
 * -XX:+HeapDumpOnOutOfMemoryError dump 内存溢出异常
 * -XX:MetaspaceSize=10m 元空间大小=10m
 * -XX:MaxMetaspaceSize=10m 最大元空间大小=10m
 * -XX:UseSerialGC 使用串行收集器
 *
 * @author HeTongHao
 * @since 2020/3/9 16:02
 */
public class SetVmOption {
    byte[] bytes = new byte[10 * 1024 * 1024];

    public static void main(String[] args) {
        System.out.println("初始总内存:" + SourceUtils.convertMb(Runtime.getRuntime().totalMemory()));
        System.out.println("最大内存:" + SourceUtils.convertMb(Runtime.getRuntime().maxMemory()));
        while (true) {
            new SetVmOption();
        }
    }
}
