package com.objectify.LineBarChartPlugin;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.models.transactions.Transaction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class LineBarChartPane extends AnchorPane {

    private final SystemPointOfSales spos;

    public LineBarChartPane(SystemPointOfSales spos) {
        super();
        this.spos = spos;
        setPadding(new Insets(10, 10, 10, 10));
        var tabPane = new TabPane();
        AnchorPane.setBottomAnchor(tabPane, 0.0);
        AnchorPane.setTopAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.FIXED);

        var lineChartContainer= new VBox();
        var lineChartTab = new Tab("Line Chart", lineChartContainer);
        var lineChart = getLineChart(); 
        lineChartContainer.setFillWidth(true);
        lineChartContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(lineChartContainer, Priority.ALWAYS);
        lineChartContainer.getChildren().add(Objects.requireNonNullElseGet(lineChart, () -> new Label("No data")));

        var barChartContainer = new VBox();
        var barChartTab = new Tab("Line Chart", barChartContainer);
        var barChart = getBarChart();
        barChartContainer.setFillWidth(true);
        barChartContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(barChartContainer, Priority.ALWAYS);
        barChartContainer.getChildren().add(Objects.requireNonNullElseGet(barChart, () -> new Label("No data")));
        tabPane.getTabs().addAll(lineChartTab, barChartTab);
        getChildren().add(tabPane);
    }

    private LineChart<String, Number> getLineChart() {
        final var xAxis = new CategoryAxis();
        final var yAxis = new NumberAxis();
        final var chart = new LineChart<>(xAxis, yAxis);
        if (processData(chart)) {
            return chart;
        }
        return null;
    }
    
    private BarChart<String, Number> getBarChart() {
        final var xAxis = new CategoryAxis();
        final var yAxis = new NumberAxis();
        final var chart = new BarChart<>(xAxis, yAxis);
        if (processData(chart)) {
            return chart;
        }
        return null;
    }
    
    private boolean processData(XYChart<String, Number> chart) {
        chart.setTitle("Accumulated Sales Per Day");
        var series = new XYChart.Series<String, Number>();
        series.setName("Sales");

        var data = spos.getTransactionManager().getListOfTransaction();
        if (data.isEmpty()) {
            return false;
        }
        data.sort((o1, o2) -> {
            var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                var do1 = formatter.parse(o1.getDateTime());
                var do2 = formatter.parse(o2.getDateTime());
                return do1.compareTo(do2);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        Map<String, List<Transaction>> groupedData = data.stream().collect(Collectors.groupingBy(Transaction::getDateTime));
        for (var datum : groupedData.entrySet()) {
            try {
                var formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                var formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                var x = formatter2.format(formatter1.parse(datum.getKey()));
                var y = datum.getValue().stream().mapToDouble(Transaction::getAmount).sum();
                series.getData().add(new XYChart.Data<>(x, y));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        chart.getData().add(series);
        return true;
    }
}
