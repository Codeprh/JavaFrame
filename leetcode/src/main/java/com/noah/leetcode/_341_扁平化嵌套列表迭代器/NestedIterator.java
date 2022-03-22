package com.noah.leetcode._341_扁平化嵌套列表迭代器;

import java.util.*;
import java.util.stream.Collectors;

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 * <p>
 * // @return true if this NestedInteger holds a single integer, rather than a nested list.
 * public boolean isInteger();
 * <p>
 * // @return the single integer that this NestedInteger holds, if it holds a single integer
 * // Return null if this NestedInteger holds a nested list
 * public Integer getInteger();
 * <p>
 * // @return the nested list that this NestedInteger holds, if it holds a nested list
 * // Return empty list if this NestedInteger holds a single integer
 * public List<NestedInteger> getList();
 * }
 */
interface NestedInteger {

    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger();

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger();

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    public List<NestedInteger> getList();
}

public class NestedIterator implements Iterator<Integer> {

    private List<Integer> list = new LinkedList<>();
    private Iterator<Integer> iterator;

    Deque<Iterator<NestedInteger>> dq = new LinkedList<>();

    public NestedIterator(List<NestedInteger> nestedList) {
        iterate(nestedList);
        iterator = list.iterator();
    }

    /**
     * 预加载所有的数据
     *
     * @param nestedList
     */
    public void iterate(List<NestedInteger> nestedList) {

        //java8
        // todo:请教大佬

        List<Integer> collect = nestedList.stream().flatMap(i -> i.getList().stream()).map(i -> i.getInteger()).filter(Objects::nonNull).collect(Collectors.toList());
        List<Integer> integerList = nestedList.stream().map(i -> i.getInteger()).filter(Objects::nonNull).collect(Collectors.toList());

        list.addAll(collect);
        list.addAll(integerList);

        //rule
        for (NestedInteger n : nestedList) {

            if (n.isInteger()) {
                list.add(n.getInteger());
            } else {
                iterate(n.getList());
            }
        }
    }

    @Override
    public Integer next() {
        return iterator.next();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
