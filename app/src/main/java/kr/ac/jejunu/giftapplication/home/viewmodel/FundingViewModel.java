/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.home.viewmodel;

import android.os.AsyncTask;

import java.util.List;

import kr.ac.jejunu.giftapplication.vo.GameVO;

interface FundingViewModel {
    public List<GameVO> getFundingList();
}
