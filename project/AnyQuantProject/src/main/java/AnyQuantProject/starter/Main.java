package AnyQuantProject.starter;

import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.bl.stockListBL.StockListBLController;
import AnyQuantProject.ui.controllerUI.MainPageController;
import AnyQuantProject.ui.singleStockInfoUI.SingleStockInfoUIController;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.eclipse.swt.widgets.Scale;

import com.hp.hpl.sparta.xpath.ParentNodeTest;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.ImageCursor;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.stage.WindowEvent;
import javafx.scene.Cursor;

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
	public static AnchorPane modulePanel,allStocksPanel,benchMarkPanel,favouritePanel,singleStockPanel,stockDealInfoPanel,singleStockInfoPanel;
//	private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//	private static double scrH =primaryScreenBounds.getHeight();
//	private static double scrW =primaryScreenBounds.getWidth();
	private static boolean move=false;
	private static Point origin = new Point();
        public static FXMLLoader fxmlLoader;
	public static SingleStockInfoUIController singleStockInfoUIController=null;
        
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
	//	mainPanel = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
		guidePanel= FXMLLoader.load(Main.class.getResource("guidePanel.fxml"));
		
		favouritePanel= FXMLLoader.load(getClass().getResource("favouritePanel.fxml"));
	//	allStocksPanel = FXMLLoader.load(getClass().getResource("allStocksPanel.fxml"));

		benchMarkPanel = FXMLLoader.load(getClass().getResource("benchMarkPanel.fxml"));
                benchMarkPanel.setId("pane");

        modulePanel = FXMLLoader.load(getClass().getResource("modulePanel.fxml"));
//		singleStockInfoPanel = (AnchorPane)FXMLLoader.load(getClass().getResource("singleStockInfoPanel.fxml"));
//		singleStockPanel = FXMLLoader.load(getClass().getResource("singleStockPanel.fxml"));
//		stockDealInfoPanel = FXMLLoader.load(getClass().getResource("stockDealInfoPanel.fxml"));


//		primaryStage.setHeight(636);
//		primaryStage.setWidth(992);
                primaryStage.setHeight(600);
                primaryStage.setWidth(980);
		primaryStage.setTitle("AnyQuant");	

		h_box =new HBox(); 
		h_box.getChildren().addAll(guidePanel,favouritePanel);    
		h_box.setHgrow(guidePanel, Priority.ALWAYS);
		h_box.setPadding(new Insets(0,0,0,0));
		h_box.setSpacing(0);

		primaryStage.setScene(new Scene(h_box));
	//	primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.isResizable();

		//界面拖拽
		primaryStage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
			   if(e.getY()<=(int)((double)primaryStage.getHeight()*22/490)){
	            	move=true;
	            	origin.x = (int) e.getX();  
	                origin.y = (int) e.getY();
	                }
			   
			}});
		
		primaryStage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
			   move =false;
			}});

		primaryStage.addEventHandler(MouseEvent.MOUSE_DRAGGED,new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {	
				if(move){
	                double x = primaryStage.getX() ;
	                double y =primaryStage.getY();
	
	                primaryStage.setX(x + e.getX() - origin.x);
	                primaryStage.setY(y + e.getY() - origin.y);
	            	}
	            }
		});
		
	//  enterMainScene();
	//  buttons();
		primaryStage.show();  
		
}  
	
//	private Scene buildScene(Node node){
//		Image image = new Image(new File("images/mouse cursor.png").toURI().toString());
//		//TODO
//		ImageCursor cursor = new ImageCursor(image, image.getWidth(),image.getHeight());
//		node.setCursor(cursor);
//		return node.getScene();
//	}
	
	
    public void enterMainScene(){
    //	Main.getPrimaryStage().setScene(new Scene(guidePanel));
    	//TODO
        this.primaryStage.setScene(new Scene(mainPanel));
//        this.primaryStage.setScene(buildScene(mainPanel));
//      MainPageController.getInstance().showAnimation();
        MainPageController.getInstance().initPanel();
    }
    
    public  static void enterAllStocksScene(){
    	try {
			allStocksPanel=FXMLLoader.load(Main.class.getResource("allStocksPanel.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    public  static void enterModuleScene(){
    	h_box =new HBox(guidePanel,modulePanel);
    	Main.getPrimaryStage().setScene(new Scene(h_box));
        MainPageController.getInstance().initPanel();
    }
    
/**
 * TO be tested
 * @param name
 */
       
       public static void endSingle() {      
              singleStockInfoUIController.endLoad();
       }
       
	public static void enterSingleStockInfoScene(String name) {
		// TODO Auto-generated method stub
                fxmlLoader=new FXMLLoader(Main.class.getResource("singleStockInfoPanel.fxml"));
            try {
                singleStockInfoPanel = (AnchorPane)fxmlLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
                singleStockInfoUIController=fxmlLoader.getController();       
	        singleStockInfoUIController.loadImage.setImage(new Image("/images/load.gif"));
                singleStockInfoUIController.laterInit(name);                 
              
                h_box =new HBox(guidePanel,singleStockInfoPanel);
	        Main.getPrimaryStage().setScene(new Scene(h_box));
                MainPageController.getInstance().initPanel();
                           
                        /**
                         * use an executor to later close the animation
                         */
//                        ScheduledExecutorService service=Executors.newScheduledThreadPool(1);
//                        ScheduledFuture future=service.schedule(new Callable() {
//                           public String call(){
//                            return "taskcancelled!";
//                           }
//                        },20,TimeUnit.SECONDS);
//                        singleStockInfoUIController.endLoad();
//                        service.shutdown();
                         
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
//		   max = ButtonBuilder.create().text("max").onAction(new EventHandler<ActionEvent>(){
//		            @Override public void handle(ActionEvent e){
//		                primaryStage.setX(primaryScreenBounds.getMinX());
//		                primaryStage.setY(primaryScreenBounds.getMinY());
//		                primaryStage.setWidth(scrW);
//		                primaryStage.setHeight(scrH);
//		            }
//		        }).build();
		   
		  // ((Group)primaryScene.getRoot()).getChildren().addAll(full,min,max,close);
		    hbox = new HBox(-2);
		    hbox.getChildren().addAll(full,min,max,close);
		    root = new Group(hbox);
		    primaryScene = SceneBuilder.create().root(root).build();
		    primaryStage.setScene(primaryScene);
	}
	
	 public static void main(String[] args) {
		 StockListBLController stockListBLController=(StockListBLController) StockListBLFactory.getStockListBLService();
		 
		 if (stockListBLController.shouldInit()) {
			Thread thread=new Thread(stockListBLController);
			thread.start();
		}
		 launch(args);
		 
	   }
}
