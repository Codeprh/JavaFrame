package com.noah.mapstruct.sampleone;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
public abstract class AbstractActivity implements Serializable {

    public static final long serialVersionUID = 42L;

    private String name;
    private String language;

    private ActivityJson activityJson;
    private ActivityTypeEnum activityTypeEnum;
}
