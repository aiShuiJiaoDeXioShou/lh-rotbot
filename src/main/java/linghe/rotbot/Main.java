package linghe.rotbot;

import linghe.rotbot.service.RobotService;

import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
//        init();
        RobotService robotService = new RobotService();
        robotService.startWindowRecord();
    }

    public static void init() {
        // 初始化OpenCv
        URL url = ClassLoader.getSystemResource("lib/opencv_java480.dll");
        System.load(url.getPath());
    }
}
