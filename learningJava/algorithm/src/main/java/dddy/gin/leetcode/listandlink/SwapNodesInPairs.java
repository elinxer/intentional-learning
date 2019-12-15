package dddy.gin.leetcode.listandlink;

import java.util.List;

/**
 * url: https://leetcode-cn.com/problems/swap-nodes-in-pairs/
 *
 * @author gin
 * 给定 1->2->3->4, 你应该返回 2->1->4->3.
 */
public class SwapNodesInPairs {

    public static void main(String[] args) {
        SwapNodesInPairs swapNodesInPairs = new SwapNodesInPairs();
        ListNode head = Utils.getListNode(1, 9);
        Utils.print(head);
        head = swapNodesInPairs.swapPairs(head);
        Utils.print(head);

    }
    public ListNode swapPairs(ListNode head) {
        ListNode result = head;
        if (head != null && head.next != null) {
            result = head.next;
        }
        //当前交换点
        ListNode currentNode = head;
        //连接点
        ListNode preCurrentNode = null;


        while (currentNode!=null && currentNode.next!=null){
            //交换两个点的位置
            ListNode nextNode = currentNode.next.next;
            ListNode tempNode =currentNode.next;
            tempNode.next=currentNode;
            currentNode.next=nextNode;
            //连接两个当前点
            if (preCurrentNode!=null){
                preCurrentNode.next=tempNode;
            }
            preCurrentNode=currentNode;
            //移动当前点
            currentNode=nextNode;
        }
            return result;
    }

    /**
     * 递归
     * @param head
     * @return
     */
    public ListNode swapPairs1(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }


    public ListNode swapPairs2(ListNode head) {
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode temp = pre;
        while(temp.next != null && temp.next.next != null) {
            ListNode start = temp.next;
            ListNode end = temp.next.next;
            temp.next = end;
            start.next = end.next;
            end.next = start;
            temp = start;
        }
        return pre.next;
    }


}
