package com.example.hitesh.qualtemp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ArrayList<HashMap> list;
    HashMap temp = new HashMap();
    HashMap temp1 = new HashMap();
    HashMap temp2 = new HashMap();
    HashMap temp3 = new HashMap();
    HashMap temp4 = new HashMap();
    ListView lview;
    listviewAdapter adapter;


    Switch myswitch;


    MyNDK m = new MyNDK();
    final static Random r = new Random();
    private static int  temp11 = (r.nextInt(40 - 0)+0);//temperature for monday
    private static int  temp22 = (r.nextInt(40 - 0)+0);//temperature for tuesday
    private static int  temp33 = (r.nextInt(40 - 0)+0);//temperature for wednesday
    private static int  temp44 = (r.nextInt(40 - 0)+0);//temperature for thursday
    private static int  temp55 = (r.nextInt(40 - 0)+0);//temperature for friday

    private SensorManager mgr;//sensor manager for ambient temperature
    private Sensor stemp;
    private TextView text;
    private StringBuilder msg = new StringBuilder(2048);//this string will get the ambient temperature

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lview = (ListView)findViewById(R.id.listview);
        populateList();//populating the list view with random temperatures
        adapter = new listviewAdapter(this, list);
        lview.setAdapter(adapter);


        mgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        stemp = mgr.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        mgr.registerListener(this, stemp, SensorManager.SENSOR_DELAY_NORMAL);

        text = (TextView) findViewById(R.id.text);//this textview will show the ambient temperature


            myswitch = (Switch)findViewById(R.id.switch1);


            myswitch.setChecked(true);
        //Whenever this condition is true this take the input in the list as celsius and it
        //will convert it into faraheit
            myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        temp11 = m.getMyString(temp11);//calling the jni method for monday
                        // m = new MyNDK();
                        temp22 = m.getMyString(temp22);//calling the jni method for tuesday
                        // m = new MyNDK();
                        temp33 = m.getMyString(temp33);//calling the jni method for wednesday
                        // m = new MyNDK();
                        temp44 = m.getMyString(temp44);//calling the jni method for thursday
                        // m = new MyNDK();
                        temp55 = m.getMyString(temp55);//calling the jni method for friday


                        populateList();

                        //notifying the list view that there have been change in the values
                        MainActivity.this.adapter.notifyDataSetChanged();
                    } else {
                            convert();
                    }
                }
            });


        }




    private void populateList() {

        list = new ArrayList<HashMap>();

        //hashmap is used to store values with multiple columns in the list
        temp.put(Constant.FIRST_COLUMN,"Monday");
        temp.put(Constant.SECOND_COLUMN, Integer.toString(temp11));
        list.add(temp);

        //HashMap temp1 = new HashMap();
        temp1.put(Constant.FIRST_COLUMN, "Tuesday");
        temp1.put(Constant.SECOND_COLUMN, Integer.toString(temp22));
        list.add(temp1);

        //HashMap temp2 = new HashMap();
        temp2.put(Constant.FIRST_COLUMN, "Wednesday");
        temp2.put(Constant.SECOND_COLUMN, Integer.toString(temp33));
        list.add(temp2);

        //HashMap temp3 = new HashMap();
        temp3.put(Constant.FIRST_COLUMN,"Thursday");
        temp3.put(Constant.SECOND_COLUMN, Integer.toString(temp44));
        list.add(temp3);

        //HashMap temp4 = new HashMap();
        temp4.put(Constant.FIRST_COLUMN,"Friday");
        temp4.put(Constant.SECOND_COLUMN, Integer.toString(temp55));
        list.add(temp4);
    }

    void convert()
    {
        double t = ((temp11 - 32) / 1.8);
        temp11 = (int)t;
        double t1 = ((temp22 - 32) / 1.8);
        temp22 = (int)t1;
        double t2 = ((temp33 - 32) / 1.8);
        temp33 = (int)t2;
        double t3 = ((temp44 - 32) / 1.8);
        temp44 = (int)t3;
        double t4 = ((temp55 - 32) / 1.8);
        temp55 = (int)t4;
        populateList();
        MainActivity.this.adapter.notifyDataSetChanged();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor stemp1 = event.sensor;

        Log.d("here","here");

        //check condition to see if the correct sensor called for the event or not
        if(stemp1.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {

            float fahrenheit = event.values[0] * 9 / 5 + 32;
            //inserting the ambient temperature value
            msg.insert(0, "Got a sensor event: " + event.values[0] + " Celsius (" +
                    fahrenheit + " F)\n");
            Log.d("fara", Float.toString(fahrenheit));
            text.setText(msg);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}
