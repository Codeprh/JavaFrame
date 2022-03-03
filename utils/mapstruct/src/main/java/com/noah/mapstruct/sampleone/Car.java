package com.noah.mapstruct.sampleone;

import lombok.Data;

import java.io.Serializable;

@Data
public class Car implements Serializable {

    public static final long serialVersionUID = 42L;

    private String mark1;
    private String language;
}
