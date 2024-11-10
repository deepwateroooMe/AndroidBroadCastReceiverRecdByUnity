// 包裹名：成SDK 与游戏端对接时的细节点。【TODO】：原理上弄明白，什么情况下用什么包裹名，什么时候用 com.unity3d.UnityPlayer
package com.unity3d.player;
// package com.dw.sdk;

import android.content.Context;
import android.content.Intent;

// 【广播转发】方式 
public abstract interface BroadcastReceiverInterface {
    public abstract void onReceive(Context var1, Intent var2);
}