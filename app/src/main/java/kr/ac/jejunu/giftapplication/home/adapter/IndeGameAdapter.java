package kr.ac.jejunu.giftapplication.home.adapter;

import android.telecom.Call;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.DownloadImageTask;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.CafeVO;

public class IndeGameAdapter extends RecyclerView.Adapter<IndeGameAdapter.ViewHolder> {
    private List<CafeVO> indeList;
    private Callback callback;

    public interface Callback {
        public void run(String url);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout itemLayout;
        private ImageView imageView_img;
        private TextView textView_title, textView_date, total_view, textView_developer;


        public ViewHolder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.news_item_layout);
            imageView_img = (ImageView) itemView.findViewById(R.id.imageView_img);
            textView_title = (TextView) itemView.findViewById(R.id.textView_title);
            textView_developer = (TextView) itemView.findViewById(R.id.textView_writer);
            textView_date = (TextView) itemView.findViewById(R.id.textView_date);
            //newsfeed item 재활용 cate -> views
            total_view = (TextView) itemView.findViewById(R.id.textView_category);
        }
    }
    public IndeGameAdapter(List<CafeVO> list, Callback callback){
        this.indeList = list;
        this.callback = callback;
    }
    @NonNull
    @Override
    public IndeGameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsfeed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView_title.setText(indeList.get(position).getTitle());
        holder.total_view.setText(indeList.get(position).getTotal_view());
        holder.textView_developer.setText(indeList.get(position).getDeveloper());
        holder.textView_date.setText(indeList.get(position).getDate());
        new DownloadImageTask(holder.imageView_img).execute(indeList.get(position).getImage());
        holder.itemLayout.setOnClickListener(v -> callback.run(indeList.get(position).getUrl()));
    }


    @Override
    public int getItemCount() {
        return indeList.size();
    }


}
