package com.cpp.Checkers.Models;

import com.cpp.Checkers.CustomAnnotations.FieldsMinMax;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

//@FieldsMinMax.List({
//    @FieldsMinMax(
//        message = "Поля должны быть валидны",
//        min = "x_min",
//        FieldToMatch = "x_max"
//    )
//})
public class BlenderData {
    public BlenderData() {
    }


    @NotNull
    @Min(value = 0)
    @Max(value = 24)
    @JsonProperty()
    private int numOfCheckers;

    @NotNull
    @JsonProperty()
    private String resolution;

    @NotNull
    @Min(value = 10, message = "Samples = ")
    @Max(value = 800)
    @JsonProperty("samples")
    private int Samples;

    @NotNull
    @DecimalMin(value = "0.6")
    @DecimalMax(value = "1.0")
    @JsonProperty("figureSizeMin")
    private float FigureSizeMin;

    @NotNull
    @DecimalMin(value = "0.6")
    @DecimalMax(value = "1.0")
    @JsonProperty("figureSizeMax")
    private float FigureSizeMax;

    @NotNull
    @Min(value = 0)
    @Max(value = 30)
    @JsonProperty()
    private byte x_min;

    @NotNull
    @Min(value = 0)
    @Max(value = 30)
    @JsonProperty
    private byte x_max;

    @NotNull
    @Min(value = 0)
    @Max(value = 30)
    @JsonProperty
    private byte y_min;

    @NotNull
    @Min(value = 0)
    @Max(value = 30)
    @JsonProperty
    private byte y_max;

    @NotNull
    @Min(value = 0)
    @Max(value = 30)
    @JsonProperty
    private byte z_min;

    @NotNull
    @Min(value = 0)
    @Max(value = 30)
    @JsonProperty
    private byte z_max;


    public BlenderData(int numOfCheckers, String resolution, int samples, float figureSizeMin, float figureSizeMax, byte x_min, byte x_max, byte y_min, byte y_max, byte z_min, byte z_max) {
        this.numOfCheckers = numOfCheckers;
        this.resolution = resolution;
        Samples = samples;
        FigureSizeMin = figureSizeMin;
        FigureSizeMax = figureSizeMax;
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = y_min;
        this.y_max = y_max;
        this.z_min = z_min;
        this.z_max = z_max;
    }

    public int getNumOfCheckers() {
        return numOfCheckers;
    }

    public void setNumOfCheckers(int numOfCheckers) {
        this.numOfCheckers = numOfCheckers;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getSamples() {
        return Samples;
    }

    public void setSamples(int samples) {
        Samples = samples;
    }

    public float getFigureSizeMin() {
        return FigureSizeMin;
    }

    public void setFigureSizeMin(float figureSizeMin) {
        FigureSizeMin = figureSizeMin;
    }

    public float getFigureSizeMax() {
        return FigureSizeMax;
    }

    public void setFigureSizeMax(float figureSizeMax) {
        FigureSizeMax = figureSizeMax;
    }

    public byte getX_min() {
        return x_min;
    }

    public void setX_min(byte x_min) {
        this.x_min = x_min;
    }

    public byte getX_max() {
        return x_max;
    }

    public void setX_max(byte x_max) {
        this.x_max = x_max;
    }

    public byte getY_min() {
        return y_min;
    }

    public void setY_min(byte y_min) {
        this.y_min = y_min;
    }

    public byte getY_max() {
        return y_max;
    }

    public void setY_max(byte y_max) {
        this.y_max = y_max;
    }

    public byte getZ_min() {
        return z_min;
    }

    public void setZ_min(byte z_min) {
        this.z_min = z_min;
    }

    public byte getZ_max() {
        return z_max;
    }

    public void setZ_max(byte z_max) {
        this.z_max = z_max;
    }

    @Override
    public String toString() {
        return "BlenderData{" +
                "numOfCheckers=" + numOfCheckers +
                ", resolution='" + resolution + '\'' +
                ", Samples=" + Samples +
                ", FigureSizeMin=" + FigureSizeMin +
                ", FigureSizeMax=" + FigureSizeMax +
                ", x_min=" + x_min +
                ", x_max=" + x_max +
                ", y_min=" + y_min +
                ", y_max=" + y_max +
                ", z_min=" + z_min +
                ", z_max=" + z_max +
                '}';
    }
}

