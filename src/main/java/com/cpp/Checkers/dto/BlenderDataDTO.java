package com.cpp.Checkers.dto;

public class BlenderDataDTO {
    public BlenderDataDTO() {
    }

    private String Text;

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        this.Text = text;
    }

    public BlenderDataDTO(String Text) {
        this.Text = Text;
    }

    @Override
    public String toString() {
        return "BlenderDataDTO{" +
                "Text='" + Text + '\'' +
                '}';
    }
}
