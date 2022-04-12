package com.noah.mapstruct.sampleone;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrainActivity extends AbstractActivity{

    private String trainJson;
}
