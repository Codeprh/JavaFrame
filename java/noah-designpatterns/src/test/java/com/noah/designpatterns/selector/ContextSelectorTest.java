package com.noah.designpatterns.selector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ContextSelectorTest {

    @Test
    public void pipelineTest() {

    }

    @Test
    public void executeTest() {
        QueryParam param = new QueryParam();
        param.setTargetId(10L);
        param.setQueryType(QueryType.id);

        ContextSelector.init(param)
                .register(() -> new ContextSelector.Context<>(p -> p.queryType.equals(QueryType.id), this::idType))
                .register(() -> new ContextSelector.Context<>(p -> p.queryType.equals(QueryType.time), this::timeType))
                .register(() -> new ContextSelector.Context<>(p -> p.queryType.equals(QueryType.other), this::otherType))
                .execute();
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