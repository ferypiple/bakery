package com.example.bakery;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController {

    @FXML
    private Label producionVal;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button apply;

    @FXML
    private Label snailLabel;

    @FXML
    private Label valLabel;
    @FXML
    private Label puffsLabel;
    @FXML
    private Label ciabattaLabel;

    @FXML
    private Label croissantLabel;

    @FXML
    private Label greekSnailLabel;

    @FXML
    private LineChart<String, Number> salesChart;
    @FXML
    private Label sumLabel;
    @FXML
    private Button breakButton;
    @FXML
    private PieChart pieChart;
    @FXML
    private Button repairButton;
    @FXML
    private Button breakButton1;
    @FXML
    private Button repairButton1;

    private XYChart.Series<String, Number> series;
    double sum = 0;
    Double puffs = 0.0;
    Double greekSnail = 0.0;
    Double croissant = 0.0;
    Double snail = 0.0;
    Double ciabatta = 0.0;
    double val = 0.0;
    boolean isWork = true;
    List<Thread> threads = new ArrayList<>();
    List<Thread> threadWithOperation = new ArrayList<>();
    int index1 = 0;
    PieChart.Data slice1 = new PieChart.Data("Слойки с повидлом", 70);
    PieChart.Data slice2 = new PieChart.Data("Улитка греческая", 20);
    PieChart.Data slice3 = new PieChart.Data("Круассан", 0);
    PieChart.Data slice4 = new PieChart.Data("Улитка с изюмом и заварным кремом", 0);
    PieChart.Data slice5 = new PieChart.Data("Чиабатта", 0);
    updateChartData updateAll = new updateChartData();
    notWork notWork = new notWork();
    boolean type = false;

    @FXML
    void initialize() {
        series = new XYChart.Series<>();

        pieChart.getData().addAll(slice1, slice2, slice3, slice4, slice5);
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        series.getData().add(new LineChart.Data<>(formattedTime, 1000));
        salesChart.getData().add(series);
        salesChart.setLegendVisible(false);
        apply.setOnAction(actionEvent -> {
            threads.add(new Thread(updateAll));
            threads.get(index1).start();
            threadWithOperation.add(new Thread(notWork));
            threadWithOperation.get(index1).start();
        });
        breakButton.setOnAction(actionEvent -> {
            type = false;
            noMoreMoney();
        });
        repairButton.setOnAction(actionEvent -> {
            repair();
        });
        repairButton1.setOnAction(actionEvent -> {
            repair();
        });
        breakButton1.setOnAction(actionEvent -> {
            type = true;
            noMoreMoney();
        });
    }

    void noMoreMoney() {
        threads.get(index1).interrupt();
        threadWithOperation.get(index1).interrupt();
        index1++;
        if (type = false) {
            JOptionPane.showMessageDialog(null, "Банк перестал принимать платежи", "Денег нет!",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Отказала одна из систем", "Поломка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void repair() {
        threads.add(new Thread(updateAll));
        threadWithOperation.add(new Thread(notWork));
        threads.get(index1).start();
        threadWithOperation.get(index1).start();
        if(type=false) {
            JOptionPane.showMessageDialog(null, "Связались с банком, все исправили", "Деньги есть",
                    JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null, "Мастер все починил", "Работаем",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class updateChartData implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                Random random = new Random();
                Double puffsRandom = random.nextDouble(1000);
                puffs = puffs + puffsRandom;
                String formattedNumber1 = String.format("%.2f", puffs);
                Double greekSnailRandom = random.nextDouble(1000);
                greekSnail = greekSnail + greekSnailRandom;
                String formattedNumber2 = String.format("%.2f", greekSnail);
                Double croissantRandom = random.nextDouble(1000);
                croissant = croissant + croissantRandom;
                String formattedNumber3 = String.format("%.2f", croissant);
                Double snailRandom = random.nextDouble(1000);
                snail = snail + snailRandom;
                String formattedNumber4 = String.format("%.2f", snail);
                Double ciabattaRandom = random.nextDouble(1000);
                ciabatta = ciabatta + ciabattaRandom;
                String formattedNumber5 = String.format("%.2f", ciabatta);
                double randomValue = puffsRandom + greekSnailRandom + croissantRandom + snailRandom + ciabattaRandom;
                sum += puffs + greekSnail + croissant + snail + ciabatta;
                String formattedNumber7 = String.format("%.2f", sum);
                val = sum * 1.4;
                int production = (int) (val / random.nextDouble(30.5));
                String formattedNumber6 = String.format("%.2f", val);
                javafx.application.Platform.runLater(() -> {
                    producionVal.setText(production + " штук");
                    puffsLabel.setText(formattedNumber1 + " рублей");
                    greekSnailLabel.setText(formattedNumber2 + " рублей");
                    snailLabel.setText(formattedNumber4 + " рублей");
                    ciabattaLabel.setText(formattedNumber5 + " рублей");
                    croissantLabel.setText(formattedNumber3 + " рублей");
                    valLabel.setText(formattedNumber6 + " рублей");
                    sumLabel.setText(formattedNumber7 + " рублей");
                });
                LocalDateTime currentTime = LocalDateTime.now();
                String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                series.getData().add(new LineChart.Data<>(formattedTime, randomValue));
                slice1.setPieValue(puffs);
                slice2.setPieValue(greekSnail);
                slice3.setPieValue(croissant);
                slice4.setPieValue(snail);
                slice5.setPieValue(ciabatta);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private class notWork implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(10000);
                    if (isWork) {
                        isWork = false;
                        JOptionPane.showMessageDialog(null, "Обновление продукции автомата прошло успешно");
                    } else {
                        isWork = true;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}