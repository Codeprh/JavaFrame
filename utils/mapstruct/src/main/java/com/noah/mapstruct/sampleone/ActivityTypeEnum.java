package com.noah.mapstruct.sampleone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityTypeEnum {
    Train(100);
    private final Integer type;


    public static ActivityTypeEnum getActivityType(Integer type) {

        for (ActivityTypeEnum typeEnum : values()) {

            if (typeEnum.type.equals(type)) {
                return typeEnum;
            }
        }

        return null;
    }
}
