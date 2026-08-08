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
- **HTTPS 协议增强**: `gradle.properties` 增加 TLSv1.2/v1.3 支持，确保旧 system 安全连接。
- **异步逻辑适配**: `Async.java` 与 `Media.java` 中将 `CompletableFuture` 替换为本地或 Guava 的 `SettableFuture`，确保 API 23 静态 Lint Vital 检测安全通过。
- **RTMP 重复类排除**: 对 `media3-datasource-rtmp` 依赖进行 `exclude group: "io.antmedia", module: "rtmp-client"`，避免其与主模块中自带的 `mcxinyu:LibRtmp-Client` 重复类冲突。
- **AAR Metadata SDK 拦截屏蔽**: 在 `app/build.gradle` 底端配置 `tasks.whenTaskAdded` 过滤，将 `check*AarMetadata` 任务失效（`task.enabled = false`），规避 Glide 5.0.9 对 `compileSdk 37` 的强校验拦截。
- **UI 降级规避**: 对于低版本缺失的 API，需要加版本判断。例如：`isInPictureInPictureMode()` 需要 `Build.VERSION.SDK_INT >= Build.VERSION_CODES.N` 保护。
- **资源适配 (Gradle 9+)**:
  - **Positional Format**: 所有的 `strings.xml` 必须使用位置索引格式（如 `%1$s`），严禁使用非位置占位符（如 `%s`），否则会导致 Gradle 9 资源合并失败。
  - **XLIFF Namespace**: 必须使用标准的 `xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2"`。
- **CI 环境适配**: 在 `app/build.gradle` 中必须对 `local.properties` 进行 `exists()` 判断，以兼容 GitHub Actions 等没有本地属性文件的编译环境。

### 2. 腾讯 X5 核心 (TBS) 集成与 WebView 架构重构
解决旧设备系统 WebView 性能差、嗅探失败的问题。
- **WebView 工厂模式**: 引入 `IWebView` 接口，通过 `WebViewFactory` 动态创建 `SystemWebViewWrapper` (原 `CustomWebView`) 或 `X5WebViewWrapper`。
- **解析逻辑 (`ParseJob.java`)**: 所有的 WebView 操作必须通过工厂模式进行。
- **本地 SDK 依赖**: 必须在 `app/libs` 中放置 `tbs_sdk_*.jar`，且 `app/build.gradle` 的 `fileTree` 必须包含 `include: ["*.jar"]` 才能确保 X5 代码编译通过。

### 3. Chaquopy 编译解耦与自动化构建 (Build System & Automation)
解决不同 Gradle 版本竞争导致的死锁和编译难题，并保持完全自动化的开发体验。
- **`chaquo_build` 独立构建**: 将 Python 代码编译完全解耦，根 `settings.gradle` 中移除 `include ':chaquo'`，使用独立的 Gradle 进行编译。
- **自动化构建任务链**: 
  - 在根目录 `build.gradle` 中声明了 `buildChaquo` 任务（`Exec` 类型）。它在运行前自动执行 `catvod` 与 `chaquo` 的源码拷贝同步，运行中启动独立进程调用子项目的 `./gradlew :chaquo:assembleRelease`，并在结束后自动拷贝 AAR 至 `app/libs`。
  - 在 `app/build.gradle` 中配置了 `preBuild.dependsOn(buildChaquo)`。这使得任何 APK 构建任务都会全自动、按需增量构建 Chaquopy AAR，无需再手动执行多阶段构建或复制文件。
  - 通过使用非阻塞的 `Exec` 独立进程机制，完美避免了 Gradle 9 直接嵌套子工程构建导致的文件锁死（86% 卡死）问题。

## 三、 合并与同步注意事项 (Merging Tips)

1.  **同步官方进展**：当 `release` 或 `release-build` 有新提交时，优先同步依赖库版本。**目前已知稳定的对齐版本为 Media3 1.10.0, jupnp 3.0.4, flexbox 3.0.0**。
2.  **代码对齐**：必须保持 `Util.java`, `ExoUtil.java`, `PlayerManager.java` 等核心逻辑与 `release` 分支 100% 对齐。
3.  **Lint 严格校验**：`app/build.gradle` 中开启了 `lint { fatal 'NewApi' }`。

## 四、 本地开发与编译建议

### 1. 编译流程 (重构后)
目前构建流程已实现完全自动化，可直接运行：
```bash
. env.sh
./gradlew assembleLeanbackArmeabi_v7a
```
此时 Gradle 会自动触发 `buildChaquo` 任务来编译并拷贝 Python AAR。

**手动编译 Python AAR (可选)**
如果您想单独同步源码并编译 Python 部分：
```bash
./gradlew buildChaquo
```

### 2. 常见问题
- **编译卡死在 86%**: 通常是因为嵌套子工程构建直接共享 Gradle daemon 造成的锁死。若遇到，可运行 `./gradlew --stop` 结束残留守护进程。当前通过 `Exec` 独立进程任务已解决此问题。
- **R8 资源优化报错 (Optimized resource shrinking requires non-final IDs)**: 由于主项目禁用了非 Final 资源 ID，R8 可能会因启用优化资源缩减而报错。可在 `gradle.properties` 中加入 `android.r8.optimizedResourceShrinking=false` 绕过。
- **配置失败 (No local.properties)**: 确认已按照本指南第一章第 1 节“CI 环境适配”部分修改了 `build.gradle`。
