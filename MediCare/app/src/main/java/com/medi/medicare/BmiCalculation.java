package com.medi.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BmiCalculation extends AppCompatActivity {
    EditText weightedittext,heighttedittext;
    Button result_id;
    TextView suggestionid,resulttxtid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculation);
        weightedittext = findViewById(R.id.weightedittext);
        heighttedittext = findViewById(R.id.heighttedittext);

        result_id = findViewById(R.id.result_id);

        suggestionid = findViewById(R.id.suggestionid);
        resulttxtid = findViewById(R.id.resulttxtid);

        result_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightfrom = weightedittext.getText().toString();
                String heightfrom = heighttedittext.getText().toString();

                if(weightfrom.equals(""))
                {
                    weightedittext.setError("Enter Your weight");
                    weightedittext.requestFocus();
                    return;
                }

                if(heightfrom.equals(""))
                {
                    heighttedittext.setError("Enter Your height");
                    heighttedittext.requestFocus();
                    return;
                }

                float weight = Float.parseFloat(weightfrom);
                float height = Float.parseFloat(heightfrom);

                float bmivalue = calculateor(weight,height);
                suggestionid.setText(interpete(bmivalue));
                resulttxtid.setText("Your BMI is: "+bmivalue);
            }
        });


    }

    private float calculateor(float weight, float height) {


        float finalfinalheight = (float) height/100;
        final float finalresult = weight/(finalfinalheight*finalfinalheight);

        return finalresult;
    }
    private String interpete(float bmivalue) {
        if (bmivalue < 18.5) {
            return "Opsss! You are underweight";
        } else if (bmivalue >= 18.5 && bmivalue <= 24.9) {
            return "Congratulations! You have a perfect weight";
        } else if (bmivalue >= 25 && bmivalue <= 29.9) {
            return "Warning!! You have an overweight!!!";
        } else
            return "Alarming!! Release weight as early as possible!";
    }
}