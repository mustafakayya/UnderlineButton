package com.kayya.underlinebutton;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by mustafakaya on 16/12/15.
 */
public class DimensCalculator {

    public static int dp2px(Context context, float dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }

    public static int px2dp(Context context, int px) {
        return Math.round(px / context.getResources().getDisplayMetrics().density);
    }
}
