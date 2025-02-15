package com.example.myapplication21;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
DB_SQLite db = new DB_SQLite(this);
EditText Name,Email,Password,RePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name=findViewById(R.id.username);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        RePassword = findViewById(R.id.repassword);
        TextView btn=findViewById(R.id.textbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
    }
    public void signupbtn(View view)
    {
        String name=Name.getText().toString();
        String email=Email.getText().toString();
        String password=Password.getText().toString();
        String re_password=RePassword.getText().toString();
        if(password.equals("") || re_password.equals("")|| email.equals("")|| name.equals(""))
        {
            Toast.makeText(RegisterActivity.this,"Filed cant be empty !",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) // example.@gmail.com
            {
                Boolean Check_email = db.check_email(email);
                if(Check_email == false)
                {
                    if(password.equals(re_password)) //password==repassword
                    {
                        if(password.length() > 7) //password>=8
                        {
                            Boolean Check_user = db.check_name(name);
                            if (Check_user == false) {
                                Boolean result = db.insert_data(name, email, password);
                                if (result == true)
                                {
                                    Name.setText("");
                                    Password.setText("");
                                    RePassword.setText("");
                                    Email.setText("");
                                    Toast.makeText(RegisterActivity.this, "saved", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this, "not saved", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Username already used, please try with a new username !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"Password must be greater than 8 characters !",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,"The passwords you entered do not match. Please try again",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"The email is already used, please choose another email",Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(RegisterActivity.this,"The email is incorrect, try again",Toast.LENGTH_SHORT).show();
            }
        }


    }
}