package com.noah.guava.str;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class SplitterTest {

    public static void main1(String[] args) {
        List<String> split = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .splitToList("foo,bar,,   qux");
        System.out.println(split);
    }

    public static void main2(String[] args) {

        for (int i = 0; i < 10; i++) {
            // test
            //find the first element

        }
    }

    public static void main(String[] args) {
        List<String> split = Splitter.on("],")
                .trimResults()
                .omitEmptyStrings()
                .splitToList("[{\"index\":1,\"windowsSize\":5,\"maxSameSize\":2,\"scatterType\":\"CATEGORY\",\"topX\":60,\"channelId\":20},{\"index\":2,\"windowsSize\":5,\"maxSameSize\":1,\"scatterType\":\"MIXED_BRAND_THEME\",\"topX\":60,\"channelId\":20,\"mixedBrandNum\":2}],[{\"index\":1,\"windowsSize\":5,\"maxSameSize\":1,\"scatterType\":\"MIXED_BRAND_THEME\",\"topX\":60,\"channelId\":21,\"mixedBrandNum\":2}],[{\"index\":1,\"windowsSize\":5,\"maxSameSize\":1,\"scatterType\":\"MIXED_BRAND_THEME\",\"topX\":60,\"channelId\":22,\"mixedBrandNum\":2}],[{\"index\":1,\"windowsSize\":5,\"maxSameSize\":1,\"scatterType\":\"MIXED_BRAND_THEME\",\"topX\":60,\"channelId\":23,\"mixedBrandNum\":2}],[{\"index\":1,\"windowsSize\":5,\"maxSameSize\":1,\"scatterType\":\"MIXED_BRAND_THEME\",\"topX\":60,\"channelId\":24,\"mixedBrandNum\":2}],[{\"index\":1,\"windowsSize\":5,\"maxSameSize\":1,\"scatterType\":\"MIXED_BRAND_THEME\",\"topX\":60,\"channelId\":47,\"mixedBrandNum\":2}],[{\"index\":1,\"windowsSize\":5,\"maxSameSize\":1,\"scatterType\":\"MIXED_BRAND_THEME\",\"topX\":60,\"channelId\":55,\"mixedBrandNum\":2}],[{\"index\":1,\"windowsSize\":5,\"maxSameSize\":1,\"scatterType\":\"MIXED_BRAND_THEME\",\"topX\":60,\"channelId\":128,\"mixedBrandNum\":2}],[{\"index\":1,\"windowsSize\":5,\"maxSameSize\":1,\"scatterType\":\"MIXED_BRAND_THEME\",\"topX\":60,\"channelId\":136,\"mixedBrandNum\":2}]");
        List<String> result = Lists.newArrayList();
        for (String s : split) {

            if (StrUtil.endWithIgnoreCase(s, "}")) {
                s = s + "]";
            }
            result.add(s);
        }
    }
}
