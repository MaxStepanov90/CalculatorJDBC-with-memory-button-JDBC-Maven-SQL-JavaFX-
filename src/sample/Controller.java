package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.SQLException;


public class Controller {


    @FXML
    private Text output;
    private long sum1 = 0;
    private long sum2 = 0;

    private boolean start = true;

    private String operator = "";
    private String memoryoperator = "";
    Jdbc jdbc = new Jdbc();
    private Model model = new Model();

    @FXML
    private void processnum(ActionEvent event) {
        if (start) {
            output.setText("");
            start = false;
        }
        String value = ((Button) event.getSource()).getText();
        output.setText(output.getText() + value);
    }

    @FXML
    private void processoperator(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        if ("C".equals(value)) {
            output.setText(null);
            return;
        }
        if (!"=".equals(value)) {
            if (!operator.isEmpty()) return;

            operator = value;
            sum1 = Long.parseLong(output.getText());
            output.setText("");

        } else {
            if (operator.isEmpty()) return;
            output.setText(String.valueOf(model.calculation(sum1, Long.parseLong(output.getText()), operator)));
            operator = "";
            start = true;
        }
    }

    @FXML
    private void processmemory(ActionEvent event) {

        Long sum;
        Long result;
        String value = ((Button) event.getSource()).getText();

        memoryoperator = value;
        sum1 = Long.parseLong(output.getText());

        switch (memoryoperator) {
            case "MC":
                jdbc.delete();
                memoryoperator = "";
                start = true;
            case "MR":
                output.setText("");
                result = jdbc.select();
                if (result.equals(null))
                    output.setText("");
                else {
                    output.setText(String.valueOf(jdbc.select()));
                }
                memoryoperator = "";
                start = true;
            case "M+":
                result = jdbc.select();
                if (result.equals(null)) return;
                else {
                    sum = result + sum1;
                    jdbc.update(sum);
                }

                memoryoperator = "";
                start = true;
            case "M-":
                result = jdbc.select();
                if (result.equals(null)) return;
                else {
                    sum = result - sum1;
                    jdbc.update(sum);
                }
                memoryoperator = "";
                start = true;
        }

    }
}