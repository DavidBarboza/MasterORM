package mx.com.contecno.masterorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mx.com.contecno.masterorm.pojos.Customer;
import mx.com.contecno.masterorm.pojos.Item;
import mx.com.contecno.masterorm.pojos.SalesLine;
import mx.com.contecno.masterorm.pojos.SalesOrder;

/**
 * Created by dbarboza on 30/09/2016.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ormTest.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Customer, String> customerDao;
    private Dao<Item, String> itemDao;
    private Dao<SalesLine,Integer> salesLineDao;
    private Dao<SalesOrder, String> salesOrderDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Customer.class);
            TableUtils.createTable(connectionSource, Item.class);
            TableUtils.createTableIfNotExists(connectionSource, SalesLine.class);
            TableUtils.createTableIfNotExists(connectionSource, SalesOrder.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(database,connectionSource);
    }

    public Dao<Customer, String> getCustomerDao() throws SQLException {
        if (customerDao == null){
            customerDao = getDao(Customer.class);
        }
        return customerDao;
    }

    public Dao<Item, String> getItemDao()throws SQLException {
        if (itemDao == null) {
            itemDao = getDao(Item.class);
        }
        return itemDao;
    }

    public Dao<SalesLine, Integer> getSalesLineDao()throws SQLException {
        if (salesLineDao == null) {
            salesLineDao = getDao(SalesLine.class);
        }
        return salesLineDao;
    }

    public Dao<SalesOrder, String> getSalesOrderDao()throws SQLException {
        if (salesOrderDao == null) {
            salesOrderDao = getDao(SalesOrder.class);
        }
        return salesOrderDao;
    }

    @Override
    public void close(){
        super.close();
        customerDao = null;
        itemDao = null;
        salesLineDao = null;
        salesOrderDao = null;
    }
}
