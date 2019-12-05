package dddy.gin.deep_understanding_jvm.chapter07;

public class ClassInitializing01 {

    public static void main(String[] args) {
        //new 关键字
        NewInitialization newInit = new NewInitialization();
        //getstatic
        System.out.println(GetStatic.S);
        //putstatic
        PutStatic.S = "getStatic initialization";
        //invokestatic
        InvokeStatic.getTime();

    }

}

class NewInitialization{
    static {
        System.out.println("new initialization");
    }
}

class GetStatic{
    static String S = "GetStatic.class";

    static {
        System.out.println("getStatic.class initialization");
    }
}

class PutStatic{
    static String S;

    static {
        System.out.println("PutStatic.class initialization");
    }
}

class InvokeStatic{
    static long getTime(){
        return System.currentTimeMillis();
    }

    static {
        System.out.println("InvokeStatic.class initialization");
    }
}