package org.app.AirConditioningApplication.response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class ApiResponse {
    Integer status;
    String message;
    Object data = new Object();
}
