package AnyQuantProject.ui.graphUI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.bl.factoryBL.SingleStockBLFactory;
import AnyQuantProject.blService.singleStockDealBLService.SingleStockDealBLService;
import AnyQuantProject.dataStructure.Stock;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
 
public class LineChartSample extends Application {
 
    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
//        //defining the axes
//        final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        xAxis.setLabel("Number of Month");
//        //creating the chart
//        final LineChart<Number,Number> lineChart = 
//                new LineChart<Number,Number>(xAxis,yAxis);
//                
//        lineChart.setTitle("Stock Monitoring, 2010");
//        //defining a series
//        XYChart.Series series = new XYChart.Series();
//        series.setName("My portfolio");
//        //populating the series with data
//        series.getData().add(new XYChart.Data(1, 23));
//        series.getData().add(new XYChart.Data(2, 14));
//        series.getData().add(new XYChart.Data(3, 15));
//        series.getData().add(new XYChart.Data(4, 24));
//        series.getData().add(new XYChart.Data(5, 34));
//        series.getData().add(new XYChart.Data(6, 36));
//        series.getData().add(new XYChart.Data(7, 22));
//        series.getData().add(new XYChart.Data(8, 45));
//        series.getData().add(new XYChart.Data(9, 43));
//        series.getData().add(new XYChart.Data(10, 17));
//        series.getData().add(new XYChart.Data(11, 29));
//        series.getData().add(new XYChart.Data(12, 25));
//        SingleStockDealBLService singleStockDealBLService=SingleStockBLFactory.getSingleStockDealBLService();
//        Calendar te=Calendar.getInstance();
//        te.set(Calendar.MONTH,1);
//        StockListBLService stockListBLService=StockListBLFactory.getStockListBLService();
//        List<Stock> anStocks=stockListBLService.getAllStocks();
//        List<String> stockNames=new ArrayList<>();
//        stockNames.add("sh600900");
//        stockNames.add("sh600837");
//        stockNames.add("sh601001");
//        stockNames.add("sh600728");
//        stockNames.add("sh600023");
//        stockNames.add("sh600894");
        //
        XYChart<String, Number> xyChart=LineChartFactory.getPoly("sh600030");
        xyChart.setPrefSize(800, 1000);
        StackPane stackPane =new StackPane(xyChart);
        
        ScrollPane scrollPane=new ScrollPane(stackPane);
        Scene scene=new Scene(scrollPane);
        scrollPane.setPrefSize(800, 1000);
        stage.setScene(scene);
        stage.show();
        
        
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}
