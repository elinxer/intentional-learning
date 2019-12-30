package dddy.gin.jvm.chapter07;


class Parent{
    public static int A = 1;
    static{
        A = 2;
    }
}

/**
 * @author gin
 */
public class ClassInitialization07 extends Parent{
    public static int B = A;

    public static void main(String[] args) {
        //2
        System.out.println(B);
    }
}


