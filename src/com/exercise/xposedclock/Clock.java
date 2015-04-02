package com.exercise.xposedclock;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.R.color;
import android.graphics.Color;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Clock implements IXposedHookLoadPackage {

 @Override
 public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
  if (!lpparam.packageName.equals("com.android.systemui"))//check if the package being loaded is systemUI
            return;
  //all code here is only called if it is indeed systemUI
  //The findAndHookMethod is hooking onto com.android.systemui.statusbar.policy.Clock
   findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
       //The afterHookedMethod allows us to add our own code after the system method is executed     
	   @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
		   //The MethodHookParam allows us to modify the TextView of the method we are hooking(updateClock). 
		   //So we need to get hold of that TextView		   
             TextView tv = (TextView) param.thisObject;             
           //This is the TextView containing the clock, whatever we do with this TextView at this point,
          //will happen to the TextView in the statusbar, So now simply add :            
             //String text=tv.getText().toString();       		 
              String text=tv.getText().toString();
              tv.setText("¡ù"+text+"¡ù");           		 
              tv.setTextColor(Color.RED);
            }
 });
 }
}
