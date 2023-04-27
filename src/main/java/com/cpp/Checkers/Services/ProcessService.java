package com.cpp.Checkers.Services;

import com.cpp.Checkers.Models.Process;
import com.cpp.Checkers.Repositories.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional(readOnly = true)
public class ProcessService {

    private final ProcessRepository processRepository;

    @Autowired
    public ProcessService(ProcessRepository processRepository){
        this.processRepository = processRepository;
    }

    @Transactional
    public void save(Process process){
        process.setInit_date(Timestamp.from(Instant.now()));
        process.setDel_date(Timestamp.from(Instant.now().plus(2, ChronoUnit.DAYS)));
        processRepository.save(process);
    }

//    public Process findLast(){
//        processRepository.find
//    }

}
