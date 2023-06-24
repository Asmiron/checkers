package com.cpp.Checkers.Controllers;

import com.cpp.Checkers.Models.BlenderData;
import com.cpp.Checkers.Models.Process;
import com.cpp.Checkers.Services.BlenderDataService;
import com.cpp.Checkers.Services.ProcessService;
import com.cpp.Checkers.dto.BlenderDataDTO;
import com.cpp.Checkers.dto.CheckersCoord;
import com.cpp.Checkers.dto.ProcessDTO;
import com.cpp.Checkers.util.BlenderDataErrorResponse;
import com.cpp.Checkers.util.BlenderDataNotCreatedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/processes")
@SessionAttributes("process")
public class StartPage implements ServletContextAware {

    //private final Logger logger= LoggerFactory.getLogger(StartPage.class);
    private ServletContext servletContext;
    private final ProcessService processService;
    private final BlenderDataService blenderDataService;

    private final ModelMapper modelMapper;

    @Value("${Images.directories}")
    private String ImagesDirectory;

    @Autowired
    public StartPage(ServletContext servletContext, ProcessService processService, BlenderDataService blenderDataService, ModelMapper modelMapper) {
        this.servletContext = servletContext;
        this.blenderDataService = blenderDataService;
        this.processService = processService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    @GetMapping()
    public String Start() {
        return "Start_page";
    }

    @ResponseBody
    @GetMapping("/{process_id}")
    @Transactional
    public ResponseEntity<BlenderDataDTO> get_data(Model model, @PathVariable Integer process_id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Process process = processService.getProcessById(process_id);
        if (process == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return getBlenderDataDTOResponseEntity(process, objectMapper);
    }

    @GetMapping("/index")
    public ResponseEntity<List<ProcessDTO>> index(Model model, @RequestParam(value = "page") Optional<Integer> page,
                                                  @RequestParam(value = "limit") Optional<Integer> limit){
        int currPage = page.orElse(1);
        int currLimit = limit.orElse(20);
        List<Process> processes = processService.showAll(currPage - 1, currLimit);
        model.addAttribute("processes", processes);
        return new ResponseEntity<>(processes.stream().map(this::convertToProcessDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/list")
    public String List(@RequestParam("page") Optional<Integer> page, @RequestParam("limit") Optional<Integer> limit){
        return "processes";
    }

    @ResponseBody
    @GetMapping(value = "/check")
    @Transactional
    public ResponseEntity<BlenderDataDTO> check_data(Model model) throws IOException {
        Process process = (Process) model.getAttribute("process");
        ObjectMapper objectMapper = new ObjectMapper();
        if (process == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return getBlenderDataDTOResponseEntity(process, objectMapper);
    }

    private ResponseEntity<BlenderDataDTO> getBlenderDataDTOResponseEntity(Process process, ObjectMapper objectMapper) throws IOException {
        if (process.getStatus().equals("INPRG"))
            return new ResponseEntity<>(null, HttpStatus.PROCESSING);
        else if (process.getStatus().equals("DEL"))
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else {
            InputStream in = servletContext.getResourceAsStream(ImagesDirectory + "/" + process.getProcessid() + "/"
            + process.getProcessid() + ".png");
            return new ResponseEntity<>(new BlenderDataDTO(IOUtils.toByteArray(in),
                    objectMapper.readValue(new File(ImagesDirectory + "/" + process.getProcessid() + "/"
                            + process.getProcessid() + "_out.json" ), CheckersCoord.class)),
                    HttpStatus.CREATED);
        }
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

    private  Process convertToProcess(ProcessDTO processDTO){
        return modelMapper.map(processDTO, Process.class);
    }
    private ProcessDTO convertToProcessDTO(Process process){
        return modelMapper.map(process, ProcessDTO.class);
    }
}
