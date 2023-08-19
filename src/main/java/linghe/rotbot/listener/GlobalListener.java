package linghe.rotbot.listener;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.*;

public class GlobalListener implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener {
    GlobalKeyListener keyListener = new GlobalKeyListener();
    GlobalMouseListener mouseListener = new GlobalMouseListener();
    GlobalMouseWheelListener mouseWheelListener = new GlobalMouseWheelListener();
    public void nativeMouseClicked(NativeMouseEvent e) {
        mouseListener.nativeMouseClicked(e);
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        mouseListener.nativeMousePressed(e);
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        mouseListener.nativeMouseReleased(e);
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        mouseListener.nativeMouseMoved(e);
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        mouseListener.nativeMouseDragged(e);
    }

    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        mouseWheelListener.nativeMouseWheelMoved(e);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        keyListener.nativeKeyPressed(e);
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        keyListener.nativeKeyReleased(e);
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        keyListener.nativeKeyTyped(e);
    }

}
