package com.cpp.Checkers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckersCoord {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Coordinate{
        private float x;
        private float y;
    }

    private List<Coordinate> white;
    private List<Coordinate> black;
}
