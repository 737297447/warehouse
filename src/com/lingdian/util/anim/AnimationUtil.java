package com.lingdian.util.anim;

import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.lingdian.warehouse.R;

/***
 * ����������
 * 
 * @author daijy
 * 
 */
public class AnimationUtil {
	/** ���ƶ����Ƿ�� */
	private static boolean isAnimationOpen = true;

	/** �������� */
	public static void lowerIn(Activity a) {
		if (isAnimationOpen == true) {
			a.overridePendingTransition(R.anim.push_lower_in, R.anim.push_lower);
		}
	}

	/** ���������˳� */
	public static void lowerOut(Activity a) {
		if (isAnimationOpen == true) {
			a.overridePendingTransition(R.anim.push_lower, R.anim.push_lower_out);
		}
	}

	/** ���һ��� */
	public static void rightIn(Activity a) {
		if (isAnimationOpen == true) {
			a.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	/** ���һ��� */
	public static void LeftIn(Activity a) {
		if (isAnimationOpen == true) {
			a.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		}
	}

	public static void scale(Activity a) {
		if (isAnimationOpen == true) {
			a.overridePendingTransition(R.anim.scale_action, R.anim.alpha_action);
		}
	}

	public static void scaleLogin(Activity a) {
		if (isAnimationOpen == true) {
			a.overridePendingTransition(R.anim.scale_action_login, R.anim.alpha_action);
		}
	}
	
	public static void scaleLogin2(Activity a) {
		if (isAnimationOpen == true) {
			a.overridePendingTransition(R.anim.scale_action_login2, R.anim.alpha_action);
		}
	}

	public static void scaleLogin3(Activity a) {
		if (isAnimationOpen == true) {
			a.overridePendingTransition(R.anim.alpha_action, R.anim.scale_action_login2);
		}
	}
	
	/**����͸���л�*/
	public static void scaleLogin4(Activity a) {
		if (isAnimationOpen == true) {
			a.overridePendingTransition(R.anim.scale_action_login4_in, R.anim.scale_action_login4_out);
		}
	}

	/** ���Լ����Ϸ����������� */
	public static void TransUpIn(View v, AnimationListener listener, long durationMillis) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0);
		animation.setDuration(durationMillis);
		if (listener != null) {
			animation.setAnimationListener(listener);
		}
		v.startAnimation(animation);
	}

	/** ���Լ���ʼ�������� */
	public static void TransUpOut(View v, AnimationListener listener, long durationMillis) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.0f);
		animation.setDuration(durationMillis);
		if (listener != null) {
			animation.setAnimationListener(listener);
		}
		v.startAnimation(animation);
	}

	/** ���Լ��Ŀ�ʼ���������� */
	public static void TransUpInOfLower(View v, AnimationListener listener, long durationMillis) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setDuration(durationMillis);
		if (listener != null) {
			animation.setAnimationListener(listener);
		}
		v.startAnimation(animation);
	}

	/** ���Լ����·� �������� */
	public static void TransUpOutOfLower(View v, AnimationListener listener, long durationMillis) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
		animation.setDuration(durationMillis);
		if (listener != null) {
			animation.setAnimationListener(listener);
		}
		v.startAnimation(animation);
	}

	/** ���Լ����·� �������� */
	public static void TransUpOutOfLower(View v, AnimationListener listener, long durationMillis, float toXValue) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, toXValue, Animation.RELATIVE_TO_SELF, 0);
		animation.setDuration(durationMillis);
		if (listener != null) {
			animation.setAnimationListener(listener);
		}
		v.startAnimation(animation);
	}

	/** ��ת���������Լ�������Ϊ���� */
	public static void rotateAnimation(View v, float fromDegrees, float toDegrees, long durationMillis, boolean fillAfter) {
		RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(durationMillis);
		animation.setFillAfter(fillAfter);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		animation.setRepeatMode(Animation.REVERSE);
		v.startAnimation(animation);
	}

	/**
	 * AccelerateDecelerateInterpolator �ڶ�����ʼ������ĵط����ʸı�Ƚ��������м��ʱ�����
	 * AccelerateInterpolator �ڶ�����ʼ�ĵط����ʸı�Ƚ�����Ȼ��ʼ���� AnticipateInterpolator
	 * ��ʼ��ʱ�����Ȼ����ǰ˦ AnticipateOvershootInterpolator ��ʼ��ʱ�����Ȼ����ǰ˦һ��ֵ�󷵻�����ֵ
	 * BounceInterpolator ����������ʱ���� CycleInterpolator ����ѭ�������ض��Ĵ��������ʸı�������������
	 * DecelerateInterpolator �ڶ�����ʼ�ĵط���Ȼ���� LinearInterpolator �Գ������ʸı�
	 * OvershootInterpolator ��ǰ˦һ��ֵ���ٻص�ԭ��λ��
	 * ���android�����interpolators���������Ч��Ҳ�����Զ���interpolators ƽ�ƶ��� ������Լ�,X �᲻�䣬Y�᲻��
	 */
	public static void transyAnimation(View v, float fromYValue, float toYValue, long durationMillis, boolean fillAfter, Interpolator i, AnimationListener listener) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF,
				toYValue);
		animation.setDuration(durationMillis);
		animation.setFillAfter(fillAfter);
		if (i != null) {
			animation.setInterpolator(i);
		}
		if (listener != null) {
			animation.setAnimationListener(listener);
		}
		animation.setRepeatMode(Animation.REVERSE);
		v.startAnimation(animation);
	}

	public static void transxAnimation(View v, float fromxalue, float toxValue, long durationMillis, boolean fillAfter, Interpolator i, AnimationListener listener) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fromxalue, Animation.RELATIVE_TO_SELF, toxValue, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(durationMillis);
		animation.setFillAfter(fillAfter);
		if (i != null) {
			animation.setInterpolator(i);
		}
		if (listener != null) {
			animation.setAnimationListener(listener);
		}
		animation.setRepeatMode(Animation.REVERSE);
		v.startAnimation(animation);
	}

	/**
	 * the animation. ͸���ȱ仯
	 * @param fromAlpha Starting alpha value for the animation, where 1.0 means fully
	 * opaque and 0.0 means fully transparent.  
	 * @param toAlpha Ending alpha value for
	 * @param view
	 */
	public static void alphaAnimation(View v, float fromAlpha, float toAlpha, long durationMillis, boolean fillAfter) {
		AlphaAnimation animation = new AlphaAnimation(fromAlpha, toAlpha);
		animation.setDuration(durationMillis);
		animation.setFillAfter(fillAfter);
		animation.setRepeatMode(Animation.REVERSE);
		v.startAnimation(animation);
	}
	/**���Ŷ���*/
	public static void scaleAnimation(View v,AnimationListener listener){
		ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(800);
		animation.setRepeatCount(2);
		animation.setInterpolator(new BounceInterpolator());
		animation.setRepeatMode(Animation.REVERSE);
		animation.setAnimationListener(listener);
		v.startAnimation(animation);
	}
	/**Y�����Ŷ���*/
	public static void scaleYAnimation(View v, float fromYalue, float toYValue, long durationMillis, boolean fillAfter, Interpolator i, AnimationListener listener){
		ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, fromYalue, toYValue, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(durationMillis);
		if(i!=null){
			animation.setInterpolator(i);
		}
		animation.setFillAfter(fillAfter);
		animation.setRepeatMode(Animation.REVERSE);
		if(listener!=null){
			animation.setAnimationListener(listener);
		}
		v.startAnimation(animation);
	}
}
