package com.infinity.infoway.vimal.util.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

/**
 * Created by ADMIN on 30-04-2018.
 */

public class CustomButtonView extends AppCompatButton {

    public CustomButtonView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        //  Typeface customFont = FontCache.getTypeface("SourceSansPro-Regular.ttf", context);
       /* Typeface font = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/OpenSansRegular.ttf");
        setTypeface(font);*/
        //  setTextColor(Color.BLUE);

        Typeface font = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/PoppinsRegular.otf");
        setTypeface(font);
    }
}