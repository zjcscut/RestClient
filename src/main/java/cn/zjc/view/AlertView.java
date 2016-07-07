package cn.zjc.view;

import javafx.scene.control.Alert;

/**
 * @author zjc
 * @version 2016/7/7 22:38
 * @function
 */
public class AlertView extends Alert {

	public AlertView() {
		super(AlertType.ERROR);
	}

	public void showError(String content) {
		this.setTitle("Error");
		this.setHeaderText("咩事甘悲惨竟然Error");
		this.setContentText(content);
		this.setHeight(200);
		this.setWidth(300);
		this.showAndWait();
	}
}
