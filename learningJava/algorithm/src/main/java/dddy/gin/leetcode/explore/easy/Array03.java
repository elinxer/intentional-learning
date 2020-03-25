package dddy.gin.leetcode.explore.easy;

import static dddy.gin.leetcode.explore.easy.Const.C2;

/**
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/23/
 *
 * @author gin
 */
public class Array03 {
    /**
     * 暴力法
     * 需要注意的是 采用暴力法第一位的开始是最后一位，结束操作的是倒数最后一位，
     * 需要遍历 k 次 每次移动 nums.length 位，能达到原地算法 并且 空间复制度也是O(1)
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }
        for (int i = 0; i < k; i++) {
            int p = nums[nums.length - 1];

            for (int j = 0; j < nums.length; j++) {
                int temp = nums[j];
                nums[j] = p;
                p = temp;
            }
        }

    }

    /**
     * 搬移法，非原地算法，非O(1)
     * 思路是新建一个同等大小的数组将数据搬移过去
     * 利用 i+k 来进行环形操作
     *
     * @param nums
     * @param k
     */
    public void rotate2(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int[] nums2 = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            nums2[(i + k) % (nums.length)] = nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nums2[i];
        }

    }

    public void rotate3(int[] nums, int k) {
        //真实的偏移量，特别是，当 k > n 时,能节省一周期。get 到了
        k = k % nums.length;
        // 最有意思的一点。因为每个数据都需要换位置，只要换 nums.length 就能停止。
        // 如果不采用这种记录 count 将会特别复杂。
        int count = 0;
        // 这里的start 第从第一位开始，如果一次调整 k ,重新回到 start，说明完成一次换位
        // 如果不能达到 count = nums.length 说明还有数据未移动，这时 start 要加 1。重复以上动作
        // 直到 count = nums.length， 完成全部换位
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                //环形换位的一个技巧，利用取余 。(当前位置+需要偏移的距离)%整个环形的长度
                int next = (current + k) % nums.length;
                //将下一个的数存到 temp ,上一个的值占用下一个值得位置,然后再把 temp 得值给 perv 看成下上一个
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                //移动下一个指针
                current = next;
                //移动数加 1
                count++;
            } while (start != current);
        }
    }

    public void rotate4(int[] nums, int k) {
        //真实偏移量
        k = k % nums.length;
        //换位次数
        int count = 0;

        for (int start = 0; count < nums.length; start++) {
            //当前要换位的位置指针
            int current = start;
            //要换位置的前一个值
            int perv = nums[start];

            do {
                //后一个换位的值
                int next = (current + k) % nums.length;
                //将
                int temp = nums[next];
                nums[next] = perv;
                perv = temp;

                current = next;
                count++;


            } while (current != start);
        }

    }

    public void rotate5(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    /**
     * 反转方法
     *
     * @return
     */
    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[end];
            nums[end] = nums[start];
            nums[start] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        Array03 a = new Array03();
        int[] prices = {1, 2, 3, 4, 5, 6};
        a.rotate5(prices, 2);
        for (int p : prices) {
            System.out.println(p);
        }
    }
}
