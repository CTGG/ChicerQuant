package AnyQuantProject.data.util;

import java.io.IOException;
import java.util.Calendar;

import AnyQuantProject.util.exception.NetFailedException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import AnyQuantProject.util.method.CalendarHelper;


/**
 * @author G
 *
 */
public class JsonHelper {
	
	APIHelper helper = new APIHelper();
	String keyheader = "http://121.41.106.89:8010/api/";
	String key;
	
	
	public JSONArray getAll(String Pkey) throws NetFailedException{
		JSONArray jarr = new JSONArray();
		JSONArray result = new JSONArray();
		try {
			jarr = helper.getAnyAPI(keyheader+Pkey);
		} catch (IOException e) {
			throw new NetFailedException("anyquant net connect failed");
		}

		for (int i = 0; i < jarr.size(); i++) {
			JSONObject jo = (JSONObject) jarr.get(i);
			result.add(jo.get("name"));			
		}
		
		return result;
	}
	
	
	public JSONArray getAllWithChinese(String Pkey) throws NetFailedException{
		JSONArray jarr = new JSONArray();
		JSONArray result = new JSONArray();
		try {
			jarr = helper.getAnyAPI(keyheader+Pkey);
		} catch (IOException e) {
			throw new NetFailedException("anyquant net connect failed");
		}

		for (int i = 0; i < jarr.size(); i++) {
			JSONObject jo = (JSONObject) jarr.get(i);
			String name = (String) jo.get("name");
			String nameWithChinese = name + " " + helper.getSingleStockChineseName(name);
			result.add(nameWithChinese);
			
		}
		
		return result;
	}
	
	public JSONObject getOperation(DataType type, String name, Calendar date) throws NetFailedException{
		key = getKeyWithDate(type, name, date, date);
		JSONObject jo = new JSONObject();

		try {
		
				jo = helper.getAnyAPI(keyheader+key).getJSONObject(0);
		} catch (IOException e) {
			throw new NetFailedException("anyquant net connect failed");
		}
		
		if (jo.size() != 0) {
			JSONArray arr = jo.getJSONArray("trading_info");
			if (arr.size() != 0) {
				return arr.getJSONObject(0);
			}else {
				return new JSONObject();
			}			
		}else {
			return new JSONObject();
		}
		
	}
	
	public JSONArray getAmongDate(DataType type, String name, Calendar start,
			Calendar end) throws NetFailedException{
		key = getKeyWithDate(type, name, start, end);
		JSONArray jarr = new JSONArray();
//		System.out.println("jhelper  key : "+key);
		JSONObject jo = new JSONObject();
		try {
			jo = helper.getAnyAPI(keyheader+key).getJSONObject(0);
		} catch (IOException e) {
			throw new NetFailedException("anyquant net connect failed");
		}
		jarr = jo.getJSONArray("trading_info");
		return jarr;
	}
	
	public String getKeyWithDate(DataType type, String name, Calendar start, Calendar end) {
//		Calendar previousdate = CalendarHelper.getPreviousDay(start);
		String startday = CalendarHelper.getDate(start);
		Calendar afterdate = CalendarHelper.getAfterDay(end);
		String afterday = CalendarHelper.getDate(afterdate);
		switch (type) {
		case BENCHMARK:
			return "benchmark/"+name+"?start="+startday+"&end="+afterday+"&fields=open+high+low+close+volume+adj_price";
		default:
			return "stock/"+name+"/?start="+startday+"&end="+afterday+"&fields=open+high+low+close+volume+adj_price+turnover+pe_ttm+pb";
		}
	}
	
	public static void main(String[] args) {
		JsonHelper j = new JsonHelper();
//		j.getAllWithChinese("stock/sh600000/?start=2016-02-01&end=2016-02-03&fields=open+high+close");
//		j.getAll("stock/sh600000/?start=2016-02-01&end=2016-02-03&fields=open+high+close");
//		j.getOperation(DataType.BENCHMARK, "hs300", Calendar.getInstance());
	}

}
