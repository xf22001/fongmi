package com.fongmi.mediacompat;

/**
 * 声明当前开源 Media3 栈相对 FongMi 闭源 fork Media3 的能力差异。
 *
 * <p>上游 FongMi/TV 依赖一个闭源的 fork Media3 AAR（mpv、libass、弹幕渲染、
 * 以及若干能力查询 API 都在其中，仓库内无源码、无法为 API 23 重建）。
 * 本工程改用官方 Media3 后，依赖这些能力的特性无法工作，由本模块内的
 * 同名同包占位类承接编译。
 *
 * <p>本类用于把"哪些功能真的能用"显式化：UI 应据此隐藏或置灰对应入口，
 * 避免出现点了没反应的死开关。新增占位实现时，请同步在这里登记。
 */
public final class Capability {

    private Capability() {
    }

    /** mpv 播放引擎：实现整体位于闭源 fork，开源栈下 MpvPlayer.isAvailable() 恒为 false */
    public static boolean mpv() {
        return false;
    }

    /** libass（ASS 特效字幕）：闭源 fork 内置 native libass，开源栈无此原生库 */
    public static boolean libass() {
        return false;
    }

    /** 弹幕渲染：控制器位于闭源 fork，开源栈下为空实现。数据层（DanmakuApi 等）仍可用 */
    public static boolean danmaku() {
        return false;
    }

    /** 第二字幕（双语）：依赖闭源 fork 的 SecondaryTextOutput / SecondaryTextTrackSelector */
    public static boolean secondarySubtitle() {
        return false;
    }

    /** 内存预加载：DefaultPreloadManager 为占位实现，build() 返回 null */
    public static boolean memoryPreload() {
        return false;
    }

    /** 磁盘预加载：可用，但当前只预取开头的固定字节，忽略时长与线程数配置 */
    public static boolean diskPreload() {
        return true;
    }

    /** DolbyVision 输出策略：ExoUtil 中的消费点已随 fork API 一并移除，设置项当前不生效 */
    public static boolean dolbyVisionOutputPolicy() {
        return false;
    }

    /** 自定义音频输出：AudioTrackAudioOutputProvider 为占位实现，已不再注入 */
    public static boolean audioOutputProvider() {
        return false;
    }

    /** 跳过静音：上游用 fork 的 isSkipSilenceSupported() 判定，官方 API 尚未确认 */
    public static boolean skipSilence() {
        return false;
    }

    /** 音视频特效：不依赖闭源库，走官方 BaseAudioProcessor / media3-effect，可用 */
    public static boolean effect() {
        return true;
    }
}
