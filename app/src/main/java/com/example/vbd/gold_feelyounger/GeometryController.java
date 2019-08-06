package com.example.vbd.gold_feelyounger;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class GeometryController {

    public static ArrayList<NearbyHospitalsDetail> detailArrayList = new ArrayList();

    public static void manipulateData(StringBuffer buffer,double latitude,double longitude){

        try {
            detailArrayList.clear();

            JSONObject jsonpObject = new JSONObject(buffer.toString());

            JSONArray array = jsonpObject.getJSONArray("results");

            for(int i=0; i<array.length(); i++){
                try {
                    JSONObject jsonObject = array.getJSONObject(i);
                    NearbyHospitalsDetail hospitalsDetail = new NearbyHospitalsDetail();

                    if(jsonObject.getString("name")!=null) {
                        hospitalsDetail.setHospitalName(jsonObject.getString("name"));
                        //Log.d("hospname",jsonObject.getString("name"));
                    }
                    else  hospitalsDetail.setHospitalName("Not Available");

                    try {
                        hospitalsDetail.setRating(String.valueOf(jsonObject.getDouble("rating")));
                    }catch (Exception e){
                        hospitalsDetail.setRating("Not Available");
                    }

                    try {
                        if (jsonObject.getJSONObject("opening_hours").getBoolean("open_now"))  hospitalsDetail.setOpeningHours("Opened");
                        else hospitalsDetail.setOpeningHours("closed");
                    } catch (Exception e) {
                        hospitalsDetail.setOpeningHours("Not Available");
                    }

                    hospitalsDetail.setAddress(jsonObject.getString("vicinity"));
                    hospitalsDetail.setGeometry(new double[]{jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                            jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng")});

                    float[] results=new float[10];
                    Location.distanceBetween(jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                            jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng"),
                            latitude,longitude,results);
                    double km=results[0]/1000;

                    DecimalFormat decimalFormat=new DecimalFormat("#.##");
                    String km1=decimalFormat.format(km);
                    hospitalsDetail.setkm(km1);

                    detailArrayList.add(hospitalsDetail);
                   // Log.d("data",GeometryController.detailArrayList.get(i).getHospitalName());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
