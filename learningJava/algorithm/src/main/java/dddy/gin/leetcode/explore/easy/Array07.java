package dddy.gin.leetcode.explore.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/plus-one/comments/
 * @author gin
 */
public class Array07 {
    /**
     * 暴力法
     * @param digits
     * @return
     */
    public static int[] plusOne(int[] digits) {

        for (int i = digits.length-1; i >=0 ; i--) {
            if (digits[i]!=9){
                digits[i]=digits[i]+1;
                return digits;
            }else {
                digits[i]=0;
            }
        }

        int[] tmp = new int[digits.length+1];
        tmp[0]=1;
        digits=tmp;

        return digits;
    }

    public static void main(String[] args) {
        int[] digits = {9};
        int[] result = plusOne(digits);
        for (int r: result){
            System.out.println(r);
        }

    }
}
