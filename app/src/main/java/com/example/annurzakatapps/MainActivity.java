package com.example.annurzakatapps;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
//import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText weight, currentgold, totalGold, zakatPayable, totalZakat;
    Button button;

    Float weights, currValue;

    SharedPreferences sharedPref;
    SharedPreferences sharedPref1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = (EditText) findViewById(R.id.weight);
        currentgold= (EditText) findViewById(R.id.currentgold);
        button = (Button) findViewById(R.id.button);
        totalGold = (EditText) findViewById((R.id.totalGoldAns));
        zakatPayable = (EditText) findViewById((R.id.zakatPayableAns));
        totalZakat = (EditText) findViewById((R.id.totalZakatAns));


       sharedPref = this.getSharedPreferences("weights", Context.MODE_PRIVATE);
       sharedPref1 = this.getSharedPreferences("currValue", Context.MODE_PRIVATE);

       //load the gold weight data
       weights = sharedPref.getFloat("weights", 0);
       weight.setText(""+weights);

       //load the gold value data
        currValue = sharedPref1.getFloat("currValue", 0);
        currentgold.setText(""+currValue);


        button.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home :
                break;


            case R.id.aboutus :

                Intent intent = new Intent(this,aboutPage.class);
                startActivity(intent);

                break;

        }
        return super.onOptionsItemSelected(item);
    }


        @Override

        public void onClick (View v)
        {
            switch (v.getId()) {

                //case R.id.button:
                case R.id.button:

                    try {
                        float wght, value;
                        wght = Float.parseFloat(weight.getText().toString());
                        value = Float.parseFloat(currentgold.getText().toString());

                        //save data
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putFloat("weights", wght);
                        editor.apply();

                        SharedPreferences.Editor editor1 = sharedPref1.edit();
                        editor1.putFloat("currValue", value);
                        editor1.apply();

                        double totalValueGold = wght * value;
                        String totalValueGoldCur = new Double(totalValueGold).toString();
                        totalGold.setText("Total value of the gold: RM " + (numberCurrencyFormat(totalValueGoldCur)));


                        RadioButton keep = (RadioButton) findViewById(R.id.keep);
                        RadioButton wear = (RadioButton) findViewById(R.id.wear);

                        double minus;  //calculation for gold weight
                        if (keep.isChecked()) {
                            minus = wght - 85;
                        } else {
                            minus = wght - 200;
                        }

                        double currValue = Double.parseDouble(currentgold.getText().toString()); //calculation for zakat payable
                        double zktPayable = minus * currValue;


                        if (zktPayable >= 0) {
                            String zakatPayableCur = new Double(zktPayable).toString();
                            zakatPayable.setText("Total Zakat Payable: RM " + (numberCurrencyFormat(zakatPayableCur)));
                            double totalZkt = zktPayable * 0.025;
                            String totalZktCur = new Double(totalZkt).toString();
                            totalZakat.setText("Total Zakat: RM " + (numberCurrencyFormat(totalZktCur)));

                        } else {
                            double newPayable = zktPayable - zktPayable;        //for output if zakat payable get -ve
                            String newPayableCur = new Double(newPayable).toString();
                            zakatPayable.setText("Total Zakat Payable: RM " + (numberCurrencyFormat(newPayableCur)));
                            double totalZkt = newPayable * 0.025;
                            String totalZktCur = new Double(totalZkt).toString();
                            totalZakat.setText("Total Zakat: RM " + (numberCurrencyFormat(totalZktCur)));  //for output total zakat if zakat payable get -ve
                        }

                    } catch (java.lang.NumberFormatException nfe) {
                        Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();

                    } catch (Exception exp) {
                        Toast.makeText(this, "Unknown exception" + exp.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Exception", exp.getMessage());
                    }
                    break;
            }
        }


    //change double to currency format
    private static String numberCurrencyFormat(String currency){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
        return decimalFormat.format(Double.parseDouble(currency));
    }

}