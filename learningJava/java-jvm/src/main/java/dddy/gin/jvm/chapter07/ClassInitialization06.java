package dddy.gin.jvm.chapter07;

/**
 * @author gin
 */
public class ClassInitialization06 {
    static {
        i = 2;
        //向前引用错误
        //System.out.println(i);
    }
    static int i=1;

    public static void main(String[] args) {
        // 1 原因是 i准阶段是0,经过静态数据块赋值 所以是2, 最后是通过赋值为1，所以输出为1
        System.out.println(i);
    }
}
