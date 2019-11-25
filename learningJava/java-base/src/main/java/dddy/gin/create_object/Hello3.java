package dddy.gin.create_object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hello3 {
    public String greet="hello";
    public static void main(String[] args) throws Exception {

        Class<Hello3> hello3Class = Hello3.class;
        Hello3 hello3 = hello3Class
                .getConstructor(String.class)
                .newInstance("hello world");
        System.out.println(hello3.getGreet()); // hello world
    }
}
