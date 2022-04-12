package com.noah.mapstruct.sampleone.use;

import com.noah.mapstruct.sampleone.Activity;
import com.noah.mapstruct.sampleone.ActivityDto;
import com.noah.mapstruct.sampleone.TrainActivity;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper
@Component
@Slf4j
public class ActivityAfterMapping {

    @AfterMapping
    public void activityMapping2(Activity activity, @MappingTarget ActivityDto activityDto) {
        log.info("i am activityMapping2");
    }

    @AfterMapping
    public void activityMapping1(Activity activity, @MappingTarget ActivityDto activityDto) {
        log.info("i am activityMapping1");
    }

    @AfterMapping
    public void trainAfterMaooing(Activity activity, @MappingTarget TrainActivity trainActivity) {
        log.info("i am trainActivity");
        trainActivity.setTrainJson("{}");
    }

}
