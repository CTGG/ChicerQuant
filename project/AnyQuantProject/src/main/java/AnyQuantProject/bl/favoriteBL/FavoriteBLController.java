package AnyQuantProject.bl.favoriteBL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.InitialContext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.omg.IOP.IORHelper;

import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.blService.stockListBLService.StockListBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.ui.net.TipPop;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.exception.NotReadyException;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;
import AnyQuantProject.util.method.IOHelper;

/** 
*AnyQuantProject//AnyQuantProject.bl.favoriteBL//FavoriteBLController.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:47:48 
*/

public class FavoriteBLController implements FavoriteBLService {
	private ArrayList<String> favor;
	
	public FavoriteBLController() {
		init();
	}
	
	private void init(){
		//try to read
				Object ans=IOHelper.read(R.FavorFilePath, R.FavorFileName);
				if (ans==null) {
					favor=new ArrayList<>();
				}
				else {
					
					favor=(ArrayList<String>) ans;
				}
	}

	@Override
	public List<Stock> getMyFavor() {
		//
		if (favor.isEmpty()) {
			return new ArrayList<>(0);
		}
		StockListBLService service=StockListBLFactory.getStockListBLService();
		List<Stock> ans=new ArrayList<>(favor.size());
		try {
			for (int i = 0; i < favor.size(); i++) {
				Stock data=service.getTodayData(favor.get(i));
				ans.add(data);
			}
			return ans;
		} catch (NotReadyException e) {
			return getFromNet();
		}
		
	}
	
	private List<Stock> getFromNet(){
		// get data service
		FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService = factoryDATAService.getSingleStockDATAService();
		//
		try {
			List<Stock> ans = new ArrayList<>();
			for (int i = 0; i < favor.size(); i++) {
				String name = favor.get(i);
				ans.add(singleStockDATAService.getOperation(name,
						Calendar.getInstance()));
			}
			return ans;
		} catch (Exception e) {
			TipPop.showTip();
			return this.getMyFavor();
		}
	}

	@Override
	public OperationResult favorStock(String name) {
		//check if have
		if(!Checker.checkStringNotNull(name)||favor.contains(name)){
			return new OperationResult(false, "已加入关注列表");
		}
		//
		favor.add(name);
		return IOHelper.save(R.FavorFilePath, R.FavorFileName, favor);
		
	}

	@Override
	public OperationResult unFavorStock(String name) {
		if(!Checker.checkStringNotNull(name)||!favor.contains(name)){
			return new OperationResult(false, "未加入关注列表");
		}
		//
		favor.remove(name);
		return IOHelper.save(R.FavorFilePath, R.FavorFileName, favor);
	}

	@Override
	public boolean checkIsFavored(String name) {
		//check
		if (!Checker.checkStringNotNull(name)) {
			return false;
		}
		if (!Checker.checkListNotNull(favor)) {
			init();
		}
		return favor.contains(name);
	}
}
