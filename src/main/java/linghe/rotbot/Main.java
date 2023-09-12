package linghe.rotbot;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import linghe.rotbot.listener.GlobalListener;

import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
//        init();
        listener();
    }

    public static void init() {
        // 初始化OpenCv
        URL url = ClassLoader.getSystemResource("lib/opencv_java480.dll");
        System.load(url.getPath());
    }

    // 注册全局监听事件
    public static void listener() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalListener globalListener = new GlobalListener();
        GlobalScreen.addNativeKeyListener(globalListener);
        GlobalScreen.addNativeMouseListener(globalListener);
        GlobalScreen.addNativeMouseWheelListener(globalListener);
        GlobalScreen.addNativeMouseMotionListener(globalListener);
    }
}
