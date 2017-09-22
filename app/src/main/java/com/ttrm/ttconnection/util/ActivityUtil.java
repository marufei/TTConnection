package com.ttrm.ttconnection.util;

import android.app.Activity;
import android.content.Intent;


import com.ttrm.ttconnection.activity.LoginActivity;

import java.util.ArrayList;

public class ActivityUtil {
	static String TAG ="TAG--ActivityUtil";

	static ArrayList<Activity> mActivities = new ArrayList<Activity>();

	public static void exitAll() {
		for (Activity activity : mActivities) {
			activity.finish();
		}
	}
	public static void add(Activity activity) {
		if (!mActivities.contains(activity)) {
			mActivities.add(activity);
		}
//		MyUtils.Loge(TAG,"启动activity得个数="+mActivities.size()+"---activity顺序-0="+mActivities.get(0).toString()+"最后="+mActivities.get(mActivities.size()-1).toString());
		getLastActivityName();
	}
	public static int select() {
		return mActivities.size();
	}
	public static void delect(Activity activity){
		if (mActivities.contains(activity)){
			mActivities.remove(activity);
		}
//		MyUtils.Loge(TAG,"启动activity得个数="+mActivities.size());

	}
	public static String getLastActivityName(){
//		MyUtils.Loge(TAG,"最后ActivityName="+mActivities.get(mActivities.size()-1).toString());
		return mActivities.get(mActivities.size()-1).toString();

	}
	public static void toLogin(Activity activity,int errcode){
		if(errcode==40001) {
			exitAll();
			Intent intent = new Intent(activity, LoginActivity.class);
			intent.putExtra("logout", true);
			activity.startActivity(intent);
		}
	}
}
