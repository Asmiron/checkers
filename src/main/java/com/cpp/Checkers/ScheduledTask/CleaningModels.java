package com.cpp.Checkers.ScheduledTask;

import com.cpp.Checkers.Models.Process;
import com.cpp.Checkers.Services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Objects;


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
            File file = new File("C:/Users/yaram/IdeaProjects/Checkers/src/main/resources/static/images/" + process.getProcessid());
            if(file.exists()){
                recursiveDelete(file);
                process.setStatus("DEL");
                processService.save(process);
            } else System.out.println(process.getJsPath() + " не существует");
        }
    }

    public static void recursiveDelete(File file) {
        // до конца рекурсивного цикла
        if (!file.exists())
            return;

        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                // рекурсивный вызов
                recursiveDelete(f);
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        boolean delExCode = file.delete();
        if (delExCode)  System.out.println("Удаленный файл или папка: " + file.getAbsolutePath());
        else System.out.println("Не удалось удалить файл");
    }
}
