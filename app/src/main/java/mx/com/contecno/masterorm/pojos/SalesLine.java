package mx.com.contecno.masterorm.pojos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dbarboza on 14/09/2016.
 */
@DatabaseTable
public class SalesLine {

    public static final String SALESORDER_ID = "salesorder_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, foreign = true)
    private Item item;

    @DatabaseField
    private String salesQty;

    @DatabaseField
    private String status;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = SALESORDER_ID)
    private SalesOrder salesOrder;

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getSalesQty() {
        return salesQty;
    }

    public void setSalesQty(String salesQty) {
        this.salesQty = salesQty;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    SalesLine(){

    }

    public SalesLine(Item item, String salesQty, String status) {
        this.item = item;
        this.salesQty = salesQty;
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append(item.getItemId());
        line.append(" - ");
        line.append(salesQty);
        line.append(" - ");
        line.append(status);
        return line.toString();
    }
}
