package dddy.gin.deep_understanding_jvm.chapter07;

import java.lang.reflect.Constructor;

public class ClassInitializing02 {
    public static void main(String[] args) throws Exception {
        Class<?> c = ReflectPackage.class;
        Constructor constructor = c.getConstructor();
        constructor.newInstance();
    }
}

class ReflectPackage {
    public ReflectPackage() {
    }

    static {
        System.out.println("Initialize ReflectPackage.class");
    }
}