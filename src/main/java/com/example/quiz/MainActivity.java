package com.example.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String QUIZ_TAG = "MainActivity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;
    private static boolean answerWasShown;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextVIew;
    private Question[] questions = new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_version, false),
    };
    private int currentIndex = 0;
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i(QUIZ_TAG, "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(QUIZ_TAG, "Wywołana zostala metoda cyklu życia; onCreate");

        if (savedInstanceState != null)
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);

        setContentView(R.layout.activity_main);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.prompt_button);
        questionTextVIew = findViewById(R.id.question_text_view);
        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentIndex = (currentIndex + 1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });
        promptButton.setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);

            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });

        setNextQuestion();
    }
    protected void onStart(){
        super.onStart();
        Log.i(QUIZ_TAG, "Wywołana zostala metoda cyklu życia; onStart");
    }
    protected void onResume(){
        super.onResume();
        Log.i(QUIZ_TAG, "Wywołana zostala metoda cyklu życia; onResume");
    }
    protected void onPause(){
        super.onPause();
        Log.i(QUIZ_TAG, "Wywołana zostala metoda cyklu życia; onPause");
    }
    protected void onStop(){
        super.onStop();
        Log.i(QUIZ_TAG, "Wywołana zostala metoda cyklu życia; onStop");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i(QUIZ_TAG, "Wywołana zostala metoda cyklu życia; onDestroy");
    }
    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if (answerWasShown)
            resultMessageId = R.string.answer_was_shown;
        else {
            if (userAnswer == correctAnswer)
                resultMessageId = R.string.correct_answer;
            else
                resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }
    private void setNextQuestion(){
        questionTextVIew.setText(questions[currentIndex].getQuestionId());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){return;}
        if(requestCode == REQUEST_CODE_PROMPT){
            if(data == null ){return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
}