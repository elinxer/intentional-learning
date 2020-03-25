package dddy.gin.leetcode.explore.easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/25/
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * @author gin
 */
public class Array05 {
    /**
     * 暴力法
     *
     * @param nums
     * @return
     */
    public static int singleNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            boolean flag = false;
            for (int j = 0; j < nums.length; j++) {
                if (i != j && nums[i] == nums[j]) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return nums[i];
            }
        }
        return nums[0];
    }

    /**
     * 哈希法
     *
     * @param nums
     * @return
     */
    public static int singleNumber2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            if (set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }
        return set.stream().findFirst().get();
    }

    /**
     * 排序法
     *
     * @param nums
     * @return
     */
    public static int singleNumber3(int[] nums) {
        if (nums.length < 2) {
            return nums[0];
        }

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
            i++;
        }
        return nums[nums.length - 1];
    }

    /**
     * 异或，因为异或时满足交换律
     *
     * @param nums
     * @return
     */
    public static int singleNumber4(int[] nums) {
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            result = result ^ nums[i];
        }
        return result;
    }

    /**
     * 数学思维 2（a+b+c+d）-(a+b+c+d+a+b+d)=c
     *
     * @param nums
     * @return
     */
    public static int singleNumber5(int[] nums) {
        Set<Integer> set = new HashSet();
        int sum = 0;
        for (int n : nums) {
            sum += n;
            set.add(n);
        }
        int sum2 = 0;
        for (int s : set) {
            sum2 += s;
        }

        return (2 * sum2 - sum);
    }

    public static void main(String[] args) {
        int[] nums = {4, 1, 2, 1, 2};
        System.out.println(singleNumber5(nums));
    }

}
