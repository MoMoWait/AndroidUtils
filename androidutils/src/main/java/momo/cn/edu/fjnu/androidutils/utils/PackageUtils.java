package momo.cn.edu.fjnu.androidutils.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import momo.cn.edu.fjnu.androidutils.data.CommonValues;

/**
 * 获取应用包工具
 */
public class PackageUtils {

	private static final String TAG = PackageUtils.class.getSimpleName();

	private PackageUtils(){
		
	}

	/**
	 * 获取设备所有已安装的应用程序(除了系统APP)
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getAllApp(Context context){
		PackageManager pm=context.getPackageManager();
		List<PackageInfo> packageInfos=pm.getInstalledPackages(0);
		List<PackageInfo> results=new ArrayList<>();
		for(PackageInfo packageInfo:packageInfos){
			if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==0){
				results.add(packageInfo);
			}
		}
		return results;
	}

	/**
	 * 获取包名
	 * @return
	 */
	public static String getPackageName(){
		return CommonValues.application.getPackageName();
	}

	/**
	 * 获取APK文件的图标
	 * @param context
	 * @param apkPath
	 * @return
	 */
	public static Drawable getApkIcon(Context context, String apkPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = apkPath;
			appInfo.publicSourceDir = apkPath;
			try {
				return appInfo.loadIcon(pm);
			} catch (OutOfMemoryError e) {
				Log.e(TAG, "getApkIcon->OutOfMemory:" + e);
			}
		}
		return null;
	}
}
