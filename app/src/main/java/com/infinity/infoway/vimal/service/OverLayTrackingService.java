package com.infinity.infoway.vimal.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.infinity.infoway.vimal.R;


public class OverLayTrackingService extends Service implements View.OnTouchListener {
    private static final String TAG = com.infinity.infoway.vimal.service.OverLayTrackingService.class.getSimpleName();

    private WindowManager windowManager;

    WindowManager.LayoutParams params;
    private View floatyView;
    private ImageView img_launcher;
    private GestureDetector gestureDetector;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        addOverlayView();
    }

    private void addOverlayView() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE/**09-1010*/

                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT);

            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.x = 0;
            params.y = 100;
            //windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        } else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT);


            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.x = 0;
            params.y = 100;
            //windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        }


       /* final WindowManager.LayoutParams params =
                new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        0,
                        PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER | Gravity.START;
        params.x = 0;
        params.y = 0;
*/
        FrameLayout interceptorLayout = new FrameLayout(this) {

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {

                // Only fire on the ACTION_DOWN event, or you'll get two events (one for _DOWN, one for _UP)
                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                    // Check if the HOME button is pressed
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

                        Log.v(TAG, "BACK Button Pressed");

                        // As we've taken action, we'll return true to prevent other apps from consuming the event as well
                        return true;
                    }
                }

                // Otherwise don't intercept the event
                return super.dispatchKeyEvent(event);
            }
        };

        floatyView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_floating_window, interceptorLayout);

        img_launcher=floatyView.findViewById(R.id.img_launcher);


        floatyView.setOnTouchListener(this);


        windowManager.addView(floatyView, params);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        if (floatyView != null) {
            windowManager.removeView(floatyView);

            floatyView = null;
        }
    }

    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    @Override
    public boolean onTouch(View view, MotionEvent event) {

//        if (gestureDetector.onTouchEvent(event)) {
//            if (floatyView != null) {
//
//                windowManager.removeView(floatyView);
//
//                floatyView = null;
//            }
//
//            stopSelf();
//
//            Intent intent = new Intent(OverLayTrackingService.this, Activity_Splash.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//
//        }else{
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    //remember the initial position.
                    initialX = params.x;
                    initialY = params.y;

                    //get the touch location
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                    return true;
                case MotionEvent.ACTION_UP:
                    int Xdiff = (int) (event.getRawX() - initialTouchX);
                    int Ydiff = (int) (event.getRawY() - initialTouchY);


                    //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                    //So that is click event.

                    return true;
                case MotionEvent.ACTION_MOVE:
                    //Calculate the X and Y coordinates of the view.
                    params.x = initialX + (int) (event.getRawX() - initialTouchX);
                    params.y = initialY + (int) (event.getRawY() - initialTouchY);


                    //Update the layout with new X & Y coordinate
                    windowManager.updateViewLayout(floatyView, params);
                    return true;



//            }
        }


        return false;
        //Log.v(TAG, "onTouch...");

        // Kill service
        //onDestroy();

       // return true;
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}
