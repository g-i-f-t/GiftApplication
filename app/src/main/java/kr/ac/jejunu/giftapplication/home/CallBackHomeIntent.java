/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.home;

import android.view.View;
import android.widget.ImageView;

import kr.ac.jejunu.giftapplication.vo.GameVO;

public interface CallBackHomeIntent {
    public void callback(final ImageView view, final GameVO gameVO);
}
