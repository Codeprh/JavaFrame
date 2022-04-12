package com.noah.mapstruct.sampleone;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityDto implements Serializable {

    public static final long serialVersionUID = 42L;

    private String name;
    private String language;

    private ActivityJson activityJson;
}
