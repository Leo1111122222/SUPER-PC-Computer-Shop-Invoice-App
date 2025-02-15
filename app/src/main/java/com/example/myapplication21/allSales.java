package com.example.myapplication21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class allSales extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    DB_SQLite db = new DB_SQLite(this);
    String endDate = "11/11/1111", startDate="22/22/2222";
    TextView T_V_S;
    RecyclerView recycler_View;
    ArrayList<String> PType , PName ,quantity, price,agentName, Is_Buy, D_Product, D_Customer,d ;
    listAdapter adapter;
    boolean Sound_boolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_sales);
        PType=new ArrayList<>();
        PName=new ArrayList<>();
        quantity=new ArrayList<>();
        price=new ArrayList<>();
        agentName=new ArrayList<>();
        Is_Buy=new ArrayList<>();
        D_Product=new ArrayList<>();
        D_Customer=new ArrayList<>();
        d=new ArrayList<>();
        recycler_View=findViewById(R.id.r);
        adapter=new listAdapter(this,PType,PName,quantity,price,agentName,Is_Buy,D_Product,D_Customer,d);
        recycler_View.setAdapter(adapter);
        recycler_View.setLayoutManager(new LinearLayoutManager(this));


        mediaPlayer = MediaPlayer.create(this,R.raw.beep);
        T_V_S=findViewById(R.id.The_Value_Sales);
        EditText from2=findViewById(R.id.sfs);  //for the day from in the all sales page
        EditText to2= findViewById(R.id.sts);  //for the day to in the all sales page
        Calendar calendar3 = Calendar.getInstance();  //for the day from in the all sales page
        Calendar calendar4 = Calendar.getInstance();  //for the day to in the all sales page




        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar3.set(Calendar.YEAR , year);
                calendar3.set(Calendar.MONTH , month);
                calendar3.set(Calendar.DAY_OF_MONTH , day);

                updateCalender();
            }
            private void  updateCalender(){

                String format = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format , Locale.US);
                from2.setText(sdf.format(calendar3.getTime()));

            };
        };
        from2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view3) {

                new DatePickerDialog(allSales.this,date2,calendar3.get(Calendar.YEAR),
                        calendar3.get(Calendar.MONTH),calendar3.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar4.set(Calendar.YEAR , year);
                calendar4.set(Calendar.MONTH , month);
                calendar4.set(Calendar.DAY_OF_MONTH , day);

                updateCalender();
            }
            private void  updateCalender(){

                String format = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format , Locale.US);
                to2.setText(sdf.format(calendar4.getTime()));

            };
        };
        to2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(allSales.this,date3,calendar4.get(Calendar.YEAR),
                        calendar4.get(Calendar.MONTH),calendar4.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        from2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                startDate = from2.getText().toString();
                boolean x =checkDates(startDate,endDate);
                if(x == true)
                {
                    view_SV();
                    displaydata();
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        to2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                endDate = to2.getText().toString();
                boolean x =checkDates(startDate,endDate);
                if(x == true)
                {
                    view_SV();
                    displaydata();
                    adapter.notifyDataSetChanged();

                }



            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        Button btn = findViewById(R.id.backbttn2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(allSales.this,InfoActivity.class));
            }
        });

    }

    private void displaydata() {
        String StartDate = startDate.replace("/", "");
        String EndDate = endDate.replace("/", "");
        Cursor cursor=db.getDataBetweenDates("Sale" ); /*, EndDate , StartDate*/
        if(cursor.getCount()==0)
        {

            return;
        }
        else
        {
            while (cursor.moveToNext())
            {
                PType.add(cursor.getString(0));
                PName.add(cursor.getString(1));
                D_Product.add(cursor.getString(2));
                agentName.add(cursor.getString(3));
                D_Customer.add(cursor.getString(4));
                String inputDate = cursor.getString(5);
                SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = inputFormat.parse(inputDate);
                    String outputDate = outputFormat.format(date);
                    d.add(outputDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Is_Buy.add(cursor.getString(6));
                quantity.add(cursor.getString(7));
                price.add(cursor.getString(8));
            }
        }
    }

    private void view_SV() {
        Integer sv = db.getSumValue("Sale");
        T_V_S.setText(sv.toString());
    }


    private boolean checkDates(String startDate, String endDate) {
        if (endDate.equals("11/11/1111") || startDate.equals("22/22/2222"))
        {
            Toast.makeText(allSales.this, "Enter the other date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
                return true;
             }
            }


    public void SoundButton(View view){
        if(Sound_boolean){
            mediaPlayer.start();
        }else {

        }
    }

    private class MyAdapter {
    }
}