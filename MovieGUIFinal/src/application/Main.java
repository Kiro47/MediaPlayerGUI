package application;
	
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
	TilePane vbox;
	public static Stage window;
	public  Scene scene,movie;
	private Button[] btns = new Button[fl.length];
	public static File loc;
	   
	public static void main(String[] args) {
		fc.setInitialDirectory(new File("F:\\Movies"));
		launch(args);
	}
	
	@Override
	public void start(Stage pS) {
		window = pS;
		initHash();
		initBtnsArray();
		
		
		Group root = new Group();
		ScrollBar sc = new ScrollBar();
	 scene = new Scene(root, 800, 600);
		scene.getStylesheets().add("/application/main.css");
		
		scene.setFill(Color.MISTYROSE);
		window.setScene(scene);
		window.setTitle("Movie GUI");
		root.getChildren().addAll(getGrid(),sc);
		
		DoubleProperty width = vbox.prefWidthProperty();
		DoubleProperty height = vbox.prefHeightProperty();
		width.bind(Bindings.selectDouble(vbox.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(vbox.sceneProperty(), "height"));
		
		vbox.setLayoutX(5);
		vbox.setAlignment(Pos.CENTER);
		
		sc.setLayoutX(scene.getWidth() - sc.getWidth());
		sc.setMin(0.0);
		sc.setOrientation(Orientation.VERTICAL);
		sc.setMaxHeight(scene.getHeight());
		
		
		sc.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				vbox.setLayoutY(-new_val.doubleValue());
			}
		});
		System.out.println(fl.length);
		window.show();
	}
	
    public Pane getGrid() {
        int i = 0;
       vbox = new TilePane();
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
	            btns[i].setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(0.5), new Insets(5))));
	            btns[i].setPadding(new Insets(2));
	            
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
