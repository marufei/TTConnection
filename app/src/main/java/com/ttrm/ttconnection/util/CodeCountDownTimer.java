package com.ttrm.ttconnection.util;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 *
 * @Title: CodeCountDownTimer.java
 * @Description: 获取验证码倒计时
 * @author ※简单※
 * @date 2015年11月24日
 */
public class CodeCountDownTimer extends CountDownTimer {
	private Button targetBtn;
	private int availableResId;// 可用时的资源图片
	private int unavailableResId;// 不可用时的资源图片

	private CodeCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}
	
	/**
	 * <p>Title: 构造函数</p>
	 * <p>Description: 构造函数，作用：初始化总时间、间隔时间、可用时背景图片和不可用时背景图片</p>
	 * @param millisInFuture	计时总时间
	 * @param countDownInterval	间隔时间
	 * @param targetBtn	目标按钮
	 * @param availableResId	可用时背景图
	 * @param unavailableResId	不可用时背景图
	 */
	public CodeCountDownTimer(long millisInFuture, long countDownInterval, Button targetBtn, int availableResId,
                              int unavailableResId) {
		this(millisInFuture, countDownInterval);
		this.targetBtn = targetBtn;
		this.availableResId = availableResId;
		this.unavailableResId = unavailableResId;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		targetBtn.setBackgroundResource(unavailableResId);
		targetBtn.setText(millisUntilFinished / 1000L + "s后重发");
		targetBtn.setClickable(false);
	}

	@Override
	public void onFinish() {
		targetBtn.setBackgroundResource(availableResId);
		targetBtn.setText("重新获取");
		targetBtn.setClickable(true);
	}
}
