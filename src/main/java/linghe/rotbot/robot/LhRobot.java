package linghe.rotbot.robot;

import linghe.rotbot.comm.Config;
import linghe.rotbot.comm.MD5Utils;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class LhRobot {
    public static final Robot bot;
    // 识别文本
    public static ITesseract tesseract;
    public static int delay = 100;
    static  {
        try {
            bot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将鼠标移动到指定位置
     * @param x x坐标的位置
     * @param y y坐标的位置
     */
    public static void move(int x,int y) {
        bot.mouseMove(x, y);
    }

    /**
     * 将鼠标移动到指定位置
     * @param point 鼠标移动的位置
     */
    public static void move(Point point) {
        bot.mouseMove(point.x, point.y);
    }

    /**
     * 将鼠标移动到指定位置,并按下,inputEvent设置是左键位还是右键位
     * @param x 鼠标按下的位置
     * @param y 鼠标按下的位置
     * @param delay 按下松开的间隔时间
     * @param inputEvent 设置鼠标按下的是左键位还是右键位
     */
    public static void click(int x, int y, int delay,int inputEvent) {
        bot.mouseMove(x,y);
        // 按下鼠标左键
        bot.mousePress(inputEvent);
        bot.delay(delay);
        // 释放鼠标左键
        bot.mouseRelease(inputEvent);
    }

    /**
     *
     * 将鼠标移动到指定位置,并按下,inputEvent设置是左键位还是右键位
     * @param x 鼠标按下的位置
     * @param y 鼠标按下的位置
     * @param delay 按下松开的间隔时间
     */
    public static void click(int x, int y,int delay) {
        click(x, y, delay, InputEvent.BUTTON1_MASK);
    }

    /**
     * 将鼠标移动到指定位置,并按下 默认是左键位
     * @param x 鼠标按下的位置
     * @param y 鼠标按下的位置
     */
    public static void click(int x, int y) {
        click(x,y, delay,InputEvent.BUTTON1_MASK);
    }

    /**
     * 按下鼠标右键
     * @param x 鼠标按下的位置
     * @param y 鼠标按下的位置
     */
    public static void rightClick(int x, int y) {
        click(x,y, delay,InputEvent.BUTTON3_MASK);
    }

    /**
     * 按下鼠标右键
     * @param point 鼠标按下的位置
     */
    public static void rightClick(Point point) {
        click(point.x,point.y, delay,InputEvent.BUTTON3_MASK);
    }

    /**
     * 按下鼠标左键
     * @param point 鼠标按下的位置
     */
    public static void click(Point point) {
        click(point.x,point.y, delay,InputEvent.BUTTON3_MASK);
    }

    /**
     * 获取现在鼠标的位置
     * @return 坐标位置
     */
    public static Point getMousePosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    /**
     * 按下指定键,并松开,delay是按键的间隔时间
     * @param keyCode the key code
     * @param delay the delay
     */
    public static void key(int keyCode,int delay) {
        bot.keyPress(keyCode);
        bot.delay(delay);
        bot.keyRelease(keyCode);
    }

    /**
     * 按下指定键,并松开,delay是按键的间隔时间
     * @param keyCode the key code
     */
    public static void key(int keyCode) {
        key(keyCode, delay);
    }

    /**
     * 封装快捷键指令,同时按下两个按键
     */
    public static void keyTwo(int keyCode,int keyCode2) {
        bot.keyPress(keyCode);
        bot.keyPress(keyCode2);
        bot.delay(delay);
        bot.keyRelease(keyCode);
        bot.keyRelease(keyCode2);
    }

    /**
     * 封装快捷键指令,同时按下三个按键
     */
    public static void keyThree(int keyCode,int keyCode2,int keyCode3) {
        bot.keyPress(keyCode);
        bot.keyPress(keyCode2);
        bot.keyPress(keyCode3);
        bot.delay(delay);
        bot.keyRelease(keyCode);
        bot.keyRelease(keyCode2);
        bot.keyRelease(keyCode3);
    }

    /**
     * 截取指定区域
     * @param rectangle 保存位置
     * @return Java图片流
     */
    public static BufferedImage capture(Rectangle rectangle) {
        //从x坐标为0，y坐标为0的位置开始截取（左上角）
        return bot.createScreenCapture(rectangle);
    }

    /**
     * 截取全屏并保存为java图片流
     * @return 返回图片流
     */
    public static BufferedImage capture() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        //屏幕宽度
        int screenWidth = (int) dimension.getWidth();
        //屏幕高度
        int screenHeight = (int) dimension.getHeight();
        //从x坐标为0，y坐标为0的位置开始截取（左上角）
        return capture(new Rectangle(0, 0, screenWidth, screenHeight));
    }

    /**
     * 截取全屏并保存到指定路径
     * @param path 保存位置
     */
    public static void capture(String path) {
        //从x坐标为0，y坐标为0的位置开始截取（左上角）
        BufferedImage screenCapture = capture();
        // 保存图片到指定位置
        try {
            ImageIO.write(screenCapture, "png", new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 截取全屏并保存到指定文件
     * @param file 保存的文件
     */
    public static void capture(File file) {
        //从x坐标为0，y坐标为0的位置开始截取（左上角）
        BufferedImage screenCapture = capture();
        // 保存图片到指定位置
        try {
            ImageIO.write(screenCapture, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 截取指定区域,并保存到指定位置
     * @param rectangle 截取区域
     * @param path 保存位置
     */
    public static void capture(Rectangle rectangle, String path) {
        //从x坐标为0，y坐标为0的位置开始截取（左上角）
        BufferedImage screenCapture = capture(rectangle);
        // 保存图片到指定位置
        try {
            ImageIO.write(screenCapture, "png", new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 截取指定区域,并保存到指定文件
     * @param rectangle 截取区域
     * @param file 保存位置
     */
    public static void capture(Rectangle rectangle, File file) {
        //从x坐标为0，y坐标为0的位置开始截取（左上角）
        BufferedImage screenCapture = capture(rectangle);
        // 保存图片到指定位置
        try {
            ImageIO.write(screenCapture, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sourceSrc 源文件
     * @param templateSrc 目标文件
     */
    public static Point findImagePoint(String sourceSrc, String templateSrc) {
        Mat source, template;
        //将文件读入为OpenCV的Mat格式
        source = imread(sourceSrc);
        template = imread(templateSrc);
        //创建于原图相同的大小，储存匹配度
        Mat result = Mat.zeros(source.rows() - template.rows() + 1, source.cols() - template.cols() + 1, CvType.CV_32FC1);
        //调用模板匹配方法
        Imgproc.matchTemplate(source, template, result, Imgproc.TM_SQDIFF_NORMED);
        //规格化
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
        //获得最可能点，MinMaxLocResult是其数据格式，包括了最大、最小点的位置x、y
        Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
        org.opencv.core.Point matchLoc = mlr.minLoc;
        return new Point((int) matchLoc.x, (int) matchLoc.y);
    }

    /**
     * 查找指定文件在屏幕中的位置
     * @param filePath 查找文件的路径
     * @return 该文件所在坐标
     */
    public static Point findImagePoint(String filePath) {
        // 使用MD5计算唯一值
        String md5 = MD5Utils.md5DigestAsHex(filePath);
        // 截取全屏
        File tempFile = createTempFile(Config.INSTANCE.getProperty("CachePath") +"/"+md5, ".png");
        capture(tempFile);
        if (tempFile.exists()) {
            return findImagePoint(tempFile.getAbsolutePath(), filePath);
        }
        return null;
    }

    /**
     * 查找指定文件在屏幕中的位置,这个根据图标的中心点计算
     * @param filePath 查找文件的路径
     * @return 该文件所在坐标
     */
    public static Point findImageCenterPoint(String filePath) {
        // 使用MD5计算唯一值
        String md5 = MD5Utils.md5DigestAsHex(filePath);
        // 截取全屏
        File tempFile = createTempFile(Config.INSTANCE.getProperty("CachePath") +"/"+md5, ".png");
        capture(tempFile);
        if (tempFile.exists()) {
            Size imageSize = getImageSize(filePath);
            Point point = findImagePoint(tempFile.getAbsolutePath(), filePath);
            return new Point((int) (point.x + imageSize.width / 2), (int) (point.y + imageSize.height / 2));
        }
        return null;
    }

    /**
     * @param filePath 输入文件路径
     * @return 文件的宽和高
     */
    public static Size getImageSize(String filePath) {
        // 获取filePath的高和宽
        int width = 0;
        int height = 0;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(filePath));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Size(width, height);
    }

    /**
     * @param file 输入文件
     * @return 文件的宽和高
     */
    public static Size getImageSize(File file) {
        // 获取filePath的高和宽
        int width = 0;
        int height = 0;
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Size(width, height);
    }


    /**
     * 创建临时文件
     * @param prefix 前缀名
     * @param suffix 文件后缀名
     * @return 该文件对象
     */
    public static File createTempFile(String prefix, String suffix) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile(prefix, suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempFile.deleteOnExit();
        return tempFile;
    }

    /**
     * 提取指定图片的文字
     * @param file 文件路径
     * @return 识别的文本
     */
    public static String getImageText(File file) {
        if (tesseract == null) {
            tesseract = new Tesseract();
        }
        try {
            return tesseract.doOCR(file);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 提取指定图片的文字
     * @param path 文件路径
     * @return 识别的文本
     */
    public static String getImageText(String path) {
        return getImageText(new File(path));
    }

    /**
     * 提取指定区域的文字
     * @param rectangle 区域范围
     * @return 识别的文本
     */
    public static String getImageText(Rectangle rectangle) {
        // 创建临时文件
        // 使用MD5计算唯一值
        String md5 = MD5Utils.md5DigestAsHex(rectangle.toString() + rectangle.width + rectangle.height);
        // 截取全屏
        File tempFile = createTempFile(Config.INSTANCE.getProperty("CachePath") +"/"+md5, ".png");
        // 根据区域获取文件
        capture(rectangle, tempFile);
        if (tempFile.exists()) {
            return getImageText(tempFile);
        }
        return "";
    }

    /**
     * 寻找到某张图片进行点击
     * @param file 文件路径
     */
    public static void clickForImage(File file) {
        Point point = findImageCenterPoint(file.getAbsolutePath());
        if (point != null) {
            click(point);
        }
    }

    /**
     * 寻找到某张图片进行点击
     * @param path 文件路径
     */
    public static void clickForImage(String path) {
        Point point = findImageCenterPoint(path);
        if (point != null) {
            click(point);
        }
    }

    /**
     * 移动到某张图片进行点击
     * @param file 文件路径
     */
    public static void moveForImage(File file) {
        Point point = findImageCenterPoint(file.getAbsolutePath());
        if (point != null) {
            click(point);
        }
    }

    /**
     * 移动到某张图片进行点击
     * @param path 文件路径
     */
    public static void moveForImage(String path) {
        Point point = findImageCenterPoint(path);
        if (point != null) {
            move(point);
        }
    }

    /**
     * 模拟电脑拖拽操作
     * @param point 移动到进行拖拽的地方
     * @param point2 拖拽目的地
     */
    public static void drag(Point point, Point point2) {
        move(point);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        move(point2);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * 模拟电脑拖拽操作
     * @param point 拖拽目的地
     */
    public static void drag(Point point) {
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        move(point);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * 向着y轴方向拖拽多少
     * @param y 移动距离
     */
    public static void dragY(int y) {
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Point position = getMousePosition();
        position.y += y;
        move(position);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * 向着x轴方向拖拽多少
     * @param x 移动距离
     */
    public static void dragX(int x) {
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Point position = getMousePosition();
        position.x += x;
        move(position);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

}
