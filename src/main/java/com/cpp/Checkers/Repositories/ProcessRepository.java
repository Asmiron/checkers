package com.cpp.Checkers.Repositories;

import com.cpp.Checkers.Models.Process;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {

    @Query(value = "Select * from Process p where del_date < :date and status = 'COMP'",
            nativeQuery = true)
    List<Process> ToBeDeleted(@Param("date") Date date);

    List<Process> findAllByStatusIsOrderByProcessidDesc(String status, Pageable var1);

    Process findByProcessid(Integer process_id);
}
