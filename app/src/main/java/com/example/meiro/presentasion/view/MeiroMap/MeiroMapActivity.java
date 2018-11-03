package com.example.meiro.presentasion.view.MeiroMap;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.meiro.Constant.Constant;
import com.example.meiro.Constant.Constant.POSITION;
import com.example.meiro.R;

public class MeiroMapActivity extends AppCompatActivity {

    private MeiroMapFragment mFragment;

    private Button mGiveUp;
    private Button mReturn;

    private RelativeLayout mMoveButtonArea;
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
        mGiveUp = findViewById(R.id.give_up_button);
        mReturn = findViewById(R.id.return_home_button);

        mMoveButtonArea = (RelativeLayout) findViewById(R.id.move_button_area);
        mMoveUp    = findViewById(R.id.move_up);
        mMoveDown  = findViewById(R.id.move_down);
        mMoveRight = findViewById(R.id.move_right);
        mMoveLeft  = findViewById(R.id.move_left);

        mFragment = MeiroMapFragment.of(x, y);
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
                showButton(true);
            }
        });

        showButton(false);

        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mMoveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFragment.isEndMove(POSITION.POSITION_UP)) {
                    mFragment.endMeiro();
                    showButton(true);
                }
                else {
                    mFragment.moveUp();
                }
            }
        });

        mMoveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFragment.isEndMove(POSITION.POSITION_DOWN)) {
                    mFragment.endMeiro();
                    showButton(true);
                }
                else {
                    mFragment.moveDown();
                }
            }
        });

        mMoveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFragment.isEndMove(POSITION.POSITION_RIGHT)) {
                    mFragment.endMeiro();
                    showButton(true);
                }
                else {
                    mFragment.moveRight();
                }
            }
        });

        mMoveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFragment.isEndMove(POSITION.POSITION_LEFT)) {
                    mFragment.endMeiro();
                    showButton(true);
                }
                else {
                    mFragment.moveLeft();
                }
            }
        });
    }

    private void showButton(boolean isEnd) {
        if (!isEnd) {
            mGiveUp.setVisibility(View.VISIBLE);
            mMoveButtonArea.setVisibility(View.VISIBLE);
            mMoveUp.setVisibility(View.VISIBLE);
            mMoveDown.setVisibility(View.VISIBLE);
            mMoveRight.setVisibility(View.VISIBLE);
            mMoveLeft.setVisibility(View.VISIBLE);
            mReturn.setVisibility(View.GONE);
        } else {
            mGiveUp.setVisibility(View.GONE);
            mMoveButtonArea.setVisibility(View.GONE);
            mMoveUp.setVisibility(View.GONE);
            mMoveDown.setVisibility(View.GONE);
            mMoveRight.setVisibility(View.GONE);
            mMoveLeft.setVisibility(View.GONE);
            mReturn.setVisibility(View.VISIBLE);
        }
    }

}
