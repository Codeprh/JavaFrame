package com.noah.mapstruct.sampleone;

import com.noah.mapstruct.sampleone.use.ActivityAfterMapping;
import com.noah.mapstruct.sampleone.use.ActivityMapperConfig;
import com.noah.mapstruct.sampleone.use.ActivityUseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(config = ActivityMapperConfig.class,
        uses = {ActivityUseMapper.class, ActivityAfterMapping.class},
        imports = {ActivityTypeEnum.class}
)
public interface ActivityMapper {

    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    @Mappings({
            @Mapping(source = "mark1", target = "name"),
            @Mapping(target = "activityJson", source = "json", qualifiedByName = "str2JsonObj")
    })
    ActivityDto toActivityDto(Activity activity);

    @InheritConfiguration(name = "toActivity")
    TrainActivity toTrainActivity(Activity activity);

    @InheritConfiguration(name = "toActivityLess")
    TrainActivity toTrainActivityLess(Activity activity);

    default ActivityTypeEnum toActivityTypeEnum(Integer type) {
        return ActivityTypeEnum.getActivityType(type);
    }



}
