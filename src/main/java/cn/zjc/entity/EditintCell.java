package cn.zjc.entity;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

/**
 * @author zjc
 * @version 2016/7/10 0:41
 * @function
 */
public class EditintCell extends TableCell<TableKeyVal, String> {

	private TextField textField;

	public EditintCell() {
	}

	@Override
	public void startEdit() {
		super.startEdit();
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
	}

	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
	}
}
