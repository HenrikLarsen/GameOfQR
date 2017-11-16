package com.example.henriklarsen.gameofqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText textInput;
    private String output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInput = (EditText) findViewById(R.id.textInput);

        Button qrCodeConverter = (Button)findViewById(R.id.qrCodeConverter);
        qrCodeConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                output = textInput.getText().toString();
                if(!output.isEmpty()){
                    launchActivity(output);
                }else{
                    Toast.makeText(MainActivity.super.getApplicationContext(), "Input field is empty!",  Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void launchActivity(String output){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("output", output);
        startActivity(intent);
    }

}
