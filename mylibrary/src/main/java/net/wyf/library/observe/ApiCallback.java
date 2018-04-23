package net.wyf.library.observe;

import net.wyf.library.utils.log.XLogger;

import java.io.IOException;

import io.reactivex.Observer;
import retrofit2.HttpException;

/**
 * 封装返回类
 */

public abstract class ApiCallback<T> implements Observer<T> {

    public abstract void onHandleSuccess(T response);

    public abstract void onHandleFailure(String msg);

    public abstract void onHandleFinish();

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            String errorBody = httpException.response().errorBody().toString();
            try {
                httpException.response().errorBody().string();
            } catch (IOException e1) {
                XLogger.e("IOException e1=" + e1.getMessage());
                e1.printStackTrace();
            }
            XLogger.e("onError errorBody.toString=" + errorBody);
            int code = httpException.code();
            String msg = httpException.getMessage();
            //LogUtil.e("ApiCallback","code=" + code);
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onHandleFailure(msg);
        } else {
            XLogger.e("onError is called and exception is not a HttpException instance ");
            onHandleFailure(e.getMessage());
        }
        onHandleFinish();
    }

    @Override
    public void onNext(T value) {
        onHandleSuccess(value);
    }

    @Override
    public void onComplete() {
        onHandleFinish();
    }

}
