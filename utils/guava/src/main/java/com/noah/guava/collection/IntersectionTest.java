package com.noah.guava.collection;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class IntersectionTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ConditionTagResponse implements java.io.Serializable {

        private static final long serialVersionUID = 1L;
        private Long tagId;
    }

    public static void main(String[] args) {

        List<ConditionTagResponse> rl1 = Lists.newArrayList();
        ConditionTagResponse r1 = new ConditionTagResponse();
        r1.setTagId(new Long(1L));

        rl1.add(r1);

        List<ConditionTagResponse> rl2 = Lists.newArrayList();

        ConditionTagResponse r2 = new ConditionTagResponse();
        r2.setTagId(new Long(1L));
        rl2.add(r2);

        ConditionTagResponse r3 = new ConditionTagResponse();
        r3.setTagId(new Long(3L));
        rl2.add(r3);

        Collection<ConditionTagResponse> intersection = CollectionUtil.intersection(rl1, rl2);
        Collection<ConditionTagResponse> intersection1 = CollectionUtils.intersection(rl1, rl2);

        Set<ConditionTagResponse> srl1 = Sets.newHashSet(rl1);
        Set<ConditionTagResponse> srl2 = Sets.newHashSet(rl2);

        Set<ConditionTagResponse> filter = Sets.filter(srl1, srl2::contains);

        System.out.println(intersection);
        System.out.println(intersection1);
        System.out.println(filter);

    }

}
