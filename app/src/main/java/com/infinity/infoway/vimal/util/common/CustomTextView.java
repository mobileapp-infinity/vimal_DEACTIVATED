package com.infinity.infoway.vimal.util.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by ADMIN on 30-04-2018.
 */

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface font = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/PoppinsRegular.otf");
        setTypeface(font);
       // setTextSize(context.getResources().getDimension(R.dimen.text_size));
        //   setTextColor(Color.WHITE);
        setHintTextColor(Color.GRAY);
    }
}