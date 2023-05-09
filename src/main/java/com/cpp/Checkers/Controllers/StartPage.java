package com.cpp.Checkers.Controllers;

import com.cpp.Checkers.Models.BlenderData;
import com.cpp.Checkers.Models.Process;
import com.cpp.Checkers.Services.BlenderDataService;
import com.cpp.Checkers.Services.ProcessService;
import com.cpp.Checkers.dto.BlenderDataDTO;
import com.cpp.Checkers.util.BlenderDataErrorResponse;
import com.cpp.Checkers.util.BlenderDataNotCreatedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/processes")
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
    @GetMapping("/{process_id}")
    @Transactional
    public ResponseEntity<BlenderDataDTO> check_data(Model model, @PathVariable Integer process_id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Process process = processService.getProcessById(process_id);
        if (process != null && process.getStatus().equals("INPRG"))
            return new ResponseEntity<>(null, HttpStatus.PROCESSING);
        else if (process != null && process.getStatus().equals("DEL"))
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(
                new BlenderDataDTO("images/akrinskaja-1.jpg",
                        objectMapper.readValue(new File("images/" + process_id.toString() + "_text.txt"), BlenderData.class)), HttpStatus.CREATED);

    }


    @ResponseBody
    @GetMapping("/check")
    @Transactional
    public ResponseEntity<BlenderDataDTO> check_data(Model model) throws IOException {
        Process process = (Process)model.getAttribute("process");
        ObjectMapper objectMapper = new ObjectMapper();
        if (process != null && process.getStatus().equals("INPRG"))
            return new ResponseEntity<>(null, HttpStatus.PROCESSING);
        else if (process != null && process.getStatus().equals("DEL"))
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        //File file = new File("images/" + process.getProcessid()+ "_text.txt");
        BlenderData blenderData = objectMapper.readValue(new File("C:/Users/yaram/IdeaProjects/Checkers/src/main/resources/static/images/" +
                process.getProcessid() + "/" + process.getProcessid() + "_text.json"), BlenderData.class);
        return new ResponseEntity<>(new BlenderDataDTO("images/akrinskaja-1.jpg",
                objectMapper.readValue(new File("C:/Users/yaram/IdeaProjects/Checkers/src/main/resources/static/images/" +
                        process.getProcessid() + "/" + process.getProcessid() + "_text.json"), BlenderData.class)),
                HttpStatus.CREATED);
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
        System.out.println(process.getProcessid());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
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
