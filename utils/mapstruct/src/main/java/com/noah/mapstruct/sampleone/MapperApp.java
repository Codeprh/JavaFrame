package com.noah.mapstruct.sampleone;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperApp {

    public static void main(String[] args) {
        Car car = new Car();

        car.setMark1("这是po的mark");
        car.setLanguage("这是language");
        CarDto carDto = CarMapper.INSTANCE.toCarDto(car);
        log.info(carDto + "");

    }
}
