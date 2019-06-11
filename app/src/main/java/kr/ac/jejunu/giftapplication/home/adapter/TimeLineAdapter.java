package kr.ac.jejunu.giftapplication.home.adapter;

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

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLine> {
    private List<PayInfoVO> latestTransaction;

    public TimeLineAdapter(List<PayInfoVO> latestTransaction) {
        this.latestTransaction = latestTransaction;
    }

    @NonNull
    @Override
    public TimeLine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_investment_item, parent, false);
        return new TimeLine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLine holder, int position) {
        PayInfoVO payInfoVO = latestTransaction.get(position);
        holder.developer.setText(payInfoVO.getGame().getDeveloper().getName());
        new DownloadImageTask(holder.gameImage).execute(payInfoVO.getGame().getProfileImage());
        holder.date.setText(payInfoVO.getPayDate());
        holder.title.setText(payInfoVO.getGame().getName());
        holder.price.setText(String.valueOf(payInfoVO.getPrice()));
    }

    @Override
    public int getItemCount() {
        return latestTransaction.size();
    }

    public class TimeLine extends RecyclerView.ViewHolder {
        private TextView date, title, developer, price;
        private ImageView gameImage;
        public TimeLine(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.investment_date);
            title = itemView.findViewById(R.id.game_title);
            developer = itemView. findViewById(R.id.game_developer);
            gameImage = itemView.findViewById(R.id.game_image);
            price = itemView.findViewById(R.id.game_price);
        }
    }
}
