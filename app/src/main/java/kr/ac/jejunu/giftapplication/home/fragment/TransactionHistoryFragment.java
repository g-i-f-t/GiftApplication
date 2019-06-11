package kr.ac.jejunu.giftapplication.home.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import kr.ac.jejunu.giftapplication.DownloadImageTask;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.viewmodel.TransactionHistoryViewModel;
import kr.ac.jejunu.giftapplication.vo.GameVO;

public class TransactionHistoryFragment extends Fragment {

    private TransactionHistoryViewModel mViewModel;
    private ImageView hotImageView;
    private TextView hotTitle, hotPercentage;

    public static TransactionHistoryFragment newInstance() {
        return new TransactionHistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_history_fragment, container, false);
        hotImageView = view.findViewById(R.id.hot_image_view);
        hotTitle = view.findViewById(R.id.hot_title);
        hotPercentage = view.findViewById(R.id.hot_percentage);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TransactionHistoryViewModel.class);
        // TODO: Use the ViewModel
        setItem();

    }

    private void setItem() {
        GameVO gameVO = mViewModel.findHotGame();
        double percentage = ((double) gameVO.getCurrentPrice() / (double) gameVO.getGoalPrice()) * 100;
        hotPercentage.setText(String.format(Locale.getDefault(), "%.0f%%", percentage));
        hotTitle.setText(gameVO.getName());
        new DownloadImageTask(hotImageView).execute(gameVO.getProfileImage());
    }

}
