package top.zyaire.ui;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class UIMain extends Application {
    public static void main(String[] args) {
    launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Gcode生成器");
        // 窗口的图标
       // stage.getIcons().add(new Image("icon.png"));
        // 窗口的宽度
        //stage.setWidth(800);
        // 窗口的最小宽度
        stage.setMinWidth(400);
        // 窗口的高度
       // stage.setHeight(600);
        // 窗口的最小高度
        stage.setMinHeight(300);
        // 设置宽高尺寸可调整，true:可以拖拽边缘调整窗口尺寸，false：不可调整
        stage.setResizable(true);
        // 窗口最大化
       // stage.setMaximized(true);
        // 定位横纵坐标,避免太靠边上遮盖菜单栏，这两行如果不屑
        // 一般电脑默认是居中屏幕显示，但在有些电脑会跑偏
        stage.setX(5);
        stage.setY(10);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/main.fxml")));
        Scene scene = new Scene(root, 900, 500);
        scene.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/file/icon.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        // 3、打开窗口
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });



    }

    public void logout(Stage stage){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("退出程序");
        alert.setHeaderText("你正在退出程序");
        alert.setContentText("确定所有内容都保存了吗？");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }
}
