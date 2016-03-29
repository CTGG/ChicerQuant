package AnyQuantProject.ui.moduleUI;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.starter.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MoreModuleUIController implements Initializable{
	
	private static MoreModuleUIController  instance = null;
	@FXML
	private AnchorPane moreModulePanel;
	@FXML
	private Button returnBtn,btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28;
	private int num=13;
	private Button[] buttons =new Button[13];
	private IndustryBLService industryBLService = IndustryBLFactory.getIndustryBLService();
	private List<String> allIndustryName;
	public static MoreModuleUIController  getInstance() {
		 System.out.println("here is the instance of MoreModuleUIController  ");
        FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ModuleUIController .class.getResource("moreModulePanel.fxml"));
		return instance;
  }
	
	public  void init(){
		buttons =new Button[]{btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28}; 
		allIndustryName = industryBLService.getAllIndustries();
		for (int i=0;i<num;i++){
			buttons[i].setText(
					allIndustryName.get(i+15));
		}
	}

	@FXML
	private void toDetailMoulePane(){
		
	}
	@FXML
	private void toReturnPane(){
		 Main.enterModuleScene();
		 ModuleUIController.getInstance().init();
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance =this;
		init();
	}
}