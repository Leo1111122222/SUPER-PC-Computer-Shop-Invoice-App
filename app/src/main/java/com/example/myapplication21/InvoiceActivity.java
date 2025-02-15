package com.example.myapplication21;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaCodec;
import android.util.Patterns;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import java.util.regex.Pattern;
public class InvoiceActivity extends AppCompatActivity {
    DB_SQLite db = new DB_SQLite(this);
    Spinner InvoiceType;
    String v;
    String[] is_buy={"Sale","Buy"};
    EditText ProductType,ProductName,DescriptionOfTheProduct,AgentName,DescriptionOfTheAgent,Quantity,Price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        ProductType=findViewById(R.id.type);
        ProductName=findViewById(R.id.name);
        DescriptionOfTheProduct=findViewById(R.id.prodes);
        AgentName=findViewById(R.id.coname);
        DescriptionOfTheAgent=findViewById(R.id.codes);
        InvoiceType=findViewById(R.id.intype);
        Quantity=findViewById(R.id.quantity);
        Price=findViewById(R.id.price);
        ArrayAdapter<String> adapter = new  ArrayAdapter<String>(InvoiceActivity.this, android.R.layout.simple_spinner_item,is_buy);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        InvoiceType.setAdapter(adapter);
        InvoiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>parent , View view, int position, long id) {
                 v = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        TextView btn=findViewById(R.id.backbttn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InvoiceActivity.this,HomeActivity.class));
            }
        });
    }
    public void savebtn(View view)
    {

        String type=ProductType.getText().toString();
        String name=ProductName.getText().toString();
        String prodes=DescriptionOfTheProduct.getText().toString();
        String coname=AgentName.getText().toString();
        String codes=DescriptionOfTheAgent.getText().toString();

        String quantity= Quantity.getText().toString();
        String price=Price.getText().toString();

        if(type.equals("") || name.equals("")|| prodes.equals("")|| v.equals("") || coname.equals("")|| codes.equals("")|| quantity.equals("") || price.equals(""))
        {
            Toast.makeText(InvoiceActivity.this,"Filed cant be empty !",Toast.LENGTH_SHORT).show();
        }
        else
        {

                Boolean result = db.insert_invoice_data( type, name, prodes, coname, codes, v, quantity, price);
                if (result == true)
                {
                    ProductType.setText("");
                    ProductName.setText("");
                    DescriptionOfTheProduct.setText("");
                    AgentName.setText("");
                    DescriptionOfTheAgent.setText("");
                    Quantity.setText("");
                    Price.setText("");
                    Toast.makeText(InvoiceActivity.this, "saved", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(InvoiceActivity.this, "not saved", Toast.LENGTH_SHORT).show();
                }




        }
    }

}