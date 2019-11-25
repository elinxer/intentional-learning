package dddy.gin.create_object;

import lombok.Data;

@Data
public class Hello4 implements Cloneable {
    public String greet="hello";

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) throws Exception {
        Hello4 hello4 = new Hello4();
        hello4.setGreet("hello world");
        Hello4 hello41 = (Hello4) hello4.clone();
        System.out.println(hello4.getGreet()==hello41.getGreet()); //true
    }
}
