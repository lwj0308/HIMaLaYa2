package com.linlinlin.himalaya2.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linlinlin.himalaya2.R;

public class LoadingView extends androidx.appcompat.widget.AppCompatImageView {
    private float degress = 0;
    private boolean mNeedRotate = false;

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置图标
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //绑定
        mNeedRotate = true;
        post(new Runnable() {
            @Override
            public void run() {
                degress += 30;
                degress= degress<=360 ? degress:0;
                invalidate();
                if (mNeedRotate) {
                    postDelayed(this,100);
                }

            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //解绑
        mNeedRotate = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(degress, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }
}
