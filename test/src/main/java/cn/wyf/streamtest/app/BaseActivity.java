package cn.wyf.streamtest.app;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.wyf.library.utils.log.XLogger;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 基础activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    public App application;
    public AppCompatActivity activity;
    public CompositeDisposable compositeDisposable = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = App.getIntstance();
        activity = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setLayout();
    }

    abstract protected void setLayout();


    public void showToast(String msg) {
        application.showToast(msg);
    }

    public void addSubscription(Observable<?> Observable, Observer observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        Observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (compositeDisposable != null) {
            XLogger.v("onUnsubscribe is called");
            compositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
    }
}
