package mx.com.contecno.masterorm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.com.contecno.masterorm.pojos.Customer;
import mx.com.contecno.masterorm.pojos.Item;
import mx.com.contecno.masterorm.pojos.SalesLine;
import mx.com.contecno.masterorm.pojos.SalesOrder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BenjiGoenitz on 4/18/2016.
 */
public class TwoFragment  extends Fragment {

    private ArrayList<SalesLine> salesLines = new ArrayList<>();
    private ArrayAdapter<SalesLine>  adapterForList;
    private ListView salesLineList;
    private View rootView;
    private Spinner spinner;
    private EditText clientOrderET, clientIdET;

    public static final String SALES_LINE_NUMBER = "sales_line_number";

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_two, container, false);

        setUpViewElements();
        // Inflate the layout for this fragment
        return rootView;
    }

    public void setUpViewElements(){
        //ListView
        salesLineList = (ListView) rootView.findViewById(R.id.listView);
        adapterForList = new ArrayAdapter<>(getActivity(), R.layout.simple_list, salesLines);
        salesLineList.setAdapter(adapterForList);

        //EditText
        clientOrderET = (EditText) rootView.findViewById(R.id.clientOrderEditText);
        clientIdET = (EditText) rootView.findViewById(R.id.clientIdEditText);

        //Spiner
        spinner = (Spinner) rootView.findViewById(R.id.clientSpinner);
        populateSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbHelper = OpenHelperManager.getHelper(rootView.getContext(), DBHelper.class);
                try {
                    Dao<Customer, String> custDao = dbHelper.getCustomerDao();
                    QueryBuilder<Customer, String> queryBuilder = custDao.queryBuilder();
                    queryBuilder.where().eq("Name",spinner.getItemAtPosition(position));
                    PreparedQuery<Customer> preparedQuery = queryBuilder.prepare();
                    List<Customer> customers = custDao.query(preparedQuery);
                    clientIdET.setText(customers.get(0).getAccountNum());
                } catch (SQLException e) {
                    Toast.makeText(rootView.getContext(),"Error al buscar"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // end Spiner

        final FloatingActionsMenu menuFAB = (FloatingActionsMenu) rootView.findViewById(R.id.multiple_actions);
        rootView.findViewById(R.id.fab_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFAB.collapse();
                saveSalesOrder();
                Toast.makeText(rootView.getContext(),"Orden de venta ha sido guardada",Toast.LENGTH_LONG).show();
            }
        });

        rootView.findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFAB.collapse();
                Intent intent = new Intent(getActivity(), SalesLineForm.class);
                intent.putExtra(SALES_LINE_NUMBER, salesLines.size()+1);
                startActivityForResult(intent, 1);
               /* Snackbar.make(v, "Línea de venta creada", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();*/
            }
        });

      //  setUpFonts();
        clientIdET.setFocusable(false);
    }

    private void saveSalesOrder(){
        Customer customer = new Customer(clientIdET.getText().toString());
        SalesOrder salesOrder = new SalesOrder(customer,clientOrderET.getText().toString());

        final DBHelper dbHelper = OpenHelperManager.getHelper(rootView.getContext(), DBHelper.class);
        try {
            Dao<SalesOrder, String> salesOrderDao = dbHelper.getSalesOrderDao();
            Dao<SalesLine, Integer> salesLineDao = dbHelper.getSalesLineDao();

            salesOrderDao.create(salesOrder);

            for (int i=0; i<salesLines.size(); i++) {
                SalesLine sl = salesLines.get(i);
                sl.setSalesOrder(salesOrder);
                salesLineDao.create(sl);
            }


        }
        catch (SQLException e){
            Toast.makeText(rootView.getContext(), "ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();
            Log.e("ERROR", e.getMessage());
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == 1){
                Item item = new Item(data.getStringExtra("itemId"));
                SalesLine sl = new SalesLine(item, data.getStringExtra("salesQty"),"OK");
                addSalesLine(sl);
            }
        }
    }

    private void addSalesLine(SalesLine salesLine){
        salesLines.add(salesLine);
        adapterForList.notifyDataSetChanged();
    }

    //llama la interface implementada del fragmet three
    public void populateSpinner(){
        ArrayList<String> clientes = new ArrayList<String>();
        DBHelper dbHelper = OpenHelperManager.getHelper(rootView.getContext(), DBHelper.class);
        try {
            Dao<Customer, String> custDao = dbHelper.getCustomerDao();
            List<Customer> customers = custDao.queryForAll();
            for (int i=0;i<customers.size();i++){
                clientes.add(customers.get(i).getName());
            }
            ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_spinner_item, clientes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } catch (SQLException e) {
            Toast.makeText(rootView.getContext(),"Error al buscar"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}