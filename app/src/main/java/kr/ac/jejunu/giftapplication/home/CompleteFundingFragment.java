package kr.ac.jejunu.giftapplication.home;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.jejunu.giftapplication.R;

public class CompleteFundingFragment extends Fragment {

    private CompleteFundingViewModel mViewModel;

    public static CompleteFundingFragment newInstance() {
        return new CompleteFundingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.complete_funding_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CompleteFundingViewModel.class);
        // TODO: Use the ViewModel
    }

}
