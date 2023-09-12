package linghe.robot;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import linghe.robot.comm.Config;
import linghe.robot.listener.GlobalListener;
import linghe.robot.view.MainFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean useOsr = false;
        new MainFrame(Config.src("view/html/MainView.html"), useOsr, false, args);
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
