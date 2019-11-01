package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btnc, btndel;
    Button btnchange;
    TextView tvsrc, tvdst, tvwordsrc, tvworddst;

    boolean isdotadded;
    int MAX_LENGTH;
    float first_currency_to_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MAX_LENGTH = 10;
        isdotadded = false;
        first_currency_to_second = (float) 16.6;

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

        btnchange = findViewById(R.id.btnchange);

        tvsrc = findViewById(R.id.tvsrc);
        tvdst = findViewById(R.id.tvdst);
        tvwordsrc = findViewById(R.id.tvwordsrc);
        tvworddst = findViewById(R.id.tvworddst);

        tvsrc.setText("0");
        tvdst.setText("0");

        View.OnClickListener numbersListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button temp = (Button) view;
                String s = temp.getText().toString();
                String total = tvsrc.getText().toString();

                if (total.equals("0")) { //if number is already zero (empty)
                    total = s;
                    tvsrc.setText(total);
                    calc();
                    return;
                }
                //any other digit
                if (total.length() < MAX_LENGTH) {
                    total += s;
                    tvsrc.setText(total);
                    calc();
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

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvwordsrc.getText().toString().equals("EGP")) {
                    tvwordsrc.setText(getResources().getString(R.string.First_currency));
                    tvworddst.setText(getResources().getString(R.string.Second_currency));
                    calc();
                } else {
                    tvwordsrc.setText(getResources().getString(R.string.Second_currency));
                    tvworddst.setText(getResources().getString(R.string.First_currency));
                    calc();
                }
            }
        });

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvsrc.setText("0");
                isdotadded = false;
                calc();
            }
        });

        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = tvsrc.getText().toString();
                if (s.length() > 1) {
                    if (s.charAt(s.length() - 1) == '.')
                        isdotadded = false;
                    s = s.substring(0, s.length() - 1);
                    tvsrc.setText(s);
                    calc();
                } else if (s.length() == 1) {
                    tvsrc.setText("0");
                    isdotadded = false;
                    calc();
                }
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isdotadded) {
                    String s = tvsrc.getText().toString();
                    if (s.length() <= MAX_LENGTH - 2) {
                        if (s.equals("0"))
                            s = "0.";
                        else
                            s += ".";
                        tvsrc.setText(s);
                        calc();
                        isdotadded = true;
                    }
                } else {
                    Log.d("tag", "dot exist");
                    Toast.makeText(getApplicationContext(), "the dot already exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This function check what is the main word currency to change from
     * it reads what's in the src then make the required calculation to compute the dest
     */
    void calc() {
        String s = tvsrc.getText().toString();
        float n = Float.parseFloat(s);
        if (tvwordsrc.getText().toString().equals(getResources().getString(R.string.Second_currency)))
            n = n / first_currency_to_second;
        else if (tvwordsrc.getText().toString().equals(getResources().getString(R.string.First_currency)))
            n = n * first_currency_to_second;
        tvdst.setText(String.format("%.02f", n));
    }
}
