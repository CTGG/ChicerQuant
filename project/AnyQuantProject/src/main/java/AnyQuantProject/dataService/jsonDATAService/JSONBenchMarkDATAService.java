/**
 * 
 */
package AnyQuantProject.dataService.jsonDATAService;

import java.util.Calendar;

import AnyQuantProject.util.exception.NetFailedException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;





/**
 * @author G
 *
 */
public interface JSONBenchMarkDATAService {
	/**
	 * 返回当前的大盘代号名列表
	 */
	public JSONArray getAllBenchMark()throws NetFailedException;
	
	
	
	/**
	 * 返回当前的大盘代号名和中文名列表
	 */
	public JSONArray getAllBenchMarkWithChinese()throws NetFailedException;
	
	/**
	 * 由大盘名称、指定日期得到该大盘指定日期的数据
	 * @param name
	 * @param date
	 * @return
	 */
	public JSONObject getOperation(String name, Calendar date)throws NetFailedException;
	
	/**
	 * 由大盘名称、日期区间得到该大盘指定日期的数据
	 * @param name
	 * @param start
	 * @param end
	 * @return
	 */
	public JSONArray getBenchMarkAmongDate(String name, Calendar start, Calendar end)throws NetFailedException;
}
