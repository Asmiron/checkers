package com.cpp.Checkers.Models;

import com.cpp.Checkers.CustomAnnotations.FieldsMinMax;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


public class BlenderData {
    public BlenderData() {
    }

    @Valid
    private offset offset;

    @Valid
    private FigureSize figureSize;




    @NotNull(message = "numOfCheckers should not be null")
    @Min(value = 0, message = "numOfCheckers should be more than 0 and less than 24")
    @Max(value = 24, message = "numOfCheckers should be more than 0 and less than 24")
    @JsonProperty()
    private int numOfCheckers;

    @NotNull(message = "resolution should not be empty")
    @JsonProperty()
    private String resolution;

    @NotNull(message = "Samples should not be null")
    @Min(value = 10, message = "Samples should be more than 10 and less than 800")
    @Max(value = 800, message = "Samples should be more than 10 and less than 800")
    @JsonProperty("samples")
    private int Samples;


    @FieldsMinMax(
            message = "Field FigureSizeMin should not be more than FigureSizeMax",
            Min = "FigureSizeMin",
            Max = "FigureSizeMax"
    )
    private static class FigureSize{
        public FigureSize() {
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

        public FigureSize(float figureSizeMin, float figureSizeMax) {
            FigureSizeMin = figureSizeMin;
            FigureSizeMax = figureSizeMax;
        }

        @NotNull(message = "FigureSizeMin should not be null")
        @DecimalMin(value = "0.6", message = "FigureSizeMin should be more than 0.6 and less than 1.0")
        @DecimalMax(value = "1.0", message = "FigureSizeMin should be more than 0.6 and less than 1.0")
        @JsonProperty("figureSizeMin")
        private float FigureSizeMin;

        @NotNull(message = "FigureSizeMax should not be null")
        @DecimalMin(value = "0.6", message = "FigureSizeMax should be more than 0.6 and less than 1.0")
        @DecimalMax(value = "1.0", message = "FigureSizeMax should be more than 0.6 and less than 1.0")
        @JsonProperty("figureSizeMax")
        private float FigureSizeMax;

        @Override
        public String toString() {
            return "FigureSize{" +
                    "FigureSizeMin=" + FigureSizeMin +
                    ", FigureSizeMax=" + FigureSizeMax +
                    '}';
        }
    }

    @FieldsMinMax.List({
            @FieldsMinMax(
                    Min = "x_min",
                    Max = "x_max",
                    message = "Field x_min should not be more than x_max"
            ),
            @FieldsMinMax(
                    Min = "y_min",
                    Max = "y_max",
                    message = "Field y_min should not be than more y_max"
            ),
            @FieldsMinMax(
                    Min = "z_min",
                    Max = "z_max",
                    message = "Field z_min should not be more than z_max"
            )
    })
    public static class offset{
        public offset() {
        }

        @Override
        public String toString() {
            return "Offset{" +
                    "x_min=" + x_min +
                    ", x_max=" + x_max +
                    ", y_min=" + y_min +
                    ", y_max=" + y_max +
                    ", z_min=" + z_min +
                    ", z_max=" + z_max +
                    '}';
        }

        public offset(float x_min, float x_max, float y_min, float y_max, float z_min, float z_max) {
            this.x_min = x_min;
            this.x_max = x_max;
            this.y_min = y_min;
            this.y_max = y_max;
            this.z_min = z_min;
            this.z_max = z_max;
        }

        public float getX_min() {
            return x_min;
        }

        public void setX_min(float x_min) {
            this.x_min = x_min;
        }

        public float getX_max() {
            return x_max;
        }

        public void setX_max(float x_max) {
            this.x_max = x_max;
        }

        public float getY_min() {
            return y_min;
        }

        public void setY_min(float y_min) {
            this.y_min = y_min;
        }

        public float getY_max() {
            return y_max;
        }

        public void setY_max(float y_max) {
            this.y_max = y_max;
        }

        public float getZ_min() {
            return z_min;
        }

        public void setZ_min(float z_min) {
            this.z_min = z_min;
        }

        public float getZ_max() {
            return z_max;
        }

        public void setZ_max(float z_max) {
            this.z_max = z_max;
        }

        @NotNull
        @Min(value = 0)
        @Max(value = 30)
        @JsonProperty()
        private float x_min;

        @NotNull
        @Min(value = 0)
        @Max(value = 30)
        @JsonProperty
        private float x_max;

        @NotNull
        @Min(value = 0)
        @Max(value = 30)
        @JsonProperty
        private float y_min;

        @NotNull
        @Min(value = 0)
        @Max(value = 30)
        @JsonProperty
        private float y_max;

        @NotNull
        @Min(value = 0)
        @Max(value = 30)
        @JsonProperty
        private float z_min;

        @NotNull
        @Min(value = 0)
        @Max(value = 30)
        @JsonProperty
        private float z_max;
    }




    public BlenderData(int numOfCheckers, String resolution, int samples, float figureSizeMin, float figureSizeMax, byte x_min, byte x_max, byte y_min, byte y_max, byte z_min, byte z_max) {
        this.numOfCheckers = numOfCheckers;
        this.resolution = resolution;
        this.Samples = samples;
        figureSize = new FigureSize(figureSizeMin, figureSizeMax);
        offset = new offset(x_min, x_max, y_min, y_max, z_min, z_max);
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

    public FigureSize getFigureSize() {
        return figureSize;
    }

    public void setFigureSize(FigureSize figureSize) {
        this.figureSize = figureSize;
    }

    public offset getOffset() {
        return offset;
    }

    public void setOffset(offset offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "BlenderData{" +
                "offset=" + offset.toString() +
                ", numOfCheckers=" + numOfCheckers +
                ", resolution='" + resolution +
                ", Samples=" + Samples +
                ", FigureSize=" + figureSize.toString() +
                '}';
    }
}

