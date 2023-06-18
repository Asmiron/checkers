package com.cpp.Checkers.dto;

import com.cpp.Checkers.Models.BlenderData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlenderDataDTO {
    public BlenderDataDTO() {
    }

    @JsonProperty("image")
    private byte[] Image;


    @JsonProperty("json_data")
    public BlenderData json_data;


}
