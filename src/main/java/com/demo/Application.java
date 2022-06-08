package com.demo;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@OpenAPIDefinition(
    info = @Info(
            title = "first",
            version = "0.0"
    )
)
public class Application {
    public static void main(String[] args) throws IOException {
        File f=new File(Objects.requireNonNull(Application.class.getClassLoader().getResource("firestoreServiceAccountKey.json")).getFile());
        FileInputStream serviceAccount=new FileInputStream(f.getAbsolutePath());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://workinprogress-925b5.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        Micronaut.run(Application.class, args);
    }
}
