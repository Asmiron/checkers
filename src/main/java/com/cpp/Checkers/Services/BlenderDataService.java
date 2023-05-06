package com.cpp.Checkers.Services;

import com.cpp.Checkers.Models.BlenderData;
import com.cpp.Checkers.Models.Process;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@Service
public class BlenderDataService {

    private final ProcessService processService;
    @Value("${PythonScript.path}")
    private String pythonScriptPath;
    @Autowired
    public BlenderDataService(ProcessService processService) {
        this.processService = processService;
    }


    @Transactional
    public void sendToBlender(BlenderData blenderData, Process process) throws IOException, InterruptedException {
        File jsonMap = File.createTempFile(process.getProcess_id() + "_", ".json", new File(pythonScriptPath));
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(jsonMap, blenderData);
        ProcessBuilder processBuilder =
                new ProcessBuilder("python", pythonScriptPath + "/__init__.py", jsonMap.getPath(),
                        Integer.toString(process.getProcess_id()));
        java.lang.Process processPy = processBuilder.start();
        int exitCode = processPy.waitFor();
        jsonMap.delete();
        if (exitCode == 0) System.out.println("Процесс №" + process.getProcess_id() + " успешно выполнился");
        else System.out.println("Процесс №" + process.getProcess_id() + " не выполнился");
        processService.changeStatus(process, "COMP");
        process.setJsPath("static/images/"+process.getProcess_id()+"_text.txt");
        processService.save(process);
    }
}
