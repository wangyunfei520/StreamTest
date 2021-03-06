package net.wyf.library.request;

import net.wyf.library.utils.NetworkUtils;
import net.wyf.library.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求类
 */

public class Requester {
    private static final int CONNECT_TIMEOUT = 300;  //请求超时时间，单位：s
    private static final int READ_TIMEOUT = 300;       //读写超时时间，单位：s

    private static OkHttpClient.Builder builder;

    /**
     * 初始化OkHttpClient.Builder()
     */
    public static void initBuilder(boolean isDebug, int timeout) {

        builder = new OkHttpClient.Builder();
        if (isDebug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }

        //配置缓存选项
        File cacheFile = new File(Utils.getContext().getCacheDir(), "responses");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);// 50 MiB
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnected(Utils.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isConnected(Utils.getContext())) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置文件下载监听
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse))
                        .build();
            }
        });
        //设置超时
        if (timeout == 0) {
            builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        } else {
            builder.connectTimeout(timeout, TimeUnit.SECONDS);
        }
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
    }

    /**
     * 构造retrofit请求api
     *
     * @param host    host地址
     * @param service retrofit请求api
     * @param <T>     retrofit请求api
     * @return retrofit请求api
     */
    public static <T> T getRequestAPI(String host, Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//此converter放在最后，因为不确定返回的数据是否为json
                .build();
        return retrofit.create(service);
    }
}
