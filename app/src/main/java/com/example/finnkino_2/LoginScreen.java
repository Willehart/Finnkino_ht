package com.example.finnkino_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.ims.ImsRegistrationAttributes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginScreen extends AppCompatActivity {

    EditText user;
    EditText pass;
    Button login;
    TextView register;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        user = findViewById(R.id.editTextUserName);
        pass = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.textRegister);

        context = LoginScreen.this;

        Boolean authentication = false;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputStream inS = context.openFileInput("accounts.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(inS));

                    String line;
                    String[] acc;
                    while((line = br.readLine())!=null) {
                         acc = line.split(",");
                        if (acc[0].equals(user.getText().toString()) & acc[1].equals(pass.getText().toString())) {
                            Boolean authentication = true;
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            break;
                        }
                    }
                br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationScreeen.class);
                startActivity(intent);
            }
        });
    }
}