package mx.com.contecno.masterorm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import mx.com.contecno.masterorm.pojos.Customer;
import mx.com.contecno.masterorm.pojos.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BenjiGoenitz on 4/18/2016.
 */
public class ThreeFragment  extends Fragment {

    private Button btnSyncCust, btnSyncItem;
    private TextView testTV;
    private View rootView;

    //Interface to communicate between fragments
    SyncFragment mCallback;

    public interface SyncFragment{
        void repopulateSpinner();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (SyncFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SyncFragment");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null; // => avoid leaking
        super.onDetach();
    }

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_three, container, false);

        setUpViewElements();

        return rootView;
    }

    private void setUpViewElements(){
        btnSyncCust = (Button)rootView.findViewById(R.id.btnSyncCust);
        btnSyncCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncCustomers();
            }
        });

        btnSyncItem = (Button)rootView.findViewById(R.id.btnSyncItem);
        btnSyncItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncItems();
            }
        });

        testTV = (TextView)rootView.findViewById(R.id.txtTest);
    }

    private void syncCustomers() {
        final ProgressDialog loading = ProgressDialog.show(rootView.getContext(), "Sincronizando", "Espere", false, false);

        final DBHelper dbHelper = OpenHelperManager.getHelper(rootView.getContext(), DBHelper.class);
        try {
            Dao<Customer, String> custDao = dbHelper.getCustomerDao();
            for (int i=0; i<9; i++) {
                Customer cust = new Customer("Cust"+i,"Customersito "+i);
                custDao.createIfNotExists(cust);
            }
            loading.dismiss();
            mCallback.repopulateSpinner();
        } catch (SQLException e) {
            testTV.setText(e.getMessage());
        }
    }

    private void syncItems() {
        final ProgressDialog loading = ProgressDialog.show(rootView.getContext(), "Sincronizando", "Espere", false, false);

        final DBHelper dbHelper = OpenHelperManager.getHelper(rootView.getContext(), DBHelper.class);
        try {
            Dao<Item, String> custDao = dbHelper.getItemDao();
            for (int i=0; i<9; i++) {
                Item item = new Item("item"+i,"itemDesc "+i,"itemsito "+i,"pza");
                custDao.createIfNotExists(item);
            }
            loading.dismiss();
        } catch (SQLException e) {
            testTV.setText(e.getMessage());
        }
    }
}