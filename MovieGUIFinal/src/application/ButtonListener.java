package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;

public class ButtonListener implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if (e.getSource() instanceof Button) {
			Button b = (Button) e.getSource();
		//	b.setEffect();
			String btnText = (String) b.getText();
			System.out.println(btnText);
		}
	}

}
