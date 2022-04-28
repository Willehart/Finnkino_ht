package com.example.finnkino_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RegistrationScreeen extends AppCompatActivity {

    EditText newUser;
    EditText newPass;
    Button register;
    TextView login;
    TextView errorMsg;
    Boolean accountExists;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screeen);

        context = RegistrationScreeen.this;

        newUser = findViewById(R.id.editTextUserName);
        newPass = findViewById(R.id.editTextPassword);
        register = findViewById(R.id.registerButton);
        login = findViewById(R.id.textLogin);
        errorMsg = findViewById(R.id.textViewErrorMessage2);
        errorMsg.setText("");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAccounts();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
    }

    public void saveAccount() {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("accounts.txt", Context.MODE_APPEND));
            writer.write(newUser.getText().toString() + "," + newPass.getText().toString() + "\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void checkAccounts() {
        try {
            accountExists = false;
            errorMsg.setText("");
            InputStream inS = context.openFileInput("accounts.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inS));

            String line;
            String[] acc;
            while((line = br.readLine())!=null) {
                acc = line.split(",");

                // go to main screen if the account exists
                if (acc[0].equals(newUser.getText().toString())) {
                    accountExists = true;
                    errorMsg.setText("Käyttäjätunnus on jo olemassa");
                    break;
                }
            }
            inS.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!accountExists) {
            saveAccount();
            goToMain();
        }
    }

    public void goToMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void goToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
    }
}