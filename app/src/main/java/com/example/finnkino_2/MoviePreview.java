
package com.example.finnkino_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MoviePreview extends AppCompatActivity {

    TextView title;
    TextView year;
    TextView details;
    EditText comment;
    RatingBar rating;
    Button sendReview;
    ListView commentsList;
    MovieLibrary mLibrary = MovieLibrary.getInstance();
    Accounts accounts = Accounts.getInstance();
    Context context = this;
    ArrayList<String> ratings;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_preview);

        title = findViewById(R.id.textViewTitle);
        year = findViewById(R.id.textViewYear);
        details = findViewById(R.id.textViewDetails);
        rating = findViewById(R.id.ratingBar);
        comment = findViewById(R.id.editTextComment);
        sendReview = findViewById(R.id.reviewButton);
        commentsList = findViewById(R.id.listViewComments);

        title.setText(mLibrary.returnMovie().getName());
        year.setText(mLibrary.returnMovie().getYear());
        details.setText(mLibrary.getTime() + "\n" + mLibrary.returnMovie().getGenre() + "\n" +
                "Ikärajoitus: " + mLibrary.returnMovie().getAge() + "\nKesto: " + mLibrary.returnMovie().getLength() + " min");

        updateComments();
    }

    public MoviePreview() {
    }

    public void sendReviewBut(View v) {
        if (rating.getRating() != 0.0 | !comment.getText().toString().equals("")) {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("ratings.txt", Context.MODE_APPEND));
                writer.write(mLibrary.returnMovie().getID() + ";" + accounts.getCurrentUser() + ";" + rating.getRating() + ";" + comment.getText().toString() + " \n");
                writer.close();

            } catch (IOException e) {
            e.printStackTrace();
            }
        }
        updateComments();
    }

    public void updateComments() {
        try {
            InputStream inS = context.openFileInput("ratings.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inS));

            String line;
            String[] ratingInfo;
            ratings = new ArrayList<>();

            while((line = br.readLine())!=null) {
                ratingInfo = line.split(";");

                // go to main screen if the account exists
                if (ratingInfo[0].equals(mLibrary.returnMovie().getID())) {
                    ratings.add(ratingInfo[1] + "   " + ratingInfo[2] + "\n" + ratingInfo[3] + "\n");
                }
            }
            inS.close();
            br.close();

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.textstyle, ratings);
            commentsList.setAdapter(arrayAdapter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}