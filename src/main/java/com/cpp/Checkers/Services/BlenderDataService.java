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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;

@Service
public class BlenderDataService {

    private final ProcessService processService;
    @Value("${PythonScript.path}")
    private String pythonScriptPath;

    @Value("${Images.directories}")
    private String ImagesDirPath;

    @Autowired
    public BlenderDataService(ProcessService processService) {
        this.processService = processService;
    }


    @Transactional
    public void sendToBlender(BlenderData blenderData, Process process) throws IOException, InterruptedException {
        Files.createDirectories(Paths.get(ImagesDirPath + "/" +process.getProcessid()));
        File jsonMap = new File(ImagesDirPath + "/" + process.getProcessid() + "/" + process.getProcessid() + ".json" );
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(jsonMap, blenderData);
        /*
        ProcessBuilder processBuilder =
                new ProcessBuilder("python", pythonScriptPath + "/__init__.py", "--json " + jsonMap.getPath() +
                        " --blend C:/Users/yaram/IdeaProjects/Checkers/src/main/resources/static/model/wooden_board_plastic_draughts.blend");
        java.lang.Process processPy = processBuilder.start();
        System.out.println(processPy.isAlive());
        int exitCode = processPy.waitFor();
        if (exitCode == 0) System.out.println("Процесс №" + process.getProcessid() + " успешно выполнился");
        else System.out.println("Процесс №" + process.getProcessid() + " не выполнился");

         */
        runPython(jsonMap.getPath());
        processService.changeStatus(process, "COMP");
        process.setJsPath(ImagesDirPath + "/" +process.getProcessid()+ "/" + process.getProcessid() + "_text.json");
        processService.save(process);
        System.gc();
    }



    public void runPython(String json_path) throws IOException, InterruptedException { //need to call myscript.py and also pass arg1 as its arguments.
        //and also myscript.py path is in C:\Demo\myscript.py

        String[] cmd = {
                "python",
                pythonScriptPath + "/__init__.py",
                "--json " + json_path +
                " --blend C:/Users/yaram/IdeaProjects/Checkers/src/main/resources/static/model/wooden_board_plastic_draughts.blend"
        };
        Runtime.getRuntime().exec(cmd).waitFor();
    }
}
