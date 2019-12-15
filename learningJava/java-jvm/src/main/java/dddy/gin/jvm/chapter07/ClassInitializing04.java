package dddy.gin.jvm.chapter07;

public class ClassInitializing04 {
    static {
        System.out.println("MainClass Initialization");
    }

    public static void main(String[] args) {
        new NotMainClass();
    }
}

class NotMainClass {
    static {
        System.out.println("NotMainClass Initialization");
    }
}