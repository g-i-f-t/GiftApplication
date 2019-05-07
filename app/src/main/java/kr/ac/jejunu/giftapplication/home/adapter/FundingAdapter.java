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
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class FundingAdapter extends RecyclerView.Adapter {
    private final ArrayList<GameVO> fundingList;
    private FundingAdapter.CallBack callBackHomeIntent;

    public FundingAdapter(List<GameVO> fundingList, FundingAdapter.CallBack callbackHomeIntent) {
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
        double percentage = (fundingList.get(position).getGoalPrice() != 0 ? fundingList.get(position).getCurrentPrice() / fundingList.get(position).getGoalPrice() : 0.0f);
        fundingViewHolder.getInvestmentPercentage().setText(String.format(Locale.KOREAN, "%.0f%%", percentage));
        if(position == 0)
            fundingViewHolder.getFundingLayout().setMinHeight(360);
        else
            fundingViewHolder.getFundingLayout().setMinHeight(720);

        fundingViewHolder.getFundingLayout().setOnClickListener(v ->
                callBackHomeIntent.callback(fundingViewHolder.getGameImage(), fundingList.get(position)));
    }

    @Override
    public int getItemCount() {
        return fundingList.size();
    }

    public static class FundingViewHolder extends RecyclerView.ViewHolder{
        private TextView gameName, investmentPercentage;
        private ConstraintLayout fundingLayout;
        private ImageView gameImage;
        FundingViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.funding_game_name);
            fundingLayout = itemView.findViewById(R.id.funding_item_layout);
            gameImage = itemView.findViewById(R.id.funding_game_image);
            investmentPercentage = itemView.findViewById(R.id.investment_percentage);
        }
        TextView getGameName() { return gameName; }
        ConstraintLayout getFundingLayout() {
            return fundingLayout;
        }
        ImageView getGameImage() {
            return gameImage;
        }
        TextView getInvestmentPercentage() {
            return investmentPercentage;
        }
    }

    public interface CallBack {
        public void callback(final ImageView view, final GameVO gameVO);
    }
}
