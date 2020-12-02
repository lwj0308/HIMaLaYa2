package com.linlinlin.himalaya2.fragments;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linlinlin.himalaya2.R;
import com.linlinlin.himalaya2.adapters.AlbumListAdapter;
import com.linlinlin.himalaya2.base.BaseFragment;
import com.linlinlin.himalaya2.interfaces.IRecommendViewCallback;
import com.linlinlin.himalaya2.presenters.RecommendPresenter;
import com.linlinlin.himalaya2.utils.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback,UILoader.OnRetryClickListener {
    private static final String TAG = "RecommendFragment";
    private View mRootView;
    private RecyclerView mRecommendRv;
    private AlbumListAdapter mRecommendListAdapter;
    private RecommendPresenter mPresenter;
    private UILoader mUiLoader;

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        //UI切换器
        mUiLoader = new UILoader(requireContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(inflater, container);
            }
        };
        //获取逻辑层
        mPresenter = RecommendPresenter.getInstance();
        mPresenter.registerCallback(this);
        //请求数据
        mPresenter.getRecommendList();

        //解绑
        if (mUiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUiLoader.getParent()).removeView(mUiLoader);
        }

        mUiLoader.setOnRetryClickListener(this);
        return mUiLoader;
    }

    private View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_recommend, container, false);
        mRecommendRv = mRootView.findViewById(R.id.recommend_list);
        mRecommendRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        //item边距
        mRecommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = UIUtil.dip2px(requireContext(), 5);
                outRect.bottom = UIUtil.dip2px(requireContext(), 5);
                outRect.left = UIUtil.dip2px(requireContext(), 5);
                outRect.right = UIUtil.dip2px(requireContext(), 5);
            }
        });
        mRecommendListAdapter = new AlbumListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);
        return mRootView;
    }

    @Override
    public void onRecommendListLoaded(List<Album> result) {
        mRecommendListAdapter.setData(result);
        mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetworkError() {
        mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.unRegisterCallback(this);
        }
        super.onDestroy();
    }

    @Override
    public void onRetryClick() {
        //无网络
        if (mPresenter != null) {
            mPresenter.getRecommendList();
        }
    }
}
