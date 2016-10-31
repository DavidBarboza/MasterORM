package mx.com.contecno.masterorm.pojos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dbarboza on 30/09/2016.
 */
@DatabaseTable
public class Customer {

    @DatabaseField(id = true)
    private String AccountNum;

    @DatabaseField
    private String Name;

    public Customer(){

    }

    public Customer(String accountNum, String name) {
        this.AccountNum = accountNum;
        this.Name = name;
    }

    public Customer(String accountNum) {
        AccountNum = accountNum;
    }

    public String getAccountNum() {
        return AccountNum;
    }

    public void setAccountNum(String accountNum) {
        this.AccountNum = accountNum;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    @Override
    public String toString(){

        String str= AccountNum+" "+Name;

        return str;
    }
}
