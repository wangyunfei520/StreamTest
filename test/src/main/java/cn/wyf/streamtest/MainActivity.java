package cn.wyf.streamtest;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import net.wyf.library.observe.ApiCallback;
import net.wyf.library.utils.glide.GlideHelper;
import net.wyf.library.utils.log.XLogger;

import java.util.HashMap;
import java.util.Map;

import cn.wyf.streamtest.app.BaseActivity;
import cn.wyf.streamtest.di.modules.RequestHelper;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initviews();

        addSubscription(RequestHelper.getInstance().test(), new ApiCallback() {
            @Override
            public void onHandleSuccess(Object response) {
                XLogger.i("Living suc");
                XLogger.printJson(response.toString());
            }

            @Override
            public void onHandleFailure(String msg) {
                XLogger.i("Living fail");
                XLogger.e(msg);
            }

            @Override
            public void onHandleFinish() {
                XLogger.v("onHandleFinish");
            }

            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }
        });

//        addperson();

        allPerson();
    }

    private void initviews() {
        imageView = findViewById(R.id.imageViewId);
//        imageView.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.ic_launcher_round));
        GlideHelper.loadAvatar(this,"https://www.baidu.com/img/bd_logo1.png?where=super",imageView);
    }

    private void allPerson() {
        addSubscription(RequestHelper.getInstance().getPersons(), new ApiCallback() {
            @Override
            public void onHandleSuccess(Object response) {
                XLogger.i("Living suc");
                XLogger.printJson(response.toString());
            }

            @Override
            public void onHandleFailure(String msg) {
                XLogger.i("Living fail");
                XLogger.e(msg);
            }

            @Override
            public void onHandleFinish() {
                XLogger.v("onHandleFinish");
            }

            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }
        });
    }

    private void addperson() {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("name", "测试2");
        parmas.put("age", "25");
        addSubscription(RequestHelper.getInstance().addPerson(parmas), new ApiCallback() {
            @Override
            public void onHandleSuccess(Object response) {
                XLogger.i("onHandleSuccess");
                XLogger.printJson(response.toString());
            }

            @Override
            public void onHandleFailure(String msg) {
                XLogger.i("onHandleFailure-->" + msg);
            }

            @Override
            public void onHandleFinish() {
                XLogger.i("onHandleFinish");
            }

            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }
        });
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
    }
}
