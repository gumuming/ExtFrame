package com.example.mybatis.collection.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtArrayList implements ExtList {

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    transient Object[] elementData;

    /**
     * Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    private int size;

    public ExtArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ExtArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    @Override
    public void add(Object object) {
        ensureExplicitCapacity(size + 1);
        elementData[size++] = object;
    }

    @Override
    public void add(int index, Object object) {
        rangeCheckForAdd(index);
        ensureExplicitCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1,
                size - index);
        elementData[index] = object;
        size++;
    }

    @Override
    public Object remove(int index) {
        rangeCheck(index);
        Object oldValue = elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    @Override
    public boolean remove(Object object) {
        for(int i = 0; i <size; i++){
            if(elementData[i] == object){
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Object get(int index) {
        rangeCheck(index);
        return elementData[index];
    }

    private void ensureExplicitCapacity(int minCapacity) {
        if (size == elementData.length) {
            int oldLength = elementData.length;
            int newLength = oldLength >> 1;
            if (newLength < minCapacity)
                newLength = minCapacity;
            elementData = Arrays.copyOf(elementData, newLength);

        }
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

}
