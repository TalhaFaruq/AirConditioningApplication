package org.app.AirConditioningApplication.Contorller;

import org.app.AirConditioningApplication.Model.WorkLog;
import org.app.AirConditioningApplication.Service.WorkLogService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("WorkLog")
public class WorkLogController {
    private final WorkLogService workLogService;

    public WorkLogController(WorkLogService workLogService) {
        this.workLogService = workLogService;
    }

    @GetMapping("/list")
    public ApiResponse list() {
        return workLogService.showAll();
    }

    @PostMapping("/add")
    public ApiResponse add(@RequestBody WorkLog workLog) {
        return workLogService.save(workLog);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody WorkLog workLog) {
        return workLogService.save(workLog);
    }

    @DeleteMapping("/delete")
    public ApiResponse delete(@RequestParam Long Id) {
        return workLogService.delete(Id);
    }

    @GetMapping("/getByID")
    public ApiResponse getById(@RequestParam Long Id) {
        return workLogService.getById(Id);
    }
}
