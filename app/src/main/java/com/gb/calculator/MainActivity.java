package com.gb.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    List<Button> numbers;
    List<Button> actions;
    Button zeroButton;
    Button oneButton;
    Button twoButton;
    Button threeButton;
    Button fourButton;
    Button fiveButton;
    Button sixButton;
    Button sevenButton;
    Button eightButton;
    Button nineButton;
    Button clearButton;
    Button openParenthesisButton;
    Button closeParenthesisButton;
    Button divisionButton;
    Button multiplicationButton;
    Button subtractionButton;
    Button additionButton;
    Button toNegativeButton;
    Button pointButton;
    Button resultButton;
    TextView userTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewsAndSetActions();
        setActionsForButtons(numbers, actions);


    }

    private void setActionsForButtons(List<Button> numbers, List<Button> actions) {
        for (Button numberButton : numbers) {
            numberButton.setOnClickListener(view -> {
                userTextView.append(numberButton.getText().toString());
            });
        }
    }

    private void initViewsAndSetActions() {
        numbers = new ArrayList<>();
        actions = new ArrayList<>();
        numbers.add(zeroButton = findViewById(R.id.zero_button));
        numbers.add(oneButton = findViewById(R.id.one_button));
        numbers.add(twoButton = findViewById(R.id.two_button));
        numbers.add(threeButton = findViewById(R.id.three_button));
        numbers.add(fourButton = findViewById(R.id.four_button));
        numbers.add(fiveButton = findViewById(R.id.fife_button));
        numbers.add(sixButton = findViewById(R.id.six_button));
        numbers.add(sevenButton = findViewById(R.id.seven_button));
        numbers.add(eightButton = findViewById(R.id.eight_button));
        numbers.add(nineButton = findViewById(R.id.nine_button));
        clearButton = findViewById(R.id.clear_button);
        openParenthesisButton = findViewById(R.id.open_parenthesis__button);
        closeParenthesisButton = findViewById(R.id.close_parenthesis_button);
        actions.add(divisionButton = findViewById(R.id.division_button));
        actions.add(multiplicationButton = findViewById(R.id.multiplication_button));
        actions.add(subtractionButton = findViewById(R.id.subtraction_button));
        actions.add(additionButton = findViewById(R.id.addition_button));
        toNegativeButton = findViewById(R.id.to_negative_button);
        pointButton = findViewById(R.id.point_button);
        resultButton = findViewById(R.id.result_button);
        userTextView = findViewById(R.id.users_view);


    }
}