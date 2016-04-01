package com.shinc.duobaohui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.widget.EditText;

public class SmsContent extends ContentObserver {
    public static final String SMS_URI_INBOX = "content://sms/inbox";

    private Activity activity = null;

    @SuppressWarnings("unused")
    private String smsContent = "";

    private EditText verifyText = null;

    public SmsContent(Activity activity, Handler handler, EditText verifyText) {
        super(handler);
        this.activity = activity;
        this.verifyText = verifyText;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Cursor cursor = null;// 光标
        // 读取收件箱中指定号码的短信
        cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[]{"_id", "address", "body", "read", "date"}, null, null, "date desc");

        if (cursor != null) {// 如果短信为未读模式
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex("date")));
                String smsbody = cursor
                        .getString(cursor.getColumnIndex("body"));
                if (smsbody.contains("夺宝会") && smsbody.contains("验证码")) {
                    verifyText.setText(getDynamicPassword(smsbody));
                }
            }

        }
        // 在用managedQuery的时候，不能主动调用close()方法， 否则在Android 4.0+的系统上， 会发生崩溃
        if (Build.VERSION.SDK_INT < 14) {
            cursor.close();
        }

    }

    /**
     * 从字符串中截取连续6位数字 用于从短信中获取动态密码
     *
     * @param str 短信内容
     * @return 截取得到的6位动态密码
     */
    public String getDynamicPassword(String str) {
        Pattern continuousNumberPattern = Pattern.compile("[0-9\\.]+");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while (m.find()) {
            if (m.group().length() == 6) {
                System.out.print(m.group());
                dynamicPassword = m.group();
            }
        }
        return dynamicPassword;
    }

}
