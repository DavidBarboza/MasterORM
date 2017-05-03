package mx.com.contecno.masterorm.Model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Created by dbarboza on 28/10/2016.
 */
@DatabaseTable(tableName = "SalesOrder")
public class SalesOrder {

    @DatabaseField(generatedId = true)
    private int salesOrderId;

    @DatabaseField(canBeNull = false, foreign = true)
    private Customer customer;

    @DatabaseField
    private String purchOrderForNum;

    @ForeignCollectionField
    private ForeignCollection<SalesLine> salesLines;

    public SalesOrder(Customer customer, String purchOrderForNum) {
        this.customer = customer;
        this.purchOrderForNum = purchOrderForNum;
    }

    public SalesOrder() {
    }

    public Customer getCustomer() {
        return customer;
    }

}
