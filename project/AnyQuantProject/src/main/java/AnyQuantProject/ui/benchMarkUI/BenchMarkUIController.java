package AnyQuantProject.ui.benchMarkUI;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JComponent;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CrosshairLabelGenerator;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import AnyQuantProject.bl.factoryBL.BenchMarkBLFactory;
import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.blService.benchMarkBLService.BenchMarkBLService;
import AnyQuantProject.blService.kLineBLService.BenchmarkKLineBLService;
import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.util.constant.TimeType;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.DrawKLineChart;
import AnyQuantProject.util.method.MyCrossOverlay;
import AnyQuantProject.util.method.SimpleDoubleProperty;
import AnyQuantProject.util.method.SimpleIntegerProperty;
import AnyQuantProject.util.method.SimpleLongProperty;

/**
 * 
 * @author QiHan
 *
 */

public class BenchMarkUIController implements Initializable{

	@FXML
	private  TableView<BenchMark> table;
	@FXML
	private TableColumn<BenchMark, String> timeTable;
	@FXML
	private TableColumn<BenchMark, String> allBenchMarkID;
	@FXML
	private  TableColumn<BenchMark, Double> openTable;
	@FXML
	private  TableColumn<BenchMark, Double> highTable;
	@FXML
	private  TableColumn<BenchMark, Double> lowTable;
	@FXML
	private  TableColumn<BenchMark, Double> closeTable;
	@FXML
	private  TableColumn<BenchMark, Integer> volumTable;
	@FXML
	private  TableColumn<BenchMark, Long> turnoverTable;//成交额
	@FXML
	private  TableColumn<BenchMark, Double> adj_priceTable;
	@FXML
	private  TableColumn<BenchMark, Long> marketvalueTable;
	@FXML
	private  TableColumn<BenchMark, Long> flow;
	
	private  TableColumn<BenchMark, Double> upOrDownRateTable;
	@FXML
	private Label today,yesterday,max,min,volume,turnover,adj_price,currentTime,toLabel,dateChoiceLabel,
				benchMarkChineseID,benchMarkID,todayTitle,yesterdayTitle,maxTitle,volumeTitle,minTitle,
				turnoverTitle,adj_priceTitle;
//	@FXML
	//public  ComboBox benchMarkID;
	@FXML
	private Button okBtn;
	@FXML
	private Tab dayChatTab,monthChatTab,weekChatTab;
	@FXML 
	private TabPane tabPane;
	@FXML
	private DatePicker minTime=null,maxTime=null;
	@FXML
	private AnchorPane calendarPanel;
	
	private  ChartPanel daypanel,weekpanel,monthpanel;
	private SwingNode dayswingNode,weekswingNode,monthswingNode;
	private ScrollPane dayScroller,weekScroller,monthScroller;
	private Calendar calendar,startTime=null,endTime=null;
	private CalendarHelper calendarHelper = new CalendarHelper();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	
	String  benchMarkid;
	//表格数据
	private BenchMarkBLService benchMarkBLService =BenchMarkBLFactory.getBenchMarBLService();  
	private  List<BenchMark> benchMarkList;
	
	//K线数据
	private  String startDate ;
	private  BenchmarkKLineBLService benchmarkKLineBLService=KLineBLFactory.getBenchmarkBLService();
	private  KLineData benchMarkKLineDate,fiveAverageLine,tenAverageLine,thirtyAverageLine;
	private List<KLineDataDTO>  benchMarkKLineDataList,fiveAverageLineDataList,tenAverageLineDataList,thirtyAverageLineDataList;
	
	private static BenchMarkUIController instance = null;
	 
	/**
	 * 此处之后应该对接初始化数据的方法  
	 * @return
	 */
	public BenchMarkUIController(){
		
	}
	
	public static BenchMarkUIController getInstance() {
		System.out.println("here is the instance of BenchMarkUIController ");
	    FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BenchMarkUIController.class.getResource("benchMarkPanel.fxml"));
		return instance;
	 }
	
	/**
	 * initialize the table
	 * 
	 */
    @SuppressWarnings("deprecation")
	public  void init(){
    	
    	calendar=Calendar.getInstance();

    	currentTime.setText(dateFormat.format(new Date()));
    	System.out.println(currentTime);
    	
    	benchMarkList=benchMarkBLService.getAllBenchMark();
    	
        table.setItems(FXCollections.observableArrayList(benchMarkList));

        table.getItems().add(new BenchMark());
       
        List<String> options = new  ArrayList<String>();
        for(int i=0;i<benchMarkList.size();i++){
        	options.add(benchMarkList.get(i).getName());
        }   
             
        	benchMarkid=benchMarkList.get(0).getName();
        
        	//init the dayTabPane
        	int year =Integer.parseInt(String.valueOf(calendar.get(Calendar.YEAR)));
        	int month =Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH)));
        	int day =Integer.parseInt(String.valueOf(calendar.get(Calendar.DATE)));
        	startTime = Calendar.getInstance();
        	startTime.set(year,1,1);
        	endTime = Calendar.getInstance();
        	endTime.set(year,month+1,day-1);//yeaterday
        	
        	minTime.setValue(LocalDate.of(year, 1, 1));
        	
        	if(day==1){
        		minTime.setValue(LocalDate.of(year-1, 1, 1));
        		maxTime.setValue(LocalDate.of(year, month+1,day));
        		startTime.set(year,1,1);
        		endTime.set(year,month,day);//yeaterday
        	}else {
        		maxTime.setValue(LocalDate.of(year, month+1,day-1));
        	}
        	newDayTab();
        	newWeek_MonthTab();
        
        	benchMarkChineseID.setText("上证指数");
        	benchMarkID.setText(benchMarkid);;
        	simpleInfo(benchMarkid);
        	
        //	cambox
//        	ObservableList items = FXCollections.observableArrayList(options);
//            benchMarkID.setItems(items);
//            benchMarkID.setPromptText((String) items.get(0));
//            benchMarkID.valueProperty().addListener(new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue ov, String t, String t1) {
//                	benchMarkid = t1;
//               // 	simpleInfo(benchMarkid);
//                    System.out.println("the selected benchMarkID is: " + benchMarkid);
//                  
//                    newDayTab();
//                    newWeek_MonthTab();
//                 
//                }
//            });
	
	/**
	 * initialize the tabel columns
	 */
//        timeTable .setCellValueFactory(cellData -> new SimpleStringProperty(
//        		   cellData.getValue().getDate()));
        allBenchMarkID.setCellValueFactory(cellData -> new SimpleStringProperty(
     		   cellData.getValue().getName()));
        openTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
        		   cellData.getValue().getOpen()));
//        highTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//        		   cellData.getValue().getHigh()));
//        lowTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//        		   cellData.getValue().getLow()));
//        closeTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//        		   cellData.getValue().getClose()));
//        volumTable.setCellValueFactory(cellData -> new SimpleIntegerProperty(
//        		   cellData.getValue().getVolume()));
//        adj_priceTable.setCellValueFactory(cellData -> new SimpleDoubleProperty(
//				   cellData.getValue().getAdj_price()));
//        turnoverTable.setCellValueFactory(cellData -> new SimpleLongProperty(
//				   cellData.getValue().getTransaction()));
//        marketvalueTable.setCellValueFactory(cellData -> new SimpleLongProperty(
//				cellData.getValue().getMarketvalue()));
//        flow.setCellValueFactory(cellData -> new SimpleLongProperty(
//			    cellData.getValue().getFlow()));
   }
    
    private void datePanellisteners(){

  //      tabPane.getTabs().addAll(dayChatTab, weekChatTab,monthChatTab);
   
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
            @Override
            public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab mostRecentlySelectedTab){
                if (mostRecentlySelectedTab.equals(dayChatTab)){
                	calendarPanel.setOpacity(1);
                	toLabel.setDisable(false);
            		dateChoiceLabel.setDisable(false);
            		minTime.setDisable(false);
            		maxTime.setDisable(false);
            		okBtn.setDisable(false);
            		System.out.println("daytab");           
                }
                if (mostRecentlySelectedTab.equals(weekChatTab)){   
                	calendarPanel.setOpacity(0);
            		toLabel.setDisable(true);
            		dateChoiceLabel.setDisable(true);
            		minTime.setDisable(true);
            		maxTime.setDisable(true);
            		okBtn.setDisable(true);
            		System.out.println("weektab");
                }
                if (mostRecentlySelectedTab.equals(monthChatTab)){   
                	calendarPanel.setOpacity(0);
                	toLabel.setDisable(true);
            		dateChoiceLabel.setDisable(true);
            		minTime.setDisable(true);
            		maxTime.setDisable(true);
            		okBtn.setDisable(true);
            		System.out.println("monthtab");
                }
            }
        });

    }
    private ChartPanel getChartPanel(JFreeChart jFreeChart){
    	ChartPanel chartPanel=new ChartPanel(jFreeChart);
    	chartPanel.setMaximumSize(new Dimension(1000, 600));
    	chartPanel.setMouseWheelEnabled(true);
    	chartPanel.setPopupMenu(null);
    	//
    	CrosshairOverlay crosshairOverlay=new MyCrossOverlay();
    	Crosshair xCrosshair=new Crosshair(Double.NaN);
    	xCrosshair.setLabelPaint(java.awt.Color.white);
    	xCrosshair.setStroke(new BasicStroke(0f));
    	xCrosshair.setLabelGenerator(new CrosshairLabelGenerator() {
			@Override
			public String generateLabel(Crosshair crosshair) {
				Rectangle2D dataArea=chartPanel.getScreenDataArea();
				XYPlot plot = (XYPlot) chartPanel.getChart().getPlot();
		        DateAxis xAxis=(DateAxis) plot.getDomainAxis();
		        Date date1=xAxis.getMinimumDate();
		        double start=xAxis.dateToJava2D(date1, dataArea, plot.getDomainAxisEdge());
		        Date date2=xAxis.getMaximumDate();
		        double end=xAxis.dateToJava2D(date2, dataArea, plot.getDomainAxisEdge());
		        double value=xAxis.valueToJava2D(crosshair.getValue(), dataArea, plot.getDomainAxisEdge());
		        double percent=(value-start)/(end-start);
		        long time=date2.getTime()-date1.getTime();
		        long ans=Math.round(time*percent);
		        Date date=new Date(date1.getTime()+ans);
				return date.toString();
			}
		});
    	xCrosshair.setLabelVisible(true);
    	xCrosshair.setLabelAnchor(RectangleAnchor.TOP);
    	xCrosshair.setPaint(java.awt.Color.white);
    	xCrosshair.setLabelPaint(java.awt.Color.white);
    	xCrosshair.setLabelBackgroundPaint(java.awt.Color.yellow);
    	Crosshair yCrosshair=new Crosshair(Double.NaN);
    	yCrosshair.setStroke(new BasicStroke(0f));
    	yCrosshair.setLabelGenerator(new CrosshairLabelGenerator() {
			
			@Override
			public String generateLabel(Crosshair crosshair) {
				DecimalFormat decimalFormat=new DecimalFormat("#.000");
				JFreeChart chart=chartPanel.getChart();
				CombinedDomainXYPlot combinedDomainXYPlot=(CombinedDomainXYPlot) chart.getPlot();
				List<Plot> plots=combinedDomainXYPlot.getSubplots();
				double low=((XYPlot)plots.get(0)).getRangeAxis().getLowerBound();
				double high=((XYPlot)plots.get(0)).getRangeAxis().getUpperBound();
				double val=high-(high-crosshair.getValue())*1.5;
				return decimalFormat.format(val);
		        
			}
		});
    	yCrosshair.setLabelVisible(true);
//    	yCrosshair.setLabelAnchor(RectangleAnchor.RIGHT);
    	yCrosshair.setPaint(java.awt.Color.white);
    	yCrosshair.setLabelPaint(java.awt.Color.white);
    	yCrosshair.setLabelBackgroundPaint(java.awt.Color.yellow);
    	crosshairOverlay.addDomainCrosshair(xCrosshair);
    	crosshairOverlay.addRangeCrosshair(yCrosshair);
    	chartPanel.addOverlay(crosshairOverlay);
    	
    	chartPanel.addChartMouseListener(new ChartMouseListener() {
			
			@Override
			public void chartMouseMoved(ChartMouseEvent event) {
				 	Rectangle2D dataArea = chartPanel.getScreenDataArea();
			        JFreeChart chart = event.getChart();
			        Point point=new Point(event.getTrigger().getX(), event.getTrigger().getY());
			        Point2D point2d=chartPanel.translateScreenToJava2D(point);
			        XYPlot plot = (XYPlot) chart.getPlot();
			        ValueAxis xAxis = plot.getDomainAxis();
			        List<Plot> plots= ((CombinedDomainXYPlot)plot).getSubplots();
			        NumberAxis yAxis=(NumberAxis) ((XYPlot)plots.get(0)).getRangeAxis();
			        double x = xAxis.java2DToValue(point2d.getX(), dataArea, 
			                RectangleEdge.BOTTOM);
			        
			        double y = yAxis.java2DToValue(point2d.getY(), dataArea, RectangleEdge.LEFT);
			        xCrosshair.setValue(x);
			        yCrosshair.setValue(y);
			}
			
			@Override
			public void chartMouseClicked(ChartMouseEvent event) {
			}
		});
    	
    	
    	return chartPanel;
    }
    
    private void newDayTab(){
    	   daypanel = getChartPanel(drawDayKLine());
           dayswingNode = new SwingNode();
           daypanel.setMinimumSize(new Dimension(1000,400));
//            daypanel.setMaximumSize(new Dimension(10000,600));
           dayswingNode .setContent(daypanel); 
           dayScroller=new ScrollPane();
           dayScroller.setContent(dayswingNode);
    //       dayScroller.setFitToWidth(true);
           dayScroller.setFitToHeight(true);
           dayChatTab.setContent(dayScroller);    
    
    }

    private void newWeek_MonthTab(){
           weekpanel = new ChartPanel(drawWeekKLine());

           weekpanel.setMinimumSize(new Dimension(1000,400));
   //        weekpanel.setMaximumSize(new Dimension(10000,600));
           weekswingNode = new SwingNode();
           weekswingNode .setContent(weekpanel);    
           weekScroller=new ScrollPane();
           weekScroller.setFitToHeight(true);
     //      weekScroller.setFitToWidth(true)
           weekScroller.setContent(weekswingNode);
           weekChatTab.setContent(weekScroller);    
          
           monthpanel = getChartPanel(drawMonthKLine());
           monthpanel.setMinimumSize(new Dimension(1000,400));
 //          monthpanel.setMaximumSize(new Dimension(10000,600));
           monthswingNode = new SwingNode();
           monthswingNode .setContent(monthpanel);    
           monthScroller=new ScrollPane();
           monthScroller.setFitToHeight(true);
           monthScroller.setContent(monthswingNode);
           monthChatTab.setContent(monthScroller);    
          
    } 
    
    private void simpleInfo(String id){
    	  for(int i=0;i<benchMarkList.size();i++){
          	if(benchMarkList.get(i).getName()==id){
          		today.setText(benchMarkList.get(i).getOpen()+"");
          		yesterday.setText(benchMarkList.get(i).getClose()+"");
          		max.setText(benchMarkList.get(i).getHigh()+"");
          		min.setText(benchMarkList.get(i).getLow()+"");
          		volume.setText(benchMarkList.get(i).getVolume()+"");
          		turnover.setText(benchMarkList.get(i).getTransaction()+"");
          		adj_price.setText(benchMarkList.get(i).getAdj_price()+"");
          		break;
          	}
          }
    }
    @FXML
    private void getCalendarEnsureAction(){
    	startTime=null; endTime=null;
    	okBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
    		
    		if (minTime.getValue() != null) {
                LocalDate startLocalDate = minTime.getValue();
                startTime = calendarHelper.convert2Calendar(startLocalDate);  
            } else {
            }
            if (maxTime.getValue() != null) {
                LocalDate endLocalDate = maxTime.getValue();
                endTime = calendarHelper.convert2Calendar(endLocalDate);	
            }
            newDayTab();
    	});
    }
    
    private JFreeChart drawDayKLine() {
    	LineDayChart();
        //K线
    	 benchMarkKLineDate=benchmarkKLineBLService.dayKLineChart(benchMarkid,startTime,endTime);
    	 benchMarkKLineDataList=benchMarkKLineDate.geKLineDataDTOs();
    	 String endtime=null;
    	 if(endTime==null){
    		 endtime=null;
    	 }
    	 else{
    		 endtime=sdf.format(endTime.getTime());
    	 }
    	 System.out.println("........BenchMark..........Calendar:endtime......"+endtime);
    	 return  DrawKLineChart.DayKLineChart (benchMarkKLineDataList,fiveAverageLineDataList,tenAverageLineDataList,thirtyAverageLineDataList,benchMarkid,TimeType.DAY,endtime);
	}
    
    private JFreeChart drawWeekKLine() {
    	LineWeekChart();
    	benchMarkKLineDate=benchmarkKLineBLService.weekKLineChart(benchMarkid);
   	 	benchMarkKLineDataList=benchMarkKLineDate.geKLineDataDTOs();
   	 	return  DrawKLineChart.DayKLineChart (benchMarkKLineDataList,fiveAverageLineDataList,tenAverageLineDataList,thirtyAverageLineDataList,benchMarkid,TimeType.WEEK,null);
	}
    private JFreeChart drawMonthKLine() {
    	LineMonthChart();
    	benchMarkKLineDate=benchmarkKLineBLService.monthKLineChart(benchMarkid);
   	 	benchMarkKLineDataList=benchMarkKLineDate.geKLineDataDTOs();
   	 	return  DrawKLineChart.DayKLineChart (benchMarkKLineDataList,fiveAverageLineDataList,tenAverageLineDataList,thirtyAverageLineDataList,benchMarkid,TimeType.MONTH,null);
	}

    /**
     * Initializes the controller class.
     */
    
    public void LineDayChart(){
    	//5日线
   	 fiveAverageLine = benchmarkKLineBLService.getDayAverageLine(benchMarkid, startTime, endTime, 5);
   	 fiveAverageLineDataList = fiveAverageLine.geKLineDataDTOs();
   	//10日线
   	 tenAverageLine =benchmarkKLineBLService.getDayAverageLine(benchMarkid, startTime, endTime, 10);
     tenAverageLineDataList=tenAverageLine.geKLineDataDTOs();
       //30日线
   	 thirtyAverageLine= benchmarkKLineBLService.getDayAverageLine(benchMarkid, startTime, endTime, 30);
     thirtyAverageLineDataList =thirtyAverageLine.geKLineDataDTOs();
    }
    
    public void LineWeekChart(){
    	//5日线
   	 fiveAverageLine = benchmarkKLineBLService.getWeekAverageLine(benchMarkid, 5);
   	 fiveAverageLineDataList = fiveAverageLine.geKLineDataDTOs();
   	//10日线
   	 tenAverageLine =benchmarkKLineBLService.getWeekAverageLine(benchMarkid,10);
     tenAverageLineDataList=tenAverageLine.geKLineDataDTOs();
       //30日线
   	 thirtyAverageLine= benchmarkKLineBLService.getWeekAverageLine(benchMarkid, 30);
     thirtyAverageLineDataList =thirtyAverageLine.geKLineDataDTOs();
    }
    public void LineMonthChart(){
    	//5日线
   	 fiveAverageLine = benchmarkKLineBLService.getMonthAverageLine(benchMarkid,  5);
   	 fiveAverageLineDataList = fiveAverageLine.geKLineDataDTOs();
   	//10日线
   	 tenAverageLine =benchmarkKLineBLService.getMonthAverageLine(benchMarkid, 10);
     tenAverageLineDataList=tenAverageLine.geKLineDataDTOs();
       //30日线
   	 thirtyAverageLine= benchmarkKLineBLService.getMonthAverageLine(benchMarkid,  30);
     thirtyAverageLineDataList =thirtyAverageLine.geKLineDataDTOs();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        init();
        datePanellisteners();
    }
}
