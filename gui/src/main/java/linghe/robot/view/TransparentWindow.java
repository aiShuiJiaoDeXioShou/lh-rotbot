package linghe.robot.view;

import linghe.robot.comm.ViewConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TransparentWindow extends JFrame {

    // 定义窗口的宽度和高度
    private static final int WIDTH = ViewConfig.WIDTH;
    private static final int HEIGHT = ViewConfig.HEIGHT;

    // 定义窗口的背景颜色
    private static final Color BG_COLOR = new Color(0, 0, 0, 0.5f);

    // 定义窗口的边框颜色和宽度
    private static final Color BORDER_COLOR = new Color(0, 0, 0, 0f);
    private static final int BORDER_WIDTH = 1;

    // 定义窗口的标题栏高度和颜色
    private static final int TITLE_HEIGHT = 30;
    private static final Color TITLE_COLOR = new Color(0, 0, 0, 0f);

    // 定义窗口的标题栏上的按钮大小和颜色
    private static final int BUTTON_SIZE = 30;
    private static final int BUTTON_WIDTH  = BUTTON_SIZE + 20;
    private static final Color BUTTON_COLOR = new Color(0, 0, 0, 0f);

    // 定义窗口的标题栏上的图标大小和位置
    private static final int ICON_SIZE = 20;
    private static final int ICON_X = 10;
    private static final int ICON_Y = 5;
    // 定义窗口的阴影颜色和偏移量
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 0.2f);
    private static final int SHADOW_OFFSET = 10;

    // 定义窗口的圆角半径
    private static final int ARC_RADIUS = 50;

    // 定义窗口的内容面板
    public JPanel contentPane;

    // 定义窗口的标题栏面板
    private JPanel titlePane;

    // 定义窗口的标题栏上的按钮
    private JButton minButton, maxButton, closeButton;
    // 定义窗口的标题栏上的图标
    private JLabel iconLabel;

    // 定义窗口的拖动坐标
    private int x;
    private int y;

    public TransparentWindow() {
        // 设置窗口大小
        setSize(WIDTH, HEIGHT);
        // 设置窗口居中显示
        setLocationRelativeTo(null);
        // 设置窗口无边框
        setUndecorated(true);

        // 创建内容面板，使用边界布局
        contentPane = new JPanel(new BorderLayout());
        // 设置内容面板为窗口的内容面板
        setContentPane(contentPane);

        // 创建标题栏面板，使用流式布局，右对齐
        titlePane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // 设置标题栏面板的大小和背景颜色为透明色
        titlePane.setPreferredSize(new Dimension(WIDTH, TITLE_HEIGHT));
        titlePane.setBackground(TITLE_COLOR);

        // 创建最小化按钮，设置大小、颜色、文本和提示信息
        minButton = new JButton();
        minButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_SIZE));
        minButton.setBackground(BUTTON_COLOR);
        minButton.setText("-");
        minButton.setToolTipText("最小化");

        // 创建最大化按钮，设置大小、颜色、文本和提示信息
        maxButton = new JButton();
        maxButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_SIZE));
        maxButton.setBackground(BUTTON_COLOR);
        maxButton.setText("□");
        maxButton.setToolTipText("最大化");

        // 创建关闭按钮，设置大小、颜色、文本和提示信息
        closeButton = new JButton();
        closeButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_SIZE));
        closeButton.setBackground(BUTTON_COLOR);
        closeButton.setText("×");
        closeButton.setToolTipText("关闭");

        // 将三个按钮添加到标题栏面板中
        titlePane.add(minButton);
        titlePane.add(maxButton);
        titlePane.add(closeButton);

        // 将标题栏面板添加到内容面板的北部区域
        contentPane.add(titlePane, BorderLayout.NORTH);

        // 添加鼠标事件监听器，实现窗口的拖动功能
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen();
                int yOnScreen = e.getYOnScreen();
                setLocation(xOnScreen - x, yOnScreen - y);
            }
        };

        // 为标题栏面板和内容面板添加鼠标事件监听器
        titlePane.addMouseListener(mouseAdapter);
        titlePane.addMouseMotionListener(mouseAdapter);
        contentPane.addMouseListener(mouseAdapter);
        contentPane.addMouseMotionListener(mouseAdapter);

        // 添加窗口事件监听器，实现窗口的最小化、最大化和关闭功能
        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                setExtendedState(JFrame.NORMAL);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        };

        // 为窗口添加窗口事件监听器
        addWindowListener(windowAdapter);

        // 为三个按钮添加动作事件监听器，触发相应的窗口事件
        minButton.addActionListener(e -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_ICONIFIED));
        });
        maxButton.addActionListener(e -> {
            if (getExtendedState() == JFrame.NORMAL) {
                // 设置窗口最大化
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else {
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_DEICONIFIED));
            }
        });
        closeButton.addActionListener(e -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        // 创建一个图标对象，加载图像文件
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
        // 创建一个图标标签，设置图标对象
        iconLabel = new JLabel(icon);
        // 设置图标标签的大小和位置
        iconLabel.setBounds(ICON_X, ICON_Y, ICON_SIZE, ICON_SIZE);
        // 将图标标签添加到标题栏面板中
        // 将图标放到标题栏的最左边
        titlePane.add(iconLabel, BorderLayout.WEST);
    }

    // 重写窗口的绘制方法，绘制窗口的边框
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        // 设置边框颜色为半透明色
        g2d.setColor(BORDER_COLOR);
        // 设置边框宽度
        g2d.setStroke(new BasicStroke(BORDER_WIDTH));
        // 绘制圆角矩形作为边框
        g2d.drawRoundRect(0, 0, WIDTH, HEIGHT, ARC_RADIUS, ARC_RADIUS);
        // 设置阴影颜色
        g2d.setColor(SHADOW_COLOR);
        // 绘制阴影矩形
        g2d.fillRect(WIDTH, SHADOW_OFFSET, SHADOW_OFFSET, HEIGHT);
        g2d.fillRect(SHADOW_OFFSET, HEIGHT, WIDTH, SHADOW_OFFSET);
    }

    public static void main(String[] args) {
        // 创建一个半透明的自定义窗口对象
        TransparentWindow window = new TransparentWindow();
        // 设置窗口可见
        window.setVisible(true);
    }
}
