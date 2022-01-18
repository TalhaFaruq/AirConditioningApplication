package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.WorkLog;
import org.app.AirConditioningApplication.Service.WorkLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("WorkLog")
public class WorkLogController {
    private final WorkLogService workLogService;

    public WorkLogController(WorkLogService workLogService) {
        this.workLogService = workLogService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        return workLogService.showAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody WorkLog workLog) {
        return workLogService.save(workLog);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody WorkLog workLog) {
        return workLogService.save(workLog);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam Long Id) {
        return workLogService.delete(Id);
    }

    @GetMapping("/getByID")
    public ResponseEntity<Object> getById(@RequestParam Long Id) {
        return workLogService.getById(Id);
    }

}
