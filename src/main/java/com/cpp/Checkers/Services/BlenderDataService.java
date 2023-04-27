package com.cpp.Checkers.Services;

import com.cpp.Checkers.Models.BlenderData;
import com.cpp.Checkers.dto.BlenderDataDTO;
import org.springframework.stereotype.Service;

@Service
public class BlenderDataService {


    public BlenderDataService() {
    }

    public void sendToBlender(BlenderData blenderData){
        ProcessBuilder processBuilder = new ProcessBuilder();
    }
}
