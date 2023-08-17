package linghe.rotbot.robot;

import org.junit.Test;

import java.awt.*;

import static linghe.rotbot.robot.LhRobot.*;

public class LhRobotTest {

    @Test
    public void 图片提取文本() {
        String imageText = getImageText("D:\\1.java\\library\\lh-rotbot\\src\\test\\resources\\img_2.png");
        System.out.println(imageText);
    }

    @Test
    public void 区域提取文字() {
        String imageText = getImageText(new Rectangle(400, 400));
        System.out.println(imageText);
    }

    @Test
    public void 拖拽() {
        dragY(100);
    }
}
