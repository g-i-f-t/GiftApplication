package kr.ac.jejunu.giftapplication.selectbank;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.splash.ProfileManager;
import kr.ac.jejunu.giftapplication.vo.BankAccountVO;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SelectBankFragment extends Fragment {

    private SelectBankViewModel mViewModel;
    private List<BankAccountVO> bankAccountVOList;

    private Toolbar toolbar;
    private ActionBar actionBar;
    private RecyclerView bankRecyclerView;

    public static SelectBankFragment newInstance() {
        return new SelectBankFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_bank_fragment, container, false);
        toolbar = view.findViewById(R.id.select_bank_toolbar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SelectBankViewModel.class);
        setToolbar();
        // TODO: Use the ViewModel
        ProfileManager profileManager = ((GiftApplication) getActivity().getApplication()).getProfileManager();
        bankAccountVOList = mViewModel.getBankAccountList(profileManager, getContext());

    }

    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
