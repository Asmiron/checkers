package com.cpp.Checkers.Controllers;

import com.cpp.Checkers.Models.BlenderData;
import com.cpp.Checkers.Models.Process;
import com.cpp.Checkers.Services.BlenderDataService;
import com.cpp.Checkers.Services.ProcessService;
import com.cpp.Checkers.dto.BlenderDataDTO;
import com.cpp.Checkers.util.BlenderDataErrorResponse;
import com.cpp.Checkers.util.BlenderDataNotCreatedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/processes")
@SessionAttributes("process")
public class StartPage {

    //private final Logger logger= LoggerFactory.getLogger(StartPage.class);
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
                new BlenderDataDTO("static/images/2009/2009.jpg",
                        objectMapper.readValue(new File("images/" + process_id.toString() + "_text.json"), BlenderData.class)), HttpStatus.CREATED);

    }

    @GetMapping("/index")
    public ResponseEntity<List<Process>> index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("limit") Optional<Integer> limit){
        int currPage = page.orElse(1);
        int currLimit = limit.orElse(20);
        List<Process> processes = processService.showAll(currPage - 1, currLimit);
        model.addAttribute("processes", processes);
        return new ResponseEntity<>(processes, HttpStatus.OK);
    }

    @GetMapping("/list")
    public String List(@RequestParam("page") Optional<Integer> page, @RequestParam("limit") Optional<Integer> limit){
        return "processes";
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
        assert process != null;
        return new ResponseEntity<>(new BlenderDataDTO("C:/Users/yaram/IdeaProjects/Checkers/src/main/resources/static/images/2009/2009.jpg",
                objectMapper.readValue(new File("C:/Users/yaram/IdeaProjects/Checkers/src/main/resources/static/images/" +
                        process.getProcessid() + "/" + process.getProcessid() + "_text.json"), BlenderData.class)),
                HttpStatus.CREATED);
    }

    @PostMapping()
    @ResponseBody
    @Transactional
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BlenderData blenderData,
                                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<ObjectError> errors = bindingResult.getAllErrors();

            for (ObjectError error : errors) {
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }
            //logger.error("Not valid data: " + errorMsg.toString());
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
        //logger.error("Data not created: " + e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
