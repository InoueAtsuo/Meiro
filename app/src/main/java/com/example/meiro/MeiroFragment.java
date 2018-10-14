package com.example.meiro;

import android.os.Bundle;
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
import com.example.meiro.Util.MeiroUtil;

public class MeiroFragment extends Fragment {

    private String mPosition;
    private BlocksCreate mBlocksCreate;
    private RecyclerAdapter mAdapter;

    public static MeiroFragment of(int x, int y) {
        Bundle args = new Bundle();
        args.putInt(Constant.INTENT_KEY_X, x);
        args.putInt(Constant.INTENT_KEY_Y, y);

        MeiroFragment fragment = new MeiroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        int x = args.getInt(Constant.INTENT_KEY_X);
        int y = args.getInt(Constant.INTENT_KEY_Y);

        mBlocksCreate = BlocksCreate.of(x, y);
        mBlocksCreate.createMeiro();

        mPosition = MeiroUtil.getKey(1, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.meiro_map, container, false);

        mAdapter = RecyclerAdapter.of(getActivity(), mBlocksCreate, mPosition);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(llm);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mBlocksCreate.getMaxX() + 2));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public boolean isEndMove(POSITION position) {
        return mAdapter.getBlocksMove().isEndMove(mPosition, position);
    }

    public void moveUp() {

        if(mAdapter.getBlocksMove().isMoveUp(mPosition)) {
            mPosition = MeiroUtil.getNextKey(mPosition, POSITION.POSITION_UP);
            mAdapter.getBlocksMove().addSearch(mPosition);
            mAdapter.viewMeiro(mPosition);
        }
    }

    public void moveDown() {
        if(mAdapter.getBlocksMove().isMoveDown(mPosition)) {
            mPosition = MeiroUtil.getNextKey(mPosition, POSITION.POSITION_DOWN);
            mAdapter.getBlocksMove().addSearch(mPosition);
            mAdapter.viewMeiro(mPosition);
        }
    }

    public void moveRight() {
        if(mAdapter.getBlocksMove().isMoveRight(mPosition)) {
            mPosition = MeiroUtil.getNextKey(mPosition, POSITION.POSITION_RIGHT);
            mAdapter.getBlocksMove().addSearch(mPosition);
            mAdapter.viewMeiro(mPosition);
        }
    }

    public void moveLeft() {
        if(mAdapter.getBlocksMove().isMoveLeft(mPosition)) {
            mPosition = MeiroUtil.getNextKey(mPosition, POSITION.POSITION_LEFT);
            mAdapter.getBlocksMove().addSearch(mPosition);
            mAdapter.viewMeiro(mPosition);
        }
    }

    public void endMeiro() {
        mAdapter.showAll();
    }
}
