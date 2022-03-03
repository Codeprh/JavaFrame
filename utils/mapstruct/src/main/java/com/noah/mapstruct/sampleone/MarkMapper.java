package com.noah.mapstruct.sampleone;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class MarkMapper {

    public String mapMark(String mark) {
        return mark.toUpperCase();
    }
}
