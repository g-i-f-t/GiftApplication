package kr.ac.jejunu.giftapplication.home.adapter;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.DownloadImageTask;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.NewsVO;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private List<NewsVO> newsList;
    private Callback callback;

    public interface Callback {
        public void run(String url);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_title, textView_category, texView_writer, textView_date;
        private ConstraintLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.news_item_layout);
            imageView_img = (ImageView) itemView.findViewById(R.id.imageView_img);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            textView_category = (TextView) itemView.findViewById(R.id.textView_category);
            texView_writer = (TextView) itemView.findViewById(R.id.textView_writer);
            textView_date = (TextView) itemView.findViewById(R.id.textView_date);
        }
    }
    //생성자
    public NewsFeedAdapter(List<NewsVO> list, Callback callback) {
        this.newsList = list;
        this.callback = callback;
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
        holder.textView_title.setMovementMethod(LinkMovementMethod.getInstance());
        holder.textView_category.setText(newsList.get(position).getCategory());
        holder.texView_writer.setText(newsList.get(position).getWriter());
        holder.textView_date.setText(newsList.get(position).getDate());
        new DownloadImageTask(holder.imageView_img).execute(newsList.get(position).getImage());
        holder.itemLayout.setOnClickListener(v -> callback.run(newsList.get(position).getUrl()));

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}

