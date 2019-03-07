package com.example.mybatis.collection.list;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class Test {
    public static void main(String[] args) {
      ExtList list = new ExtArrayList(3);
      list.add(1);
      list.add(2);
      list.add(2);
      list.add(4);

      for(int i = 0; i <list.getSize(); i++){
          System.out.println(list.get(i));
      }


    }
}
