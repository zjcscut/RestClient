package cn.zjc.controller;

import cn.zjc.mvc.Controller;

import cn.zjc.utils.HttpUtil;
import cn.zjc.utils.JsonFormatTrans;
import cn.zjc.utils.JsonUtil;
import cn.zjc.utils.Validation;
import cn.zjc.view.AlertView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author zhangjinci
 * @version 2016/7/7 19:37
 */
public class RestController extends Controller {

	private static final Logger log = LogManager.getLogger(RestController.class);

	private String methodVal = "";
	private String result = "";
	private static final String[] methods = {"POST", "GET", "PUT"};
	@FXML
	private TextField url;
	@FXML
	private Button send;
	@FXML
	private Button json;
	@FXML
	private ChoiceBox<String> method;

	@FXML
	private TextArea response;

	@FXML
	private Button clear;

	public RestController() {

		log.debug("加载rest.fxml，初始化RestController");
		registerFXML("gui/rest.fxml");

		//添加初始值
		for (String s : methods) {
			method.getItems().add(s);
		}

		send.setOnAction(this::sendAction);
		clear.setOnAction(this::clearAction);
		json.setOnAction(this::jsonFormatAction);
	}

	private void sendAction(ActionEvent e) {

		methodVal = method.getValue();
		if (null == methodVal || methodVal.length() == 0) {
			new AlertView().showError("妳忘记选择请求方法了 (。・`ω´・) ");
		}

		if (null == url.getText() || url.getText().length() == 0) {
			new AlertView().showError("妳忘记填写URL了 (。・`ω´・)");
		}

		switch (methodVal) {
			case "GET":
				result = HttpUtil.get(url.getText());
				break;
			default: {
				new AlertView().showError("不合法的请求方法 (。・`ω´・)");
			}
		}
		response.setText(result);
	}

	private void jsonFormatAction(ActionEvent event) {
		if (null != result && result.length() > 0) {
			response.setText(JsonFormatTrans.format(result));
		}
	}

	private void clearAction(ActionEvent event) {
		result = "";
		response.clear();
	}

}
