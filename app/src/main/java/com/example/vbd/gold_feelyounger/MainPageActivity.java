package com.example.vbd.gold_feelyounger;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainPageActivity extends AppCompatActivity {

    public static ProgressDialog progressDialog;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
                    String number = sharedPreferences.getString("phone", "");
                //    Log.d("now", number);
                    Intent callintent = new Intent(Intent.ACTION_CALL);
                    callintent.setData(Uri.parse("tel:"+number));
                    startActivity(callintent);

                }
            }
        }
    }

                public void findpage(View view)
           {
        String id= (String) view.getTag();

        switch(id) {
            case "1":
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                } else
                {
                    Intent intent=getIntent();
                    String number=intent.getStringExtra("number");
                    Log.d("now",number );

                    Intent callintent = new Intent(Intent.ACTION_CALL);
                    callintent.setData(Uri.parse("tel:"+number));
                    startActivity(callintent);
                }
                break;



            case "2":
                if(isNetworkAvailable()) {
                    loading("Finding Location...");
                    Intent intent = new Intent(MainPageActivity.this, ListHospitals.class);
                    startActivity(intent);
                }else
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
                 break;

            case "3":
                Intent sudokuintent=new Intent(MainPageActivity.this,SudokuActivity.class);
                startActivity(sudokuintent);
                break;

            case "4":
                Intent alarmintent=new Intent(MainPageActivity.this,AlarmActivity.class);
                startActivity(alarmintent);
                break;

            case "5":
                final AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
                dialog2.setCancelable(true);
                dialog2.setTitle("TIPS");
                dialog2.setMessage("1. Get active\n" +
                        "Physical activity is an immune system booster. The more you move, the more your body is able to fight inflammation and infections.\n" +
                        "\n" +
                        "\n" +
                        "The activity you partake in doesn’t have to be strenuous. Low impact exercises are effective, too.\n" +
                        "\n" +
                        "You might consider biking, walking, swimming, or low impact aerobics. If you’re able to, engage in moderate intensity exercise for about 20 to 30 minutes a day to reach the recommended total of 150 minutes a weekTrusted Source. Also, strengthen your muscles by lifting weights or doing yoga.\n" +
                        "\n" +
                        "Modify your exercise routine to find what feels best for you.\n" +
                        "\n" +
                        "2. Take supplements as necessary\n" +
                        "Some supplements help support a healthy immune system. Before taking a supplement, always ask your doctor if it’s safe, especially if you’re taking a prescription medication. Some supplements they may recommend include calcium, vitamin D, vitamin B6, or vitamin B12.\n" +
                        "\n" +
                        "Take supplements or multivitamins as instructed to boost your immune system.\n" +
                        "\n" +
                        "3. Eat a healthy diet\n" +
                        "Diets rich in fruits, vegetables, and lean meats also give your immune system a boost and protect against harmful viruses and bacteria that cause illnesses. Fruits and vegetables are a good source of antioxidants. Antioxidants protect your cells from damage and keep your body healthy.\n" +
                        "\n" +
                        "You should also limit your consumption of sugary and fatty foods, which can trigger inflammation in the body and lower your immune system.\n" +
                        "\n" +
                        "In addition, limit your intake of alcohol. Ask your doctor about safe amounts of alcohol to drink per day or week.\n" +
                        "\n" +
                        "4. Wash your hands frequently\n" +
                        "Washing your hands on a regular basis is another excellent way to stay healthy year-round. Viruses can live on surfaces for up to 24 hours. It’s possible to become ill if you touch a virus-covered surface and contaminate your hands, and then touch your face.\n" +
                        "\n" +
                        "Wash your hands with warm soapy water often, and for at least 20 seconds. Avoid touching your nose, face, and mouth with your hands.\n" +
                        "\n" +
                        "\n" +
                        "You can also protect yourself by using antibacterial hand sanitizer when you’re unable to wash your hands. Also, disinfect surfaces around your home and workstation frequently.\n" +
                        "\n" +
                        "5. Learn how to manage stress\n" +
                        "Chronic stress increases your body’s production of the stress hormone cortisol. Too much cortisol can disrupt different functions in your body, including your immune system.\n" +
                        "\n" +
                        "To reduce stress, increase physical activity, get plenty of sleep, set reasonable expectations for yourself, and explore relaxing, enjoyable activities.\n" +
                        "\n" +
                        "6. Get plenty of rest\n" +
                        "Not only can sleep reduce your stress level, but sleep is how your body repairs itself. For this reason, getting an adequate amount of sleep can result in a stronger immune system, making it easier for your body to fight off viruses.\n" +
                        "\n" +
                        "Sleep is also important as you get older because it can improve memory and concentration. Aim for at least seven and a half to nine hours of sleep per night.\n" +
                        "\n" +
                        "If you have trouble sleeping, talk to your doctor to find the underlying cause. Causes of insomnia can include inactivity during the day and too much caffeine. Or it can be a sign of a medical condition like sleep apnea or restless leg syndrome.\n" +
                        "\n" +
                        "7. Take steps to prevent infections\n" +
                        "Getting annual vaccinations is another way to stay healthy throughout the year. If you’re age 65 and older, talk to your doctor about getting a high-dose or adjuvant flu vaccine.\n" +
                        "\n" +
                        "Flu season is between October and May in the United States. It takes about two weeks for the vaccine to be effective, and it reduces the risk of the flu by 40 to 60 percentTrusted Source when the vaccine strains match the circulating strains.\n" +
                        "\n" +
                        "The flu virus changes each year, so you should get the vaccine yearly. You can also talk to your doctor about getting pneumococcal vaccines to protect against pneumonia and meningitis.\n" +
                        "\n" +
                        "HEALTHLINE PARTNER SOLUTIONS\n" +
                        "Get Answers from a Doctor in Minutes, Anytime\n" +
                        "Have medical questions? Connect with a board-certified, experienced doctor online or by phone. Pediatricians and other specialists available 24/7.\n" +
                        "\n" +
                        "8. Schedule annual physicals\n" +
                        "Scheduling a yearly checkup can also keep you healthy. Always speak with your doctor if you have concerns about your health.\n" +
                        "\n" +
                        "Conditions like diabetes and high blood pressure can go undetected. Regular physical examinations will enable your doctor to diagnose any problems early. Getting early treatment may prevent long-term complications.\n" +
                        "\n" +
                        "Also, if you have any cold or flu symptoms, see your doctor immediately. The flu virus can lead to complications in adults over the age of 65. The immune system weakens with age, making it harder to fight off the virus.\n" +
                        "\n" +
                        "If you see a doctor within the first 48 hours of flu symptoms, they can prescribe an antiviral to reduce the severity and length of symptoms.\n" +
                        "\n" +
                        "9. Avoid contact with people who are sick\n" +
                        "Another way to protect yourself year-round is to avoid being close to people who are sick. This is easier said than done. But if there’s a flu outbreak in your area, limit contact with people who aren’t feeling well and avoid crowded areas until conditions improve.\n" +
                        "\n" +
                        "If you must go out, protect yourself by wearing a face mask. If you’re caring for someone with the flu, wear a face mask and gloves, and wash your hands frequently.");
                dialog2.setIcon(R.drawable.notification);
                dialog2.show();
                break;


            case "6":
                Intent intent=getIntent();
                String username=intent.getStringExtra("username");
                String age=intent.getStringExtra("age");
                String number=intent.getStringExtra("number");
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setCancelable(true);
                dialog.setTitle("YOURSELF");
                dialog.setMessage("NAME: "+username+"\nAGE: "+age+"\nEMERGENCY: "+number);
                dialog.setIcon(R.drawable.notification);
                dialog.show();
                break;


            case "7":
                final AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
                dialog1.setCancelable(true);
                dialog1.setTitle("ABOUT US");
                dialog1.setMessage("GOLD SOLUTIONS\n CONTACT:xyz@gmail.com");
                dialog1.setIcon(R.drawable.notification);
                dialog1.show();
                break;


            default:
                Toast.makeText(this,"WRONG OPTION!!!",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * show progress dialog while loading maps or problems
     * */
    void loading(String message){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait!!!");
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
    }
}
