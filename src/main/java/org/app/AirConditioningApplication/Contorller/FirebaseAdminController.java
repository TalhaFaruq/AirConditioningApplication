package org.app.AirConditioningApplication.Contorller;

import com.google.firebase.auth.FirebaseAuthException;
import org.app.AirConditioningApplication.Service.FirebaseAdminService;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/firebase")
public class FirebaseAdminController {
    @Autowired
    private FirebaseAdminService firebaseAdminService;

    @GetMapping("/createEmployee")
    @ResponseBody
    public ApiResponse createFirebaseEmployee(@RequestParam String email, @RequestParam String password) throws IOException, FirebaseAuthException {
        return firebaseAdminService.createFirebaseEmployee(email, password);
    }
}
