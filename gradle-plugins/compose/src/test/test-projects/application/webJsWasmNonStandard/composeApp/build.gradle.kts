import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    js(name = "webJs", IR) {
        outputModuleName.set("composeApp")
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs(name = "webWasm") {
        outputModuleName.set("composeApp")
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    // Simple task that prints "Hello World" to the console

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
        }

        val webMain by creating {
            resources.setSrcDirs(resources.srcDirs)
            dependsOn(commonMain.get())
        }

        val webJsMain by getting {
            dependsOn(webMain)
        }

        val webWasmMain by getting {
            dependsOn(webMain)
        }
    }
}


