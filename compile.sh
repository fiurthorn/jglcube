#! /bin/bash

/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/javac -d bin -cp bin\
:lib/commons-math-2.1.jar\
:lib/commons-lang-2.5.jar\
:lib/lwjgl/AppleJavaExtensions.jar\
:lib/lwjgl/asm-debug-all.jar\
:lib/lwjgl/jinput.jar\
:lib/lwjgl/laf-plugin-7.2.1.jar\
:lib/lwjgl/laf-widget-7.2.1.jar\
:lib/lwjgl/log4j-api-2.0-beta9.jar\
:lib/lwjgl/lwjgl_test.jar\
:lib/lwjgl/lwjgl_util_applet.jar\
:lib/lwjgl/lwjgl_util.jar\
:lib/lwjgl/lwjgl-debug.jar\
:lib/lwjgl/lwjgl.jar\
:lib/lwjgl/lzma.jar\
:lib/lwjgl/substance-7.2.1.jar\
:lib/lwjgl/trident-7.2.1.jar\
 src/de/raistlin77/gl/cube/*.java \
 src/de/raistlin77/gl/cube/tiles/*.java