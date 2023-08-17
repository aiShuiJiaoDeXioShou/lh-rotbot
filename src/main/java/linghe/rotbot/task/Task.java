package linghe.rotbot.task;

public interface Task {
    void run();

    void stop();

    /**
     * 间隔时间
     */
    long getInterval();

    /**
     * 任务名称
     */
    String getName();

    /**
     * 执行多少次,若为-1则一直执行
     */
    int getNumbers();

}
