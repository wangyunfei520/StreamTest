package net.wyf.library.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapperTransformation;

import net.wyf.library.R;
import net.wyf.library.utils.log.XLogger;
import net.wyf.library.widget.glide.GlideCircleTransform;
import net.wyf.library.widget.glide.GlideRoundTransform;

/**
 * 全局图片请求类：Glide
 */

public class GlideHelper {

    public static void loadPic(Context context, String url, ImageView view, int defResImg, int errResImg, Transformation transformation) {
        if (TextUtils.isEmpty(url)) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
                context instanceof Activity && ((Activity) context).isDestroyed()) return;

//        MyGlideUrl glideUrl = new MyGlideUrl(url);
        GlideUrl glideUrl = new GlideUrl(url);
        if (transformation instanceof GifBitmapWrapperTransformation) {
            Glide.with(context)
                    .load(glideUrl)
                    .dontAnimate()
                    .placeholder(defResImg)
                    .error(errResImg)
                    .transform(transformation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);
        } else if (transformation instanceof BitmapTransformation) {
            Glide.with(context)
                    .load(glideUrl)
                    .dontAnimate()
                    .placeholder(defResImg)
                    .error(errResImg)
                    .bitmapTransform(transformation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);
        }
    }

    public static void loadCenterCrop(Context context, String url, ImageView view, int defResImg, int errResImg) {
        CenterCrop bitmapCenterCrop = new CenterCrop(context);
        GifBitmapWrapperTransformation drawableTransformation = new GifBitmapWrapperTransformation(Glide.get(context).getBitmapPool(), bitmapCenterCrop);

        loadPic(context, url, view, defResImg, errResImg, drawableTransformation);
    }

    public static void loadFitCenter(Context context, String url, ImageView view, int defResImg, int errResImg) {
        FitCenter bitmapFitCenter = new FitCenter(context);
        GifBitmapWrapperTransformation drawableTransformation = new GifBitmapWrapperTransformation(Glide.get(context).getBitmapPool(), bitmapFitCenter);

        loadPic(context, url, view, defResImg, errResImg, drawableTransformation);
    }

    public static void loadCenterCrop(Context context, String url, ImageView view) {
        loadCenterCrop(context, url, view, R.drawable.default_pic, R.drawable.ico_error);
    }

    public static void loadFitCenter(Context context, String url, ImageView view) {
        loadFitCenter(context, url, view, R.drawable.default_pic, R.drawable.ico_error);
    }

    public static void loadAvatar(Context context, String url, ImageView view) {
        loadCenterCrop(context, url, view, R.drawable.default_pic, R.drawable.ico_error);
    }

    public static void loadCircle(Context context, String url, ImageView view) {
        loadCircle(context, url, view, R.drawable.default_pic, R.drawable.ico_error);
    }

    /**
     * 加载圆形图片
     */
    public static void loadCircle(Context context, String url, ImageView view, int defResImg, int errResImg) {

        loadPic(context, url, view, defResImg, errResImg, new GlideCircleTransform(context));
    }

    public static void loadRound(Context context, String url, ImageView view) {
        loadRound(context, url, view, R.drawable.default_pic, R.drawable.ico_error, 0);
    }

    /**
     * @param context
     * @param url
     * @param view
     * @param roundDegree 圆角的度数
     */
    public static void loadRound(Context context, String url, ImageView view, int roundDegree) {
        loadRound(context, url, view, R.drawable.default_pic, R.drawable.ico_error, roundDegree);
    }

    /**
     * @param context
     * @param url
     * @param view
     * @param defResImg
     * @param errResImg
     * @param roundDegree
     */

    public static void loadRound(Context context, String url, ImageView view, int defResImg, int errResImg, int roundDegree) {
        GlideRoundTransform glideRoundTransform;
        glideRoundTransform = (roundDegree == 0 ? new GlideRoundTransform(context) : new GlideRoundTransform(context, roundDegree));
        loadPic(context, url, view, defResImg, errResImg, glideRoundTransform);
    }
}
