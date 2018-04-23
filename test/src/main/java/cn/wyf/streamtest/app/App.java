package cn.wyf.streamtest.app;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import net.wyf.library.utils.Utils;
import net.wyf.library.utils.log.XLogger;
import net.wyf.library.utils.log.Xsp;

import cn.wyf.streamtest.BuildConfig;
import cn.wyf.streamtest.di.modules.RequestHelper;

public class App extends Application {
    private static App appContext;

    public static App getIntstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        initPlugins(this);
    }

    private void initPlugins(Context context) {
        Utils.init(context);//初始化
        RequestHelper.getInstance().init(BuildConfig.DEBUG);    //初始化网络请求
        XLogger.init(BuildConfig.DEBUG);//初始化日志打印
        Xsp.getInstance().init();//初始化sharedPreferences存储
    }

    public void showToast(String msg) {
        Toast.makeText(appContext, msg + "", Toast.LENGTH_SHORT).show();
    }
}
