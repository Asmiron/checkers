package com.cpp.Checkers.Controllers;

import com.cpp.Checkers.Models.BlenderData;
import com.cpp.Checkers.Models.Process;
import com.cpp.Checkers.Services.BlenderDataService;
import com.cpp.Checkers.Services.ProcessService;
import com.cpp.Checkers.dto.BlenderDataDTO;
import com.cpp.Checkers.util.BlenderDataErrorResponse;
import com.cpp.Checkers.util.BlenderDataNotCreatedException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/start")
public class StartPage {

    private final ProcessService processService;
    private final BlenderDataService blenderDataService;

    @Autowired
    public StartPage(ProcessService processService, BlenderDataService blenderDataService) {
        this.blenderDataService = blenderDataService;
        this.processService = processService;
    }

    @GetMapping()
    public String Start(Model model) {
        model.addAttribute("blenderData", new BlenderDataDTO());
        return "Start";
    }

    @ResponseBody
    @GetMapping("/check")
    public BlenderDataDTO check_data(Model model){

        return new BlenderDataDTO("Ok");
    }


    @PostMapping()
    public ResponseEntity<BlenderDataDTO> create(@RequestBody @Valid BlenderDataDTO blenderData,
                                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append("-").append(error.getDefaultMessage()).append(";");
            }

            throw new BlenderDataNotCreatedException(errorMsg.toString());
        }
        Process process = new Process("Start");
        System.out.println(blenderData);
        processService.save(process);
        //blenderDataService.sendToBlender(blenderData);
        System.out.println(process.getProcess_id());
        return new ResponseEntity<>(blenderData, HttpStatus.CREATED);
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
