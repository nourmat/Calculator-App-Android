package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btnc, btndel,btnpls, btnmns, btndiv, btnmul, btnzeros, btnmnspls, btneql;
    TextView tvmain, tvsmall;

    boolean isdotadded, displayed, canchangesign ,zerodivision ;
    int MAX_LENGTH;
    double sum = 0, lastEnteredNumber;
    char nextop = '+';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MAX_LENGTH = 10;
        isdotadded = false;
        displayed = false;
        canchangesign = true;
        zerodivision = false;
        lastEnteredNumber = 0;

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnDot = findViewById(R.id.btndot);

        btnc = findViewById(R.id.btnc);
        btndel = findViewById(R.id.btndel);

        btnpls = findViewById(R.id.btnpls);
        btnmns = findViewById(R.id.btnmns);
        btndiv = findViewById(R.id.btndiv);
        btnmul = findViewById(R.id.btnmul);
        btnzeros = findViewById(R.id.btnzeros);
        btnmnspls = findViewById(R.id.btnmnspls);
        btneql = findViewById(R.id.btneql);

        tvmain = findViewById(R.id.tvmain);
        tvsmall = findViewById(R.id.tvsmall);

        tvmain.setText("");
        tvsmall.setText("");

        View.OnClickListener numbersListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zerodivision){
                    cleartext();
                    tvsmall.setText("");
                    sum = 0;
                    nextop = '+';
                    zerodivision = false;
                }
                if (displayed)
                    cleartext();
                canchangesign = false;

                Button temp = (Button) view;
                String s = temp.getText().toString();
                String total = tvmain.getText().toString();

                if (total.equals("0")) { //if number is already zero (empty)
                    total = s;
                    tvmain.setText(total);
                    return;
                }
                //any other digit
                if (total.length() < MAX_LENGTH) {
                    total += s;
                    tvmain.setText(total);
                } else
                    Toast.makeText(getApplicationContext(), "Maximum Length reached", Toast.LENGTH_SHORT).show();

            }
        };

        btn9.setOnClickListener(numbersListner);
        btn8.setOnClickListener(numbersListner);
        btn7.setOnClickListener(numbersListner);
        btn6.setOnClickListener(numbersListner);
        btn5.setOnClickListener(numbersListner);
        btn4.setOnClickListener(numbersListner);
        btn3.setOnClickListener(numbersListner);
        btn2.setOnClickListener(numbersListner);
        btn1.setOnClickListener(numbersListner);
        btn0.setOnClickListener(numbersListner);

        View.OnClickListener actionListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zerodivision) {
                    Toast.makeText(getApplicationContext(), "Please Clear Or write numbers", Toast.LENGTH_SHORT).show();
                    return;
                }
                String btnstr = ((Button) view).getText().toString();
                String mainstr = tvmain.getText().toString();

                if (mainstr.length() != 0) {
                    if (!canchangesign) {
                        Log.d("piv", "onClick: before calc");
                        calc();
                        Log.d("piv", "onClick after calc");
                    }
                    switch (btnstr) {
                        case "+":
                            nextop = '+';
                            break;

                        case "-":
                            nextop = '-';
                            break;

                        case "*":
                            nextop = '*';
                            break;

                        case "/":
                            nextop = '/';
                            break;
                    }
                    char lastchar;
                    String smalltemp = tvsmall.getText().toString();
                    if (smalltemp.length() > 0) {
                        lastchar = smalltemp.charAt(smalltemp.length() - 1);
                        if (lastchar == '/' || lastchar == '*' || lastchar == '-' || lastchar == '+')
                            tvsmall.setText(smalltemp.substring(0, smalltemp.length() - 1) + nextop);
                        else
                            tvsmall.setText(tvmain.getText().toString() + "" + nextop);
                    }
                }else
                    Toast.makeText(getApplicationContext(),"Empty", Toast.LENGTH_SHORT);
            }
        };

        btnpls.setOnClickListener(actionListener);
        btnmns.setOnClickListener(actionListener);
        btndiv.setOnClickListener(actionListener);
        btnmul.setOnClickListener(actionListener);

        btnmnspls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mainstr = tvmain.getText().toString();
                if (mainstr.charAt(0) != '-') //not -
                    mainstr = "-" + mainstr;
                else
                    mainstr = mainstr.substring(1);
                lastEnteredNumber = Double.parseDouble(mainstr);
                tvmain.setText(mainstr);
            }
        });


        btneql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!zerodivision) {
                    if (displayed) {
                        calc2();
                        tvsmall.setText(tvmain.getText().toString() + "" + nextop);
                    }
                    else if (tvmain.getText().toString().length() > 0) {
                        calc();
                        if (!zerodivision)
                            tvsmall.setText(tvmain.getText().toString() + "" + nextop);
                    }
                    else {
                        tvmain.setText(tvsmall.getText().toString().substring(0, tvsmall.getText().toString().length()-1));
                    }
                }else{
                    cleartext();
                    tvsmall.setText("");
                    sum = 0;
                    nextop = '+';
                    zerodivision = false;
                }
            }
        });


        btnzeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (displayed)
                    cleartext();
                String total = tvmain.getText().toString();

                if (total.length() == 0 || total.equals("0")) { //if number is already zero (empty)
                    total = "0";
                    tvmain.setText(total);
                    return;
                }
                //any other digit
                else if (total.length() <= MAX_LENGTH - 2) {
                    total += "00";
                    tvmain.setText(total);
                }
                else if(total.length() < MAX_LENGTH){
                    total += "0";
                    tvmain.setText(total);
                }
                else
                    Toast.makeText(getApplicationContext(), "Maximum Length reached", Toast.LENGTH_SHORT).show();
            }
        });

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleartext();
                tvsmall.setText("");
                sum = 0;
                nextop = '+';
                zerodivision = false;
            }
        });

        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (displayed){
                    tvmain.setText("");
                    isdotadded = false;
                    displayed = false;
                    return;
                }
                String s = tvmain.getText().toString();
                if (s.length() > 1) {
                    if (s.charAt(s.length() - 1) == '.')
                        isdotadded = false;
                    s = s.substring(0, s.length() - 1);
                    tvmain.setText(s);
                } else if (s.length() == 1) {
                    tvmain.setText("");
                    isdotadded = false;
                }
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isdotadded) {
                    String s = tvmain.getText().toString();
                    if (s.length() <= MAX_LENGTH - 2) {
                        if (s.length() == 0)
                            s = "0.";
                        else
                            s += ".";
                        tvmain.setText(s);
                        isdotadded = true;
                    }
                } else {
                    Log.d("tag", "dot exist");
                    Toast.makeText(getApplicationContext(), "the dot already exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void cleartext (){
        tvmain.setText("");
        isdotadded = false;
        displayed = false;
    }

    /**
     * this function will be called and there will be a value in tvmain
     */
    void calc() {
        String valuestr = tvmain.getText().toString();
        String valuestrsmall = tvsmall.getText().toString();
        double value = Double.parseDouble(valuestr);
        lastEnteredNumber = value;
        Log.d("piv", "calc: " + value);
        switch (nextop){
            case '+':
                sum += value;
                Log.d("piv", "calc: am in " + sum);
                break;
            case '-':
                sum -= value;
                break;
            case '*':
                sum *= value;
                break;
            case '/':
                if (value == 0) {
                    Log.d("piv", "calc1: can't divide by zero");
                    tvmain.setText("Can't divide by zero");
                    zerodivision = true;
                    displayed = true;
                    canchangesign = true;
                    tvsmall.setText("");
                    return;
                }
                else
                    sum /= value;
                break;
        }
        tvsmall.setText(valuestrsmall + valuestr);
        tvmain.setText(""+sum);
        displayed = true;
        canchangesign = true;
    }

    void calc2(){
        String valuestr = tvmain.getText().toString();
        double value = Double.parseDouble(valuestr);
        Log.d("piv", "calc: " + value);
        switch (nextop){
            case '+':
                sum += lastEnteredNumber;
                Log.d("piv", "calc: am in " + sum);
                break;
            case '-':
                sum -= lastEnteredNumber;
                break;
            case '*':
                sum *= lastEnteredNumber;
                break;
            case '/':
                if (value == 0) {
//                    Toast.makeText(getApplicationContext(), "Can't divide by zero", Toast.LENGTH_SHORT);
//                    Log.d("piv", "calc: can't divide by zero");
                    tvmain.setText("Can't divide by zero");
                    displayed = true;
                    canchangesign = true;
                    zerodivision = true;
                    tvsmall.setText("");
                    return;
                }
                else
                    sum /= lastEnteredNumber;
                break;
        }
        tvmain.setText(""+sum);
        displayed = true;
        canchangesign = true;
    }
}
