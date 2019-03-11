package com.example.mybatis.collection.list;

import lombok.NoArgsConstructor;

import java.util.LinkedList;

public class ExtLinkedList {
    // 第一个元素
    Node first;
    // 最后一个元素
    Node last;
    // 实际存放在长度
    int size;

    class Node<E> {
        Node prev;

        Object object;

        Node next;
    }

    public void add(Object object) {
        // 创建新的节点
        Node node = new Node();
        // 节点内容
        node.object = object;
        if (first == null)
            first = node;
        else {
            // 存放上一个节点内容
            node.prev = last;
            // 设置上一个节点的next为当前节点
            node.next = node;
        }
        last = node;
        size++;
    }

    public void add(int index, Object object) {
        //检查Index
        checkPositionIndex(index);
        // 1.循环遍历到当前index位置Node
        // 2.新增当前节点
        Node newNode = new Node();
        newNode.object = object;
        // 获取原来的节点
        Node oldNode = getNode(index);
        // 获取原来上一个节点
        Node oldNodePrev = oldNode.prev;

        // 4.新增节点的上一个还是当前Node节点的 上一个节点,下一个就是原来的节点
        // 原来上一个节点变为当前节点
        oldNode.prev = newNode;
        if (null == oldNodePrev)
            first = newNode;
        else
            // 原来上一个节点的下一个节点变为当前节点
            oldNodePrev.next = newNode;
        // 新节点的下一个节点为原来节点
        newNode.next  = oldNode;
        size++;
    }

    public Node getNode(int index) {
        checkPositionIndex(index);
        Node node = null;
        if (first != null) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    public void remove(int index){
        //检查Index
        checkPositionIndex(index);
        Node node = getNode(index);
        if(null != node){
            Node prevNode = node.prev;
            Node nextNode = node.next;
            // 设置上一个节点的next为当前删除节点的next
            if(null != prevNode)
                prevNode.next = nextNode;
            // 判断是否是最后一个节点
            if(null != nextNode)
                nextNode.prev = prevNode;
        }
        size--;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

}
