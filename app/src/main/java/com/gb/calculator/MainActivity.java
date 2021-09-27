package com.gb.calculator;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String CALCULATION_KEY = "calculation_key";
    Calculator calculator = new Calculator();
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
    Button commaButton;
    Button resultButton;
    ImageButton settingsImageButton;
    TextView resultTextView;
    ActivityResultLauncher<Intent> settingsLauncher;
    boolean lastIsOpenParenthesis = false;
    boolean lastIsCloseParenthesis = false;
    boolean lastInputIsAction = false;
    int parenthesisCounter = 0;
    boolean pointUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewsAndSetActions();
        settingsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (Activity.RESULT_OK == result.getResultCode()) {
                Intent data = result.getData();
                boolean themeSelectionFlag = data.getBooleanExtra(SettingsActivity.KEY, false);
                if(themeSelectionFlag){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                recreate();

            }
            });

        }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CALCULATION_KEY, resultTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resultTextView.setText(savedInstanceState.getString(CALCULATION_KEY));
    }

    private void setActionsForButtons(List<Button> numbers, List<Button> actions) {
        for (Button numberButton : numbers) {
            numberButton.setOnClickListener(view -> {
                if (lastIsCloseParenthesis) {
                    resultTextView.append("*");
                    resultTextView.append(numberButton.getText().toString());
                    lastIsCloseParenthesis = false;
                } else {
                    resultTextView.append(numberButton.getText().toString());
                    lastInputIsAction = false;
                }
                lastIsOpenParenthesis = false;
            });
        }
        for (Button actionButton : actions) {
            actionButton.setOnClickListener(view -> {
                if (resultTextView.getText().toString().isEmpty() || (lastInputIsAction && parenthesisCounter > 0)) {
                    Toast.makeText(this, "Сначала введите число или скобку", Toast.LENGTH_LONG).show();
                } else if (lastInputIsAction) {
                    String temp = resultTextView.getText().toString();
                    temp = temp.substring(0, temp.length() - 1);
                    resultTextView.setText(temp);
                    resultTextView.append(actionButton.getText().toString());
                    pointUsed = false;
                } else {
                    resultTextView.append(actionButton.getText().toString());
                    lastInputIsAction = true;
                    pointUsed = false;
                    lastIsCloseParenthesis = false;
                }

            });
        }

        clearButton.setOnClickListener(view -> {
            lastIsOpenParenthesis = false;
            lastIsCloseParenthesis = false;
            lastInputIsAction = false;
            parenthesisCounter = 0;
            pointUsed = false;
            resultTextView.setText("");

        });
        closeParenthesisButton.setOnClickListener(view -> {
            if (resultTextView.getText().toString().isEmpty()) {
                Toast.makeText(this, "Скобка не открыта", Toast.LENGTH_LONG).show();
            }
            if (!lastInputIsAction) {
                if (parenthesisCounter > 0) {
                    resultTextView.append(closeParenthesisButton.getText().toString());
                    lastIsCloseParenthesis = true;
                    parenthesisCounter--;
                    pointUsed = false;
                } else {
                    Toast.makeText(this, R.string.toast_parenthesis_err, Toast.LENGTH_LONG);
                }
            }
        });
        openParenthesisButton.setOnClickListener(view -> {
            if (!resultTextView.getText().toString().isEmpty() && (!lastInputIsAction)) {
                resultTextView.append(multiplicationButton.getText().toString());
                resultTextView.append(openParenthesisButton.getText().toString());
                lastIsOpenParenthesis = true;
                lastIsCloseParenthesis = false;
                parenthesisCounter++;
                lastInputIsAction = true;
                pointUsed = false;
            } else {
                resultTextView.append(openParenthesisButton.getText().toString());
                lastIsOpenParenthesis = true;
                lastIsCloseParenthesis = false;
                parenthesisCounter++;
            }
        });
        commaButton.setOnClickListener(view -> {
            if (!pointUsed) {
                if (lastInputIsAction) {
                    resultTextView.append(zeroButton.getText().toString());
                    resultTextView.append(".");
                    lastInputIsAction = false;
                    pointUsed = true;
                } else if (resultTextView.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Введите число", Toast.LENGTH_LONG).show();
                } else {
                    if (lastIsOpenParenthesis) {
                        resultTextView.append(zeroButton.getText().toString());
                        resultTextView.append(".");
                        pointUsed = true;
                        lastIsOpenParenthesis = true;
                    } else if (lastIsCloseParenthesis) {
                        Toast.makeText(this, "Введите число", Toast.LENGTH_LONG).show();
                    } else {
                        resultTextView.append(".");
                        pointUsed = true;
                    }
                }
            }
        });
        resultButton.setOnClickListener(view -> {
            double result = calculator.result(resultTextView.getText().toString());
            resultTextView.setText(String.valueOf(result));
        });
        settingsImageButton.setOnClickListener(view -> {
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivityForResult(openSettings, SettingsActivity.THEME_SELECTOR_KEY);
        });

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
        commaButton = findViewById(R.id.point_button);
        resultButton = findViewById(R.id.result_button);
        resultTextView = findViewById(R.id.result_text_view);
        settingsImageButton = findViewById(R.id.settings_button);
        setActionsForButtons(numbers, actions);


    }
}