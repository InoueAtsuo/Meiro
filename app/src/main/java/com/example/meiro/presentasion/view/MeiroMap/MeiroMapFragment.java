package com.example.meiro.presentasion.view.MeiroMap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meiro.Constant.Constant;
import com.example.meiro.Constant.Constant.POSITION;
import com.example.meiro.Data.BlocksCreate;
import com.example.meiro.R;
import com.example.meiro.Util.MeiroUtil;

public class MeiroMapFragment extends Fragment {

    private String mPosition;
    private BlocksCreate mBlocksCreate;
    private MeiroMapAdapter mAdapter;
    private POSITION mDirecition;

    public static MeiroMapFragment of(int x, int y) {
        Bundle args = new Bundle();
        args.putInt(Constant.INTENT_KEY_X, x);
        args.putInt(Constant.INTENT_KEY_Y, y);

        MeiroMapFragment fragment = new MeiroMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        int x = 0;
        int y = 0;
        if (args != null) {
            x = args.getInt(Constant.INTENT_KEY_X);
            y = args.getInt(Constant.INTENT_KEY_Y);
        }

        mBlocksCreate = BlocksCreate.of(x, y);
        mBlocksCreate.createMeiro();

        mPosition = MeiroUtil.getKey(1, 1);
        mDirecition = POSITION.POSITION_UP;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.meiro_map, container, false);

        mAdapter = MeiroMapAdapter.of(mBlocksCreate, mPosition, mDirecition);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(llm);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mBlocksCreate.getMaxX() + 2));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public boolean isEndMove() {
        return mAdapter.getBlocksMove().isEndMove(mPosition, mDirecition);
    }

    public void moveUp() {
        if(mAdapter.getBlocksMove().isMove(mPosition, mDirecition)) {
            mPosition = MeiroUtil.getNextKey(mPosition, mDirecition);
            mAdapter.getBlocksMove().addSearch(mPosition);
            mAdapter.viewMeiro(mPosition, mDirecition);
        }
    }

    public void moveDown() {
        switch (mDirecition) {
            case POSITION_UP:
                mDirecition = POSITION.POSITION_DOWN;
                break;
            case POSITION_RIGHT:
                mDirecition = POSITION.POSITION_LEFT;
                break;
            case POSITION_DOWN:
                mDirecition = POSITION.POSITION_UP;
                break;
            case POSITION_LEFT:
                mDirecition = POSITION.POSITION_RIGHT;
                break;
        }
        mAdapter.viewMeiro(mPosition, mDirecition);
    }

    public void moveRight() {
        switch (mDirecition) {
            case POSITION_UP:
                mDirecition = POSITION.POSITION_RIGHT;
                break;
            case POSITION_RIGHT:
                mDirecition = POSITION.POSITION_DOWN;
                break;
            case POSITION_DOWN:
                mDirecition = POSITION.POSITION_LEFT;
                break;
            case POSITION_LEFT:
                mDirecition = POSITION.POSITION_UP;
                break;
        }
        mAdapter.viewMeiro(mPosition, mDirecition);
    }

    public void moveLeft() {
        switch (mDirecition) {
            case POSITION_UP:
                mDirecition = POSITION.POSITION_LEFT;
                break;
            case POSITION_RIGHT:
                mDirecition = POSITION.POSITION_UP;
                break;
            case POSITION_DOWN:
                mDirecition = POSITION.POSITION_RIGHT;
                break;
            case POSITION_LEFT:
                mDirecition = POSITION.POSITION_DOWN;
                break;
        }
        mAdapter.viewMeiro(mPosition, mDirecition);
    }

    public void endMeiro() {
        mAdapter.showAll();
    }
}
