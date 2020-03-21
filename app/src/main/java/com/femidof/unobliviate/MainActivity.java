package com.femidof.unobliviate;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import

public class MainActivity extends AppCompatActivity {
    List<Flashcard> allFlashcards;
    FlashcardDatabase flashcardDatabase;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        allFlashcards = flashcardDatabase.getAllCards();
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView question = (TextView) findViewById(R.id.question);

        question.setText("Who is the 44th president of the Uniteed State of America");

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button","Hello");
                findViewById(R.id.question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.ic_card_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);
            }
        });

        TextView answer = (TextView) findViewById(R.id.flashcard_answer);
        answer.setText("Obama");
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String string1 = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String string2 = data.getExtras().getString("string2");

            TextView question = (TextView) findViewById(R.id.question);
            TextView answer = (TextView) findViewById(R.id.flashcard_answer);

            question.setText(string1);
            answer.setText(string2);
            flashcardDatabase.insertCard(new Flashcard(question, answer));
            data.getExtras().getString("question");
            data.getExtras().getString("answer");
            allFlashcards = flashcardDatabase.getAllCards();

        }
    }

}
