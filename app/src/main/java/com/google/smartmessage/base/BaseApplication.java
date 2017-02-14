package com.google.smartmessage.base;

import android.app.Application;

import com.google.smartmessage.utils.CrashUtils;
import com.google.smartmessage.utils.LogUtils;
import com.google.smartmessage.utils.Utils;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2016
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * <p>
 * Project_Name：SmartMessage
 * Package_Name：com.google.smartmessage.base
 * Version：1.0
 * time：2016/12/28 1:04
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class BaseApplication extends Application {
    private static BaseApplication appContext;

    public static BaseApplication getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 内存泄露检查工具
       /* if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/
        appContext = this;
        Utils.init(appContext);
        CrashUtils.getInstance().init();
        LogUtils.getBuilder().setTag("MyTag").setLog2FileSwitch(true).create();
    }
}
