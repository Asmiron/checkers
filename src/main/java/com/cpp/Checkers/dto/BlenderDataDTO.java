package com.cpp.Checkers.dto;

import com.cpp.Checkers.Models.BlenderData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlenderDataDTO {

    private byte[] Image;

    @JsonProperty("json_data")
    public CheckersCoord json_data;


}
