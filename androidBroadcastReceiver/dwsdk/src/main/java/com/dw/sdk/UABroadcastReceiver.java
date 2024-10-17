package com.dw.sdk;
 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dw.sdk.BroadcastReceiverInterface;

// 【安卓SDK 自定义、系统广播接收器UABroadcastReceiver】：
//     为游戏unity端【动态注册】安卓广播提供了【桥接、接口与转发注册订阅】，
// 并不曾标明所想要注册的广播类型，会在游戏中根据需要来设定？？？  // <<<<<<<<<<<<<<<<<<<<
// 这个和【自定义广播接收器】：它【AndroidManifest.xml 里，静态注册了、安卓系统广播】
// 【静态注册】：游戏端 AndroidManifest.xml 里、静态注册【TODO】：这里，亲爱的表哥的活宝妹笨宝妹、写得云里雾里。。。
// 【动态注册】：当然代码里注册？【TODO】怎么实现的？
// SDK 中、自定义【系统广播、接收器】，自定义逻辑：
// 当SDK 接收到系统广播、当此【广播接收器】有被【订阅、求转发】BroadcastReceiverInterface receiver, 接收逻辑，就将接收到的广播，转发给游戏端的订阅者
public class UABroadcastReceiver extends BroadcastReceiver {
    String TAG = "UABroadcastReceiver";

    public static BroadcastReceiverInterface receiver;
    public UABroadcastReceiver() {  }
 
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Android has receive broadcast");
        if (receiver != null) {
            receiver.onReceive(context, intent);
        } else {
            Log.e(TAG, "BroadcastReceiverInterface receiver is null");
        }
    }
    // 游戏端：借助此接口，向SDk 注册，对【系统广播】有需要，求转发给游戏端
    public void setReceiver(BroadcastReceiverInterface unityReceiverProxy) {
        Log.d(TAG, "setReceiver");
        receiver = unityReceiverProxy;
    }
}