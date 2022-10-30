package com.noah.designpatterns.selector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class ContextSelectorTest {

    @Test
    public void pipelineTest() {
        QueryParam param = new QueryParam();
        param.setTargetId(10L);
        param.setQueryType(QueryType.other);

        List<Integer> list = ContextSelector.init(param, this::idType)
                .register(() -> new ContextSelector.Context<>(p -> QueryType.id.equals(p.queryType), this::idType))
                .register(() -> new ContextSelector.Context<>(p -> QueryType.time.equals(p.queryType), this::timeType))
                .register(() -> new ContextSelector.Context<>(p -> QueryType.other.equals(p.queryType), this::otherType))
                .pipeline();

        System.out.println(list);
    }

    @Test
    public void executeTest() {
        QueryParam param = new QueryParam();
        param.setTargetId(10L);
        param.setQueryType(null);

        Integer execute = (Integer) ContextSelector.init(param)
                .register(() -> new ContextSelector.Context<>(p -> QueryType.id.equals(p.queryType), this::idType))
                .register(() -> new ContextSelector.Context<>(p -> QueryType.time.equals(p.queryType), this::timeType))
                .register(() -> new ContextSelector.Context<>(p -> QueryType.other.equals(p.queryType), this::otherType))
                .execute();

        System.out.println(execute);
    }

    public Integer idType(QueryParam queryParam) {
        log.info("根据id来查询");
        return 1;
    }

    public Integer timeType(QueryParam queryParam) {
        log.info("根据time来查询");
        return 2;
    }

    public Integer otherType(QueryParam queryParam) {
        log.info("根据other来查询");
        return 3;
    }

    public Integer nullType(QueryParam queryParam) {
        log.info("根据null来查询");
        return null;
    }


    @Data
    class QueryParam {
        private Long targetId;
        private QueryType queryType;
    }

    @Getter
    @AllArgsConstructor
    enum QueryType {
        id, time, other
    }
}