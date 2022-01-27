package org.app.AirConditioningApplication.response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class ApiResponse {
    String status;
    String message;
//    List<Object> data = new ArrayList<>();
    Object data = new Object();
}
