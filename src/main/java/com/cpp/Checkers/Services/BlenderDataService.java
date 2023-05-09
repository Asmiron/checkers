package com.cpp.Checkers.Services;

import com.cpp.Checkers.Models.BlenderData;
import com.cpp.Checkers.Models.Process;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        Files.createDirectories(Paths.get("C:/Users/yaram/IdeaProjects/Checkers/src/main/resources/static/images/"+process.getProcessid()));
        File jsonMap = new File("C:/Users/yaram/IdeaProjects/Checkers/src/main/resources/static/images/" + process.getProcessid() + "/" + process.getProcessid() + ".json" );
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(jsonMap, blenderData);
        ProcessBuilder processBuilder =
                new ProcessBuilder("python", pythonScriptPath + "/__init__.py", jsonMap.getPath(),
                        Integer.toString(process.getProcessid()));
        java.lang.Process processPy = processBuilder.start();
        int exitCode = processPy.waitFor();
        if (exitCode == 0) System.out.println("Процесс №" + process.getProcessid() + " успешно выполнился");
        else System.out.println("Процесс №" + process.getProcessid() + " не выполнился");
        processService.changeStatus(process, "COMP");
        process.setJsPath("static/images/"+process.getProcessid()+ "/" + process.getProcessid() + "_text.json");
        processService.save(process);
    }
}
