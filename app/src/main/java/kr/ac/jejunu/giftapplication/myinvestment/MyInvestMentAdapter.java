package kr.ac.jejunu.giftapplication.myinvestment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.ac.jejunu.giftapplication.DownloadImageTask;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.PayInfoVO;

class MyInvestMentAdapter extends RecyclerView.Adapter<MyInvestMentAdapter.Item> {
    private List<PayInfoVO> payInfoVOList;

    MyInvestMentAdapter(List<PayInfoVO> payInfoVOList) {
        this.payInfoVOList = payInfoVOList;
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_investment_item, parent, false);
        return new Item(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Item holder, int position) {
        new DownloadImageTask(holder.getImageView()).execute(payInfoVOList.get(position).getGame().getProfileImage());
        holder.getGameTitle().setText(payInfoVOList.get(position).getGame().getName());
        holder.getGameDeveloper().setText(payInfoVOList.get(position).getGame().getDeveloper().getName());
        holder.getGamePrice().setText(String.valueOf(payInfoVOList.get(position).getPrice()));
        holder.getInvestmentDate().setText(payInfoVOList.get(position).getPayDate());
    }

    @Override
    public int getItemCount() {
        return payInfoVOList.size();
    }

    class Item extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView investmentDate, gameTitle, gameDeveloper, gamePrice;
        Item(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.game_image);
            investmentDate = itemView.findViewById(R.id.investment_date);
            gameDeveloper = itemView.findViewById(R.id.game_developer);
            gameTitle = itemView.findViewById(R.id.game_title);
            gamePrice = itemView.findViewById(R.id.game_price);
        }

        ImageView getImageView() {
            return imageView;
        }

        TextView getInvestmentDate() {
            return investmentDate;
        }

        TextView getGameTitle() {
            return gameTitle;
        }

        TextView getGameDeveloper() {
            return gameDeveloper;
        }

        TextView getGamePrice() {
            return gamePrice;
        }
    }
}
