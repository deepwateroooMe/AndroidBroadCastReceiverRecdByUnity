package com.unity3d.player;
// package com.dw.sdk;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;

/**
 * 获取设置系统（音乐）音量的封装类：
 // 原理上，仍然是【SDK 自定义、系统广播接收器、并与转发给游戏端】
 */
public class VolumeChangeObserver { // 【自定义广播接收器】方式
    private final String TAG = "【安卓SDK】VolumeChangedObserver";

    private static AudioManager _AudioManager;
    private static Context _Context;

    private static final String ACTION_VOLUME_CHANGED = "android.media.VOLUME_CHANGED_ACTION";
    private static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";

    // 音量广播监听
    private static VolumeBroadcastReceiver mVolumeReceiver;
    // 音量变化监听触发事件
    private VolumnReceiverInterface _VolumnReceiverInterface; // <<<<<<<<<<<<<<<<<<<< 

    // 【自定义广播接收器】：向SDK注册订阅，求转发广播。仅只，拿【上下文  Context】的方法，与 Unity 游戏端不同
    public void setVolumeCallback(VolumnReceiverInterface VolumeChangedIntereface) {
        Log.d(TAG, "setVolumeCallback() ");
        _Context = getActivity(); // 【TODO】：SDK 里拿到的 activity 实例，通过【反射机制】，是同一个 Unity 端实例吗？
        _AudioManager =(AudioManager) _Context.getSystemService(Context.AUDIO_SERVICE);
        Log.d(TAG, "(_AudioManager != null): " + (_AudioManager != null));
        _VolumnReceiverInterface = VolumeChangedIntereface; 
    }

    // 通话音量
    public int GetCallMax(){
        return  _AudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL) ;
    }
    // 通话音量
    public int GetCallMin(){
        // AudioManager.getStreamMinVolume要求 API 28，要求太高，先注掉，返回 0
        // return  _AudioManager.getStreamMinVolume(AudioManager.STREAM__CALL) ;
        return 0;
    }
    // 通话音量
    public int GetCallCurrentValue(){
        return  _AudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL) ;
    }

    // 系统音量
    public int GetSystemMax(){
        return  _AudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM) ;
    }
    // 系统音量
    public int GetSystemMin(){
        // AudioManager.getStreamMinVolume要求 API 28，要求太高，先注掉，返回 0
        // return  _AudioManager.getStreamMinVolume(AudioManager.STREAM_SYSTEM) ;
        return 0;
    }
    // 系统音量
    public int GetSystemCurrentValue(){
        return  _AudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM) ;
    }
    // 铃声音量
    public int GetRingMax(){
        return  _AudioManager.getStreamMaxVolume(AudioManager.STREAM_RING) ;
    }
    // 铃声音量
    public int GetRingMin(){
        // AudioManager.getStreamMinVolume要求 API 28，要求太高，先注掉，返回 0
        // return  _AudioManager.getStreamMinVolume(AudioManager.STREAM_RING) ;
        return 0;
    }
    // 铃声音量
    public int GetRingCurrentValue(){
        return  _AudioManager.getStreamVolume(AudioManager.STREAM_RING) ;
    }
    // 音乐音量
    public int GetMusicMax(){
        return  _AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ;
    }
    // 音乐音量
    public int GetMusicMin(){
        // AudioManager.getStreamMinVolume要求 API 28，要求太高，先注掉，返回 0
        // return  _AudioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC) ;
        return  0;
    }
    // 音乐音量
    public int GetMusicCurrentValue(){
        return  _AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) ;
    }
    // 增加音乐音量
    public void AddMusicVolumn(int value){
        int addValue = (GetMusicCurrentValue() + value) ;
        // 防止音量值越界
        addValue = addValue > GetMusicMax() ? GetMusicMax():addValue;
        _AudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,addValue,AudioManager.FLAG_PLAY_SOUND);
    }
    // 减少音乐音量
    // @param value
    public void ReduceMusicVolumn(int value){
        int reduceValue = (GetMusicCurrentValue() - value) ;
        // 防止音量值越界
        reduceValue = reduceValue < GetMusicMin() ?GetMusicMin():reduceValue;
        _AudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,reduceValue,AudioManager.FLAG_PLAY_SOUND);
    }
    // 设置音乐音量大小
    // @param value
    public void SetMusicVolumn(int value){
        // 防止越界
        if(value<GetMusicMin())value = GetMusicMin();
        if(value>GetMusicMax())value = GetMusicMax();
        _AudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,value,AudioManager.FLAG_PLAY_SOUND);
    }

    // 注册广播监听
    public void registerVolumeReceiver() {
        mVolumeReceiver = new VolumeBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        _Context.registerReceiver(mVolumeReceiver, filter);
    }
    // 取消注册广播监听
    public static void unregisterVolumeReceiver() {
        if (mVolumeReceiver != null) _Context.unregisterReceiver(mVolumeReceiver);
    }

    // // static 【内部静态类】：还记得、安卓【内部静态类】因为持有外部引用，而造成【内存泄露】吗？使用WeakReference...
    // private static class VolumeBroadcastReceiver extends BroadcastReceiver {
    //     private WeakReference<VolumeChangeObserver> mObserverWeakReference;
    //     public VolumeBroadcastReceiver(VolumeChangeObserver volumeChangeObserver) {
    //         mObserverWeakReference = new WeakReference<>(volumeChangeObserver);
    //     }
    //     @Override
    //     public void onReceive(Context context, Intent intent) {
    //         // blahblah....
    //     }
    // }
    // 音量变化广播类
    private class VolumeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Android SDK has receive broadcast from OS system");
            if (isReceiveVolumeChange(intent) == true) { // 【广播过滤器】：特定类型的广播
                int currVolume = _AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                Log.i(TAG, "onReceive:isReceiveVolumeChange currVolume "+currVolume);
                if (currVolume >= 0 && _VolumnReceiverInterface != null)
                    _VolumnReceiverInterface.onVolumeChanged(currVolume); // 【转发广播】：携带，现音量数据
            }
        }

// 判断是否是音乐音量变化（音量键改变的音量）
        public boolean isReceiveVolumeChange(Intent intent) {
            return intent.getAction() != null
                && intent.getAction().equals(ACTION_VOLUME_CHANGED) // 【广播过滤器】：特定类型的广播
                && intent.getIntExtra(EXTRA_VOLUME_STREAM_TYPE, -1) == AudioManager.STREAM_MUSIC;
        }
    }
    
    private Activity _unityActivity; // 设置一个 Activity 参数
    // 通过【反射】获取 Unity 的 Activity 的上下文
    // 【TODO】：这个例子里，通过反射获取 Unity 的 Activity 的上下文；
    //          Unity 【广播接收器】的例子里，【初始化、游戏端、广播接收器】时，用自己游戏端 activity 实例，调用安卓
    // 感觉，是两个相反方向的逻辑、2 种不同方向的实现，都弄明白 
    Activity getActivity() { // 上面初始化的时候,会需要这个上下文 
        if (_unityActivity == null) {
            try {
                // Class.forName(xxx.xx.xx)的作用就是要求JVM查找并加载指定的类，如果在类中有静态初始化器的话，JVM必然会执行该类的静态代码段。
                Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer"); // 返回的是类
                Activity activity = (Activity) classtype.getDeclaredField("currentActivity").get(classtype);
                _unityActivity = activity; // 【TODO】：这里没看懂，它不是实例？就是这么直接赋值的。。 
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            } catch (IllegalAccessException e){
                e.printStackTrace();
            } catch (NoSuchFieldException e){
                e.printStackTrace();
            }
        }
        return _unityActivity;
    }
}