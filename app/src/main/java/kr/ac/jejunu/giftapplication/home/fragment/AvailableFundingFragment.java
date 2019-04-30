package kr.ac.jejunu.giftapplication.home.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.HashMap;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import kr.ac.jejunu.giftapplication.FileIO;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.gamedetail.GameDetail;
import kr.ac.jejunu.giftapplication.home.viewmodel.AvailableFundingViewModel;
import kr.ac.jejunu.giftapplication.home.adapter.FundingAdapter;

public class AvailableFundingFragment extends Fragment {

    private final FileIO fileIO = new FileIO();
    private AvailableFundingViewModel mViewModel;
    private RecyclerView.Adapter fundingAdapter;
    private RecyclerView fundingRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public static AvailableFundingFragment newInstance() {
        return new AvailableFundingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.available_funding_fragment, container, false);
        fundingRecyclerView = view.findViewById(R.id.available_funding_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AvailableFundingViewModel.class);
        // TODO: Use the ViewModel
        setRecyclerView();
    }

    private void setRecyclerView() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        fundingRecyclerView.setLayoutManager(layoutManager);
        fundingAdapter = new FundingAdapter(mViewModel.getFundingList(), (view, position) -> transition(view, position));
        fundingRecyclerView.setAdapter(fundingAdapter);
    }

    private void transition(ImageView view, int position) {
        Intent intent = new Intent(getContext(), GameDetail.class);
        HashMap<String, Object> params = new HashMap<>();
        String fileName = "homeGameImage.png";
        params.put("index", position);
        params.put("fileName", fileName);
        intent.putExtra("params", params);
        fileIO.saveImage(view, fileName, getContext());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), view, getResources().getString(R.string.funding_game_image));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }
}
