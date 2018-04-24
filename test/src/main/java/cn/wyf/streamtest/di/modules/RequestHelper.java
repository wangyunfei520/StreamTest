package cn.wyf.streamtest.di.modules;

import com.google.gson.JsonElement;

import net.wyf.library.request.Requester;


import java.util.Map;

import cn.wyf.streamtest.di.api.RequestApiService;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 网络请求管理类，基于Request对retrofit的二次封装
 */

public class RequestHelper {
    private volatile static RequestHelper instance = null;

    private RequestHelper() {
    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            synchronized (RequestHelper.class) {
                if (instance == null) {
                    instance = new RequestHelper();
                }
            }
        }
        return instance;
    }

    private static RequestApiService requestAPI;

    /**
     * 初始化网络请求
     */
    public void init(boolean isDebug) {
        Requester.initBuilder(isDebug, 60);
        requestAPI = Requester.getRequestAPI("http://192.168.10.223:8080/Demo/", RequestApiService.class);
    }

    /**
     * 请求所有用户信息
     * @return
     */
    public Observable<JsonElement> getPersons() {
        return requestAPI.getAllperson()
                .map(jsonElement -> jsonElement);
    }

    public Observable<JsonElement> addPerson(Map<String,String> parmas){
        return requestAPI.addPerson(parmas)
                .map(jsonElement -> jsonElement);
    }


    public Observable<JsonElement> test(){
        return requestAPI.test("wangyunfei","123456")
                .map(jsonElement -> jsonElement);
    }

    public Observable<JsonElement> getCoursebyId(){
        return requestAPI.getCoursebyId("1","测试")
                .map(jsonElement -> jsonElement);
    }
}
