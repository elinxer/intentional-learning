package dddy.gin.jvm.chapter07;

/**
 * 通过数组定义来引用类，不会触发此类的初始化
 */
public class PassiveQuote02 {
    public static void main(String[] args) {
        SuperClass02[] sca = new SuperClass02[10];
    }
}

class SuperClass02{
    static {
        System.out.println("SuperClass02 init");
    }
}

