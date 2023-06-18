package com.cpp.Checkers.dto;

import com.cpp.Checkers.Models.Process;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDTO {
    @JsonProperty
    @NotEmpty
    private int processid;

    @JsonProperty
    @Temporal(TemporalType.TIMESTAMP)
    private Date init_date;

    @JsonProperty
    @Temporal(TemporalType.TIMESTAMP)
    private Date del_date;


}
