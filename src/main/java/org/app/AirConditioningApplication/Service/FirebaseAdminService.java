package org.app.AirConditioningApplication.Service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.app.AirConditioningApplication.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
public class FirebaseAdminService {

    public ApiResponse createFirebaseEmployee(String email, String password) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            UserRecord.CreateRequest request = new UserRecord.CreateRequest();
            email = email.trim();
            password = password.trim();
            request.setEmail(email);
            request.setPassword(password);

            UserRecord userRecord = auth.createUser(request);

            System.out.println("user Record after saving : " + userRecord.getProviderData().toString());

            if (userRecord != null) {
                apiResponse.setStatus(200);
                apiResponse.setMessage("Success");
                apiResponse.setData(userRecord);
            }
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            apiResponse.setStatus(200);
            apiResponse.setMessage("Success");
            apiResponse.setData(null);
        }

        return apiResponse;
    }

    public ApiResponse deleteFirebaseEmployee(String uid) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.deleteUser(uid);

            Firestore firestoreClient = FirestoreClient.getFirestore();
            firestoreClient.document("users/" + uid).delete();

            apiResponse.setStatus(200);
            apiResponse.setMessage("Success");
            apiResponse.setData("Employee Deleted Successfully");

        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            apiResponse.setStatus(200);
            apiResponse.setMessage("Success");
            apiResponse.setData("No data");
        }

        return apiResponse;
    }

    public ApiResponse getUserUid(String email) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);

            System.out.println("User id by email : "+ userRecord.getUid());
            apiResponse.setStatus(200);
            apiResponse.setMessage("Success");
            apiResponse.setData(userRecord.getUid());
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            apiResponse.setStatus(200);
            apiResponse.setMessage("Success");
            apiResponse.setData(null);
        }
        return apiResponse;
    }
}
