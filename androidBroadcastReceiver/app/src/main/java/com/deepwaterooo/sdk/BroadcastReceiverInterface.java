package com.deepwaterooo.sdk;

import android.content.Context;
import android.content.Intent;
 
public abstract interface BroadcastReceiverInterface {
    public abstract void onReceive(Context var1, Intent var2);
}