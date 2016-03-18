package AnyQuantProject.bl.graphBL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.blService.graphBLService.LineChartBLService;
import AnyQuantProject.blService.kLineBLService.KLineBLService;
import AnyQuantProject.blService.kLineBLService.StockKLineBLService;
import AnyQuantProject.dataStructure.AbstractStock;
import AnyQuantProject.dataStructure.BarData;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.dataStructure.LineChartData;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.Checker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Rectangle;

/** 
*AnyQuantProject//AnyQuantProject.bl.graphBL//LineChartBLImpl.java
* @author  cxworks 
* @date 创建时间：2016年3月5日 下午3:20:00 
*/

public class LineChartBLImpl implements LineChartBLService {

	@Override
	public LineChartData drawMonthLineChart(List<? extends AbstractStock> src) {
		//check
		if (!Checker.checkListNotNull(src)) {
			return new LineChartData();
		}
		//get title
		AbstractStock head=src.get(0);
		String title=head.getYear()+this.year+head.getMonth()+this.month+" "+head.getName()+" "+this.chartType;
		//x,y
		CategoryAxis xAxis=new CategoryAxis();
		NumberAxis yAxis=new NumberAxis();
		//series high price
		XYChart.Series<?,?> highSeries=new XYChart.Series();
		src.stream()
			.map(stock->new XYChart.Data(stock.getDay(), stock.getHigh()))
			.forEach(da->highSeries.getData().add(da));
		highSeries.setName("最高价");
		//low price
		XYChart.Series<?,?> lowSeries=new XYChart.Series();
		src.stream()
			.map(stock->new XYChart.Data(stock.getDay(), stock.getLow()))
			.forEach(da->lowSeries.getData().add(da));
		lowSeries.setName("最低价");
		return new LineChartData(title, xAxis, yAxis, highSeries,lowSeries);
	}

	@Override
	public LineChartData drawMonthKLineChart(List<? extends AbstractStock> src) {
		//check
		if (!Checker.checkListNotNull(src)) {
			return new LineChartData();
		}
		//get title
		AbstractStock head = src.get(0);
		String title = head.getYear() + this.year + head.getMonth() + this.month + " " + head.getName() + " K线图";
		// x,y
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis =new NumberAxis();
		//
		XYChart.Series<?, ?> highlowSeries=new XYChart.Series();
		for(Iterator<? extends AbstractStock> iterator=src.iterator();iterator.hasNext();){
			AbstractStock temp=iterator.next();
			highlowSeries.getData().add(new XYChart.Data(temp.getDay(), temp.getHigh()));
			highlowSeries.getData().add(new XYChart.Data(temp.getDay(), temp.getLow()));
		}
		//aver
		XYChart.Series<?, ?> averSeries=new XYChart.Series();
		src.stream()
		.map(stock->{
			XYChart.Data ans=new XYChart.Data(stock.getDay(), (stock.getOpen()+stock.getClose())/2);
			Rectangle r = new Rectangle();
	        r.setWidth(20);
	        r.setHeight(100);
	        ans.setNode(r);
	        return ans;
			})
		.forEach(da->averSeries.getData().add(da));
		averSeries.setName(head.getName()+"走势");
		//open
		XYChart.Series<?, ?> openSeries=new XYChart.Series();
		src.stream()
		.map(stock->new XYChart.Data(stock.getDay(), stock.getOpen()))
		.forEach(da->openSeries.getData().add(da));
		//close
		XYChart.Series<?, ?> closeSeries=new XYChart.Series();
		src.stream()
		.map(stock->new XYChart.Data(stock.getDay(), stock.getClose()))
		.forEach(da->closeSeries.getData().add(da));
		//
		return new LineChartData(title, xAxis, yAxis,highlowSeries, openSeries,closeSeries,averSeries);
	}

	@Override
	public List<BarData> drawKLineChart(String name) {
		StockKLineBLService stockKLineBLService=KLineBLFactory.getStockKLineBLService();
		KLineData data=stockKLineBLService.monthKLineChart(name);
		Calendar calendar=Calendar.getInstance();
		calendar.set(2006, 1, 1);
		GregorianCalendar gregorianCalendar=new GregorianCalendar();
		gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
		List<KLineDataDTO> kLineDataDTOs=data.geKLineDataDTOs();
		List<BarData> barDatas=new ArrayList<>(kLineDataDTOs.size());
		
		for (int i = 0; i < kLineDataDTOs.size(); i++) {
			KLineDataDTO kLineDataDTO=kLineDataDTOs.get(i);
			
			BarData barData=new BarData((GregorianCalendar)gregorianCalendar.clone(), kLineDataDTO.getOpen(), kLineDataDTO.getHigh(), kLineDataDTO.getLow(), kLineDataDTO.getClose(), 10);
			
			gregorianCalendar.add(GregorianCalendar.MONTH, 1);
			if (kLineDataDTO.getClose()==0||kLineDataDTO.getOpen()==0) {
				continue;
			}
			barDatas.add(barData);
		}
		System.out.println(kLineDataDTOs.size());
		return barDatas;
	}

}
