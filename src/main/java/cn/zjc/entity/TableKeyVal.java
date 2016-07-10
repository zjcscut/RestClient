package cn.zjc.entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author zjc
 * @version 2016/7/8 23:58
 * @function
 */
public class TableKeyVal {

	private  final SimpleBooleanProperty choose;

	private final SimpleStringProperty key;

	private final SimpleStringProperty val;


	public TableKeyVal(Boolean choose, String key, String val) {
		this.choose = new SimpleBooleanProperty(choose);
		this.key = new SimpleStringProperty(key);
		this.val = new SimpleStringProperty(val);

	}


	public boolean getChoose() {
		return choose.get();
	}


	public SimpleBooleanProperty chooseProperty() {
		return choose;
	}

	public void setChoose(boolean choose) {
		this.choose.set(choose);
	}

	public String getKey() {
		return key.get();
	}

	public SimpleStringProperty keyProperty() {
		return key;
	}

	public void setKey(String key) {
		this.key.set(key);
	}

	public String getVal() {
		return val.get();
	}

	public SimpleStringProperty valProperty() {
		return val;
	}

	public void setVal(String val) {
		this.val.set(val);
	}

	@Override
	public String toString() {
		return "TableKeyVal{" +
				"choose=" + choose.get() +
				", key=" + key.get() +
				", val=" + val.get() +
				'}';
	}
}
