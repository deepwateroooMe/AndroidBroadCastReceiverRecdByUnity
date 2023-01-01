package com.deepwaterooo.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

// https://www.cnblogs.com/alps/p/11206465.html 还没有测试,没有看很懂
public class UnityBroadcastHelper {
    private static final String TAG = "UnityBroadcastHelper FromAndroid";

    // public interface BroadcastListener { // 这里为什么没有公用API 用来设置
    //     void onReceive(String action);
    // }
    // private final BroadcastListener listener;

    // private Queue<String[]> keysQueue = new LinkedList<>();
    // private Queue<String[]> valuesQueue = new LinkedList<>();

// // 原理是:　游戏端调用这个方法,本质上仍然是从安卓端来注册接听,然后通过回调的方式将广播内容回调给游戏端使用    
//     public UnityBroadcastHelper(String[] actions, BroadcastListener listener) {
//         Log.d(TAG, "UnityBroadcastHelper() from Android");
//         this.listener = listener;
//         IntentFilter intentFilter = new IntentFilter();
//         for (String action : actions) 
//             intentFilter.addAction(action);
//         Context context = UnityPlayer.currentActivity;
//         if (context == null) 
//             return;
//         context.registerReceiver(broadcastReceiver, intentFilter);
//     }

//     public boolean hasKeyValue() {
//         return !keysQueue.isEmpty();
//     }
//     public String[] getKeys() {
//         return keysQueue.peek();
//     }
//     public String[] getValues() {
//         return valuesQueue.peek();
//     }

//     public void pop() {
//         keysQueue.poll();
//         valuesQueue.poll();
//     }
//     public void stop() {
//         Context context = UnityPlayer.currentActivity;
//         if (context == null) 
//             return;
//         context.unregisterReceiver(broadcastReceiver);
//     }

//     private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//             @Override
//             public void onReceive(Context context, Intent intent) {
//                 String action = intent.getAction();
//                 Log.d(TAG, "BroadcastReceiver onReceive() action: " + action);

//                 Bundle bundle = intent.getExtras();
//                 if (bundle == null) 
//                     bundle = new Bundle();
//                 int n = bundle.size();
//                 String[] keys = new String[n];
//                 String[] values = new String[n];
//                 int i = 0;
//                 for (String key : bundle.keySet()) {
//                     keys[i] = key;
//                     Object value = bundle.get(key);
//                     values[i] = value != null ? value.toString() : null;
//                     Log.d(TAG, "UnityBroadcastHelper: key[" + i + "]: " + key);
//                     Log.d(TAG, "UnityBroadcastHelper: value[" + i + "]: " + value);
//                     i++;
//                 }
//                 keysQueue.offer(keys);
//                 valuesQueue.offer(values);
//                 listener.onReceive(action);
//             }
//         };
}