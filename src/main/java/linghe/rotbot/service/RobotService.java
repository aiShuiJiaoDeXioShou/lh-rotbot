package linghe.rotbot.service;

import linghe.rotbot.robot.LhRobot;
import linghe.rotbot.task.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RobotService {
    public ExecutorService executorService = Executors.newFixedThreadPool(2);
    public boolean isRunning = false;

    public RobotService() {
        this.isRunning = true;
        Subscriber.on("System.err", (args)-> WindowService.windowError(args[0]));
    }

    // 开启监听屏幕
    public void listenWindow() {
        // 创建一个新的任务线程
        // 规定临时文件的字符串模板
        String moveTemp = "%s %s\n";
        // %s 填写left或者right
        String clickTemp = "click %s";
        // %s 填写用户按下的键位编号
        String keyTemp = "key %s";
        // 设置临时文件的位置参数
        String tempFile = "D:\\App\\Java_S\\lh-rotbot\\src\\main\\resources\\temp_window";
        System.out.println(tempFile);
        // 创建临时文件
        File tempWindow;
        try {
//            tempWindow = File.createTempFile(tempFile, "txt");
            tempWindow = new File(tempFile);
            tempWindow.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 将它保存到文件里面
        executorService.submit(() -> {
            while (true) {
                if (!isRunning) return;
                // 线程休眠100
                try {
                    Thread.sleep(100);
                    // 不断的获取鼠标当前的位置
                    Point position = LhRobot.getMousePosition();
                    // 将它追加到临时文件当中
                    Files.writeString(Paths.get(tempFile), String.format(moveTemp, position.x, position.y), StandardOpenOption.APPEND);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // 主线程等待executorService结束
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("录制完成！");
    }

    // 开启屏幕录制
    public void startWindowRecord() {
        // 添加全局监听事件判断是否按下了鼠标或者其他按键
        JFrame frame = new JFrame("KeyListener Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("Key typed: " + e.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed: " + e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Key released: " + e.getKeyCode());
            }
        });
        frame.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked: " + e.getButton());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed: " + e.getButton());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse released: " + e.getButton());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // 获取鼠标的位置
                System.out.printf("x: %s, y: %s%n", e.getX(), e.getY());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //  鼠标离开
                System.out.println("Mouse exited: " + e.getButton());
            }
        });
        // 不断获取鼠标移动的目标
        frame.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.printf("Mouse dragged: x: %s, y: %s%n", e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.printf("Mouse moved: x: %s, y: %s%n", e.getX(), e.getY());
            }
        });
        // 将frame最大化透明化
        frame.setUndecorated(true);
        frame.setOpacity(0.01f);
        // frame 初始化的时候最大化
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

    // 关闭监听屏幕
    public void listenWindowClose() {
        this.isRunning = false;
        executorService.shutdown();
    }

    // 执行任务
    public void runTask(Task[] run) {
        // TODO
    }

    // 关闭任务
    public void closeTask(Task[] run) {
        // TODO
    }
}
