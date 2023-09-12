package linghe.rotbot.comm;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class Config {
    public static GroovyObject INSTANCE;
    static {
        // 读取配置文件中的值
        String config = src("config.groovy");
        if (Objects.isNull(INSTANCE)) {
            try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
                Class<?> groovyClass = classLoader.parseClass(new File(config));
                INSTANCE = (GroovyObject) groovyClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String src(String name) {
        // 截取前面的file:/
        return Objects.requireNonNull(Config.class.getClassLoader().getResource(name)).toExternalForm().substring("file:/".length());
    }

    public static URL url(String name) {
        return Objects.requireNonNull(Config.class.getClassLoader().getResource(name));
    }


    public static String dst(String name) {
        return "file:" + src(name);
    }
}
