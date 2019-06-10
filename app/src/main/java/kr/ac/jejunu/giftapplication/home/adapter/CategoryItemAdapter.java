package kr.ac.jejunu.giftapplication.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.jejunu.giftapplication.DownloadImageTask;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.DeveloperVO;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryItem>{
    private List<GameVO> gameList;

    private Callback callback;
    public interface Callback {
        public void callback(final ImageView view, final GameVO gameVO);
    }

    public CategoryItemAdapter (List<GameVO> gameList, Callback callback) {
        this.gameList = gameList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CategoryItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_game_item, null);
        return new CategoryItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItem holder, int position) {
        holder.getItemTitle().setText(gameList.get(position).getName());
        new DownloadImageTask(holder.getItemImage()).execute(gameList.get(position).getProfileImage());
        holder.getItemDeveleoper().setText(gameList.get(position).getDeveloper().getName());
        holder.getItemPercentage().setText("30%");
        holder.getGameItemCard().setOnClickListener(v -> callback.callback(holder.getItemImage(), gameList.get(position)));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class CategoryItem extends RecyclerView.ViewHolder {
        private CardView gameItemCard;
        private ImageView itemImage;
        private TextView itemTitle, itemDeveleoper, itemPercentage;
        public CategoryItem(@NonNull View itemView) {
            super(itemView);
            gameItemCard = itemView.findViewById(R.id.game_item_card);
            itemTitle = itemView.findViewById(R.id.game_title);
            itemDeveleoper = itemView.findViewById(R.id.game_developer);
            itemPercentage = itemView.findViewById(R.id.game_percentage);
            itemImage = itemView.findViewById(R.id.game_image);
        }

        public CardView getGameItemCard() {
            return gameItemCard;
        }

        public TextView getItemTitle() {
            return itemTitle;
        }
        public TextView getItemDeveleoper() {
            return itemDeveleoper;
        }
        public TextView getItemPercentage() {
            return itemPercentage;
        }
        public ImageView getItemImage() {
            return itemImage;
        }

    }
}
