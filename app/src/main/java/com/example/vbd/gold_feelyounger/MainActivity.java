package com.example.vbd.gold_feelyounger;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    private RelativeLayout relativeLayout;
    private AnimationDrawable animationDrawable;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button proceedbutton;
    private EditText name;
    private TextView dob;
    private TextView nametextview;
    private TextView dobtextview;
    private TextView newtextview;
    private TextView contacttextview;
    private TextView ctextview;
    private TextView wishtextview;
    int gday;
    int gmonth;
    int gyear;
    Pattern pattern=Pattern.compile("^[a-zA-Z]{1,15}$");
    Matcher matcher;
    String number="";

    public final int PICK_CONTACT = 2015;

    public void contact(View view)
    {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(i, PICK_CONTACT);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int c1 = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            Log.d("phone number", cursor.getString(column));
            Log.d("name", cursor.getString(c1));
            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("phone", cursor.getString(column)).apply();
            sharedPreferences.edit().putString("name", cursor.getString(c1)).apply();
            contacttextview.setText(sharedPreferences.getString("phone",""));
            number=sharedPreferences.getString("phone","");
        }
    }


    public void proceed(View view) throws ParseException {
        SharedPreferences sharedPreferences1 = this.getPreferences(Context.MODE_PRIVATE);
        String user1 = sharedPreferences1.getString("username", "");
        String name1=sharedPreferences1.getString("name","");
        if (user1.equals("")) {

            matcher = pattern.matcher(name.getText().toString());
            if (!matcher.matches()) {
                if (name.getText().length() > 15) {
                    Toast.makeText(MainActivity.this, "Please enter a shorter name", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Please enter a valid name", Toast.LENGTH_SHORT).show();
            } else if (dob.getText().equals("Select the date")) {
                Toast.makeText(MainActivity.this, "Please enter a valid DOB", Toast.LENGTH_SHORT).show();
            } else {
                int year1 = Calendar.getInstance().get(Calendar.YEAR);
                int month1 = Calendar.getInstance().get(Calendar.MONTH);
                int day1 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                month1 += 1;

                if ((year1 > gyear) || (year1 == gyear && month1 > gmonth) || (year1 == gyear && month1 == gmonth && day1 >= gday)) {

                    int diff = year1 - gyear;
                    if (diff != 0 && (gmonth > month1 || (month1 == gmonth && gday > day1))) {
                        diff--;
                    }
                    if(name1.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Please select a contact", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("username", name.getText().toString()).apply();
                        sharedPreferences.edit().putString("dob", dob.getText().toString()).apply();
                        sharedPreferences.edit().putString("age", String.valueOf(diff)).apply();
                        String user = sharedPreferences.getString("username", "");
                        String dob = sharedPreferences.getString("dob", "");
                        String age = sharedPreferences.getString("age", "");
                        Intent mainpageintent = new Intent(MainActivity.this, MainPageActivity.class);
                        number= sharedPreferences.getString("phone","");
                        mainpageintent.putExtra("number",number);
                        mainpageintent.putExtra("username",user);
                        mainpageintent.putExtra("age",age);
                        startActivity(mainpageintent);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Please enter a valid DOB", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Intent mainpageintent = new Intent(MainActivity.this, MainPageActivity.class);
            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);

            number=sharedPreferences.getString("phone","");
            mainpageintent.putExtra("number",number);
            String user = sharedPreferences.getString("username", "");
            String dob = sharedPreferences.getString("dob", "");
            String age = sharedPreferences.getString("age", "");
            mainpageintent.putExtra("username",user);
            mainpageintent.putExtra("age",age);

            startActivity(mainpageintent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proceedbutton = (Button) findViewById(R.id.proceedbutton);
        name = (EditText) findViewById(R.id.name);
        dob = (TextView) findViewById(R.id.dob);
        nametextview = (TextView) findViewById(R.id.nametextView);
        dobtextview = (TextView) findViewById(R.id.dobtextview);
        newtextview=(TextView) findViewById(R.id.newtextview);
        contacttextview=(TextView) findViewById(R.id.emergencynumber);
        ctextview=(TextView) findViewById(R.id.contacttextview);
        wishtextview=(TextView) findViewById(R.id.wishtextview);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(3000);

        animationDrawable.start();

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("username", "");
        String dateofbirth = sharedPreferences.getString("dob", "");
        String age = sharedPreferences.getString("age", "");

        if (user.equals("")) {
            dob = (TextView) findViewById(R.id.dob);
            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(MainActivity.this,
                            dateSetListener, year, month, day);

                    datePickerDialog.show();
                }
            });

            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    String date = day + "/" + month + "/" + year;
                    dob.setText(date);
                    gday = day;
                    gmonth = month;
                    gyear = year;
                }
            };
        }

        else
            {
          //  proceedbutton.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            dobtextview.setVisibility(View.GONE);
            dob.setVisibility(View.GONE);
            nametextview.setVisibility(View.GONE);
            contacttextview.setVisibility(View.GONE);
            ctextview.setVisibility(View.GONE);
            newtextview.setText("WELCOME BACK "+user);
            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
            Log.d("time",String.valueOf(timeOfDay));

            if(timeOfDay >= 0 && timeOfDay < 12){
                wishtextview.setText("Good Morning!!!");
            }else if(timeOfDay >= 12 && timeOfDay < 16){
                wishtextview.setText("Good Afternoon!!!");
            }else if(timeOfDay >= 16 && timeOfDay < 21){
                wishtextview.setText("Good Evening!!!");
            }else if(timeOfDay >= 21 && timeOfDay < 24){
                wishtextview.setText("Good Night!!!");
            }


        }
    }
}
