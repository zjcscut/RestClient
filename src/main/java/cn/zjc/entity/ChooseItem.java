package cn.zjc.entity;

/**
 * @author  zjc
 * @version 2016/7/8 23:44
 * @description 
 */
public class ChooseItem {
    
    private final int id;
    private final String name;

    public ChooseItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getKey() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
