package com.objectify.PieChartPlugin;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.models.entities.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.stream.Collectors;

public class PieChartPane extends AnchorPane {

    private final SystemPointOfSales spos;

    public PieChartPane(SystemPointOfSales spos) {
        super();
        this.spos = spos;
        setPadding(new Insets(10, 10, 10, 10));
        var content = new VBox();
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
        VBox.setVgrow(content, Priority.ALWAYS);
        content.setAlignment(Pos.CENTER);
        var pieChart = getPieChart();
        content.getChildren().add(Objects.requireNonNullElseGet(pieChart, () -> new Label("No data")));
        getChildren().add(content);
    }

    private PieChart getPieChart() {
        final var chart = new PieChart();
        final var data = spos.getUserManager().getListOfUsers();
        if (data.isEmpty()) {
            return null;
        }
        final var groupedData = data.stream().collect(Collectors.groupingBy(User::getType));
        for (final var datum : groupedData.entrySet()) {
            chart.getData().add(new PieChart.Data(datum.getKey(), datum.getValue().size()));
        }
        chart.setTitle("Members");
        return chart;
    }
}
