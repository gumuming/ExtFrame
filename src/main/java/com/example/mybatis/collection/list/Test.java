package com.example.mybatis.collection.list;

import lombok.extern.slf4j.Slf4j;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

@Slf4j
public class Test {
    public static void main(String[] args) {
    /*  ExtList list = new ExtArrayList(3);
      list.add(1);
      list.add(2);
      list.add(2);
      list.add(4);

      for(int i = 0; i <list.getSize(); i++){
          System.out.println(list.get(i));
      }*/
        ExtLinkedList linkedList = new ExtLinkedList();

        linkedList.add(44);
        linkedList.add(42);
        linkedList.add(3);
        linkedList.add(33);
        linkedList.add(55);
        log.info("size:{}", linkedList.size);
        for(int i = 0; i<linkedList.size;i++){
            log.info("size:{}",linkedList.getNode(i));
        }

    }
}
