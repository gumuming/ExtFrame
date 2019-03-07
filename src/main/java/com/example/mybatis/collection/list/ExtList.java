package com.example.mybatis.collection.list;

import java.util.Objects;

public interface ExtList {
    void add(Object object);

    void add(int index, Object object);

    Object remove(int index);

    boolean remove(Object object);

    int getSize();

    Object get(int index);
}
