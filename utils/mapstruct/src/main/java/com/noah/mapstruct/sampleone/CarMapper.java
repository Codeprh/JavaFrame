package com.noah.mapstruct.sampleone;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(
        uses = MarkMapper.class,
        injectionStrategy = InjectionStrategy.FIELD)
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mappings(
            @Mapping(source = "mark1", target = "name")
    )
    CarDto toCarDto(Car car);
}
