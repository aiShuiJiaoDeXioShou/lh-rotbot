package linghe.rotbot.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import linghe.rotbot.comm.FxLoading;

public class MainFrame extends Application {
    public static final Scene scene = new Scene(FxLoading.loadFXML("fxml/MainView.fxml"));
    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        MainFrame.stage = stage;
        stage.setTitle("Rotbot");
        stage.setScene(scene);
        stage.show();
    }
}
