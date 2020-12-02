package com.linlinlin.himalaya2.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linlinlin.himalaya2.R;
import com.linlinlin.himalaya2.base.BaseApplication;

public abstract class UILoader extends FrameLayout {

    private View mLoadingView;
    private View mSuccessView;
    private View mErrorView;
    private View mEmptyView;

    private OnRetryClickListener mOnRetryClickListener;

    public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener) {
        mOnRetryClickListener = onRetryClickListener;
    }

    public enum UIStatus {
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    private UIStatus mStatus = UIStatus.NONE;

    public UILoader(@NonNull Context context) {
        this(context, null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        switchUIByCurrentStatus();
    }

    public void updateStatus(UIStatus uiStatus){
        mStatus = uiStatus;
        BaseApplication.getHandler().post(this::switchUIByCurrentStatus);
    }

    private void switchUIByCurrentStatus() {
        if (mLoadingView == null) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
        }
        mLoadingView.setVisibility(mStatus == UIStatus.LOADING ? VISIBLE : GONE);

        if (mSuccessView == null) {
            mSuccessView = getSuccessView(this);
            addView(mSuccessView);
        }
        mSuccessView.setVisibility(mStatus == UIStatus.SUCCESS ? VISIBLE : GONE);

        if (mErrorView == null) {
            mErrorView = getErrorViewView();
            addView(mErrorView);
        }
        mErrorView.setVisibility(mStatus == UIStatus.NETWORK_ERROR ? VISIBLE : GONE);

        if (mEmptyView == null) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
        }
        mEmptyView.setVisibility(mStatus == UIStatus.EMPTY ? VISIBLE : GONE);
    }

    private View getEmptyView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
    }

    private View getErrorViewView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_error_view, this, false);
        view.findViewById(R.id.network_error_icon).setOnClickListener(v -> {
            //重新获取数据
            if (mOnRetryClickListener != null) {
                mOnRetryClickListener.onRetryClick();
            }
        });
        return view;
    }

    protected abstract View getSuccessView(ViewGroup container);

    private View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
    }

    public interface OnRetryClickListener{
        void onRetryClick();
    }
}
