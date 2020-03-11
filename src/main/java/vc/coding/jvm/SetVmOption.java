package vc.coding.jvm;

/**
 * -verbose:class 输出jvm载入类的相关信息，当jvm报告说找不到类或者类冲突时可此进行诊断。
 * -verbose:gc  输出每次GC的相关情况。
 * -verbose:jni 输出native方法调用的相关情况，一般用于诊断jni调用错误信息。
 *
 * -Xprof 跟踪正运行的程序，并将跟踪数据在标准输出输出；适合于开发环境调试。
 *
 * -Xmx8m 最大内存8m
 * -Xms8m 初始内存8m
 * -Xmn200m 设置年轻代大小为200M。整个堆大小=年轻代大小 + 年老代大小 + 持久代大小。持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。
 * -Xss128k 设置每个线程的堆栈大小。JDK5.0以后每个线程堆栈大小为1M，以前每个线程堆栈大小为256K。更具应用的线程所需内存大小进行调整。在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在3000~5000左右。
 * -XX:+PrintGCDetails 打印GC详情
 * -XX:+HeapDumpOnOutOfMemoryError dump 内存溢出异常
 * -XX:MetaspaceSize=10m 元空间大小=10m
 * -XX:MaxMetaspaceSize=10m 最大元空间大小=10m
 * -XX:+UseSerialGC 使用串行收集器
 * -XX:+UseParallelGC 使用并行收集器
 * -XX:+TraceClassLoading 跟踪类加载
 *
 *
 * jdk13:-Xlog:gc*
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
