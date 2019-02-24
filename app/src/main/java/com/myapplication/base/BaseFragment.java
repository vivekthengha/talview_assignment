package com.myapplication.base;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment implements BaseView{
    @Override
    public void showNoNetworkError() {}
}
