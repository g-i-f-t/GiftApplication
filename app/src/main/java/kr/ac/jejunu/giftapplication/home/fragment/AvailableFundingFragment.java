/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.home.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import kr.ac.jejunu.giftapplication.FileIO;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.gamedetail.GameDetail;
import kr.ac.jejunu.giftapplication.home.adapter.CategoryAdapter;
import kr.ac.jejunu.giftapplication.home.adapter.FundingAdapter;
import kr.ac.jejunu.giftapplication.home.viewmodel.AvailableFundingViewModel;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class AvailableFundingFragment extends Fragment {
    private AvailableFundingViewModel mViewModel;
    private RecyclerView fundingRecyclerView;

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
        setRecyclerView();
    }

    private void setRecyclerView() {
        fundingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CategoryAdapter categoryAdapter = new CategoryAdapter(mViewModel.getAllCategory(), getActivity(), this::transition);
        fundingRecyclerView.setAdapter(categoryAdapter);

    }

    private void transition(ImageView view, GameVO game) {
        Intent intent = new Intent(getContext(), GameDetail.class);
        intent.putExtra("gameVO", game);
        String fileName = "homeGameImage.png";
        FileIO.saveImage(view, fileName, getContext());
        intent.putExtra("fileName", fileName);

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(getActivity(), view, getResources().getString(R.string.funding_game_image));
        startActivity(intent, options.toBundle());
    }
}
