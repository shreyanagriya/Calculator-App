package com.example.shreya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText edtNumber;
    private TextView tvResult;
    private Button btnDel, btnAc, btnPercent, btnPlus, btnMinus, btnMulti, btnDivide, btnEqual, btnDecimal, btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views by ID
        tvResult = findViewById(R.id.tvResult);
        edtNumber = findViewById(R.id.edtNumber);
        edtNumber = findViewById(R.id.edtNumber);
        btnDel = findViewById(R.id.btnDel);
        btnAc = findViewById(R.id.btnAc);
        btnPercent = findViewById(R.id.btnPercent);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMulti = findViewById(R.id.btnMulti);
        btnDivide = findViewById(R.id.btnDivide);
        btnEqual = findViewById(R.id.btnEqual);
        btnDecimal = findViewById(R.id.btnDecimal);
        btnZero = findViewById(R.id.btnZero);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven);
        btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine);

        // Set click listeners
        btnZero.setOnClickListener(v -> appendNumber("0"));
        btnOne.setOnClickListener(v -> appendNumber("1"));
        btnTwo.setOnClickListener(v -> appendNumber("2"));
        btnThree.setOnClickListener(v -> appendNumber("3"));
        btnFour.setOnClickListener(v -> appendNumber("4"));
        btnFive.setOnClickListener(v -> appendNumber("5"));
        btnSix.setOnClickListener(v -> appendNumber("6"));
        btnSeven.setOnClickListener(v -> appendNumber("7"));
        btnEight.setOnClickListener(v -> appendNumber("8"));
        btnNine.setOnClickListener(v -> appendNumber("9"));

        btnPlus.setOnClickListener(v -> appendOperator("+"));
        btnMinus.setOnClickListener(v -> appendOperator("-"));
        btnMulti.setOnClickListener(v -> appendOperator("*"));
        btnDivide.setOnClickListener(v -> appendOperator("/"));

        btnDecimal.setOnClickListener(v -> appendDecimal());
        btnDel.setOnClickListener(v -> deleteLastCharacter());
        btnAc.setOnClickListener(v -> clearEditText());
        btnPercent.setOnClickListener(v -> calculatePercentage());
        btnEqual.setOnClickListener(v -> calculateResult());
    }

    private void appendNumber(String number) {
        edtNumber.append(number);
    }

    private void appendOperator(String operator) {
        String currentText = edtNumber.getText().toString().trim();

        if (!currentText.isEmpty()) {
            char lastChar = currentText.charAt(currentText.length() - 1);
            if (lastChar != '+' && lastChar != '-' && lastChar != '*' && lastChar != '/') {
                edtNumber.append(operator);
            }
        }
    }

    private void appendDecimal() {
        String currentText = edtNumber.getText().toString().trim();

        if (currentText.isEmpty()) {
            edtNumber.append("0.");
        } else {
            char lastChar = currentText.charAt(currentText.length() - 1);
            if (lastChar != '.') {
                edtNumber.append(".");
            }
        }
    }

    private void deleteLastCharacter() {
        String currentText = edtNumber.getText().toString().trim();

        if (!currentText.isEmpty()) {
            edtNumber.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    private void clearEditText() {
        edtNumber.setText("");
    }

    private void calculatePercentage() {
        String currentText = edtNumber.getText().toString().trim();

        if (!currentText.isEmpty()) {
            double value = Double.parseDouble(currentText) / 100;
            edtNumber.setText(String.valueOf(value));
        }
    }

    //    private void calculateResult() {
//        String currentText = edtNumber.getText().toString().trim();
//
//        if (!currentText.isEmpty()) {
//            try {
//                double result = eval(currentText);
//                edtNumber.setText(String.valueOf(result));
//            } catch (Exception e) {
//                edtNumber.setText("Error");
//            }
//        }
//    }
    private void calculateResult() {
        String currentText = edtNumber.getText().toString().trim();

        if (!currentText.isEmpty()) {
            try {
                double result = eval(currentText);
                tvResult.setText(String.valueOf(result));
            } catch (Exception e) {
                tvResult.setText("Error");
            }
        }
    }

    private double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                return x;
            }
        }.parse();
    }
}

