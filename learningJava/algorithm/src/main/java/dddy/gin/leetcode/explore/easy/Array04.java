package dddy.gin.leetcode.explore.easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/24/
 *
 * @author gin
 */
public class Array04 {
    /**
     * 暴力法
     * 逻辑正确，但是超时了
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsDuplicate2(int[] nums) {
        Set set = new HashSet<>(nums.length);
        for (int n : nums) {
            if (set.contains(n)) {
                return true;
            }
            set.add(n);
        }
        return false;
    }

    /**
     * 排序法
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate3(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Array04 a = new Array04();
        int[] nums = {0};
        System.out.println(a.containsDuplicate2(nums));
    }
}
