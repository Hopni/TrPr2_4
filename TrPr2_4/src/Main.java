import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {
    static final String NATURAL_NUMBER = "[+]?[1-9]\\d*";
    static final String INTEGER = "[+|-]?([1-9]\\d*|[+|-]?0)$";
    static final String FLOAT = "^[+-]?(\\.?\\d+(\\.\\d*)?)([e][+-]?\\d+)?$";
    static final String DATE = "(((0[1-9]|[12]\\d|3[01])\\.(0[13578]|1[02])\\.(\\d){4})|((0[1-9]|[12]\\d|30)\\.(0[469]|11)\\.(\\d){4})|((0[1-9]|1\\d|2[0-8])\\.02\\.(\\d){4})|(29\\.02\\.(\\d){2}([02468][048]|[13579][26])))[ \n]?";
    static final String TIME = "^([0-1][0-9]|2[0-3])(:[0-5][0-9]){2}$";
    static final String EMAIL = "[A-Za-z0-9-_~!$%&'()*+,.;=:]+@([A-z0-9]+\\.)+[A-z]{2,4}$";

    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override public void start(Stage primaryStage) {
        primaryStage.setTitle("Application");
        ComboBox<String> comboBox = new ComboBox<>();
        TextArea textArea = new TextArea("");
        textArea.setWrapText(true);
        Canvas label = new Canvas(40, 40);
        GraphicsContext graphicsContext = label.getGraphicsContext2D();
        ListView<String> listView = new ListView<>();
        listView.setMaxHeight(50);
        comboBox.getItems().add("Natural number");
        comboBox.getItems().add("Integer");
        comboBox.getItems().add("Float");
        comboBox.getItems().add("Date");
        comboBox.getItems().add("Time");
        comboBox.getItems().add("E-mail");
        comboBox.setPromptText("Mode");
        final Pattern[] pattern = new Pattern[1];
        pattern[0] = Pattern.compile("");
        comboBox.setOnAction((ActionEvent event) -> {
            switch (comboBox.getSelectionModel().getSelectedIndex()) {
                case 0:
                    pattern[0] = Pattern.compile(NATURAL_NUMBER);
                    break;
                case 1:
                    pattern[0] = Pattern.compile(INTEGER);
                    break;
                case 2:
                    pattern[0] = Pattern.compile(FLOAT);
                    break;
                case 3:
                    pattern[0] = Pattern.compile(DATE);
                    break;
                case 4:
                    pattern[0] = Pattern.compile(TIME);
                    break;
                case 5:
                    pattern[0] = Pattern.compile(EMAIL);
                    break;
                default:
                    pattern[0] = Pattern.compile("");
            }
            String text = textArea.getText();
            Matcher matcher = pattern[0].matcher(text);
            if (matcher.matches() || text.isEmpty() || pattern[0].pattern().isEmpty()) {
                graphicsContext.clearRect(0, 0,40,40);
                graphicsContext.setFill(Color.GREEN);
            }
            else {
                graphicsContext.clearRect(0, 0,40,40);
                graphicsContext.setFill(Color.RED);
            }
            graphicsContext.fillOval(0, 0, 40, 40);
            listView.getItems().clear();
            Matcher m = Pattern.compile(DATE).matcher(text);
            while (m.find()) {
                listView.getItems().add(m.group());
            }
        });
        textArea.setOnKeyTyped(event -> {
            String text = textArea.getText();
            Matcher matcher = pattern[0].matcher(text);
            if (matcher.matches() || text.isEmpty() || pattern[0].pattern().isEmpty()) {
                graphicsContext.clearRect(0, 0,40,40);
                graphicsContext.setFill(Color.GREEN);
            }
            else {
                graphicsContext.clearRect(0, 0,40,40);
                graphicsContext.setFill(Color.RED);
            }
            graphicsContext.fillOval(0, 0, 40, 40);
            listView.getItems().clear();
            Matcher m = Pattern.compile(DATE).matcher(text);
            while (m.find()) {
                listView.getItems().add(m.group());
            }
        });


        BorderPane borderPane = new BorderPane();
        BorderPane top = new BorderPane();
        top.setLeft(comboBox);
        top.setRight(label);
        borderPane.setTop(top);
        borderPane.setCenter(textArea);
        borderPane.setBottom(listView);
        Scene scene = new Scene(borderPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}