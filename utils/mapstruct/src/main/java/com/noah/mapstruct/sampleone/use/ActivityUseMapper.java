package com.noah.mapstruct.sampleone.use;

import com.google.gson.Gson;
import com.noah.mapstruct.sampleone.ActivityJson;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public class ActivityUseMapper {

    public String mapMark(String mark) {
        return mark.toUpperCase();
    }

    @Named("str2JsonObj")
    public ActivityJson str2JsonObj(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ActivityJson.class);
    }
}
