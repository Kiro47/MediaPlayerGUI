package application;
	
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Main extends Application {

	/*
	 * PRIORITY: Make that main page prettier, and show all by default....
	 * Click on pane to pause/play
	 * back type button
	 * Scroll Wheel support
	 * Arrow Scroll Support
	 */
	static FileChooser fc = new FileChooser();
	File[] fl = fc.getInitialDirectory().listFiles();
	HashMap<Integer, String> hash = new HashMap<Integer, String>();
	TilePane tilePane;
	public static Stage window;
	public static  Scene scene,movie;
	private Button[] btns = new Button[fl.length];
	public static File loc;
	   
	public static void main(String[] args) {
		fc.setInitialDirectory(new File("/Volumes/WD_Elements_Movies/Movies"));
		launch(args);
	}
	
	@Override
	public void start(Stage pS) {
		window = pS;
		initHash();
		initBtnsArray();
		
		
		Group root = new Group();
		ScrollBar sc = new ScrollBar();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double swidth = (screenSize.getWidth() *0.9);
		double sheight = (screenSize.getHeight() *0.9);
	 scene = new Scene(root, swidth, sheight);
		scene.getStylesheets().add("/application/main.css");
		
		scene.setFill(Color.rgb(132, 27, 45));
		window.setScene(scene);
		window.setTitle("Breena's Movies");
		root.getChildren().addAll(getGrid(),sc);
		
		DoubleProperty width = tilePane.prefWidthProperty();
		DoubleProperty height = tilePane.prefHeightProperty();
		width.bind(Bindings.selectDouble(tilePane.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(tilePane.sceneProperty(), "height"));
		
		tilePane.setLayoutX(3);
		tilePane.setAlignment(Pos.CENTER);

		
		sc.setLayoutX(scene.getWidth() - sc.getWidth());
		sc.setMin(0.0);
		sc.setMax(1500);
		sc.setOrientation(Orientation.VERTICAL);
		sc.setPrefHeight(scene.getHeight());
		
		sc.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				tilePane.setLayoutY(-new_val.doubleValue());
			}
		});
		System.out.println(fl.length);
		window.show();
	}
	
    public Pane getGrid() {
        int i = 0;
       tilePane = new TilePane();
       for (Button b: btns) {
    	   tilePane.getChildren().add(i, b);
    	   i++;
       }
       return tilePane;
    }
    
    public void initBtnsArray() {
	        for(int i = 0; i < btns.length; i++) {
	            btns[i] = new Button(hash.get(i));
	            btns[i].setAlignment(Pos.CENTER);
	            btns[i].setOnAction(e -> {

	        		if (e.getSource() instanceof Button) {
	        			Button b = (Button) e.getSource();
	        			String btnText = (String) b.getText();
	        			System.out.println(btnText);
	        			 loc = new File(fc.getInitialDirectory().getAbsolutePath() + "//" + btnText +".mp4");
						try {
							Parent root = FXMLLoader.load(getClass().getResource("/application/MediaView.fxml"));
		        			movie = new Scene(root);
		        			window.setScene(movie);
		        			window.show();
		        			window.setFullScreen(true);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
	        			
	        			
	        		}
	        	
	            });
	        //    btns[i].setTextFill(Color.WHITE);
	        //    btns[i].setBackground(new Background(new BackgroundFill(Color.rgb(0, 35, 102), new CornerRadii(5), new Insets(5, 3, 5, 3))));
	            btns[i].setPadding(new Insets(5, 3, 5, 3));
	            
	        }
	    }
    public void initHash() {
    	int i = 0;
    	for (File f: fl) {
    		hash.put(i, f.getName().replace(".mp4", ""));
    		System.out.println(f.getName());
    		i++;
    	}
    }
}
