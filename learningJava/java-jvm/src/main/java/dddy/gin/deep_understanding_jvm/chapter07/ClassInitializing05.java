package dddy.gin.deep_understanding_jvm.chapter07;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ClassInitializing05 {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle handle;
        handle = lookup.findStatic(REFGetStatic.class, "getS", MethodType.methodType(String.class, String.class));
        handle.invoke("hello world");
    }
}

class REFGetStatic {
    public static String getS(String s) {
        return s;
    }

    static {
        System.out.println("REFGetStatic Initialization");
    }
}