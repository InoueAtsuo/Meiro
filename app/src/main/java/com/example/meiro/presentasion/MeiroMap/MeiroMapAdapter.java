package com.example.meiro.presentasion.MeiroMap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.meiro.Constant.Constant;
import com.example.meiro.Constant.Constant.WALL;
import com.example.meiro.Data.BlocksCreate;
import com.example.meiro.Data.BlocksMove;
import com.example.meiro.R;
import com.example.meiro.Util.MeiroUtil;

public class MeiroMapAdapter extends RecyclerView.Adapter<MeiroMapAdapter.ViewHolder> {

    private BlocksMove mBlocksMove;
    private Context mContext;
    private String mPosition;

    private MeiroMapAdapter(Context context, BlocksCreate blocksCreate, String position) {
        mContext = context;
        mBlocksMove = BlocksMove.of(blocksCreate.getMaxX() + 2, blocksCreate.getMaxY() + 2);
        mBlocksMove.setWallData(blocksCreate.getDatas());
        mPosition = position;
        mBlocksMove.addSearch(mPosition);
    }

    public static MeiroMapAdapter of(Context context, BlocksCreate blocksCreate, String position) {
        MeiroMapAdapter adapter = new MeiroMapAdapter(context, blocksCreate, position);
        return adapter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String key = mBlocksMove.getKeys().get(i);
        setImage(viewHolder, key);
    }

    @Override
    public int getItemCount() {
        return mBlocksMove.getKeys().size();
    }

    public void viewMeiro(String position) {
        mPosition = position;
        notifyDataSetChanged();
    }

    public BlocksMove getBlocksMove() {
        return mBlocksMove;
    }

    public void showAll() {
        mBlocksMove.showAll();
        notifyDataSetChanged();
    }

    private void setImage(ViewHolder viewHolder, String key) {

        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);
        WALL wall = mBlocksMove.getWall(key);
        boolean isSearch = mBlocksMove.isSearch(key);

        ImageView imageTop   = (ImageView) viewHolder.itemView.findViewById(R.id.block_top);
        ImageView imageBot   = (ImageView) viewHolder.itemView.findViewById(R.id.block_bot);
        ImageView imageRight = (ImageView) viewHolder.itemView.findViewById(R.id.block_right);
        ImageView imageLeft  = (ImageView) viewHolder.itemView.findViewById(R.id.block_left);

        ImageView imageBox   = (ImageView) viewHolder.itemView.findViewById(R.id.block_box);

        ImageView imageDotTopLeft  = (ImageView) viewHolder.itemView.findViewById(R.id.dot_top_left);
        ImageView imageDotTopRight = (ImageView) viewHolder.itemView.findViewById(R.id.dot_top_right);
        ImageView imageDotBotLeft  = (ImageView) viewHolder.itemView.findViewById(R.id.dot_bot_left);
        ImageView imageDotBotRight = (ImageView) viewHolder.itemView.findViewById(R.id.dot_bot_right);

        if (x == mBlocksMove.getMaxX() - 1) {
            imageTop.setVisibility(View.GONE);
            imageDotTopRight.setVisibility(View.GONE);
            imageBox.setVisibility(View.GONE);
            imageRight.setVisibility(View.GONE);
            imageBot.setVisibility(View.GONE);
            imageDotBotRight.setVisibility(View.GONE);
        }

        if (y == 0) {
            imageLeft.setVisibility(View.GONE);
            imageBox.setVisibility(View.GONE);
            imageRight.setVisibility(View.GONE);
            imageDotBotLeft.setVisibility(View.GONE);
            imageBot.setVisibility(View.GONE);
            imageDotBotRight.setVisibility(View.GONE);
        }

        if (!isSearch) {
            imageDotTopLeft.setImageResource(R.drawable.block_dot_gray);
            imageDotTopRight.setImageResource(R.drawable.block_dot_gray);
            imageDotBotLeft.setImageResource(R.drawable.block_dot_gray);
            imageDotBotRight.setImageResource(R.drawable.block_dot_gray);

            imageTop.setImageResource(R.drawable.block_horizontal_gray);
            imageBot.setImageResource(R.drawable.block_horizontal_gray);
            imageRight.setImageResource(R.drawable.block_vertical_gray);
            imageLeft.setImageResource(R.drawable.block_vertical_gray);

            imageBox.setImageResource(R.drawable.block_box_gray);

            return;
        }

        if (y == mBlocksMove.getMaxY() - 1) {
            imageDotTopLeft.setImageResource(R.drawable.block_dot_white);
            imageTop.setImageResource(R.drawable.block_horizontal_white);
            imageDotTopRight.setImageResource(R.drawable.block_dot_white);
            imageLeft.setImageResource(R.drawable.block_vertical_white);
            imageRight.setImageResource(R.drawable.block_vertical_white);
        }
        if (y != 0) {
            WALL botWall = mBlocksMove.getWall(MeiroUtil.getKey(x, y - 1));
            switch(botWall){
                case WALL_ALL:
                case WALL_HORIZONTAL:
                    imageBot.setImageResource(R.drawable.block_horizontal_black);
                    break;
                case WALL_VERTICAL:
                case WALL_NONE:
                    imageBot.setImageResource(R.drawable.block_horizontal_white);
                    break;
            }
        }

        if (x == 0) {
            imageDotTopLeft.setImageResource(R.drawable.block_dot_white);
            imageTop.setImageResource(R.drawable.block_horizontal_white);
            imageLeft.setImageResource(R.drawable.block_vertical_white);
            imageDotBotLeft.setImageResource(R.drawable.block_dot_white);
            imageBot.setImageResource(R.drawable.block_horizontal_white);
        }
        else {
            WALL leftWall = mBlocksMove.getWall(MeiroUtil.getKey(x - 1, y));
            switch(leftWall){
                case WALL_ALL:
                case WALL_VERTICAL:
                    imageLeft.setImageResource(R.drawable.block_vertical_black);
                    break;
                case WALL_HORIZONTAL:
                case WALL_NONE:
                    imageLeft.setImageResource(R.drawable.block_vertical_white);
                    break;
            }
        }

        switch(wall){
            case WALL_ALL:
                imageTop.setImageResource(R.drawable.block_horizontal_black);
                imageRight.setImageResource(R.drawable.block_vertical_black);
                break;
            case WALL_VERTICAL:
                imageTop.setImageResource(R.drawable.block_horizontal_white);
                imageRight.setImageResource(R.drawable.block_vertical_black);
                break;
            case WALL_HORIZONTAL:
                imageTop.setImageResource(R.drawable.block_horizontal_black);
                imageRight.setImageResource(R.drawable.block_vertical_white);
                break;
            case WALL_NONE:
                imageTop.setImageResource(R.drawable.block_horizontal_white);
                imageRight.setImageResource(R.drawable.block_vertical_white);
                break;
        }

        if (key.equals(mPosition)) {
            imageBox.setImageResource(R.drawable.block_box_red);
        }
        else {
            imageBox.setImageResource(R.drawable.block_box_white);
        }
    }
}
