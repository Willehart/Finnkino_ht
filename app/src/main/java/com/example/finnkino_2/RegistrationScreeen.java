package com.example.finnkino_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationScreeen extends AppCompatActivity {

    EditText newUser;
    EditText newPass;
    Button register;
    TextView login;
    TextView errorMsg;
    String[] passwordErrors = {"", "", "", "", ""};
    Boolean accountExists;
    String[] acc;
    String pass = "";
    char[] passwordCharSeq;
    Boolean hasSpecial = false, hasDigit = false, hasCapital = false, hasSmall = false, isLongEnough = false;
    Boolean passwordOK = false;
    Context context;
    Accounts accounts = Accounts.getInstance();

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
        newPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                checkPasswordRequirements();
            }
        });
    }

    // save account to the accounts.txt file
    public void saveAccount() {
        try {
            accounts.addAccount(newUser.getText().toString(), newPass.getText().toString());

            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("accounts.txt", Context.MODE_APPEND));
            writer.write(newUser.getText().toString() + "," + newPass.getText().toString() + "\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // checked when register button is pressed
    @SuppressLint("SetTextI18n")
    public void checkAccounts() {
        try {
            accountExists = false;
            errorMsg.setText("");
            InputStream inS = context.openFileInput("accounts.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inS));

            String line;
            while((line = br.readLine())!=null) {
                acc = line.split(",");

                // go to main screen if the account exists
                if (acc[0].equals(newUser.getText().toString())) {
                    accountExists = true;
                    errorMsg.setTextColor(Color.RED);
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
            checkPasswordRequirements();
            if (passwordOK) {
                errorMsg.setText("");
                accounts.setCurrentUser(newUser.getText().toString());
                saveAccount();
                goToMain();
            }
        }
    }

    // executed whenever the password field text is changed
    @SuppressLint("SetTextI18n")
    public void checkPasswordRequirements() {
        errorMsg.setTextColor(Color.RED);

        pass = newPass.getText().toString();
        passwordCharSeq = pass.toCharArray();
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        Matcher m = p.matcher(pass);

        hasSpecial = false;
        hasDigit = false;
        hasCapital = false;
        hasSmall = false;
        isLongEnough = false;

        hasSpecial = m.find();
        if (hasSpecial) {
            hasSpecial = true;
        }
        for (char c : passwordCharSeq) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            }
            if (Character.isLowerCase(c)) {
                hasSmall = true;
            }
            if (pass.length() >= 12) {
                isLongEnough = true;
            }
        }

        if (hasSpecial) {
            passwordErrors[0] = "";
        } else {
            passwordErrors[0] = "Vähintään yksi erikoismerkki\n";
        }
        if (hasDigit) {
            passwordErrors[1] = "";
        } else {
            passwordErrors[1] = "Vähintään yksi numero\n";
        }
        if (hasCapital) {
            passwordErrors[2] = "";
        } else {
            passwordErrors[2] = "Vähintään yksi iso kirjain\n";
        }
        if (hasSmall) {
            passwordErrors[3] = "";
        } else {
            passwordErrors[3] = "Vähintään yksi pieni kirjain\n";
        }
        if (isLongEnough) {
            passwordErrors[4] = "";
        } else {
            passwordErrors[4] = "Vähintään 12 merkkiä (pituus " + pass.length() + ")\n";
        }

        errorMsg.setText(passwordErrors[0] + passwordErrors[1] + passwordErrors[2] + passwordErrors[3]+ passwordErrors[4]);

        if (hasSpecial & hasDigit & hasCapital & hasSmall & isLongEnough) {
            passwordOK = true;
            errorMsg.setTextColor(Color.GREEN);
            errorMsg.setText("Salasana OK");
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