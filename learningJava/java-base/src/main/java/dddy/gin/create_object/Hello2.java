package dddy.gin.create_object;

import lombok.Data;

@Data

public class Hello2 {
    public String greet="hello";
    public static void main(String[] args) throws Exception {
        Hello2 hello2 = new Hello2();
        hello2.setGreet("hello world");
        System.out.println(hello2.getGreet()); // hello world

        Class<Hello2> hello2Class = (Class<Hello2>) hello2.getClass();
        Hello2 hello21 = hello2Class.newInstance();
        System.out.println(hello21.getGreet()); // hello
    }
}
