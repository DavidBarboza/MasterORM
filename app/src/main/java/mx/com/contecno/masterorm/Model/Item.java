package mx.com.contecno.masterorm.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dbarboza on 14/10/2016.
 */
@DatabaseTable(tableName = "Item")
public class Item {

    @DatabaseField(id = true)
    private String ItemId;

    @DatabaseField
    private String ItemDesc;

    @DatabaseField
    private String ItemName;

    @DatabaseField
    private String SalesUnit;

    public Item() {
    }

    public Item(String itemId){
        ItemId = itemId;
    }

    public Item(String itemId, String itemDesc, String itemName, String salesUnit) {
        ItemId = itemId;
        ItemDesc = itemDesc;
        ItemName = itemName;
        SalesUnit = salesUnit;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getSalesUnit() {
        return SalesUnit;
    }

    public void setSalesUnit(String salesUnit) {
        SalesUnit = salesUnit;
    }

    @Override
    public String toString(){
        return ItemId+" "+ItemName+" "+ItemDesc+" "+SalesUnit;
    }
}
