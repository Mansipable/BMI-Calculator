package com.example.bmicalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5;
    Button btnSave,btnShare,btnBack;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String m;
    SharedPreferences sp1 , sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);

        database=FirebaseDatabase.getInstance();
        myRef = database.getReference("bmi");
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnShare = findViewById(R.id.btnShare);
        sp1=getSharedPreferences("f2",MODE_PRIVATE);
        sp2=getSharedPreferences("f3",MODE_PRIVATE);
        String b1=sp1.getString("bmi","");
        String b3=sp2.getString("t","");
        final Float b2 = Float.parseFloat(b1);
        if (b2 < 18.5) {
            m = b3 + " Your BMI is " + b2 + " and  you are underweight. ";
            tv1.setText(" Your BMI is " + b2 + " and  you are underweight. ");
            tv2.setTextColor(0XFFFF0000);
        }
        if (b2 > 18.5 && b2 < 25) {
            m = b3 + " Your BMI is " + b2 + " and  you are normal. ";
            tv1.setText(" Your BMI is " + b2 + " and  you are normal. ");
            tv3.setTextColor(0XFFFF0000);
        }
        if (b2 > 25 && b2 < 30) {
            m = b3 + " Your BMI is " + b2 + " and  you are overweigtht. ";
            tv1.setText(" Your BMI is " + b2 + " and  you are overweight. ");
            tv4.setTextColor(0XFFFF0000);
        }
        if (b2 > 30) {
            m = b3 + " Your BMI is " + b2 + " and  you are obese. ";
            tv1.setText(" Your BMI is " + b2 + " and   you are obese. ");
            tv5.setTextColor(0XFFFF0000);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateTime = df.format(c.getTime());
                BMI b=new BMI(b2,dateTime);
                myRef.push().setValue(b);
                Toast.makeText(ResultActivity.this, "Record is saved", Toast.LENGTH_SHORT).show();

            }
        });


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s=new Intent(Intent.ACTION_SEND);
                s.setType("text/plain");
                s.putExtra(Intent.EXTRA_TEXT,m);
                startActivity(s);
            }
        });
    }
}
