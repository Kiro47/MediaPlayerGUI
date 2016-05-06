package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MediaViewController implements Initializable{

	@FXML private BorderPane borderPane;
	@FXML private MediaView mv;
	private MediaPlayer mp;
	private Media me;
	@FXML private ToolBar toolbar;
	@FXML private ToggleButton toggleButton;
	@FXML private ToggleButton fullscreen;
	@FXML private Slider volumeSlider;
	Stage window = Main.window;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = Main.loc.getAbsolutePath();
		me = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(me);
		mv.setMediaPlayer(mp);
		mp.setAutoPlay(true);
		final Image play = new Image("/media/button_pause_icon.jpg");
		final Image pause = new Image("/media/play-button.jpg");
		final ImageView toggleImage = new ImageView();
		toggleButton.setGraphic(toggleImage);
		toggleImage.imageProperty().bind(Bindings.when(toggleButton.selectedProperty()).then(pause).otherwise(play));
		final Image fulls = new Image("/media/full-screen.jpg");
		final ImageView togglefull =  new ImageView();
		fullscreen.setGraphic(togglefull);
		togglefull.imageProperty().bind(Bindings.when(fullscreen.selectedProperty()).then(fulls).otherwise(fulls));
		DoubleProperty width = mv.fitWidthProperty();
		DoubleProperty height = mv.fitHeightProperty();
		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));	
		volumeSlider.setValue(mp.getVolume() *100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				mp.setVolume(volumeSlider.getValue()/100);
			}
		});
		borderPane.setOnMouseEntered(new EventHandler() {
			@Override
			public void handle(Event e) {
				toolbar.setVisible(true);
				return;
			}
		});
		borderPane.setOnMouseExited(new EventHandler() {
			@Override
			public void handle(Event e) {
				toolbar.setVisible(false);
				return;
			}
		});
		borderPane.setOnMouseMoved(new EventHandler() {
			@Override
			public void handle(Event e) {
				toolbar.setVisible(true);
				return;
			}
		});
		mp.setOnEndOfMedia(new Runnable() {
			
			@Override
			public void run() {
				mp.stop();
		// change play button graphic? 
				toggleButton.setSelected(true);
				return;
			}
		});
	}
	public void mainAction(ActionEvent e) {
		Status s = mp.getStatus();
		if ((s.equals(Status.PLAYING))) {
			mp.pause();
			return;
		}
		if ((s.equals(Status.PAUSED)) || (s.equals(Status.STALLED))|| (s.equals(Status.READY)) || (s.equals(Status.UNKNOWN))) {
			mp.play();
			return;
		}
		else if (s.equals(Status.STOPPED)) {
			mp.seek(Duration.ZERO);
			mp.play();
			return;
		}
	}
	
	public void pauseAndStart(ActionEvent e) {
		Status s = mp.getStatus();
		if (s.equals(Status.PAUSED)) {
			mp.play();
			return;
		}
		if (s.equals(Status.PLAYING)){
			mp.pause();
			return;
		}
	}
	
	public void fullscreen (ActionEvent e) {
		if (window.isFullScreen() == true) {
			window.setFullScreen(false);
			toolbar.setVisible(true);
			return;
		}
		else if (window.isFullScreen() == false) {
			window.setFullScreen(true);
			toolbar.setVisible(false);
		//	borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0.5), new Insets(0.1))));
			return;
		}
	}
	
}
