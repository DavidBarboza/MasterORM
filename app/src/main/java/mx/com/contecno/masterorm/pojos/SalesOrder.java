package mx.com.contecno.masterorm.pojos;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by dbarboza on 28/10/2016.
 */
@DatabaseTable
public class SalesOrder {

    @DatabaseField(generatedId = true)
    private int salesId;

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
