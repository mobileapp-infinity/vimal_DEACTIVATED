package com.infinity.infoway.vimal.util.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by ADMIN on 30-04-2018.
 */

public class CustomEditText extends AppCompatEditText {

    public CustomEditText(Context context) {
        super(context);

      //  applyCustomFont(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

      //  applyCustomFont(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

       // applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface font = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/PoppinsRegular.otf");
        setTypeface(font);
        setPadding(3,0,0,0);
    }
}