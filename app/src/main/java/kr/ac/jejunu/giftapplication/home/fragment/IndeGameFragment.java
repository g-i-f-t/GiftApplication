package kr.ac.jejunu.giftapplication.home.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.WebViewActivity;
import kr.ac.jejunu.giftapplication.home.adapter.IndeGameAdapter;
import kr.ac.jejunu.giftapplication.home.adapter.NewsFeedAdapter;
import kr.ac.jejunu.giftapplication.home.viewmodel.IndeGameViewModel;
import kr.ac.jejunu.giftapplication.vo.CafeVO;

public class IndeGameFragment extends Fragment {
    private List<CafeVO> result;
    private IndeGameViewModel mViewModel;
    private RecyclerView recyclerView;
    private IndeGameAdapter recyclerAdapter;
    private WebView webView;
    private Button button;
    private int page;

    public static IndeGameFragment newInstance() {
        return new IndeGameFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inde_game_fragment, container, false);
        recyclerView = view.findViewById(R.id.indegame_recycler_view);
        button = view.findViewById(R.id.on_top_button);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(IndeGameViewModel.class);
        // TODO: Use the ViewModel

        //List에 data가 없을 시 get해오도록
        if(result == null) result = mViewModel.getCafe(++page);
        recyclerAdapter = new IndeGameAdapter(result, url -> {
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
                    List<CafeVO> moreList = mViewModel.getCafe(++page);
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
        button.setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));
    }

}
