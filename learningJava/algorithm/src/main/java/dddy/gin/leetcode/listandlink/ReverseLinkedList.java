package dddy.gin.leetcode.listandlink;


/**
 * url:https://leetcode-cn.com/problems/reverse-linked-list/
 * 反转一个单链表
 *  输入: 1->2->3->4->5->NULL
 *  输出: 5->4->3->2->1->NULL
 * @author gin
 */
public class ReverseLinkedList {
    public static void main(String[] args) {
        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();
        ListNode head = Utils.getListNode(1,5);
        Utils.print(head);
        head = reverseLinkedList.reverseList(head);
        Utils.print(head);

    }

    /**
     * 解题方法
     * @param head
     * @return
     */
    private ListNode reverseList(ListNode head) {
        //前一个点
        ListNode preNode = null;
        //当前点
        ListNode currentNode = head;

        while (currentNode != null) {
            ListNode temp = currentNode.next;
            currentNode.next = preNode;
            preNode = currentNode;
            currentNode = temp;
        }
        return preNode;
    }



}

