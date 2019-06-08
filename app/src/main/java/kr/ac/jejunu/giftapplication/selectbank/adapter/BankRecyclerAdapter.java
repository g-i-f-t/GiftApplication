package kr.ac.jejunu.giftapplication.selectbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.vo.BankAccountVO;

public class BankRecyclerAdapter extends RecyclerView.Adapter<BankRecyclerAdapter.BankHolder>{

    private List<BankAccountVO> bankAccountList;
    private Context context;
    private Callback callback;
    public interface Callback {
        public void run(BankAccountVO item, int index);
    }
    public BankRecyclerAdapter(List<BankAccountVO> bankAccountList, Context context, Callback callback) {
        this.bankAccountList = bankAccountList;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public BankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_account, parent, false);
        return new BankHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankHolder holder, int position) {
        holder.getBankAccountLayout().setBackgroundColor(ContextCompat.getColor(context, bankAccountList.get(position).getBankColor()));
        holder.getBankName().setText(bankAccountList.get(position).getBankName());
        holder.getAccountHolderName().setText(bankAccountList.get(position).getAccountHolderName());
        holder.getAccountNum().setText(bankAccountList.get(position).getAccountNum());
        holder.getAccountAlias().setText(bankAccountList.get(position).getAccountAlias());
        holder.getBankAccountLayout().setOnClickListener(v -> callback.run(bankAccountList.get(position), position));
    }

    @Override
    public int getItemCount() {
        return bankAccountList.size();
    }

    public class BankHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout bankAccountLayout;
        private TextView accountAlias, bankName, accountNum, accountHolderName;

        public BankHolder(@NonNull View itemView) {
            super(itemView);
            accountAlias = itemView.findViewById(R.id.account_alias);
            bankAccountLayout = itemView.findViewById(R.id.account_layout);
            bankName = itemView.findViewById(R.id.bank_name);
            accountHolderName = itemView.findViewById(R.id.account_holder_name);
            accountNum = itemView.findViewById(R.id.account_num);
        }

        public TextView getAccountAlias() {
            return accountAlias;
        }

        public void setAccountAlias(TextView accountAlias) {
            this.accountAlias = accountAlias;
        }

        public ConstraintLayout getBankAccountLayout() {
            return bankAccountLayout;
        }

        public void setBankAccountLayout(ConstraintLayout bankAccountLayout) {
            this.bankAccountLayout = bankAccountLayout;
        }

        public TextView getBankName() {
            return bankName;
        }

        public void setBankName(TextView bankName) {
            this.bankName = bankName;
        }

        public TextView getAccountNum() {
            return accountNum;
        }

        public void setAccountNum(TextView accountNum) {
            this.accountNum = accountNum;
        }

        public TextView getAccountHolderName() {
            return accountHolderName;
        }

        public void setAccountHolderName(TextView accountHolderName) {
            this.accountHolderName = accountHolderName;
        }
    }
}
