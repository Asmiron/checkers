package com.cpp.Checkers.ScheduledTask;

import com.cpp.Checkers.Models.Process;
import com.cpp.Checkers.Services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;


@Component
public class CleaningModels {

    private final ProcessService processService;

    @Autowired
    public CleaningModels(ProcessService processService) {
        this.processService = processService;
    }

    @Scheduled(cron = "${cron.expression}")
    public void CleanModels(){
        List<Process> processes = processService.getToBeDeleted();
        for (Process process: processes) {
            File file = new File(process.getJsPath());
            if(file.exists()){
                file.delete();
                process.setStatus("DEL");
                processService.save(process);
            } else System.out.println(process.getJsPath() + " не существует");
        }
    }
}
