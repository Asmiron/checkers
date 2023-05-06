package com.cpp.Checkers.Controllers;

import com.cpp.Checkers.Models.BlenderData;
import com.cpp.Checkers.Models.Process;
import com.cpp.Checkers.Services.BlenderDataService;
import com.cpp.Checkers.Services.ProcessService;
import com.cpp.Checkers.dto.BlenderDataDTO;
import com.cpp.Checkers.util.BlenderDataErrorResponse;
import com.cpp.Checkers.util.BlenderDataNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/start")
@SessionAttributes("process")
public class StartPage {

    private final ProcessService processService;
    private final BlenderDataService blenderDataService;

    @Autowired
    public StartPage(ProcessService processService, BlenderDataService blenderDataService) {
        this.blenderDataService = blenderDataService;
        this.processService = processService;
    }

    @GetMapping()
    public String Start() {
        return "Start_page";
    }

    @ResponseBody
    @GetMapping("/check")
    @Transactional
    public ResponseEntity<BlenderDataDTO> check_data(Model model){
        Process process = (Process) model.getAttribute("process");
        if (process != null && process.getStatus().equals("INPRG"))
            return new ResponseEntity<>(null, HttpStatus.PROCESSING);
        else if (process != null && process.getStatus().equals("DEL"))
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new BlenderDataDTO("images/x970sK_5LeQ.jpg"), HttpStatus.CREATED);
    }


    @PostMapping()
    @ResponseBody
    @Transactional
    public ResponseEntity<HttpStatus> create(@RequestBody BlenderData blenderData,
                                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" ").append(error.getDefaultMessage()).append(";");
            }

            throw new BlenderDataNotCreatedException(errorMsg.toString());
        }
        System.out.println(processService.getToBeDeleted());
        Process process = new Process("INPRG");
        System.out.println(blenderData);
        processService.init(process);
        try {
            blenderDataService.sendToBlender(blenderData, process);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("process", process);
        System.out.println(process.getProcess_id());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{process_id}")
    public BlenderDataDTO show(@PathVariable int process_id, Model model){

        return new BlenderDataDTO("images/x970sK_5LeQ.jpg");
    }


    @ExceptionHandler
    private ResponseEntity<BlenderDataErrorResponse> handleException(BlenderDataNotCreatedException e){
        BlenderDataErrorResponse response = new BlenderDataErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
