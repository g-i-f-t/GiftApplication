package kr.ac.jejunu.giftapplication.home.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.WebViewActivity;
import kr.ac.jejunu.giftapplication.home.adapter.NewsFeedAdapter;
import kr.ac.jejunu.giftapplication.home.viewmodel.NewsfeedViewModel;
import kr.ac.jejunu.giftapplication.vo.NewsVO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.List;

public class NewsFeedFragment extends Fragment {

    private NewsfeedViewModel mViewModel;
    private RecyclerView recyclerView;
    private NewsFeedAdapter recyclerAdapter;
    private List<NewsVO> result;
    private WebView webView;

    public static NewsFeedFragment newInstance() {
        return new NewsFeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newsfeed_fragment, container, false);
        recyclerView = view.findViewById(R.id.news_feed_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsfeedViewModel.class);
        if(result == null) result = mViewModel.getNews();
        recyclerAdapter = new NewsFeedAdapter(result, url -> {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url" , url);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);

    }



}
