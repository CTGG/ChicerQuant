package AnyQuantProject.ui.controllerUI;

import AnyQuantProject.ui.allStocksUI.AllStocksUIController;

import AnyQuantProject.ui.singleStockInfoUI.SingleStockInfoUIController;

import AnyQuantProject.ui.singleStockInfoUI.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * 
 * @author QiHan
 *
 */

public class Main extends Application {
	private static Main instance; 
	private static Stage primaryStage;   //舞台
	private static Scene primaryScene;
	private static Pane head ; //
	private static Button close;
	private static Button min;
	private static Button max;
	private static Button full; //全屏
	private static Group root;
	private static HBox h_box,hbox;	
	static AnchorPane mainPanel,guidePanel,writePanel;
	static AnchorPane allStocksPanel,benchMarkPanel,favouritePanel,
				singleStockInfoPanel,singleStockPanel,stockDealInfoPanel;
	
	private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	private static double scrH =primaryScreenBounds.getHeight();
	private static double scrW =primaryScreenBounds.getWidth();
	
	public static Main getInstance(){
	        return instance;
	    }
	    
	public static Scene getPrimaryScene() {
		// TODO Auto-generated method stub
		return primaryScene;
	}
    
        public static Stage getPrimaryStage() {
	// TODO Auto-generated method stub
	return primaryStage;
        }

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		instance=this;
		this.primaryStage = primaryStage;
		mainPanel = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
		guidePanel= FXMLLoader.load(getClass().getResource("guidePanel.fxml"));
		
		favouritePanel= FXMLLoader.load(getClass().getResource("favouritePanel.fxml"));
		allStocksPanel = FXMLLoader.load(getClass().getResource("allStocksPanel.fxml"));
		benchMarkPanel = FXMLLoader.load(getClass().getResource("benchMarkPanel.fxml"));

//		singleStockInfoPanel = (AnchorPane)FXMLLoader.load(getClass().getResource("singleStockInfoPanel.fxml"));
//		singleStockPanel = FXMLLoader.load(getClass().getResource("singleStockPanel.fxml"));
//		stockDealInfoPanel = FXMLLoader.load(getClass().getResource("stockDealInfoPanel.fxml"));

		primaryStage.setHeight(600);
		primaryStage.setWidth(950);
		primaryStage.setTitle("AnyQuant");	

		h_box =new HBox();          
//		h_box.getChildren().addAll(guidePanel,favouritePanel);
                h_box.getChildren().addAll(guidePanel,allStocksPanel);
		h_box.setHgrow(guidePanel, Priority.ALWAYS);
		h_box.setPadding(new Insets(0,0,0,0));
		h_box.setSpacing(0);

		primaryStage.setScene(new Scene(h_box));

                primaryStage.initStyle(StageStyle.DECORATED);
//		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.isResizable();
	//  enterMainScene();
	//  buttons();
		primaryStage.show();  
		
}  
	
	
    public void enterMainScene(){
    //	Main.getPrimaryStage().setScene(new Scene(guidePanel));
        this.primaryStage.setScene(new Scene(mainPanel));
    //  MainPageController.getInstance().showAnimation();
        MainPageController.getInstance().initPanel();
    }
    
    public  static void enterAllStocksScene(){
		h_box =new HBox(guidePanel,allStocksPanel);
	    Main.getPrimaryStage().setScene(new Scene(h_box));
        MainPageController.getInstance().initPanel();
    }
    
    public  static void enterFavouriteScene(){
    	h_box =new HBox(guidePanel,favouritePanel);
    	Main.getPrimaryStage().setScene(new Scene(h_box));
        MainPageController.getInstance().initPanel();
    }
    
    public  static void enterBenchMarkScene(){
    	h_box =new HBox(guidePanel,benchMarkPanel);
    	Main.getPrimaryStage().setScene(new Scene(h_box));
        MainPageController.getInstance().initPanel();
    }
    
    

	public void buttons(){
		  close = ButtonBuilder.create().text("close").onAction(new EventHandler<ActionEvent>(){
		        @Override public void handle(ActionEvent e){
		          Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST ));
		        }
		      }).build();
		  min = ButtonBuilder.create().text("min").onAction(new EventHandler<ActionEvent>(){
		            @Override public void handle(ActionEvent e){
		            	primaryStage.setIconified(true);
		            }
		        }).build();
		   full = ButtonBuilder.create().text("full").onAction(new EventHandler<ActionEvent>(){
		            @Override public void handle(ActionEvent e){
		            	primaryStage.setFullScreen(true);
		            }
		      }).build();
		   max = ButtonBuilder.create().text("max").onAction(new EventHandler<ActionEvent>(){
		            @Override public void handle(ActionEvent e){
		                primaryStage.setX(primaryScreenBounds.getMinX());
		                primaryStage.setY(primaryScreenBounds.getMinY());
		                primaryStage.setWidth(scrW);
		                primaryStage.setHeight(scrH);
		            }
		        }).build();
		   
		  // ((Group)primaryScene.getRoot()).getChildren().addAll(full,min,max,close);
		    hbox = new HBox(-2);
		    hbox.getChildren().addAll(full,min,max,close);
		    root = new Group(hbox);
		    primaryScene = SceneBuilder.create().root(root).build();
		    primaryStage.setScene(primaryScene);
	}
	
	 public static void main(String[] args) {
		 launch(args);
	   }
  
	
}
