package com.example.myapplication21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Modifier;

public class MainActivity extends AppCompatActivity {
    EditText NAME,PASSWORD;
    DB_SQLite db = new DB_SQLite(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);
        NAME = findViewById(R.id.username); //id=username
        PASSWORD = findViewById(R.id.password);//id=password
        SharedPreferences prefs=getSharedPreferences("myPrefs",MODE_PRIVATE);
        String un = prefs.getString("username","");
        String p = prefs.getString("password","");
        if(!un.isEmpty() && !p.isEmpty())
        {
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
        TextView btn=findViewById(R.id.textbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n=NAME.getText().toString();
                String p=PASSWORD.getText().toString();
                if(p.equals("") || n.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Filed cant be empty !",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean check_p_n = db.check_pass_name(n,p);
                    if(check_p_n == true)
                    {
                        SharedPreferences.Editor e=getSharedPreferences("myPrefs",MODE_PRIVATE).edit();
                        e.putString("username",n);
                        e.putString("password",p);
                        e.apply();
                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Login is not successful",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}