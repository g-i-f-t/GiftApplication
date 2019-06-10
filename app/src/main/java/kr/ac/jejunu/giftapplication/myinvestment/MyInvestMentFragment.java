package kr.ac.jejunu.giftapplication.myinvestment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.splash.ProfileManager;
import kr.ac.jejunu.giftapplication.vo.GameVO;
import kr.ac.jejunu.giftapplication.vo.PayInfoVO;

public class MyInvestMentFragment extends Fragment {

    private MyInvestMentViewModel mViewModel;
    private RecyclerView recyclerView;

    public static MyInvestMentFragment newInstance() {
        return new MyInvestMentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_invest_ment_fragment, container, false);
        recyclerView = view.findViewById(R.id.my_investment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyInvestMentViewModel.class);
        // TODO: Use the ViewModel
        ProfileManager profileManager = ((GiftApplication) getActivity().getApplication()).getProfileManager();
        List<PayInfoVO> payInfoVOList = mViewModel.getFundTask(profileManager.getLoginKey(getContext()).get("userSeqNo"));
        recyclerView.setAdapter(new MyInvestMentAdapter(payInfoVOList));
    }

}
