package com.noah.guava.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testTopN {

    public static void main(String[] args) {

        int[] nums = new int[]{2, 2, 2, 2, 2, 5, 6, 1};
        int N = 3;

        System.out.println(getTopNUniqueElements(nums, N));
    }

    public static List<Integer> getTopNUniqueElements(int[] nums, int N) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> window = new HashMap<>();
        int duplicateCount = 0;

        for (int i = 0; i < nums.length; i++) {
            int currentElement = nums[i];

            // 更新窗口中元素的计数
            window.put(currentElement, window.getOrDefault(currentElement, 0) + 1);

            // 如果窗口大小达到N，移除窗口最左侧的元素
            if (i >= N) {
                int leftElement = nums[i - N];
                int count = window.get(leftElement);
                if (count == 1) {
                    window.remove(leftElement);
                } else {
                    window.put(leftElement, count - 1);
                    duplicateCount--;
                }
            }

            // 如果当前元素的计数超过2，表示有重复元素
            if (window.get(currentElement) > 2) {
                duplicateCount++;
            }

            // 如果重复元素的数量超过2，不将当前元素添加到结果列表中
            if (duplicateCount <= 2) {
                result.add(currentElement);
            }
        }

        return result;
    }

}
