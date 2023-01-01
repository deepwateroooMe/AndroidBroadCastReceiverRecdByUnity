using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UnityBroadcastProxy : AndroidJavaProxy {
    private const string TAG = "UnityBroadcastProxy";

    public delegate void BroadcastOnReceiveDelegate(AndroidJavaObject context, AndroidJavaObject intent);
    public BroadcastOnReceiveDelegate onReceiveDelegate;

    public UnityBroadcastProxy() : base("com.deepwaterooo.sdk.BroadcastReceiverInterface") { }

    public void onReceive(AndroidJavaObject context, AndroidJavaObject intent) { // 
        Debug.Log(TAG + " onReceive()");
        Debug.Log(TAG + " (onReceiveDelegate != null): " + (onReceiveDelegate != null));
        if (onReceiveDelegate != null) 
            onReceiveDelegate(context, intent);
    }
}
