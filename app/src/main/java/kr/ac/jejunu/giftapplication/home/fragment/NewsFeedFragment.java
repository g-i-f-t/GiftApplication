package kr.ac.jejunu.giftapplication.home.fragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
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
import kr.ac.jejunu.giftapplication.vo.CafeVO;
import kr.ac.jejunu.giftapplication.vo.NewsVO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;

import java.util.List;

public class NewsFeedFragment extends Fragment {

    private NewsfeedViewModel mViewModel;
    private RecyclerView recyclerView;
    private NewsFeedAdapter recyclerAdapter;
    private ImageButton imageButton;
    private List<NewsVO> result;
    private WebView webView;
    private int page;

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
        imageButton = view.findViewById(R.id.on_top_button);
        return view;
    }
    private void setDrawerSettings() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.home_tab_newsfeed));
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsfeedViewModel.class);
        setDrawerSettings();
        if(result == null) result = mViewModel.getNews(++page);
        recyclerAdapter = new NewsFeedAdapter(result, url -> {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url" , url);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //view의 최하단 판별 후 data 더보기 '1' == 최하단
                if (!recyclerView.canScrollVertically(1)) {
                    List<NewsVO> moreList = mViewModel.getNews(++page);
                    if(!moreList.isEmpty()) {
                        result.addAll(moreList);
                        recyclerAdapter.notifyDataSetChanged();
                    } else {
                        recyclerView.removeOnScrollListener(this);
                    }
                }
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        imageButton.setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));

    }



}
