package com.example.mybatis.collection.list;

import lombok.NoArgsConstructor;

import java.util.LinkedList;

public class ExtLinkedList {

    Node first;
    Node last;
    int size;

    class Node<E> {
        Node prev;

        Object object;

        Node next;
    }

    public void add(Object object) {
        Node node = new Node();
        node.object = object;
        if (first == null)
            first = node;
        else {
            node.prev = last;
            node.next = node;
        }
        size++;
    }

    public void add(int index,Object object){

    }
}
