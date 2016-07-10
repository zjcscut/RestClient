package cn.zjc.utils;

import cn.zjc.entity.TableKeyVal;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zjc
 * @version 2016/7/10 1:56
 * @function
 */
public class TableKVUtil {

	public static Map<String, String> obListToMap(ObservableList<TableKeyVal> data) {
		if (null == data || data.isEmpty()) {
			return null;
		}
		Map<String, String> re = new HashMap<>();
		if (data.size() > 0) {
			for (TableKeyVal k : data) {
				re.put(k.getKey(), k.getVal());
			}
		}
		return re;
	}
}
