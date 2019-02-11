package com.greenaddress.greenbits.ui;

import android.content.Intent;
import android.util.Log;

import com.greenaddress.greenapi.ConnectionManager;
import com.greenaddress.greenapi.model.ToastObservable;

import java.util.Observable;
import java.util.Observer;

public abstract class LoggedActivity extends GaActivity implements Observer {

    private boolean mChangingActivity = false;

    @Override
    protected void onResumeWithService() {
        super.onResumeWithService();
        if (mService == null)
            return;
        if (mService.isDisconnected()) {
            toFirstOrPinScreen();
            return;
        }
        mService.getConnectionManager().addObserver(this);
        mService.getModel().getToastObservable().addObserver(this);
    }

    @Override
    protected void onPauseWithService() {
        super.onPauseWithService();
        if (mService == null)
            return;
        mService.getConnectionManager().deleteObserver(this);
        mService.getModel().getToastObservable().deleteObserver(this);
    }

    @Override
    public void update(final Observable observable, final Object o) {
        if (observable instanceof ConnectionManager) {
            final ConnectionManager cm = mService.getConnectionManager();
            if (cm.isLoginRequired()) {
                cm.login(this, cm.getHWDeviceData(), cm.getHWResolver());
            }
        }
    }

    private void toScreen(final Intent intent) {
        if (!mChangingActivity) {
            mChangingActivity = true;
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finishOnUiThread();
        }
    }

    public void logout() {
        startLoading();
        mService.getConnectionManager().deleteObserver(this);
        mService.getExecutor().execute(() -> {
            mService.disconnect();
            toFirstOrPinScreen();
        });
    }

    private void toFirstOrPinScreen() {
        if (mService.hasPin())
            toScreen(new Intent(this, PinActivity.class));
        else
            toScreen(new Intent(this, FirstScreenActivity.class));
        finishOnUiThread();
    }

}
