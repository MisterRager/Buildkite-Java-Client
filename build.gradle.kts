buildscript {
    val kotlin_version = "1.3.31"

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath("com.android.tools.build:gradle:3.4.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

group = "es.lolrav"
version = "0.0.1"
