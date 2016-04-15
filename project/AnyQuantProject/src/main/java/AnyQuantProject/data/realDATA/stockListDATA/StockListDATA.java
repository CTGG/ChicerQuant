/**
 * 
 */
package AnyQuantProject.data.realDATA.stockListDATA;


import java.util.Calendar;
import java.util.List;

import AnyQuantProject.data.util.ChineseName;
import AnyQuantProject.util.exception.NetFailedException;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import AnyQuantProject.data.jsonDATA.JSONStockListDATA;
import AnyQuantProject.dataService.jsonDATAService.JSONStockListDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.StockListDATAService;
import AnyQuantProject.dataStructure.Exchange;

/**
 * @author G
 *
 */
public class StockListDATA implements StockListDATAService{

	JSONStockListDATAService JSONStockList = new JSONStockListDATA();
	
	private static StockListDATA stockListDATA;
	private StockListDATA() {
	}
	
	
	public static StockListDATA getInstance(){
		if ( stockListDATA == null) {
			stockListDATA = new StockListDATA();
		}
		return stockListDATA;
	}
	
	
	@SuppressWarnings("null")
	@Override

	public List<String> getAllStocks(Calendar date, Exchange exchange) throws NetFailedException {
		JSONArray resultArray = JSONStockList.getAllStocks(date, exchange);
		List<String> resultList = JSONArray.toList(resultArray, new String(), new JsonConfig());
		return resultList;
	}

	@Override
	public List<String> getAllWithChinese(Calendar date, Exchange exchange)throws NetFailedException {
		return ChineseName.getAllChineseName();
	}


	public List<String> getAllStocksWithChinese(Calendar date, Exchange exchange) throws NetFailedException{
		JSONArray resultArray = JSONStockList.getAllStocksWithChinese(date, exchange);
		List<String> resultList = JSONArray.toList(resultArray, new String(), new JsonConfig());
		return resultList;
	}

	@Override
	public String getChineseName(String name)throws NetFailedException {
		return ChineseName.getChineseName(name);
	}

	public static void main(String[] args) {

		try {
			StockListDATA.getInstance().getAllStocks(Calendar.getInstance(),Exchange.SH);
		} catch (NetFailedException e) {
			System.out.println("i'll handle it");
		}
	}
}
