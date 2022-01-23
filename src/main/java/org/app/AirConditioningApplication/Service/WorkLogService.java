package org.app.AirConditioningApplication.Service;

import org.app.AirConditioningApplication.Model.WorkLog;
import org.app.AirConditioningApplication.Repository.WorkLogRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkLogService {
    private final WorkLogRepo workLogRepo;

    public WorkLogService(WorkLogRepo workLogRepo) {
        this.workLogRepo = workLogRepo;
    }

    public ResponseEntity<Object> save(WorkLog workLog) {
        try {
            workLog.setDate(LocalDate.now());
            workLogRepo.save(workLog);
            return ResponseEntity.accepted().body(workLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> showAll() {
        try {
            List<WorkLog> workLogList = workLogRepo.findAll();
            if (!workLogList.isEmpty())
                return ResponseEntity.ok().body(workLogList);
            else
                return ResponseEntity.ok().body("There are no workLogs");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    public ResponseEntity<Object> getById(Long Id) {
        try {
            Optional<WorkLog> workLog = workLogRepo.findById(Id);
            if (workLog.isPresent())
                return ResponseEntity.ok().body(workLog);
            else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


    public ResponseEntity<Object> delete(Long Id) {
        try {
            Optional<WorkLog> workLog = workLogRepo.findById(Id);
            if (workLog.isPresent()) {
                workLogRepo.delete(workLog.get());
                return ResponseEntity.ok().body("Deleted");
            }else return ResponseEntity.ok().body("Invalid ID");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
