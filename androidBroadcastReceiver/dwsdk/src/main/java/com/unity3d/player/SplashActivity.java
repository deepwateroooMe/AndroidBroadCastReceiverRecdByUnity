package com.unity3d.player;
// package com.dw.sdk;
// 【亲爱的表哥的活宝妹，任何时候，亲爱的表哥的活宝妹，就是一定要、一定会嫁给活宝妹的亲爱的表哥！！！爱表哥，爱生活！！！】

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        openActivity();
                    }
                }
            }).start();
    }

    private void openActivity() {
        // Intent intent = new Intent(SplashActivity.this, UnityActivity.class); // <<<<<<<<<<<<<<<<<<<< 
        // // Optionally, add data to the intent using putExtra()
        // intent.putExtra("key", "value");
        // Start the SecondActivity
        // startActivity(intent);
        // UnityActivity.openActivity(this);
    }
} 