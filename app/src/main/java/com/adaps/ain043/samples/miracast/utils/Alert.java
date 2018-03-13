package com.adaps.ain043.samples.miracast.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog.Builder;
import android.util.TypedValue;
import android.widget.Toast;

import com.adaps.ain043.samples.R;

public class Alert {

    static class C10071 implements OnClickListener {
        C10071() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    static class C10082 implements OnClickListener {
        C10082() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    static class C10093 implements OnClickListener {
        C10093() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    static class C10104 implements OnClickListener {
        C10104() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    static class C10115 implements OnClickListener {
        C10115() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    public static void show(Context context, String title, String message) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843605, typedValue, true);
        new Builder(context).setTitle(title.toUpperCase()).setCancelable(false).setMessage((CharSequence) message).setIcon(typedValue.resourceId).setPositiveButton(context.getString(R.string.close), new C10071()).show();
    }

    public static void showPleaseFillInTextBox(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843605, typedValue, true);
        new Builder(context).setTitle(context.getString(R.string.warning)).setCancelable(false).setMessage(context.getString(R.string.msg_fill_in_text_box)).setIcon(typedValue.resourceId).setPositiveButton(context.getString(R.string.close), new C10082()).show();
    }

    public static void showWithOK(Context context, String title, String message, OnClickListener onClickListener) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843605, typedValue, true);
        new Builder(context).setTitle(title.toUpperCase()).setCancelable(false).setMessage((CharSequence) message).setIcon(typedValue.resourceId).setPositiveButton(context.getString(R.string.close), onClickListener).show();
    }

    public static void showWithOKCancel(Context context, String title, String message, OnClickListener onClickListener) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843605, typedValue, true);
        new Builder(context).setTitle(title.toUpperCase()).setCancelable(false).setMessage((CharSequence) message).setIcon(typedValue.resourceId).setPositiveButton(context.getString(R.string.close), onClickListener).setNegativeButton(context.getString(R.string.close), new C10093()).show();
    }

    public static void somethingWentWrong(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843605, typedValue, true);
        new Builder(context).setTitle(context.getString(R.string.oops).toUpperCase()).setCancelable(false).setMessage(context.getString(R.string.something_went_wrong)).setPositiveButton(context.getString(R.string.close), new C10104()).setIcon(typedValue.resourceId).show();
    }

    public static void somethingWentWrongWithServer(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843605, typedValue, true);
        new Builder(context).setTitle(context.getString(R.string.warning).toUpperCase()).setCancelable(false).setMessage(context.getString(R.string.msg_can_not_connect_to_server)).setPositiveButton(context.getString(R.string.close), new C10115()).setIcon(typedValue.resourceId).show();
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, 0).show();
    }
}
