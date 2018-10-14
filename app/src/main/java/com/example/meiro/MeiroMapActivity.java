package com.example.meiro;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.meiro.Constant.Constant;

public class MeiroMapActivity extends AppCompatActivity {

    private MeiroFragment mFragment;

    private Button mGiveUp;
    private Button mReturn;

    private Button mMoveUp;
    private Button mMoveDown;
    private Button mMoveRight;
    private Button mMoveLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int x = intent.getIntExtra(Constant.INTENT_KEY_X, 0);
        int y = intent.getIntExtra(Constant.INTENT_KEY_Y, 0);

        setContentView(R.layout.activity_meiro_map);
        mGiveUp = (Button) findViewById(R.id.give_up_button);
        mReturn = (Button) findViewById(R.id.return_home_button);

        mMoveUp    = (Button) findViewById(R.id.move_up);
        mMoveDown  = (Button) findViewById(R.id.move_down);
        mMoveRight = (Button) findViewById(R.id.move_right);
        mMoveLeft  = (Button) findViewById(R.id.move_left);

        mFragment = MeiroFragment.of(x, y);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.meiro_fragment, mFragment);
        transaction.commit();

        setUp();
    }

    private void setUp(){

        mGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.endMeiro();
                showButton(false);
            }
        });
        showButton(true);

        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mMoveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.moveUp();
            }
        });

        mMoveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.moveDown();
            }
        });

        mMoveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.moveRight();
            }
        });

        mMoveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.moveLeft();
            }
        });
    }

    private void showButton(boolean giveUpFLg) {
        if (giveUpFLg) {
            mGiveUp.setVisibility(View.VISIBLE);
            mReturn.setVisibility(View.GONE);
        } else {
            mGiveUp.setVisibility(View.GONE);
            mReturn.setVisibility(View.VISIBLE);
        }
    }

}
