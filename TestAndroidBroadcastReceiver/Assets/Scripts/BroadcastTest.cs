using Assets.Scripts;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BroadcastTest : MonoBehaviour {
    private const string TAG = "BroadcastTest";

    void Start() {
        Application.runInBackground = true;
    }

    public void initBroadcast() {
        UnityBroadcastReceiver.instance.initBroadcast();

    }

    public void SendBroadcastWithArgs() {
        Debug.Log(TAG + " SendBroadcastWithArgs()");
        SendBroadcastWithArgs("android.media.VOLUME_CHANGED_ACTION", Application.identifier, "com.deepwaterooo.sdk.VolumeBroadcastReceiver");
    }

    // 下面的这个晚点儿再测
    void SendBroadcastWithArgs(string actionName, string packageName, string broadcastName) {
        AndroidJavaObject intentObject = new AndroidJavaObject("android.content.Intent", actionName);
        intentObject.Call<AndroidJavaObject>("putExtra", "enable", false);
        intentObject.Call<AndroidJavaObject>("putExtra", "enable2", true);
        intentObject.Call<AndroidJavaObject>("putExtra", "mystring", "mystring args");
        double[] data = { 4.3f, 45, -78, 10000, 89 };
        intentObject.Call<AndroidJavaObject>("putExtra", "datas", data);
        AndroidJavaObject componentNameJO = new AndroidJavaObject("android.content.ComponentName", packageName, broadcastName);
        intentObject.Call<AndroidJavaObject>("setComponent", componentNameJO);
        AndroidJavaClass unity = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject context = unity.GetStatic<AndroidJavaObject>("currentActivity").Call<AndroidJavaObject>("getApplicationContext");
        context.Call("sendBroadcast", intentObject);
    }
}
