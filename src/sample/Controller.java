package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.SQLException;


public class Controller {


    @FXML
    private Text output;
    private long screennum = 0;

    private boolean start = true;

    private String operator = "";
    private String memoryoperator = "";
    private Jdbc jdbc = new Jdbc();
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
            screennum = Long.parseLong(output.getText());
            output.setText("");

        } else {
            if (operator.isEmpty()) return;
            output.setText(String.valueOf(model.calculation(screennum, Long.parseLong(output.getText()), operator)));
            operator = "";
            start = true;
        }
    }

    @FXML
    private void processmemory(ActionEvent event) {

        Long screennum;
        Long resultsql;
        Long sum;
        String value = ((Button) event.getSource()).getText();

        memoryoperator = value;
        screennum = Long.parseLong(output.getText());

        switch (memoryoperator) {
            case "MC":
                jdbc.delete();
                memoryoperator = "";
                start = true;
                break;
            case "MR":
                output.setText("");
                resultsql = jdbc.select();
                if (resultsql.equals(null))
                    output.setText("");
                else {
                    output.setText(String.valueOf(jdbc.select()));
                }
                memoryoperator = "";
                start = true;
                break;
            case "M+":
                resultsql = jdbc.select();
                if (resultsql.equals(null)) return;
                else {
                    sum = resultsql + screennum;
                    jdbc.update(sum);
                }
                memoryoperator = "";
                start = true;
                break;
            case "M-":
                resultsql = jdbc.select();
                if (resultsql.equals(null)) return;
                else {
                    sum = resultsql - screennum;
                    jdbc.update(sum);
                }
                memoryoperator = "";
                start = true;
                break;
        }
    }
}