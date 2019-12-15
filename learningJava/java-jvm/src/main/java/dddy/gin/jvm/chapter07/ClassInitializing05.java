package dddy.gin.jvm.chapter07;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author gin
 */
public class ClassInitializing05 {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle handle;
        handle = lookup.findStatic(RefGetStatic.class, "getStr", MethodType.methodType(String.class, String.class));
        handle.invoke("hello world");
    }
}

class RefGetStatic {
    public static String getStr(String s) {
        return s;
    }

    static {
        System.out.println("REFGetStatic Initialization");
    }
}