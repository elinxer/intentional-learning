package dddy.gin.jvm.chapter07;

/**
 * 被动引用示例一
 * 通过子类引用父类的静态变量，不会导致子类初始
 */
public class PassiveQuote01 {
    public static void main(String[] args) {
        System.out.println(SonClass01.value);
    }
}

class SonClass01 extends SuperClass01{
    static {
        System.out.println("SubClass init!");
    }
}

class SuperClass01{
    static {
        System.out.println("SuperClass init!");
    }
    public static int value = 123;
}
