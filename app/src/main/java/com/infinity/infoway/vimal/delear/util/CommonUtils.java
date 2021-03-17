package com.infinity.infoway.vimal.delear.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CommonUtils {

    public static boolean checkIsEmptyOrNullCommon(Object object) {
        boolean isNullOrEmpty = false;
        if (object == null) {
            isNullOrEmpty = true;
        } else if (object.getClass() == String.class ||
                object.getClass() == CharSequence.class) {
            if (object.toString().trim().isEmpty()) {
                isNullOrEmpty = true;
            }
        } else if (object == "") {
            isNullOrEmpty = true;
        }

        return isNullOrEmpty;
    }


    public static void hideKeyboardCommon(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
