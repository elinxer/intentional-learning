package dddy.gin.create_object;

import lombok.Data;

@Data
public class Hello {
    public String greet;

    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.setGreet("hello world");
        System.out.println(hello.getGreet());
    }
}
