import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeDatabaseApp extends Application {

    private static final String URL = "jdbc:oracle:thin//localhost:1521/orcl";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";

//    private static final String URL = "jdbc:postgresql//localhost:5432/orcl";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "root";

    private TextField textField;
    private List<Label> labelList;

    private void getEmployee() {
        String idString = textField.getText();
        if (idString.equals("")) {
            alert("Пустое поле");
            return;
        }
        try {
            int id = Integer.parseInt(idString);
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM emp WHERE empno = " + id
            );
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int i;
                for (i = 1; i < resultSet.getMetaData().getColumnCount() + 1; ++i) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    this.labelList.get(i - 1).setText(columnName + ": " + resultSet.getString(columnName));
                }
                statement = connection.prepareStatement(
                        "SELECT dname, loc FROM  dept WHERE deptno = " + resultSet.getString("deptno")
                );
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    for (int j = 1; j < resultSet.getMetaData().getColumnCount() + 1; ++j) {
                        String columnName = resultSet.getMetaData().getColumnName(j);
                        this.labelList.get(i + j - 2).setText(columnName + ": " + resultSet.getString(columnName));
                    }
                }
            } else alert("Пользователь не найден");
        } catch (NumberFormatException | SQLException e) {
            alert(e.getMessage());
        }
    }

    private static void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    @Override
    public void start(Stage stage) {
        labelList = new ArrayList<>();
        for (int i = 0; i < 10; ++i)
            labelList.add(new Label());
        textField = new TextField();
        textField.setPromptText("Введите идентификатор");
        Button showButton = new Button("Показать");
        showButton.setOnAction(event -> getEmployee());
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(5);
        for (Label label: labelList)
            vBox.getChildren().add(label);
        vBox.getChildren().addAll(textField, showButton);
        Scene mainScene = new Scene(vBox);
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

