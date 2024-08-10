package com.example.herambtinder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

public class CustomSwipeFlingAdapterView extends SwipeFlingAdapterView {

    private float initialX;
    private float initialY;
    private boolean isScrolling;
    private int touchSlop;

    public CustomSwipeFlingAdapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = ev.getX();
                initialY = ev.getY();
                isScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = Math.abs(ev.getX() - initialX);
                float deltaY = Math.abs(ev.getY() - initialY);

                if (deltaX > touchSlop && deltaX > deltaY) {
                    // Detected a horizontal swipe, allow parent to handle the event
                    return true;
                } else if (deltaY > touchSlop && deltaY > deltaX) {
                    // Detected a vertical scroll, do not intercept, let the child handle it
                    isScrolling = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isScrolling = false;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScrolling) {
            // If scrolling, do not process swipe
            return false;
        }
        return super.onTouchEvent(ev);
    }
}
