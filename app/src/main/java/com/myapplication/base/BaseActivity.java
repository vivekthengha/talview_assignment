package com.myapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.myapplication.R;
import com.myapplication.network.FailureResponse;

import butterknife.ButterKnife;

/**
 * Created by vivek jha on 22/2/19.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public void addFragment(int layoutResId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null)
            getSupportFragmentManager().beginTransaction()
                    .add(layoutResId, fragment, tag)
                    .commit();
    }

    public void addFragmentWithBackstack(int layoutResId, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    protected abstract View getSnackView();

    /**
     * A common place to handle no network error
     * Can show a full screen View, Snackbar with retry action
     * or a simple Toast
     */

    @Override
    public void showNoNetworkError() {
        showToastLong(getString(R.string.no_network_error));
    }

    @Override
    public void showToastLong(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method helps both ways
     * Using this generic handling as well as Specific handling can be done
     *
     * @param failureResponse contains errorCode
     *                        which can decide what kind of handling can be done
     */

    @Override
    public void showSpecificError(FailureResponse failureResponse) {
        String message = (failureResponse != null) ? failureResponse.getMsg() : getString(R.string.something_went_wrong);
        showToastLong(message);
    }

    @Override
    public void showProgressDialog() {
        //todo show progress dialog if needed
    }

    @Override
    public void hideProgressDialog() {
        //todo hide progress dialogue
    }

}
