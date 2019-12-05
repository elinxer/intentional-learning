package dddy.gin.deep_understanding_jvm.chapter07;

public class ClassInitializing03 {
    public static void main(String[] args) {
        new SonClass();
    }
}

class SuperClass{
    static {
        System.out.println("SuperClass Initialization");
    }
}

class SonClass extends SuperClass{
    static {
        System.out.println("SonClass Initialization");
    }
}
