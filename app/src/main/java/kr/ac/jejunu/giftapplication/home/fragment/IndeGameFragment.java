package kr.ac.jejunu.giftapplication.home.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.viewmodel.IndeGameViewModel;

public class IndeGameFragment extends Fragment {

    private IndeGameViewModel mViewModel;

    public static IndeGameFragment newInstance() {
        return new IndeGameFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inde_game_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(IndeGameViewModel.class);
        // TODO: Use the ViewModel
    }

}
