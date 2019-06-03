package kr.ac.jejunu.giftapplication.selectbank;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.BankAccountVO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SelectBankFragment extends Fragment {

    private SelectBankViewModel mViewModel;
    private List<BankAccountVO> bankAccountVOList;

    public static SelectBankFragment newInstance() {
        return new SelectBankFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.select_bank_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SelectBankViewModel.class);
        // TODO: Use the ViewModel
        bankAccountVOList = mViewModel.getBankAccountList(((GiftApplication) getActivity().getApplication()).getProfileManager(), getContext());
    }

}
