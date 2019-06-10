package kr.ac.jejunu.giftapplication.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.viewmodel.GetFundingTask;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Category>{
    private final List<String> categoryList;
    private final Context context;
    private CategoryItemAdapter.Callback callback;

    public CategoryAdapter(List<String> allCategory, Context context, CategoryItemAdapter.Callback callback) {
        this.categoryList = allCategory;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Category onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new Category(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Category holder, int position) {
        holder.getCategoryTitle().setText(categoryList.get(position));
        holder.getCategoryRecyclerView()
                .setAdapter(new CategoryItemAdapter(getGameList(categoryList.get(position)), callback));
    }

    private List<GameVO> getGameList(String category) {
        String uri = "http://117.17.102.139:8080/game/category/" + category;
        List<GameVO> gameVOList = new ArrayList<>();
        try {
            gameVOList = new GetFundingTask(uri).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return gameVOList;
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class Category extends RecyclerView.ViewHolder {
        private TextView categoryTitle;
        private RecyclerView categoryRecyclerView;
        public Category(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.category_title);
            categoryRecyclerView = itemView.findViewById(R.id.category_recycler_view);
            categoryRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }

        public TextView getCategoryTitle() {
            return categoryTitle;
        }

        public RecyclerView getCategoryRecyclerView() {
            return categoryRecyclerView;
        }
    }
}
