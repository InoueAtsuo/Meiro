package com.example.meiro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.meiro.Constant.Constant;
import com.example.meiro.Util.MeiroUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button level_1;
    private Button level_2;
    private Button level_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        level_1 = (Button) findViewById(R.id.level_1);
        level_2 = (Button) findViewById(R.id.level_2);
        level_3 = (Button) findViewById(R.id.level_3);

        setUp();
    }

    private void setUp() {
        level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(5, 5);
            }
        });
        level_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(8, 8);
            }
        });
        level_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(10, 10);
            }
        });
    }

    private void move(int x, int y) {
        Intent intent = new Intent(this, MeiroMapActivity.class);
        intent.putExtra(Constant.INTENT_KEY_X, x);
        intent.putExtra(Constant.INTENT_KEY_Y, y);
        startActivity(intent);
    }
}
