package com.medi.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.StackView;

import java.util.ArrayList;
import java.util.List;


public class firstAid extends AppCompatActivity {
    StackView stackView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);
        stackView = findViewById(R.id.stack_view);

        AdapterForStackView adapter = new AdapterForStackView(numberword(),numberImage(),R.layout.firstaiditem,firstAid.this);
        stackView.setAdapter(adapter);

    }

    private List<String> numberword(){
        List<String> word = new ArrayList<>();
        word.add("Bee Sting");

        word.add("Nose Bleeding");

        word.add("Fracture");

        word.add("Burn");

        word.add("Bleeding");

        word.add("Cardiac Arrest");

        word.add("Blister");

        return word;
    }

    private List<Integer> numberImage(){
        List<Integer> image = new ArrayList<>();
        image.add(R.drawable.beesting);
        image.add(R.drawable.nosebleeding);
        image.add(R.drawable.fracture);
        image.add(R.drawable.burn);
        image.add(R.drawable.bleeding);
        image.add(R.drawable.cardiac);
        image.add(R.drawable.blister);
        return image;
    }
}
