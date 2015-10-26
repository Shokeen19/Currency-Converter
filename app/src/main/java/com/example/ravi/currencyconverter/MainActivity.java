package com.example.ravi.currencyconverter;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<CharSequence> spinnerAdaptor;
    private ArrayAdapter<String> listViewAdaptor;
    private ArrayList<String> listValues;
    private TypedArray currencyValues, currencyNames;
    private Spinner spinner;
    private ListView listView;
    private Button btnConvert;
    private EditText editText;
    private double etValue;
    private int index;
    private String et_value, btnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resources = getResources();

        //get all the views from activity
        spinner = (Spinner) findViewById(R.id.cc_spinner);
        listView = (ListView) findViewById(R.id.cc_lv);
        btnConvert = (Button) findViewById(R.id.convert_btn);
        editText = (EditText) findViewById(R.id.cc_et);

        //get values from array.xml
        currencyValues = resources.obtainTypedArray(R.array.currency_values);
        currencyNames = resources.obtainTypedArray(R.array.currency_names);

        //get values from Strings.xml
        et_value = resources.getString(R.string.et_value);
        btnText = resources.getString(R.string.convert_btn);

        //Set button text and edit text
        btnConvert.setText(btnText);
        editText.setText(et_value);


        //Creating adaptor for spinner and getting values and views from XML files.
        spinnerAdaptor = ArrayAdapter.createFromResource(this, R.array.currency_type, android.R.layout.simple_spinner_item);
        spinnerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting adaptor to the spinner.
        spinner.setAdapter(spinnerAdaptor);

        //set listener on the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //setting default content to the list view
        listValues = new ArrayList<String>();
        for(int i=0;i<currencyNames.length();i++)
        {
            listValues.add(currencyValues.getString(i)+ " "+currencyNames.getString(i));
        }
        listViewAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listValues);
        listView.setAdapter(listViewAdaptor);

        //add click listener on the button
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get value from EditText
                etValue = Double.parseDouble(editText.getText().toString());

                double new_values;
                //set format for Decimal for double values
                DecimalFormat df = new DecimalFormat("#.#####");
                ArrayList<String> new_list = new ArrayList<String>();

                String listItems;
                //adding new converted value to the Array List
                for(int i=0;i<currencyValues.length();i++)
                {
                    new_values = (currencyValues.getFloat(i,0)/currencyValues.getFloat(index,0))*etValue;
                    listItems = String.valueOf(df.format(new_values))+" "+currencyNames.getString(i);
                    new_list.add(listItems);
                }
                    setListViewItems(new_list);
            }
        });
    }

    //Setting the converted values to the list view
    public void setListViewItems(ArrayList<String> arrayList)
    {
        ArrayAdapter<String> new_adaptor;
        new_adaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(new_adaptor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
