package org.app.AirConditioningApplication.Utilities;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.nio.file.Paths;

@Service
public class FirebaseAppInit {
    @PostConstruct
    public void initializeFirebaseApp(){
        try{
            String path = Paths.get("").toAbsolutePath().toString();
            FileInputStream serviceAccount =
                    new FileInputStream(path + "/ServiceAccount.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://ac-company-authentication-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp app = null;
            if(FirebaseApp.getApps().isEmpty()) { //<--- check with this line
                app = FirebaseApp.initializeApp(options);
            }
    }catch(Exception e){
            System.out.println("Exception caught: "  + e.getMessage());
        }

}}
