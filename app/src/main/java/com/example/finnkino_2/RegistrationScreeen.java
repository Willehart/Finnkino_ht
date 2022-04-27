package com.example.finnkino_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RegistrationScreeen extends AppCompatActivity {

    EditText newuser;
    EditText newpass;
    Button register;
    TextView login;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screeen);

        context = RegistrationScreeen.this;

        newuser = findViewById(R.id.editTextUserName);
        newpass = findViewById(R.id.editTextPassword);
        register = findViewById(R.id.registerButton);
        login = findViewById(R.id.textLogin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("accounts.txt", Context.MODE_APPEND));
                    writer.write(newuser.getText().toString() + "," + newpass.getText().toString() + "\n");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            }
        });
    }
}