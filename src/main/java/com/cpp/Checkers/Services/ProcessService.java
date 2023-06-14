package com.cpp.Checkers.Services;

import com.cpp.Checkers.Models.Process;
import com.cpp.Checkers.Repositories.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProcessService {

    private final ProcessRepository processRepository;

    @Value("${DaysBeforeDelete}")
    private int DaysBeforeDelete;

    @Autowired
    public ProcessService(ProcessRepository processRepository){
        this.processRepository = processRepository;
    }

    @Transactional
    public void init(Process process){
        process.setInit_date(new Date());
        Calendar del = Calendar.getInstance();
        del.setTime(new Date());
        del.add(Calendar.DATE, DaysBeforeDelete);
        process.setDel_date(del.getTime());
        processRepository.save(process);
    }

    @Transactional
    public void save(Process process){
        processRepository.save(process);
    }

    @Transactional
    public void changeStatus(Process process, String newStatus){
        process.setStatus(newStatus);
        processRepository.save(process);
    }


    @Transactional
    public List<Process> getToBeDeleted(){
        return processRepository.ToBeDeleted(new Date());
    }


    @Transactional
    public List<Process> showAll(Integer page, Integer limit){
        Pageable pageable =  PageRequest.of(page, limit);
        return processRepository.findAllByStatusIsOrderByProcessidDesc("COMP", pageable);
    }

    @Transactional
    public Process getProcessById(Integer process_id){
        return processRepository.findByProcessid(process_id);
    }

}
