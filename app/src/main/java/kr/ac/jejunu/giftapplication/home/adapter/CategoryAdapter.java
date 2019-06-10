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

    public CategoryAdapter(List<String> allCategory, Context context) {
        this.categoryList = allCategory;
        this.context = context;
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
        setRecyclerView(holder.categoryRecyclerView, categoryList.get(position));
    }

    private void setRecyclerView(RecyclerView categoryRecyclerView, String category) {
        String uri = "http://117.17.102.139:8080/game/category/" + category;
        List<GameVO> gameVOList = null;
        try {
            gameVOList = new GetFundingTask(uri).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            gameVOList = new ArrayList<>();
        } catch (InterruptedException e) {
            e.printStackTrace();
            gameVOList = new ArrayList<>();
        } finally {
            CategoryItemAdapter categoryItemAdapter = new CategoryItemAdapter(gameVOList);
            categoryRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            categoryRecyclerView.setAdapter(categoryItemAdapter);
        }
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
        }

        public TextView getCategoryTitle() {
            return categoryTitle;
        }

        public void setCategoryTitle(TextView categoryTitle) {
            this.categoryTitle = categoryTitle;
        }

        public RecyclerView getCategoryRecyclerView() {
            return categoryRecyclerView;
        }

        public void setCategoryRecyclerView(RecyclerView categoryRecyclerView) {
            this.categoryRecyclerView = categoryRecyclerView;
        }
    }
}
