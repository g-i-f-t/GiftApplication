package kr.ac.jejunu.giftapplication.home.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;

import java.util.List;

import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.WebViewActivity;
import kr.ac.jejunu.giftapplication.home.adapter.IndeGameAdapter;
import kr.ac.jejunu.giftapplication.home.viewmodel.IndeGameViewModel;
import kr.ac.jejunu.giftapplication.home.viewmodel.MobileIndeViewModel;
import kr.ac.jejunu.giftapplication.vo.CafeVO;

public class MobileIndeFragment extends Fragment {
    private List<CafeVO> result;
    private RecyclerView recyclerView;
    private IndeGameAdapter recyclerAdapter;
    private WebView webView;
    private ImageButton imageButton;
    private ImageButton hotsortButton;
    private MobileIndeViewModel mViewModel;
    private int menuId;
    private int page;

    public MobileIndeFragment(int i) {
        this.menuId = i;
    }

    public static MobileIndeFragment newInstance(int i) {
        return new MobileIndeFragment(i);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mobile_inde_fragment, container, false);
        recyclerView = view.findViewById(R.id.indegame_recycler_view);
        imageButton = view.findViewById(R.id.on_top_button);
//        hotsortButton = view.findViewById(R.id.most_view_sort_button);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MobileIndeViewModel.class);
        // TODO: Use the ViewModel

        //List에 data가 없을 시 get해오도록
        if(result == null) result = mViewModel.getCafe(menuId, ++page);
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
                    List<CafeVO> moreList = mViewModel.getCafe(menuId, ++page);
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
