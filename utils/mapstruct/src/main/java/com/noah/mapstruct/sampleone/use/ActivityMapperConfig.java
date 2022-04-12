package com.noah.mapstruct.sampleone.use;

import com.noah.mapstruct.sampleone.AbstractActivity;
import com.noah.mapstruct.sampleone.Activity;
import org.mapstruct.*;

@MapperConfig(uses = {ActivityUseMapper.class, ActivityAfterMapping.class})
public interface ActivityMapperConfig {

    @Mappings({
            @Mapping(source = "mark1", target = "name"),
            @Mapping(target = "activityJson", source = "json", qualifiedByName = "str2JsonObj"),
            @Mapping(source = "activityType",target = "activityTypeEnum")
    })
    AbstractActivity toActivity(Activity activity);

    @Mappings({
            @Mapping(source = "mark1", target = "name"),
    })
    @BeanMapping(ignoreByDefault = true)
    AbstractActivity toActivityLess(Activity activity);
}
