package cn.zjc.controller;

import cn.zjc.entity.TableKeyVal;
import cn.zjc.mvc.Controller;

import cn.zjc.utils.HttpUtil;
import cn.zjc.utils.JsonFormatTrans;
import cn.zjc.utils.SysConstant;
import cn.zjc.utils.TableKVUtil;
import cn.zjc.view.AlertView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.TextFieldTableCell;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Predicate;

/**
 * @author zhangjinci
 * @version 2016/7/7 19:37
 */
public class RestController extends Controller {

	private static final Logger log = LogManager.getLogger(RestController.class);

	private String methodVal = "";  //记录ChoiceBox选定值
	private String result = "";     //记录请求结果

	private static final String[] methods = {"POST", "GET"}; //方法ChoiceBox初始值

	//设置默认请求头和请求头表格参数数据列表
	private final ObservableList<TableKeyVal> headerData = FXCollections.observableArrayList(
			new TableKeyVal(false, SysConstant.DEFAUT_HEADER_KEY, SysConstant.DEFAUT_HEADER_VALUE)
	);


	@FXML
	private TextField url;  //url文本框
	@FXML
	private Button send;  //send按钮
	@FXML
	private Button json;  //jsonFormat按钮
	@FXML
	private ChoiceBox<String> method;  //请求方法下拉框

	@FXML
	private TextArea response;  //请求结果文本框

	@FXML
	private Button clear;  //clear按钮

	@FXML
	private TableView<TableKeyVal> headersTable;  //请求头列表对象

	@FXML
	private TextField addHeaderKey; //增加请求头key文本框

	@FXML
	private TextField addHeaderVal; //增加请求头value文本框

	@FXML
	private Button addHeader;  //增加一行header
	@FXML
	private Button delHeader;  //删除多行header


	//设置请求参数对象数据列表
	private final ObservableList<TableKeyVal> paramData = FXCollections.observableArrayList();

	@FXML
	private TableView<TableKeyVal> paramsTable;  //请求参数列表

	@FXML
	private TextField addParamKey; //增加请求参数key文本框

	@FXML
	private TextField addParamVal; //增加请求参数value文本框

	@FXML
	private Button addParam;  //增加一行Param
	@FXML
	private Button delParam;  //删除多行Param


	public RestController() {

		log.debug("加载rest.fxml，初始化RestController");
		registerFXML("gui/rest.fxml");
		initChoiceBox();
		initHeaderTable();
		initParamTable();

		send.setOnAction(this::sendAction);
		clear.setOnAction(this::clearAction);
		json.setOnAction(this::jsonFormatAction);
		addHeader.setOnAction(this::addHeaderColAction);
		delHeader.setOnAction(this::delHeaderColAction);
		addParam.setOnAction(this::addParamColAction);
		delParam.setOnAction(this::delParamColAction);
	}

	/**
	 * HeaderTable设置
	 */
	@SuppressWarnings("unchecked")
	private void initHeaderTable() {
		log.debug("初始化HeadersTable");
		TableColumn checkBoxColumn = new TableColumn("勾选");   //选中框
		checkBoxColumn.setMinWidth(24);
		TableColumn<TableKeyVal, String> keyCol = new TableColumn<>("key");
		keyCol.setMinWidth(158);

		TableColumn<TableKeyVal, String> valCol = new TableColumn<>("value");
		valCol.setMinWidth(187);

		checkBoxColumn.setCellValueFactory(new PropertyValueFactory<TableKeyVal, Boolean>("choose"));
		checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));

		keyCol.setCellValueFactory(new PropertyValueFactory<>("key"));
		keyCol.setCellFactory(TextFieldTableCell.forTableColumn());
		//设置为可编辑
		keyCol.setOnEditCommit(
				(TableColumn.CellEditEvent<TableKeyVal, String> t) -> {
					(t.getTableView().getItems().get(
							t.getTablePosition().getRow())
					).setKey(t.getNewValue());
				});

		valCol.setCellValueFactory(new PropertyValueFactory<>("val"));
		valCol.setCellFactory(TextFieldTableCell.forTableColumn());
		//设置为可编辑
		valCol.setOnEditCommit(
				(TableColumn.CellEditEvent<TableKeyVal, String> t) -> {
					(t.getTableView().getItems().get(
							t.getTablePosition().getRow())
					).setVal(t.getNewValue());
				});


		headersTable.setEditable(true);  //设置表格可编辑
		headersTable.getColumns().addAll(checkBoxColumn, keyCol, valCol);
		headersTable.setItems(headerData);

	}

	/**
	 * ParamTable设置
	 */
	@SuppressWarnings("unchecked")
	private void initParamTable() {
		log.debug("初始化ParamsTable");
		TableColumn checkBoxColumn = new TableColumn("勾选");   //选中框
		checkBoxColumn.setMinWidth(32);
		TableColumn<TableKeyVal, String> keyCol = new TableColumn<>("key");
		keyCol.setMinWidth(185);

		TableColumn<TableKeyVal, String> valCol = new TableColumn<>("value");
		valCol.setMinWidth(246);

		checkBoxColumn.setCellValueFactory(new PropertyValueFactory<TableKeyVal, Boolean>("choose"));
		checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));

		keyCol.setCellValueFactory(new PropertyValueFactory<>("key"));
		keyCol.setCellFactory(TextFieldTableCell.forTableColumn());
		//设置为可编辑
		keyCol.setOnEditCommit(
				(TableColumn.CellEditEvent<TableKeyVal, String> t) -> {
					(t.getTableView().getItems().get(
							t.getTablePosition().getRow())
					).setKey(t.getNewValue());
				});

		valCol.setCellValueFactory(new PropertyValueFactory<>("val"));
		valCol.setCellFactory(TextFieldTableCell.forTableColumn());
		//设置为可编辑
		valCol.setOnEditCommit(
				(TableColumn.CellEditEvent<TableKeyVal, String> t) -> {
					(t.getTableView().getItems().get(
							t.getTablePosition().getRow())
					).setVal(t.getNewValue());
				});


		paramsTable.setEditable(true);  //设置表格可编辑
		paramsTable.getColumns().addAll(checkBoxColumn, keyCol, valCol);
		paramsTable.setItems(paramData);

	}


	//初始化请求方法下拉框
	private void initChoiceBox() {
		log.debug("添加请求方法ChoiceBox初始值");
		for (String s : methods) {
			method.getItems().add(s);
		}
	}

	//发送请求
	private void sendAction(ActionEvent e) {
		log.debug("执行sendAction方法");
		if (StringUtils.isEmpty(method.getValue())) {
			new AlertView().showError("妳忘记选择请求方法了 (。・`ω´・) ");
			return;
		}

		methodVal = method.getValue().trim();

		if (StringUtils.isEmpty(url.getText())) {
			new AlertView().showError("妳忘记填写URL了 (。・`ω´・)");
			return;
		} else if (!url.getText().startsWith("http://")) {
			new AlertView().showError("妳的URL格式错了,请以 \"http://\"开头 (。・`ω´・)");
			return;
		}

		switch (methodVal) {
			case "GET":
				result = HttpUtil.get(url.getText().trim(), TableKVUtil.obListToMap(headerData), TableKVUtil.obListToMap(paramData));
				break;
			case "POST":
				result = HttpUtil.post(url.getText().trim(), TableKVUtil.obListToMap(headerData), TableKVUtil.obListToMap(paramData));
				break;
			default: {
				new AlertView().showError("不合法的请求方法 (。・`ω´・)");
				return;
			}
		}
		response.setText(result);
	}

	//json格式化
	private void jsonFormatAction(ActionEvent event) {
		log.debug("执行jsonFormatAction方法");
		if (null != result && result.length() > 0) {
			response.setText(JsonFormatTrans.format(result));
		}
	}

	//清除请求结果
	private void clearAction(ActionEvent event) {
		log.debug("执行clearAction方法");
		result = "";
		response.clear();
	}

	//删除多行头请求头
	private void delHeaderColAction(ActionEvent event) {
		//ObservableList中有个移除方法可以添加filter
		log.debug("执行delHeaderColAction方法");
		headerData.removeIf(new Predicate<TableKeyVal>() {
			@Override
			public boolean test(TableKeyVal tableKeyVal) {
				return tableKeyVal.getChoose();
			}
		});
	}

	//增加一行头请求头
	private void addHeaderColAction(ActionEvent event) {
		log.debug("执行addHeaderColAction方法");
		if (StringUtils.isEmpty(addHeaderKey.getText())) {
			new AlertView().showError("请求头的Key值不能为空 (。・`ω´・)");
		} else {
			headerData.add(new TableKeyVal(false, addHeaderKey.getText().trim(), addHeaderVal.getText().trim()));
			addHeaderKey.setText("");
			addHeaderVal.setText("");
		}
	}

	//删除多行请求参数
	private void delParamColAction(ActionEvent event) {
		//ObservableList中有个移除方法可以添加filter
		log.debug("执行delParamColAction方法");
		paramData.removeIf(new Predicate<TableKeyVal>() {
			@Override
			public boolean test(TableKeyVal tableKeyVal) {
				return tableKeyVal.getChoose();
			}
		});
	}

	//增加一行请求参数
	private void addParamColAction(ActionEvent event) {
		log.debug("执行addParamColAction方法");
		if (StringUtils.isEmpty(addParamKey.getText())) {
			new AlertView().showError("请求参数的Key值不能为空 (。・`ω´・)");
		} else {
			paramData.add(new TableKeyVal(false, addParamKey.getText().trim(), addParamVal.getText().trim()));
			addParamKey.setText("");
			addParamVal.setText("");
		}
	}

}
