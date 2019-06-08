package kr.ac.jejunu.giftapplication.vo;

import java.io.Serializable;

import kr.ac.jejunu.giftapplication.R;

public class BankAccountVO implements Serializable {
    private String fintech_use_num;
    private String account_alias;
    private String bank_name;
    private String account_holder_name;
    private String account_num_masked;

    public String getAccountNum() { return account_num_masked; }
    public void setAccountNum(String account_num_masked) { this.account_num_masked = account_num_masked; }
    public String getFintechUseNum() {
        return fintech_use_num;
    }

    public void setFintechUseNum(String fintech_use_num) {
        this.fintech_use_num = fintech_use_num;
    }

    public String getAccountAlias() {
        return account_alias;
    }

    public void setAccountAlias(String account_alias) {
        this.account_alias = account_alias;
    }

    public String getBankName() {
        return bank_name;
    }

    public void setBankName(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccountHolderName() {
        return account_holder_name;
    }

    public void setAccountHolderName(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public int getBankColor() {
        switch(bank_name) {
            case "KB국민은행": return R.color.kbBank;
            case "카카오은행": return R.color.kakaoBank;
            case "농협": return R.color.nhBank;
            case "기업": return R.color.ibkBank;
            default: return R.color.colorPrimary;
        }
    }
}
