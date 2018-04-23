package net.wyf.library.utils.log;

import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

import net.wyf.library.utils.Utils;


/**
 * 全局Toast提示类
 */

public class XToast {

    private static Context context = Utils.getContext();

    /**
     * 显示短时间的toast提示
     *
     * @param tip     提示文字
     * @param tipType 提示类型，如：TastyToast.SUCCESS
     */
    public static void showShort(String tip, int tipType) {
        TastyToast.makeText(context, tip, TastyToast.LENGTH_SHORT, tipType);
    }

    /**
     * 显示长时间的toast提示
     *
     * @param tip     提示文字
     * @param tipType 提示类型，如：TastyToast.SUCCESS
     */
    public static void showLong(String tip, int tipType) {
        TastyToast.makeText(context, tip, TastyToast.LENGTH_LONG, tipType);
    }

    /**
     * 显示默认样式长时间的toast提示
     *
     * @param tip     提示文字
     */
    public static void showLong(String tip) {
        TastyToast.makeText(context, tip, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
    }

    /**
     * 显示默认样式的短时间toast提示
     *
     * @param tip 提示文字
     */
    public static void showShort(String tip) {
        showShort(tip, TastyToast.DEFAULT);
    }

    /**
     * 显示成功样式的短时间toast提示
     *
     * @param tip 提示文字
     */
    public static void showShortSuccess(String tip) {
        showShort(tip, TastyToast.SUCCESS);
    }

    /**
     * 显示警告样式的短时间toast提示
     *
     * @param tip 提示文字
     */
    public static void showShortWarning(String tip) {
        showShort(tip, TastyToast.WARNING);
    }

    /**
     * 显示错误样式的短时间toast提示
     *
     * @param tip 提示文字
     */
    public static void showShortError(String tip) {
        showShort(tip, TastyToast.ERROR);
    }

    /**
     * 显示信息样式的短时间toast提示
     *
     * @param tip 提示文字
     */
    public static void showShortInfo(String tip) {
        showShort(tip, TastyToast.INFO);
    }
}
