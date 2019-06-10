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
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryItem>{
    private List<GameVO> gameList;

    public CategoryItemAdapter (List<GameVO> gameList) {
        this.gameList = gameList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_game_item, parent, false);
        return new CategoryItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItem holder, int position) {
        holder.getItemTitle().setText(gameList.get(position).getName());
        new DownloadImageTask((ImageView) holder.getItemImage()).execute(gameList.get(position).getProfileImage());
        holder.getItemDeveleoper().setText(gameList.get(position).getDeveloper().getName());
        holder.getItemPercentage().setText("30%");

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CategoryItem extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemTitle, itemDeveleoper, itemPercentage;
        public CategoryItem(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.game_title);
            itemDeveleoper = itemView.findViewById(R.id.game_developer);
            itemPercentage = itemView.findViewById(R.id.game_percentage);
            itemImage = itemView.findViewById(R.id.game_image);
        }

        public TextView getItemTitle() {
            return itemTitle;
        }

        public void setItemTitle(TextView itemTitle) {
            this.itemTitle = itemTitle;
        }

        public TextView getItemDeveleoper() {
            return itemDeveleoper;
        }

        public void setItemDeveleoper(TextView itemDeveleoper) {
            this.itemDeveleoper = itemDeveleoper;
        }

        public TextView getItemPercentage() {
            return itemPercentage;
        }

        public void setItemPercentage(TextView itemPercentage) {
            this.itemPercentage = itemPercentage;
        }

        public ImageView getItemImage() {
            return itemImage;
        }

        public void setItemImage(ImageView itemImage) {
            this.itemImage = itemImage;
        }
    }
}
