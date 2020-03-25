package dddy.gin.leetcode.explore.easy;

import java.util.*;

/**
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/26/
 *
 * @author gin
 */
public class Array06 {
    /**
     * 暴力法：双重循环 ，登记重复位置
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect(int[] nums1, int[] nums2) {
        //保证 nums1 < nums2
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        Set<Integer> set = new HashSet();
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                if (nums1[i] == nums2[j]) {
                    if (set.contains(j)) {
                        continue;
                    } else {
                        set.add(j);
                        break;
                    }
                }
            }
        }
        int[] result = new int[set.size()];
        int i = 0;
        for (int s : set) {
            result[i] = nums2[s];
            i++;
        }
        return result;

    }

    /**
     * 哈希法：用 hashmap 记录 较大数组的 key = nums[n] ,value= count(nums[n]) 的值。
     * 然后遍历另一个数组，如果，map 中存在不为零的值就添加到结果，然后map中的 count-1
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect2(int[] nums1, int[] nums2) {
        List<Integer> temp = new ArrayList<>();
        Map<Integer, Integer> map1 = new HashMap<>(nums1.length);
        for (int i = 0; i < nums1.length; i++) {
            if (map1.containsKey(nums1[i])) {
                map1.put(nums1[i], map1.get(nums1[i]) + 1);
            } else {
                map1.put(nums1[i], 1);
            }
        }
        for (int i = 0; i < nums2.length; i++) {
            if (map1.containsKey(nums2[i]) && map1.get(nums2[i]) > 0) {
                temp.add(nums2[i]);
                map1.put(nums2[i], map1.get(nums2[i]) - 1);
            }
        }

        int[] result = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            result[i] = temp.get(i);
        }
        return result;
    }

    /**
     * hashmap 官方版
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect3(int[] nums1, int[] nums2) {
        // 这个方法高！佩服，保证了 nums2 的大小一定大于等于 nums1
        if (nums1.length > nums2.length) {
            return intersect3(nums2, nums1);
        }
        HashMap<Integer, Integer> m = new HashMap<>(nums1.length);
        for (int n : nums1) {
            //利用了 getOrDefault 方法 简洁
            m.put(n, m.getOrDefault(n, 0) + 1);
        }
        int k = 0;
        for (int n : nums2) {
            int cnt = m.getOrDefault(n, 0);
            if (cnt > 0) {
                nums1[k++] = n;
                m.put(n, cnt - 1);
            }
        }
        return Arrays.copyOfRange(nums1, 0, k);
    }

    /**
     * 排序
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect4(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i=0,j=0,k=0;

        while (i<nums1.length && j<nums2.length){
            if (nums1[i]<nums2[j]){
                i++;
            }else if (nums1[i] > nums2[j]){
                j++;
            }else {
                nums1[k++] = nums1[i++];
                j++;
            }
        }
        return Arrays.copyOf(nums1,k);

    }


    public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 1}, nums2 = {2, 2};
        int[] nums = intersect4(nums1, nums2);
        for (int n : nums) {
            System.out.println(n);
        }
    }
}
