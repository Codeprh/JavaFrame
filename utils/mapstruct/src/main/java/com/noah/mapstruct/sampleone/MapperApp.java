package com.noah.mapstruct.sampleone;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperApp {

    public static void main(String[] args) {
        //mapstructUseCase();
        //trainActivityConfig();
        trainActivityBeanMapping();
    }


    /**
     * use的使用例子
     */
    private static void mapstructUseCase() {
        Activity activity = new Activity();

        activity.setMark1("这是po的mark");
        activity.setLanguage("这是language");
        activity.setJson("{\"type\":100}");

        ActivityDto activityDto = ActivityMapper.INSTANCE.toActivityDto(activity);

        log.info(activityDto + "");
    }

    private static void trainActivityConfig() {

        Activity activity = new Activity();

        activity.setMark1("这是po的mark");
        activity.setLanguage("这是language");
        activity.setJson("{\"type\":100}");
        activity.setActivityType(100);

        TrainActivity trainActivity = ActivityMapper.INSTANCE.toTrainActivity(activity);
        log.info(trainActivity + "");

    }

    private static void trainActivityBeanMapping() {

        Activity activity = new Activity();

        activity.setMark1("这是po的mark");
        activity.setLanguage("这是language");
        activity.setJson("{\"type\":100}");
        activity.setActivityType(100);

        TrainActivity trainActivity = ActivityMapper.INSTANCE.toTrainActivityLess(activity);
        log.info(trainActivity + "");

    }


}
