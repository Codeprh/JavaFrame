package com.noah.mapstruct.sampleone;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityJson implements Serializable {
    public static final long serialVersionUID = 42L;
    private Integer type;
}
