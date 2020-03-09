package vc.coding.jvm;

/**
 * 资源帮助类
 *
 * @author HeTongHao
 * @since 2020/3/9 16:04
 */
public class SourceUtils {
    public static String convertMb(long b) {
        return b / (double) 1024 / (double) 1024 + "Mb";
    }
}
