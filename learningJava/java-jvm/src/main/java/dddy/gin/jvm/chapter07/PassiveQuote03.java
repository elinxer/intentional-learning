package dddy.gin.jvm.chapter07;

/**
 * 常量在编译阶段会存入调用类的常量池中，
 * 本质上并没有直接引用到定义常量的类，
 * 因此不会触发定义类的初始化。
 */
public class PassiveQuote03 {
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLOWORLD);
    }
}

class ConstClass{
    static {
        System.out.println("ConstClass init!");
    }
    public static final String HELLOWORLD = "hello world";
}
