using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UnityEngine;

namespace Assets.Scripts {
    public class UnityBroadcastReceiver { 
        private const string TAG = "UnityBroadcastReceiver";

        public static UnityBroadcastReceiver instance = (UnityBroadcastReceiver)Activator.CreateInstance(typeof(UnityBroadcastReceiver));
        private UnityBroadcastProxy broadcastReceiverInterface;
        private AndroidJavaObject VolumeBroadcastReceiver; 

        public UnityBroadcastReceiver() {
            Debug.Log(TAG + " UnityBroadcastReceiver() constructor");
            VolumeBroadcastReceiver = new AndroidJavaObject("com.deepwaterooo.sdk.VolumeBroadcastReceiver"); 
        }

        public void initBroadcast() { // 现在是: 项目中自己并不曾 调用和初始化这些
            Debug.Log(TAG + " initBroadcast()");
            broadcastReceiverInterface = new UnityBroadcastProxy();
            broadcastReceiverInterface.onReceiveDelegate = onBroadcastReceive;       // 设置接收后的回调方法代理
            VolumeBroadcastReceiver.Call("setReceiver", broadcastReceiverInterface); // 设置接收者
            try {
                AndroidJavaObject recevierFilter = new AndroidJavaObject("android.content.IntentFilter");
// // 下面是自己音量控制的,先测它原始的                
                recevierFilter.Call("addAction", "android.media.VOLUME_CHANGED_ACTION"); // 监听手机硬件调控音量的变化
                // recevierFilter.Call("addAction", "android.intent.action.PACKAGE_REMOVED");
                // recevierFilter.Call("addAction", "android.intent.action.PACKAGE_ADDED");
                // recevierFilter.Call("addAction", "android.intent.action.PACKAGE_REPLACED");
                // recevierFilter.Call("addDataScheme", "package");
                
// 这里极有可能会错: 判断非空, 是为游戏端动态注册安卓广播接收器 拿到必要的 上下文环境
                AndroidJavaObject _UnityActivity = new AndroidJavaClass("com.unity3d.player.UnityPlayer").GetStatic<AndroidJavaObject>("currentActivity");
                _UnityActivity.Call<AndroidJavaObject>("registerReceiver", VolumeBroadcastReceiver, recevierFilter);
            }
            catch (Exception e) {
                Debug.Log(TAG + " " + e.Message.ToString());
            }
        }

        void onBroadcastReceive(AndroidJavaObject context, AndroidJavaObject intent) {
                // AndroidJavaObject action = intent.Call<AndroidJavaObject>("getAction"); // 这么写不对: 还是自己发现的并解决的问题,可是为什么最开始就没有检查呢?
            string action = intent.Call<string>("getAction");
            string pkgname = context.Call<string>("getPackageName");
            Debug.Log(TAG + " onBroadcastReceive() pkgname: " + pkgname + " action.ToString()" + action.ToString());

            if (action != null) {
                Debug.Log(TAG + " (action.ToString().Equals('android.media.VOLUME_CHANGED_ACTION')): " + (action.ToString().Equals("android.media.VOLUME_CHANGED_ACTION")));
                if (action.ToString().Equals("android.media.VOLUME_CHANGED_ACTION")) {
                    Debug.Log(TAG + " RECEIVED()");
                }
            }
        }
        
// // 下面是自己的方法,先测它的
// // 需要一个本地存储,来缓存接收到的音量的变化,保持最新最更新的状态
// // 安卓系统广播,音量变化的广播里没有数据的变化,不包含当前数据,必须得再去安卓平台再读一遍
// // 想测连通这个方法的目的是解偶合,但是感觉这么着来接收广播,再回安卓平台去读当前音量,效率比较低,可能下午的接口回调反而是最快效率比较好的
// // 这里只是提供了另一个思路,和可行方法        
//         void onBroadcastReceive(AndroidJavaObject context, AndroidJavaObject intent) {
//             Debug.Log(TAG + " onBroadcastReceive()");
//             AndroidJavaObject action = intent.Call<AndroidJavaObject>("getAction"); // <<<<<<<<<< 
//             if (action != null) {
//                 Debug.Log(TAG + "接收action：" + action.ToString());                
//                 Debug.Log(TAG + " (action.ToString().Equals('selfDefineReceiver')): " + (action.ToString().Equals("selfDefineReceiver")));
//                 if (action.ToString().Equals("selfDefineReceiver")) { // <<<<<<<<<< 这里不对,永远进不来
// // TODO 这里自己写相应，上面一句可删除
//                     string iaction = intent.Call<string>("getAction");
//                     //string extra = intent.Call<int>("getIntExtra", "android.media.EXTRA_VOLUME_STREAM_TYPE", -1); // 不知道这里调用会不会对
//                     Debug.Log(TAG + " iaction: " + iaction);
//                     //Debug.Log(TAG + " extra: " + extra);
//                     if (iaction != null && iaction.Equals("android.media.VOLUME_CHANGED_ACTION")) {
//                         // && extra == AudioManager.STREAM_MUSIC) // 这个判断,可能会多余,也拿不到安卓平台的相关设置值
//                         // TODO:　取出和存储必要的数据: 这里面没有数据,必须再去安卓平台读一遍,这样就不想再测下去了,去写别的急需解决的问题
//                         Debug.Log(TAG + " onBroadcastReceive() TODO SOMETHING HERE");
//                     }
//                 }
//             }
//         }
    }
}
