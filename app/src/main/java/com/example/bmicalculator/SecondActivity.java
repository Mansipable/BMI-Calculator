package com.example.bmicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    TextView tv1, tvName, tvTemp, tvFeet, tvInch;
    SharedPreferences sp, sp1;
    EditText etWeight;
    Button btnCalculate, btnHistory;
    Spinner spnHeight, spnInch;
    Location loc1;
    GoogleApiClient gac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;   //to lock the orientation
        setRequestedOrientation(o);

        Typeface tf1 = Typeface.createFromAsset(getAssets(),
                "fonts/Calligraffiti.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(),
                "fonts/AlexBrush-Regular.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(),
                "fonts/Chantelli_Antiqua.ttf");

        tv1 = findViewById(R.id.tv1);
        tvName = findViewById(R.id.tvName);
        tvTemp = findViewById(R.id.tvTemp);
        tvFeet = findViewById(R.id.tvFeet);
        tvInch = findViewById(R.id.tvInch);
        final Spinner spnHeight = findViewById(R.id.spnHeight);
        final Spinner spnInch = findViewById(R.id.spnInch);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnHistory = findViewById(R.id.btnHistory);
        etWeight = findViewById(R.id.etWeight);

        sp = getSharedPreferences("f1", MODE_PRIVATE);
        sp1 = getSharedPreferences("f2", MODE_PRIVATE);
        String name = sp.getString("name", "");
        tv1.setText("Welcome \n" + name);
        String b1 = sp1.getString("bmi", "");

        tv1.setTypeface(tf1);
        tvName.setTypeface(tf2);
        tvTemp.setTypeface(tf2);
        tvFeet.setTypeface(tf3);
        tvInch.setTypeface(tf3);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this); //to connect google location server to our app(client)
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);//this method for building the client
        builder.addOnConnectionFailedListener(this);

        gac = builder.build();


        final ArrayList<Integer> s = new ArrayList<>();//s

        s.add(3);
        s.add(4);
        s.add(5);
        s.add(6);
        s.add(7);
        s.add(8);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, s);
        spnHeight.setAdapter(arrayAdapter);

        final ArrayList<Integer> s1 = new ArrayList<>();//s1

        s1.add(0);
        s1.add(1);
        s1.add(2);
        s1.add(3);
        s1.add(4);
        s1.add(5);
        s1.add(6);
        s1.add(7);
        s1.add(8);
        s1.add(9);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, s1);
        spnInch.setAdapter(arrayAdapter1);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = spnHeight.getSelectedItemPosition();
                Integer n = s.get(id);
                int id1 = spnInch.getSelectedItemPosition();
                Integer n1 = s.get(id1);

                String wt = etWeight.getText().toString();
                if (wt.length() == 0) {

                    etWeight.setError("Please Enter Weight");
                    etWeight.requestFocus();
                    return;
                }
                float w = Float.parseFloat(wt);

                float m1 = (float) (n / 3.2808);
                float m2 = (float) (n1 / 39.370);
                float m = m1 + m2;

                float bmi = (w) / (m * m);
                String msg = Float.toString(bmi);
                SharedPreferences.Editor editor = sp1.edit();
                editor.putString("bmi", msg);
                editor.commit();
                Intent i = new Intent(SecondActivity.this, ResultActivity.class);
                startActivity(i);
                Toast.makeText(SecondActivity.this, "BMI : " + bmi, Toast.LENGTH_SHORT).show();

            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SecondActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.aboutus == item.getItemId()) {
            Toast.makeText(this, "App made by MANSI PABLE", Toast.LENGTH_LONG).show();
            return super.onOptionsItemSelected(item);
        }
        if (R.id.website == item.getItemId()) {
            Intent a = new Intent(Intent.ACTION_VIEW);
            a.setData(Uri.parse("http://" + "www.google.com"));
            startActivity(a);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gac != null) {
            gac.connect();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (gac != null) {
            gac.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        loc1 = LocationServices.FusedLocationApi.getLastLocation(gac);
        if (loc1 != null) {
            double lat = loc1.getLatitude();
            double lon = loc1.getLongitude();
            String msg = "lat " + lat + "  lon " + lon;
            Geocoder g = new Geocoder(SecondActivity.this, Locale.ENGLISH);
            try {
                List<Address> addressList = g.getFromLocation(lat, lon, 1);
                Address address = addressList.get(0);
                String loc = address.getLocality();
                MyClass t1 = new MyClass();
                String w1 = "http://api.openweathermap.org/data/2.5/weather?units=metric";
                String w2 = "&q=" + loc;
                String w3 = "&appid=c6e315d09197cec231495138183954bd";
                String w = w1 + w2 + w3;

                t1.execute(w);
                tvName.setText("Location : " + loc);

            } catch (IOException e) {
                Toast.makeText(this, "issue", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Check gps", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    class MyClass extends AsyncTask<String, Void, Double> {
        double temp;

        @Override
        protected Double doInBackground(String... strings) {
            String json = "", line = "";
            try {

                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                while ((line = br.readLine()) != null) {
                    json = json + line + "\n";
                }
                JSONObject o = new JSONObject(json);
                JSONObject p = o.getJSONObject("main");
                temp = p.getDouble("temp");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvTemp.setText("Temperature : " + aDouble);
        }

    }
}





















