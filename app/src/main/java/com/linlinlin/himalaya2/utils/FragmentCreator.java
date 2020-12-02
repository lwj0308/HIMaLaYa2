package com.linlinlin.himalaya2.utils;

import com.linlinlin.himalaya2.base.BaseFragment;
import com.linlinlin.himalaya2.fragments.HistoryFragment;
import com.linlinlin.himalaya2.fragments.RecommendFragment;
import com.linlinlin.himalaya2.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCreator {
    public final static int INDEX_RECOMMEND = 0;
    public final static int INDEX_SUBSCRIPTION = 1;
    public final static int INDEX_HISTORY = 2;

    public final static int PAGE_COUNT = 3;
    private static final Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        BaseFragment fragment = sCache.get(index);
        if (fragment != null) {
            return fragment;
        }
        switch (index) {
            case INDEX_RECOMMEND:
                fragment = new RecommendFragment();
                break;
            case INDEX_SUBSCRIPTION:
                fragment = new SubscriptionFragment();
                break;
            case INDEX_HISTORY:
                fragment = new HistoryFragment();
                break;
        }
        sCache.put(index, fragment);
        return fragment;
    }
}
