/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.CallBackHomeIntent;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class FundingAdapter extends RecyclerView.Adapter {
    private final ArrayList<GameVO> fundingList;
    private CallBackHomeIntent callBackHomeIntent;

    public FundingAdapter(List<GameVO> fundingList, CallBackHomeIntent callbackHomeIntent) {
        this.fundingList = (ArrayList<GameVO>) fundingList;
        this.callBackHomeIntent = callbackHomeIntent;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_funding_layout, parent, false);
        return new FundingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        FundingViewHolder fundingViewHolder = (FundingViewHolder) holder;
        fundingViewHolder.getGameName().setText(fundingList.get(position).getName());
        if(position == 0)
            fundingViewHolder.getFundingLayout().setMinHeight(360);
        else
            fundingViewHolder.getFundingLayout().setMinHeight(480);

        fundingViewHolder.getFundingLayout().setOnClickListener(v ->
                callBackHomeIntent.callback(fundingViewHolder.getGameImage(), fundingList.get(position)));
    }

    @Override
    public int getItemCount() {
        return fundingList.size();
    }

    public static class FundingViewHolder extends RecyclerView.ViewHolder{
        private TextView gameName;
        private ConstraintLayout fundingLayout;
        private ImageView gameImage;
        FundingViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.funding_game_name);
            fundingLayout = itemView.findViewById(R.id.funding_item_layout);
            gameImage = itemView.findViewById(R.id.funding_game_image);
        }
        TextView getGameName() { return gameName; }
        ConstraintLayout getFundingLayout() {
            return fundingLayout;
        }
        ImageView getGameImage() {
            return gameImage;
        }
    }
}
