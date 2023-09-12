package linghe.rotbot;

import linghe.rotbot.comm.Config;
import linghe.rotbot.service.RobotService;
import linghe.rotbot.view.MainFrame;

import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        new MainFrame(Config.src("view/html/MainView.html"), false, false, args);
    }

    public static void init() {
        // 初始化OpenCv
        URL url = ClassLoader.getSystemResource("lib/opencv_java480.dll");
        System.load(url.getPath());
        RobotService robotService = new RobotService();
        robotService.startWindowRecord();
    }
}
