package linghe.robot.comm;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class FxLoading {

    // 加载fxml
    public static <T> T loadFXML(String fxml) {
        FXMLLoader loader = new FXMLLoader(Config.url(fxml));
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
