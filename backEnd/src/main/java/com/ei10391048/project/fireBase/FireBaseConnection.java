package com.ei10391048.project.fireBase;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FireBaseConnection {
    private static Firestore db;

    private static FireBaseConnection fireBaseConnection;

    private FireBaseConnection() {
        db=initializeFireBase();
    }
    static FireBaseConnection getInstance() {
        if (fireBaseConnection == null) {
            fireBaseConnection = new FireBaseConnection();
        }
        return fireBaseConnection;
    }

    private static Firestore initializeFireBaseTest() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/java/com/ei10391048/project/fireBase/lookapp-8874e-firebase-adminsdk-9apj5-67c0d225ec.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://proyectoei1039-1048-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

            return FirestoreClient.getFirestore();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Firestore initializeFireBase(){
        try {
            if (System.getProperty("user.dir").contains("backEnd")){
                return initializeFireBaseTest();
            }
            FileInputStream serviceAccount = new FileInputStream("backEnd/src/main/java/com/ei10391048/project/fireBase/proyectoei1039-1048-firebase-adminsdk-k0u0g-61d7b914e4.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://proyectoei1039-1048-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            return FirestoreClient.getFirestore();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Firestore getDb() {
        return db;
    }
}
