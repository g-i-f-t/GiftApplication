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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(IndeGameViewModel.class);
        // TODO: Use the ViewModel
        if(result == null) result = mViewModel.getCafe();
        recyclerAdapter = new IndeGameAdapter(result, url -> {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url" , url);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);
    }

}
