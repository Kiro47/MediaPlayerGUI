package application;
	
import java.io.File;
import java.util.HashMap;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Main extends Application {

	/*
	 * Center Buttons
	 * Background Button Color
	 * Scroll Wheel support
	 * Arrow Scroll Support
	 * ALL Movies need to be in it.
	 */
	static FileChooser fc = new FileChooser();
	File[] fl = fc.getInitialDirectory().listFiles();
	HashMap<Integer, String> hash = new HashMap<Integer, String>();
	VBox vbox;
	
	   private Button[] btns = new Button[fl.length];
	   
	public static void main(String[] args) {
		fc.setInitialDirectory(new File("F:\\Movies"));
		launch(args);
	}
	
	@Override
	public void start(Stage pS) {
		initHash();
		initBtnsArray();
		
		
		Group root = new Group();
		ScrollBar sc = new ScrollBar();
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add("main.css");
		scene.setFill(Color.MISTYROSE);
		pS.setScene(scene);
		pS.setTitle("Movie GUI");
		root.getChildren().addAll(getGrid(),sc);
		
		DoubleProperty width = vbox.prefWidthProperty();
		DoubleProperty height = vbox.prefHeightProperty();
		width.bind(Bindings.selectDouble(vbox.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(vbox.sceneProperty(), "height"));
		
		vbox.setLayoutX(5);
		vbox.setSpacing(2);
		vbox.setAlignment(Pos.CENTER);
		
		sc.setLayoutX(scene.getWidth() - sc.getWidth());
		sc.setMin(0.0);
		sc.setOrientation(Orientation.VERTICAL);
		
		
		sc.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				vbox.setLayoutY(-new_val.doubleValue());
			}
		});
		System.out.println(fl.length);
		pS.show();
	}
	
    public Pane getGrid() {
        int i = 0;
       vbox = new VBox();
       vbox.applyCss();
       
       for (Button b: btns) {
    	   vbox.getChildren().add(i, b);
    	   i++;
       }
       return vbox;
    }
    
    public void initBtnsArray() {
	        for(int i = 0; i < btns.length; i++) {
	            btns[i] = new Button(hash.get(i));
	            btns[i].setAlignment(Pos.CENTER);
	            btns[i].setOnAction(new ButtonListener());
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
