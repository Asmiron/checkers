package com.cpp.Checkers.dto;

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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BlenderDataDTO(String imagePath) {
        this.imagePath = imagePath;
    }
}
