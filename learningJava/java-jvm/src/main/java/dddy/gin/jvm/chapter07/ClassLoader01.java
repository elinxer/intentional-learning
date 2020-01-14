package dddy.gin.jvm.chapter07;

import java.io.IOException;
import java.io.InputStream;

/**
 * 不同类加载器对instanceof关键字的影响
 * @author gin
 */
public class ClassLoader01 {
    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);

                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj = myLoader
                .loadClass("dddy.gin.jvm.chapter07.ClassLoader01")
                .getDeclaredConstructor()
                .newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof dddy.gin.jvm.chapter07.ClassLoader01);
    }
}
