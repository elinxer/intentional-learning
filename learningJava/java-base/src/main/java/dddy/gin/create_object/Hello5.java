package dddy.gin.create_object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;

@Data
@AllArgsConstructor
public class Hello5 implements Serializable{
    public String greet="hello";

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(buf);
        Hello5 hello5 = new Hello5("hello world");
        o.writeObject(hello5);
        //start clone
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buf.toByteArray()));
        Hello5 hello51 = (Hello5) in.readObject();
        System.out.println(hello5.getGreet()==hello51.getGreet()); //false
    }
}
