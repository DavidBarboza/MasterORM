package mx.com.contecno.masterorm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.com.contecno.masterorm.pojos.Item;

/**
 * Created by dbarboza on 15/09/2016.
 */
public class SalesLineForm extends AppCompatActivity {

    private EditText salesLineNumber, itemIdET, salesQtyET, salesUnitET;
    private Button okBtn, cnlBtn;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_line_form);
        setUpViewElements();
    }

    private void setUpViewElements(){

        //Initializing Views
        salesLineNumber = (EditText) findViewById(R.id.salesLineNumber);
        itemIdET = (EditText) findViewById(R.id.itemId);
        salesQtyET = (EditText) findViewById(R.id.salesQty);
        salesUnitET = (EditText) findViewById(R.id.salesUnit);

        salesLineNumber.setFocusable(false);
        itemIdET.setFocusable(false);
        salesUnitET.setFocusable(false);
        //Getting intent
        Intent intent = getIntent();

        //Displaying values by fetching from intent
        salesLineNumber.setText(String.valueOf(intent.getIntExtra(TwoFragment.SALES_LINE_NUMBER, 0)));


        //buttons
        okBtn = (Button) findViewById(R.id.SLFBtnOk);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("itemId",""+itemIdET.getText());
                intent.putExtra("salesQty",""+salesQtyET.getText());
                intent.putExtra("salesUnit",""+salesUnitET.getText());
                if((salesQtyET.getText().length()==0) || (Integer.parseInt(salesQtyET.getText().toString())<1)){
                    Toast.makeText(getApplicationContext(),"Falta campos por llenar",Toast.LENGTH_LONG).show();
                }
                else {
                    setResult(1, intent);
                    finish();
                }
            }
        });

        cnlBtn = (Button)findViewById(R.id.SLFBtnCancel);
        cnlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinner = (Spinner)findViewById(R.id.salesItemSpinner);
        populateSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
                try {
                    Dao<Item, String> itemDao = dbHelper.getItemDao();
                    QueryBuilder<Item, String> queryBuilder = itemDao.queryBuilder();
                    queryBuilder.where().eq("ItemName",spinner.getItemAtPosition(position));
                    PreparedQuery<Item> preparedQuery = queryBuilder.prepare();
                    List<Item> items = itemDao.query(preparedQuery);
                    itemIdET.setText(items.get(0).getItemId());
                    salesUnitET.setText(items.get(0).getSalesUnit());

                } catch (SQLException e) {
                    Toast.makeText(getApplicationContext(),"Error al buscar"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateSpinner(){

        ArrayList<String> articulos = new ArrayList<>();
        DBHelper dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        try {
            Dao<Item, String> itemDao = dbHelper.getItemDao();
            List<Item> items = itemDao.queryForAll();
            for (int i=0;i<items.size();i++){
                articulos.add(items.get(i).getItemName());
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, articulos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } catch (SQLException e) {
            Toast.makeText(this,"Error al buscar"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
