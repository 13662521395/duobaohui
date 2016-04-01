package com.shinc.duobaohui.utils;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateStatus;

/**
 *
 * @author 刘高坡 友盟更新
 */
public class UmSundryUpdateUtils {
	private Activity mActivity;

	public UmSundryUpdateUtils(Activity mActivity, String slotId) {
		super();
		this.mActivity = mActivity;
		UmengUpdateAgent.setDefault();
		// 请在调用update,forceUpdate,silentUpdate函数之前设置推广id"54357"
		UmengUpdateAgent.setSlotId(slotId);
	}

	public UmSundryUpdateUtils(Activity mActivity) {
		super();
		this.mActivity = mActivity;
		UmengUpdateAgent.setDefault();
	}

	/**
	 * 自动更新
	 */
	public void AutoUpdate() {
		// 请在调用update,forceUpdate函数之前设置推广id，silentUpdate不支持此功能
		UmengUpdateAgent.update(mActivity);
	}

	/**
	 * 手动更新
	 */
	public void ManualUpdate() {

		UmengUpdateAgent.forceUpdate(mActivity);
	}

	/**
	 * 静默更新
	 */
	public void SilentUpdate() {

		UmengUpdateAgent.silentUpdate(mActivity);
	}

	/**
	 *
	 * @param updateOnlyWifi
	 *            考虑到用户流量的限制，默认在Wi-Fi接入情况下才进行自动提醒。
	 */

	public void isNetUpdate(boolean updateOnlyWifi) {
		UmengUpdateAgent.setUpdateOnlyWifi(updateOnlyWifi);
	}

	/**
	 * @param deltaUpdate
	 *            设置自动弹出更新提示 true 默认弹出
	 */
	public void isDeltaUpdate(boolean deltaUpdate) {
		UmengUpdateAgent.setDeltaUpdate(deltaUpdate);
	}

	/**
	 * @param isDialog
	 *
	 *            设置，是否自己对用户进行更新提示
	 */

	public void autoDialog(boolean isDialog) {
		UmengUpdateAgent.setUpdateAutoPopup(isDialog);
	}

	/**
	 * @param recha
	 *            是否启动高级样式 高级样式在4.1 及以上版本显示暂停/继续，取消按钮
	 */
	public void richaNotifiycation(boolean recha) {
		UmengUpdateAgent.setRichNotification(recha);
	}

	/**
	 * @param style
	 *            更新提示 true 对话框进行提示 false 通知栏进行提示
	 */
	public void uiStyle(boolean style) {
		if (style) {
			UmengUpdateAgent.setUpdateUIStyle(UpdateStatus.STYLE_DIALOG);
		} else {
			UmengUpdateAgent.setUpdateUIStyle(UpdateStatus.STYLE_NOTIFICATION);
		}
	}

	/**
	 * @param umengUpdateListener
	 *
	 *            设置更新回调来监听检测更新的返回结果 可以自己处理检测更新的结果
	 *
	 *            UpdateStatus.Yes 发现更新
	 *
	 *            UpdateStatus.No: 没有更新
	 *
	 *            UpdateStatus.NoneWifi "没有wifi连接， 只在wifi下更新"
	 *
	 *            UpdateStatus.Timeout: "超时"
	 */
	public void isListener(UmengUpdateListener umengUpdateListener) {
		UmengUpdateAgent.setUpdateListener(umengUpdateListener);
	}

	/**
	 * @param umengDialogButtonListener
	 *
	 *            下载回调接口 自动更新APK开始，进行，结束或者失败的时候，可以·使用该接口做出相应的反应。
	 *
	 *            UpdateStatus.Update: 用户选择更新
	 *
	 *            UpdateStatus.Ignore 用户选择忽略
	 *
	 *            UpdateStatus.NotNow "用户选择取消"
	 *
	 */
	public void dialogListener(
			UmengDialogButtonListener umengDialogButtonListener) {
		// TODO Auto-generated method stub
		UmengUpdateAgent.setDialogListener(umengDialogButtonListener);
	}

	/**
	 * 设置下载回调
	 *
	 * @param isDownloadListener
	 */
	public void downLoadListener(boolean isDownloadListener) {

		if (isDownloadListener) {
			UmengUpdateAgent.setDownloadListener(new UmengDownloadListener() {

				@Override
				public void OnDownloadStart() {
					Toast.makeText(mActivity, "下载开始", Toast.LENGTH_SHORT)
							.show();
				}

				@Override
				public void OnDownloadUpdate(int progress) {
					Toast.makeText(mActivity, "下载进度 : " + progress + "%",
							Toast.LENGTH_SHORT).show();
				}

				@Override
				public void OnDownloadEnd(int result, String file) {
					switch (result) {
						case UpdateStatus.DOWNLOAD_COMPLETE_FAIL:
							Toast.makeText(mActivity, "下载失败", Toast.LENGTH_SHORT)
									.show();
							break;
						case UpdateStatus.DOWNLOAD_COMPLETE_SUCCESS:
							Toast.makeText(mActivity, "下载成功\n下载文件位置 : " + file,
									Toast.LENGTH_SHORT).show();
							break;
						case UpdateStatus.DOWNLOAD_NEED_RESTART:
							// 增量更新请求全包更新(请勿处理这种情况)
							break;
					}
				}
			});

		} else {
			UmengUpdateAgent.setDownloadListener(null);
		}
	}
}
