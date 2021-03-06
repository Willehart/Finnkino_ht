package com.example.finnkino_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
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
    TextView errorMsg;
    Accounts accounts = Accounts.getInstance();

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        user = findViewById(R.id.editTextUserName);
        pass = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.textRegister);
        errorMsg = findViewById(R.id.textViewErrorMessage);

        context = LoginScreen.this;
        errorMsg.setText("");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verification();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegistration();
            }
        });
    }

    // checks if the given username exists and if the password matches
    @SuppressLint("SetTextI18n")
    public void verification(){
        try {
            InputStream inS = context.openFileInput("accounts.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inS));

            String line;
            String[] acc;
            Boolean accountExists = false;

            errorMsg.setText("");

            while((line = br.readLine())!=null) {
                acc = line.split(",");

                // go to main screen if the account exists
                if (acc[0].equals(user.getText().toString())) {
                    accountExists = true;
                    if (acc[1].equals(pass.getText().toString())) {
                        accounts.setCurrentUser(user.getText().toString());
                        goToMain();
                    } else {
                        errorMsg.setText("V????r?? salasana");
                    }
                    break;
                }
            }
            if (!accountExists) {
                errorMsg.setText("K??ytt??j???? ei ole");
            }

            inS.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToMain() {
        user.getText();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void goToRegistration() {
        Intent intent = new Intent(getApplicationContext(), RegistrationScreeen.class);
        startActivity(intent);
    }
}