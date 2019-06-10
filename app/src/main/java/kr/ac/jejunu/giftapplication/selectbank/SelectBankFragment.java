package kr.ac.jejunu.giftapplication.selectbank;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.MainActivity;
import kr.ac.jejunu.giftapplication.selectbank.adapter.BankRecyclerAdapter;
import kr.ac.jejunu.giftapplication.splash.ProfileManager;
import kr.ac.jejunu.giftapplication.vo.BankAccountVO;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SelectBankFragment extends Fragment {

    private SelectBankViewModel mViewModel;
    private List<BankAccountVO> bankAccountVOList;

    private Toolbar toolbar;
    private ActionBar actionBar;
    private RecyclerView bankRecyclerView;
    private BankRecyclerAdapter bankRecyclerAdapter;

    public static SelectBankFragment newInstance() {
        return new SelectBankFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_bank_fragment, container, false);
        toolbar = view.findViewById(R.id.select_bank_toolbar);
        bankRecyclerView = view.findViewById(R.id.account_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SelectBankViewModel.class);
        setToolbar();
        // TODO: Use the ViewModel
        setRecyclerView();

    }

    private void setRecyclerView() {
        ProfileManager profileManager = ((GiftApplication) getActivity().getApplication()).getProfileManager();
        bankAccountVOList = mViewModel.getBankAccountList(profileManager, getContext());
        bankRecyclerAdapter = new BankRecyclerAdapter(bankAccountVOList, getContext(), fintechUseNum -> {
            withDrawWithFintechUseNum(fintechUseNum, getActivity().getIntent().getLongExtra("id", 0), getActivity().getIntent().getLongExtra("price", 0));
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("완료")
                    .setMessage("펀딩이 완료되었습니다.")
                    .setPositiveButton("확인", (dialogInterface, i) -> {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("onPurchased", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }).create();
            dialog.show();
        });
        bankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bankRecyclerView.setAdapter(bankRecyclerAdapter);
    }

    private void withDrawWithFintechUseNum(String fintechUseNum, long id, long price) {
        ProfileManager profileManager = ((GiftApplication) getActivity().getApplication()).getProfileManager();
        new WithDrawTask(fintechUseNum, id, price, profileManager.getLoginKey(getContext()).get("accessToken"), profileManager.getLoginKey(getContext()).get("userSeqNo")).execute();
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
