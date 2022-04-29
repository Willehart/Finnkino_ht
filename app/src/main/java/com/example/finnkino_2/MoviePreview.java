
package com.example.finnkino_2;

import androidx.appcompat.app.AppCompatActivity;

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
    TextView details;
    EditText comment;
    RatingBar rating;
    Button sendReview;
    ListView commentsList;
    MovieLibrary mLibrary = MovieLibrary.getInstance();
    Context context = this;
    ArrayList<String> ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_preview);

        title = findViewById(R.id.textViewTitle);
        details = findViewById(R.id.textViewDetails);
        rating = findViewById(R.id.ratingBar);
        comment = findViewById(R.id.editTextComment);
        sendReview = findViewById(R.id.reviewButton);
        commentsList = findViewById(R.id.listViewComments);

        title.setText(mLibrary.returnMovie().getName());
        details.setText(mLibrary.returnMovie().getID());

        updateComments();
    }

    public MoviePreview() {
    }

    public void sendReviewBut(View v) {

        try {
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("ratings.txt", Context.MODE_APPEND));
            writer.write(mLibrary.returnMovie().getID() + ";" + " Lauri " + ";" + rating.getRating() + ";" + comment.getText().toString() + "\n");
            writer.close();

            updateComments();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    ratings.add(ratingInfo[1] + "   " + ratingInfo[2] + "\n" + ratingInfo[3]);
                }
            }
            inS.close();
            br.close();

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ratings);
            commentsList.setAdapter(arrayAdapter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}