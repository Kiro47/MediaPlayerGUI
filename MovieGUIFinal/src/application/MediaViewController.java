package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaViewController implements Initializable{

	@FXML private MediaView mv;
	private MediaPlayer mp;
	private Media me;
	@FXML private ToolBar toolbar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = Main.loc.getAbsolutePath();
		me = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(me);
		mv.setMediaPlayer(mp);
		mp.setAutoPlay(true);
		DoubleProperty width = mv.fitWidthProperty();
		DoubleProperty height = mv.fitHeightProperty();
		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
		
		
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
	
}
