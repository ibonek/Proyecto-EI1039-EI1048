package com.ei10391048.project.fireBase;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FireBaseConnection {
    private Firestore db=null;

    private boolean test = false;

    public Firestore initializeFireBase(){
        try {

            String filePath = "src/main/java/com/ei10391048/project/fireBase/proyectoei1039-1048-firebase-adminsdk-k0u0g-61d7b914e4.json";
            if (!System.getProperty("user.dir").contains("backEnd")){
                filePath= "backEnd/"+filePath;
            }
            FileInputStream serviceAccount = new FileInputStream(filePath);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://proyectoei1039-1048-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return db;
    }
}
