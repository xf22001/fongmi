# release-build-api-23-x5 开发与维护指南

本分支以 **`release-build`** (官方稳定发布基准) 为核心底座，通过手术式定制实现：**兼容 Android 6.0 (API 23)**、**集成腾讯 X5 浏览器内核**以及**解耦 Chaquopy 编译环境**。

## 一、 核心维护原则 (Mandatory)

1.  **基准一致性**：除了 API 23 兼容性、X5 集成和编译环境修复外，**严禁添加任何 `release-build` 分支没有的功能**。
2.  **最小侵入性**：修改冲突时，必须保持原实现的逻辑、代码结构及注释，仅对必要的类名或接口进行适配。
3.  **禁止魔改依赖**：不使用官方仓库以外的“魔改版”库（如 non-standard Media3），确保项目在标准环境下可编译。

## 二、 核心改动详述

### 1. 基础配置与兼容性 (Compatibility)
- **Min SDK 全局下调**: 必须将 `app` 主模块以及所有子模块 (`catvod`, `chaquo`, `forcetech`, `hook`, `jianpian`, `quickjs`, `thunder`, `tvbus`, `zlive`) 的 `build.gradle` 中的 `minSdk` 统一降低至 `23`。
- **强制源码依赖 (Crucial)**: 为了确保所有子模块都能以 API 23 为基准进行编译，在 `app/build.gradle` 中**必须**放弃官方的 `fileTree(dir: "libs", include: ["*.aar"])` 一次性导入方案。
  - **正确做法**：使用 `implementation project(':模块名')` 显式引入除 `chaquo` 以外的所有子模块。
  - **同时配置**：在 `fileTree` 中必须增加 `exclude: ["forcetech-release.aar", "hook-release.aar", "jianpian-release.aar", "thunder-release.aar", "tvbus-release.aar", "zlive-release.aar"]`，以防止主项目错误地加载上游可能预先放置在 `libs` 目录下基于 API 24 编译的旧 AAR 产物。
- **HTTPS 协议增强**: `gradle.properties` 增加 TLSv1.2/v1.3 支持，确保旧系统安全连接。
- **异步逻辑适配**: `Async.java` 中将 `CompletableFuture` 替换为本地 `SettableFuture`。
- **UI 降级规避**: 对于低版本缺失的 API，需要加版本判断。例如：`isInPictureInPictureMode()` 需要 `Build.VERSION.SDK_INT >= Build.VERSION_CODES.N` 保护。
- **资源适配 (Gradle 9+)**:
  - **Positional Format**: 所有的 `strings.xml` 必须使用位置索引格式（如 `%1$s`），严禁使用非位置占位符（如 `%s`），否则会导致 Gradle 9 资源合并失败。
  - **XLIFF Namespace**: 必须使用标准的 `xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2"`。

### 2. 腾讯 X5 核心 (TBS) 集成与 WebView 架构重构
解决旧设备系统 WebView 性能差、嗅探失败的问题。
- **WebView 工厂模式**: 引入 `IWebView` 接口，通过 `WebViewFactory` 动态创建 `SystemWebViewWrapper` (原 `CustomWebView`) 或 `X5WebViewWrapper`。
- **解析逻辑 (`ParseJob.java`)**: 所有的 WebView 操作必须通过工厂模式进行。

### 3. Chaquopy 编译解耦与环境隔离 (Build System)
解决不同 Gradle 版本竞争导致的死锁和编译难题。
- **`chaquo_build` 独立构建**: 将 Python 代码编译完全解耦。根 `settings.gradle` 中移除 `include ':chaquo'`。
- **两阶段构建 (Workflow Optimization)**: 
  - **步骤 1**: 独立调用 `chaquo_build/gradlew` 编译产出 `chaquo-release.aar`。
  - **步骤 2**: 将 AAR 拷贝至 `app/libs` 后，再执行主项目的纯净构建。
- **禁止嵌套构建**: 严禁在主项目的 `build.gradle` 中通过 `Task` 触发子项目的 Gradle 进程，以防止 Gradle 9 出现文件锁死（86% 卡死问题）。

## 三、 合并与同步注意事项 (Merging Tips)

1.  **同步官方进展**：当 `release` 或 `release-build` 有新提交时，优先同步依赖库版本。**目前已知稳定的对齐版本为 Media3 1.10.0, jupnp 3.0.4, flexbox 3.0.0**。
2.  **代码对齐**：必须保持 `Util.java`, `ExoUtil.java`, `PlayerManager.java` 等核心逻辑与 `release` 分支 100% 对齐。
3.  **Lint 严格校验**：`app/build.gradle` 中开启了 `lint { fatal 'NewApi' }`。

## 四、 本地开发与编译建议

### 1. 编译流程 (重构后)
**第一步：编译 Python AAR (仅当 Python 或 requirements 改变时执行)**
```bash
cd chaquo_build
./gradlew :chaquo:assembleRelease
cp chaquo/build/outputs/aar/chaquo-release.aar ../app/libs/
cd ..
```

**第二步：编译主项目**
```bash
source env.sh
./gradlew assembleLeanbackArmeabi_v7a
```

### 2. 常见问题
- **编译卡死在 86%**: 通常是因为同时运行了两个不同版本的 Gradle。请执行 `./gradlew --stop` 并杀掉所有 Java 进程。
- **R8 编译报错 (IndexOutOfBounds)**: 这是 Gradle 9 的底层 Bug。如果遇到，可临时在 `app/build.gradle` 中将 `minifyEnabled` 设为 `false` 绕过。
