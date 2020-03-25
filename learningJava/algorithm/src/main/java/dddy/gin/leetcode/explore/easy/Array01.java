package dddy.gin.leetcode.explore.easy;

/**
 * 题目详情: https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/21/
 *
 * @author gin
 */
public class Array01 {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        //前一个值
        int pre = nums[0];
        //存储尾部指针
        int p = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != pre) {
                pre = nums[i];
                p++;
                nums[p] = pre;
            }
        }
        return p + 1;
    }
}
