package com.fongmi.android.tv.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.fongmi.android.tv.App;
import com.tencent.smtt.sdk.QbSdk;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class X5Initializer {

    private static final String TAG = "X5Initializer";
    private static final AtomicBoolean sIsInitializing = new AtomicBoolean(false);

    public interface Callback {
        void onInit(boolean success);
    }

    public static void init(Context context, Callback callback) {
        if (Build.VERSION.SDK_INT >= 34) { // Build.VERSION_CODES.UPSIDE_DOWN_CAKE
            Log.w(TAG, "X5 is not compatible with Android 14+, skipping initialization.");
            if (callback != null) callback.onInit(false);
            return;
        }

        if (!sIsInitializing.compareAndSet(false, true)) {
            Log.d(TAG, "X5 is already initializing.");
            return;
        }

        if (QbSdk.canLoadX5(context)) {
            Log.d(TAG, "X5 core is already available to use.");
            sIsInitializing.set(false);
            if (callback != null) callback.onInit(true);
            return;
        }

        Log.d(TAG, "Starting X5 core initialization.");
        new Thread(() -> {
            try {
                // 1. Install core (from Downloads)
                boolean coreInstalled = installLocalCore(context);
                if (!coreInstalled) {
                    Log.e(TAG, "No valid TBS core found to install.");
                    if (callback != null) callback.onInit(false);
                    sIsInitializing.set(false);
                    return;
                }

                // 2. Initialize X5 environment
                QbSdk.initX5Environment(context.getApplicationContext(), new QbSdk.PreInitCallback() {
                    @Override
                    public void onCoreInitFinished() {
                        Log.d(TAG, "X5 Core init finished.");
                    }

                    @Override
                    public void onViewInitFinished(boolean success) {
                        sIsInitializing.set(false); // Initialization process ends
                        if (success) {
                            Log.d(TAG, "X5 initialization finished successfully.");
                        } else {
                            Log.e(TAG, "X5 initialization failed. Will use system WebView.");
                        }
                        Log.d(TAG, "Final state - canLoadX5: " + QbSdk.canLoadX5(App.get()) + ", isX5Core: " + QbSdk.isX5Core());
                        if (callback != null) callback.onInit(success);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "An exception occurred during X5 initialization.", e);
                sIsInitializing.set(false);
                if (callback != null) callback.onInit(false);
            }
        }, "X5-Init-Thread").start();
    }

    private static boolean installLocalCore(Context context) {
        File coreFile = findCoreInDownloads();

        if (coreFile == null) {
            Log.d(TAG, "No TBS core found in Download directory. X5 installation skipped.");
            return false;
        }

        int coreVersion = getVersionFromFileName(coreFile.getName());
        int currentVersion = QbSdk.getTbsVersion(context);

        if (coreVersion == 0) {
            Log.e(TAG, "Could not determine TBS core version from filename: " + coreFile.getName());
            return false;
        }

        // 优化：只有当未安装 X5 或本地文件版本更高时才安装
        if (QbSdk.canLoadX5(context) && currentVersion >= coreVersion) {
            Log.d(TAG, "Current X5 core version (" + currentVersion + ") is up to date. Skipping installation.");
            return true;
        }

        Log.d(TAG, "Found TBS core in Download directory: " + coreFile.getAbsolutePath() + " (Version: " + coreVersion + ")");
        Log.d(TAG, "Resetting TBS environment and installing local core (Current: " + currentVersion + ")");
        
        QbSdk.reset(context);
        QbSdk.installLocalTbsCore(context, coreVersion, coreFile.getAbsolutePath());
        return true;
    }

    private static File findCoreInDownloads() {
        if (ContextCompat.checkSelfPermission(App.get(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "READ_EXTERNAL_STORAGE permission not granted. Cannot search for TBS core in Download directory.");
            return null;
        }

        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!downloadDir.exists()) {
            Log.w(TAG, "Download directory does not exist.");
            return null;
        }

        File[] files = downloadDir.listFiles((dir, name) -> name.toLowerCase().matches(".*(\\d{5,})_x5\\.tbs.*|.*(\\d{5,})\\.tbs\\.apk.*"));

        if (files == null || files.length == 0) {
            return null;
        }

        // Sort files to find the one with the highest version number in its name
        Arrays.sort(files, Comparator.comparingInt(f -> -getVersionFromFileName(f.getName()))); // Descending order

        Log.d(TAG, "Found " + files.length + " potential TBS files. Using the latest: " + files[0].getName());
        return files[0];
    }

    private static int getVersionFromFileName(String fileName) {
        // Extracts 5 or more digits from the filename, e.g., "046295" from "046295.tbs.apk"
        Pattern pattern = Pattern.compile("(\\d{5,})");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            try {
                String versionStr = matcher.group(1);
                if (versionStr != null) {
                    return Integer.parseInt(versionStr);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Failed to parse version from filename: " + fileName, e);
            }
        }
        return 0;
    }

    public static boolean isX5Available() {
        return QbSdk.canLoadX5(App.get());
    }
}