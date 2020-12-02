package com.linlinlin.himalaya2.interfaces;

public interface IRecommendPresenter {
    /**
     * 获取推荐内容
     */
    void getRecommendList();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上接加载更多
     */
    void loadMore();

    void registerCallback(IRecommendViewCallback callback);
    void unRegisterCallback(IRecommendViewCallback callback);
}
