/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.home.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.app.ActivityOptions;
import android.content.Intent;
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
import kr.ac.jejunu.giftapplication.home.adapter.FundingAdapter;
import kr.ac.jejunu.giftapplication.home.viewmodel.CompleteFundingViewModel;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class CompleteFundingFragment extends Fragment {

    private CompleteFundingViewModel mViewModel;
    private RecyclerView fundingRecyclerView;

    public static CompleteFundingFragment newInstance() {
        return new CompleteFundingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complete_funding_fragment, container, false);
        fundingRecyclerView = view.findViewById(R.id.done_funding_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CompleteFundingViewModel.class);
        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        fundingRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter fundingAdapter = new FundingAdapter(mViewModel.getFundingList(), getContext(), this::transition);
        fundingRecyclerView.setAdapter(fundingAdapter);
    }

    private void transition(ImageView view, GameVO game) {
        Intent intent = new Intent(getContext(), GameDetail.class);
        HashMap<String, Object> params = new HashMap<>();
        String fileName = "homeGameImage.png";
        FileIO.saveImage(view, fileName, getContext());

        intent.putExtra("params", params);
        intent.putExtra("fileName", fileName);
//        intent.putExtra("game", game);

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(getActivity(), view, getResources().getString(R.string.funding_game_image));
        startActivity(intent, options.toBundle());
    }

}
