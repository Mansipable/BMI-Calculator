package com.example.bmicalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnRegister;
    TextView tvPersonalDetails;
    EditText etName , etAge , etPhoneNo;
    RadioGroup rgGender;
    RadioButton rgMale , rgFemale;
    SharedPreferences sp,sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);


        btnRegister = findViewById(R.id.btnRegister);
        tvPersonalDetails = findViewById(R.id.tvPersonalDetails);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        sp = getSharedPreferences("f1" , MODE_PRIVATE);
        sp2 = getSharedPreferences("f3" , MODE_PRIVATE);

        String name=sp.getString("name","");
        if(name.length() ==0){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name= etName.getText().toString();
                    String age= etAge.getText().toString();
                    String phone= etPhoneNo.getText().toString();
                    String t= "Name : "+name+"\nAge: "+age+"\nPhone No.: "+phone+"\n";
                    if(name.length() ==0){

                        etName.setError("please enter your name");
                        etName.requestFocus();
                        return;
                    }

                    if(age.length() == 0  ||  age.length()>=105){

                        etAge.setError("please enter your age");
                        etAge.requestFocus();
                        return;
                    }


                    if(phone.length() != 10){

                        etPhoneNo.setError("phone no. is invalid");
                        etPhoneNo.requestFocus();
                        return;
                    }

                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("name",name);
                    editor.commit();
                    SharedPreferences.Editor editor1=sp2.edit();
                    editor1.putString("t",t);
                    editor1.commit();
                    Intent i=new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(i);
                    finish();
                }
            });

        }
        else{
            Intent i=new Intent(MainActivity.this,SecondActivity.class);
            startActivity(i);
            finish();
        }


    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit");
        alertDialog.setMessage("Do you want to exit ? ");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog a = alertDialog.create();
        a.show();

    }
}
