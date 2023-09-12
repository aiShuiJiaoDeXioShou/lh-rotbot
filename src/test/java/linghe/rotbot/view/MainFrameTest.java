package linghe.rotbot.view;

import linghe.rotbot.comm.Config;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.OS;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import me.friwi.jcefmaven.*;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainFrameTest {
    public static class MainFrame extends JFrame {
        private static final long serialVersionUID = -5570653778104813836L;
        private final JTextField address_;
        private final CefApp cefApp_;
        private final CefClient client_;
        private final CefBrowser browser_;
        private final Component browerUI_;
        private boolean browserFocus_ = true;

        private MainFrame(String startURL, boolean useOSR, boolean isTransparent, String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
            CefAppBuilder builder = new CefAppBuilder();
            builder.getCefSettings().windowless_rendering_enabled = useOSR;
            builder.setAppHandler(new MavenCefAppHandlerAdapter() {
                @Override
                public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                    if (state == CefAppState.TERMINATED) System.exit(0);
                }
            });

            if (args.length > 0) {
                builder.addJcefArgs(args);
            }

            cefApp_ = builder.build();

            client_ = cefApp_.createClient();
            CefMessageRouter msgRouter = CefMessageRouter.create();
            client_.addMessageRouter(msgRouter);
            browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
            browerUI_ = browser_.getUIComponent();
            address_ = new JTextField(startURL, 100);
            address_.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    browser_.loadURL(address_.getText());
                }
            });
            client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
                @Override
                public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
                    address_.setText(url);
                }
            });
            address_.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (!browserFocus_) return;
                    browserFocus_ = false;
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                    address_.requestFocus();
                }
            });
            client_.addFocusHandler(new CefFocusHandlerAdapter() {
                @Override
                public void onGotFocus(CefBrowser browser) {
                    if (browserFocus_) return;
                    browserFocus_ = true;
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                    browser.setFocus(true);
                }

                @Override
                public void onTakeFocus(CefBrowser browser, boolean next) {
                    browserFocus_ = false;
                }
            });
            getContentPane().add(address_, BorderLayout.NORTH);
            getContentPane().add(browerUI_, BorderLayout.CENTER);
            pack();
            setSize(800, 600);
            setVisible(true);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    CefApp.getInstance().dispose();
                    dispose();
                }
            });
        }
    }


    public static void main(String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        boolean useOsr = false;
        new MainFrame(Config.src("view/html/MainView.html"), useOsr, false, args);
    }

}
