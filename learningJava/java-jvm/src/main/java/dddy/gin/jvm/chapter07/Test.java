package dddy.gin.jvm.chapter07;

interface B{
     String getStrB();
}

 abstract class A implements B{
    @Override
    public abstract String getStrB();
}
public class Test extends A {
    public static void main(String[] args) {
    }


    @Override
    public String getStrB() {
        return "null";
    }
}
