package linghe.rotbot.service;

import org.junit.Test;

public class RobotServiceTest {

    @Test
    public void 录制操作() {
        RobotService robotService = new RobotService();
        robotService.listenWindow();
    }

    @Test
    public void 基于Frame实现的屏幕录制() {
        RobotService robotService = new RobotService();
        robotService.startWindowRecord();
    }
}
