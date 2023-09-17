package linghe.rotbot.robot;

import org.junit.Test;

import java.awt.*;
import java.net.URL;

import static linghe.robot.core.LhRobot.*;


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

    @Test
    public void 验证颜色() {
        URL url = ClassLoader.getSystemResource("lib/opencv_java480.dll");
        System.load(url.getPath());
        // 获取当前鼠标的坐标
        Point mousePosition = getMousePosition();

        Color color1 = new Color(95, 184, 101);
        Color color = getRobotColor(mousePosition.x, mousePosition.y);
        System.out.println(color);
        System.out.println(color.equals(color1));
    }

}
