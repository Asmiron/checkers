package com.cpp.Checkers.dto;

import com.cpp.Checkers.Models.BlenderData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BlenderDataDTO {
    public BlenderDataDTO() {
    }
    @JsonProperty("imagePath")
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    @JsonProperty("json_data")
    public BlenderData json_data;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BlenderDataDTO(String imagePath, BlenderData json_data) {
        this.imagePath = imagePath;
        this.json_data = json_data;
    }
}
