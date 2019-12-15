package dddy.gin.leetcode.listandlink;

/**
 * @author gin
 */
public class Utils {
    /**
     * 获取模拟数据的点
     * @param start
     * @param end
     * @return
     */
    protected static ListNode getListNode(int start,int end) {
        assert start>0;
        assert end>start;
                //记录第一个点
        ListNode head = new ListNode(1);
        //记录上一个点
        ListNode pre = head;
        for (int i = start+1; i <= end; i++) {
            ListNode node = new ListNode(i);
            pre.next = node;
            pre = node;
        }
        return head;
    }

    //
    /**
     * 打印链表
     * @param head
     * @return
     */
    protected static void print(ListNode head) {
        String s = "";
        ListNode preNode = head;
        while (preNode != null) {
            s += preNode.val + "->";
            preNode = preNode.next;
        }
        s += "NULL";
        System.out.println(s);
    }
}
