package kr.ac.jejunu.giftapplication.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.DownloadImageTask;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.NewsVO;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private List<NewsVO> newsList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_title, textView_release, texView_director;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_img = (ImageView) itemView.findViewById(R.id.imageView_img);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            textView_release = (TextView) itemView.findViewById(R.id.textView_release);
            texView_director = (TextView) itemView.findViewById(R.id.textView_developer);
        }
    }
    //생성자
    public NewsFeedAdapter(List<NewsVO> list) {
        this.newsList = list;
    }

    @NonNull
    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsfeed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView_title.setText(newsList.get(position).getTitle());
//        holder.textView_release.setText(String.valueOf(GList.get(position).getRelease()));
        holder.texView_director.setText(newsList.get(position).getDeveloper());
        new DownloadImageTask(holder.imageView_img).execute(newsList.get(position).getImage());
//        GlideApp.with(holder.itemView).load(GList.get(position).getImage())
//                .override(300,400)
//                .into(holder.imageView_img);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}

