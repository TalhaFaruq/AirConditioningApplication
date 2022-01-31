package org.app.AirConditioningApplication.Service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseAdminService {

    public ApiResponse createFirebaseEmployee(String email, String password) throws IOException, FirebaseAuthException {
        ApiResponse apiResponse = new ApiResponse();

        try{
            FileInputStream serviceAccount =
                    new FileInputStream("C:\\ac-company-authentication-firebase-adminsdk-lig3u-27eabcd2de.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://ac-company-authentication-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp app = null;
            if(FirebaseApp.getApps().isEmpty()) { //<--- check with this line
                app = FirebaseApp.initializeApp(options);
            }
//        FirebaseApp app = FirebaseApp.initializeApp( options);
//        System.out.println("App Name = " + app.getName());
            FirebaseAuth auth = FirebaseAuth.getInstance();
            UserRecord.CreateRequest request = new UserRecord.CreateRequest();
            email = email.trim();
            password = password.trim();
            request.setEmail(email);
            request.setPassword(password);

            UserRecord userRecord =  auth.createUser(request);



            System.out.println("user Record after saving : " + userRecord.getProviderData().toString());

            if(userRecord != null){
                apiResponse.setStatus(200);
                apiResponse.setMessage("Success");
                apiResponse.setData(userRecord);
            }
        }catch (Exception e){
            System.out.println("Exception caught: " + e.getMessage());
            apiResponse.setStatus(200);
            apiResponse.setMessage("Success");
            apiResponse.setData("No data");

        }

    return apiResponse;
    }
}
