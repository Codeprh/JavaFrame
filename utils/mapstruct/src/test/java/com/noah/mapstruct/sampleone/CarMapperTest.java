package com.noah.mapstruct.sampleone;

import com.noah.mapstruct.MapStuctApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = MapStuctApp.class)
@RunWith(SpringRunner.class)
@Slf4j
public class CarMapperTest {

    @Resource
    CarMapper carMapper;

    @Test
    public void testMy(){
        System.out.println("hello");
    }

    @Test
    public void  test123(){
        Car car = new Car();

        car.setMark1("这是po的mark");
        car.setLanguage("这是language");
        CarDto carDto1 = carMapper.toCarDto(car);
        //CarDto carDto = CarMapper.INSTANCE.toCarDto(car);
        log.info(carDto1 + "");
    }

}