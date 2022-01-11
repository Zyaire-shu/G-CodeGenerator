package top.zyaire.ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import top.zyaire.image.Test;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Pane rootPane;
    @FXML
    private MenuBar menubar;
    @FXML
    private AnchorPane positionPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menubar.prefWidthProperty().bind(pane.widthProperty());
        positionPane.prefWidthProperty().bind(pane.widthProperty());
        positionPane.prefHeightProperty().bind(pane.heightProperty());
    }
    public void generateGcode() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/gcode.fxml")));
        Stage stage = new Stage();
        stage.setTitle("G-Code生成");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/file/gcode.png"))));
        Scene scene = new Scene(root,900,400);
        stage.setScene(scene);
        stage.show();
    }
    public void aboutMe(){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("关于本软件");
        ButtonType type = new ButtonType("知道了", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText("本软件为金陵科技学院22级毕业,生舒泽午毕业设计作品！");
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
}
