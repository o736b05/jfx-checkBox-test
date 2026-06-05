package org.example.app;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controls.MyCheckBox;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Создание корневого контейнера - BorderPane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // ПАНЕЛЬ ИНСТРУМЕНТОВ
        VBox TestPanel = new VBox(10);
        TestPanel.setPadding(new Insets(10));
        TestPanel.setStyle("-fx-background-color: #f0f0f0;");
        TestPanel.setPrefWidth(200);

        MyCheckBox checkBox1 = new MyCheckBox("Опция 1");
        MyCheckBox checkBox2 = new MyCheckBox("Опция 2");

        TestPanel.getChildren().addAll(checkBox1, new Separator(), checkBox2);

        root.setCenter(TestPanel);

        // СОЗДАНИЕ И НАСТРОЙКА СЦЕНЫ
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("JavaFX Демонстрация - Основные инструменты");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}