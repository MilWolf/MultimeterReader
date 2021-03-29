package com.github.milwolf.mmreader.ui;

import com.fazecast.jSerialComm.SerialPort;
import com.github.milwolf.mmreader.core.fs9721lp3.FS9721LP3Iterable;
import com.github.milwolf.mmreader.core.fs9721lp3.IteratorWithException;
import com.github.milwolf.mmreader.core.fs9721lp3.Record;
import com.github.milwolf.mmreader.ui.filechooser.SmartFileChooser;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * @author MilWolf
 */
public class MainController implements Initializable {

    private final SmartFileChooser smartFileChooser = new SmartFileChooser();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    @FXML
    private ComboBox<String> portsComboBox;
    @FXML
    private Button refreshPortsButton;
    @FXML
    private Button startRecordButton;
    @FXML
    private Button stopRecordButton;
    @FXML
    private TextField outputFileTextField;
    @FXML
    private VBox settingsContainer;
    @FXML
    private Label valueLabel;
    private File outputFile;
    private volatile SerialPort commPort;
    private final Service<Void> service = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    commPort.setBaudRate(2400);
                    commPort.openPort();
                    commPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

                    InputStream inputStream = commPort.getInputStream();

                    IteratorWithException<Record> iterator = new FS9721LP3Iterable(inputStream).iterator();

                    Calendar calendar = Calendar.getInstance();

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter((outputFile)))) {

                        writer.write("Date/time (dd/MM/yyyy HH:mm:ss);Value;Metric;Unit;\n");

                        do {
                            Record record = iterator.next();

                            if (record != null) {
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                String dateAndHour = simpleDateFormat.format(calendar.getTime());
                                writer.write(dateAndHour + ";" + record.getValue() + ";" + record.getMetric() + ";" + record.getUnit() + "\n");
                                updateMessage(record.getValue() + " " + (record.getMetric() == null ? "" : record.getMetric().getAbbreviation()) + record.getUnit().getAbbreviation());
                            }

                        } while (!this.isCancelled());

                    } finally {
                        commPort.closePort();
                    }

                    return null;
                }
            };
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refreshPorts();

        smartFileChooser.getFileChooser().getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
        smartFileChooser.getFileChooser().setInitialFileName("*.csv");

        startRecordButton.disableProperty().bind(
                portsComboBox.getSelectionModel().selectedItemProperty().isNull()
                        .or(outputFileTextField.textProperty().isEmpty()));

        service.messageProperty().addListener((observable, oldValue, newValue) -> valueLabel.setText(newValue));
    }

    private void refreshPorts() {

        refreshPortsButton.setDisable(true);

        portsComboBox.getItems().clear();

        SerialPort[] commPorts = SerialPort.getCommPorts();

        for (SerialPort commPort : commPorts) {

            portsComboBox.getItems().add(commPort.getSystemPortName());
        }

        refreshPortsButton.setDisable(false);
    }

    @FXML
    private void onActionButtonRefreshPorts() {

        refreshPorts();
    }

    @FXML
    private void onActionButtonChooseOutputFile() {

        File selectedFile = smartFileChooser.showSaveDialog(outputFileTextField.getScene().getWindow());

        if (selectedFile != null) {
            outputFile = selectedFile;
            outputFileTextField.setText(outputFile.getAbsolutePath());
        }
    }

    @FXML
    private void onActionButtonStopRecord() {

        service.cancel();
        startRecordButton.setVisible(true);
        stopRecordButton.setVisible(false);
        settingsContainer.setDisable(false);
        valueLabel.setText("---");
    }

    @FXML
    private void onActionButtonStartRecord() {

        startRecordButton.setVisible(false);
        stopRecordButton.setVisible(true);
        settingsContainer.setDisable(true);

        commPort = SerialPort.getCommPort(portsComboBox.getSelectionModel().getSelectedItem());

        service.restart();
    }
}
