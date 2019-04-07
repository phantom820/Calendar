package com.example.calendar;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class DailyView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent m;
    LinearLayout Items;
    TextView time;
    TextView Slot;
    View Divider;
    FrameLayout vert;
    TextView WeekDay;
    ImageView display;
    String current_date,checked_date;
    ArrayList<TextView>TimeSlots;
    ArrayList<Booking>Bookings;
    Toolbar Heading;
    String Months[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
    Dialog booking;
    String DayOfWeek,DayOfMonth,MonthOfYear;
    TextView Checkout;
    TextView Cancel;
    ScrollView UpdateData;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Heading=(Toolbar)findViewById(R.id.toolbar);
        Heading.setTitleTextColor(Color.BLACK);
        Items=(LinearLayout)findViewById(R.id.col);
        time=(TextView)findViewById(R.id.slotOne);
        Slot=(TextView)findViewById(R.id.g);
        Divider=(View)findViewById(R.id.divider);
        vert=(FrameLayout)findViewById(R.id.verticaldiv);
        WeekDay=(TextView)findViewById(R.id.weekDay);
        display=(ImageView)findViewById(R.id.dayDisplay);

        Bookings=new ArrayList<>();
        m=getIntent();
        DayOfWeek=m.getStringExtra("WeekDay");
        DayOfMonth=m.getStringExtra("Date");
        String Information=DayOfWeek+"\n"+DayOfMonth;
        String Picture=m.getStringExtra("Month");
        current_date=m.getStringExtra("Current");
        checked_date=m.getStringExtra("Checked");

        WeekDay.setText(Information);

        Display(Picture);
        MonthOfYear=Months[Index(Picture)];
        setTitle(MonthOfYear+" "+checked_date.substring(0,4));
        TimeSlots=new ArrayList<>();
        PopulateView();
        DailySchedule(checked_date);
        booking=new Dialog(this);
        booking.setContentView(R.layout.dialog_booking);

        Checkout=(TextView)booking.findViewById(R.id.checkout);
        Cancel=(TextView)booking.findViewById(R.id.cancel);

        UpdateData=(ScrollView)findViewById(R.id.touchslots);
        UpdateData.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                DailySchedule(checked_date);
            }
        });

        toolbar.setTitleTextColor(Color.BLACK);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule) {
            // Handle the camera action
        } else if (id == R.id.nav_week) {

        } else if (id == R.id.nav_month) {
            finish();

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void  Display(String input){

        if(!input.equals("")){

            String Month=input;

            if(Month.equals("Apr")){
                display.setImageResource(R.drawable.april);
            }

            else if(Month.equals("Mar")){
                display.setImageResource(R.drawable.march);
            }

            else if(Month.equals("Jan")){
                display.setImageResource(R.drawable.january);
            }

            else if(Month.equals("Feb")){
                display.setImageResource(R.drawable.february);
            }

            else if(Month.equals("May")){

                display.setImageResource(R.drawable.may);
            }

            else if(Month.equals("Jun")){
                display.setImageResource(R.drawable.june);
            }

            else if(Month.equals("Jul")){
                display.setImageResource(R.drawable.july);

            }


            else if(Month.equals("Aug")){
                display.setImageResource(R.drawable.august);
            }

            else if(Month.equals("Sep")){
                display.setImageResource(R.drawable.september);}

            else if(Month.equals("Oct")){
                display.setImageResource(R.drawable.october);}

            else if(Month.equals("Nov")){
                display.setImageResource(R.drawable.november);}

            else{
                display.setImageResource(R.drawable.december);}


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void PopulateView(){
        int value=15;
        int hour=8;
        int current_val= Integer.parseInt(current_date);
        int checked_val=Integer.parseInt(checked_date);
        Slot.setText("");
        Slot.setTextSize(18);
        Slot.setBackgroundColor(Color.parseColor("#008577"));
        String properTime;

        for(int i=0;i<40;++i){

            if(value==60){
                value=0;
                ++hour;
            }
            properTime=""+(hour)+":"+value;



            String Time="";
            if(hour<10){
                if(value!=0){
                    Time="0"+properTime;}

                else{
                    Time="0"+properTime+"0";
                }
            }

            else{
                if(value!=0){
                    Time=""+properTime;}

                else{
                    Time=""+properTime+"0";
                }
            }
            String hint=Time;
            if(hour<12){
                Time=Time+" AM";
            }

            else{
                Time=Time+" PM";
            }

            //have one version of cardview with textview then duplicate its layout

            CardView cardView=new CardView(this);
            LinearLayout temp=new LinearLayout(this);
            temp.setLayoutParams(Items.getLayoutParams());

            TextView a=new TextView(this);
            a.setLayoutParams(time.getLayoutParams());
            a.setTextSize(15);
            a.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            a.setTextColor(Color.BLACK);
            a.setText(Time);


            TextView b=new TextView(this);
            b.setId(i);
            b.setLayoutParams(Slot.getLayoutParams());
            b.setTextSize(18);
            b.setHint(hint);
            b.setHintTextColor(Color.TRANSPARENT);
            b.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            b.setPadding(12,12,12,12);
            b.setTextColor(Color.WHITE);
            b.setBackgroundColor(Color.parseColor("#008577"));
            b.setText("Free");
            Slot.setText("Free");

            if(current_val-checked_val>0){
                b.setBackgroundColor(Color.parseColor("#d13c04"));
                Slot.setBackgroundColor(Color.parseColor("#d13c04"));
                b.setText("--------------");
                Slot.setText("-----------");
            }


            //note setting hint is used as way of keeping track of which textview belongs to which time

            //more than a week ago old data pointless

            View c=new View(this);
            c.setLayoutParams(Divider.getLayoutParams());
            c.setBackgroundColor(Color.LTGRAY);


            FrameLayout d=new FrameLayout(this);
            d.setBackgroundColor(Color.LTGRAY);
            d.setLayoutParams(vert.getLayoutParams());

            temp.addView(a);
            temp.addView(d);
            temp.addView(b);
            cardView.addView(c);
            cardView.addView(temp);
            Items.addView(cardView);
            value=value+15;
            //set onclicklistener for all the textviews
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUp(v);
                }
            });

            TimeSlots.add(b);
        }

        Slot.setHintTextColor(Color.TRANSPARENT);
        Slot.setTextColor(Color.WHITE);
        Slot.setHint("08:00");
        Slot.setPadding(12,12,12,12);


        TimeSlots.add(Slot);
        CardView cardView=new CardView(this);
        View c=new View(this);
        c.setLayoutParams(Divider.getLayoutParams());
        c.setBackgroundColor(Color.LTGRAY);
        TextView space=new TextView(this);
        space.setTextSize(10);
        cardView.addView(c);
        cardView.addView(space);
        Items.addView(cardView);

    }

    public  int Index(String x){

        if(x.equals("Apr")){
           return  3;
        }

        else if(x.equals("Mar")){
            return  2;
        }

        else if(x.equals("Jan")){
            return  0;
        }

        else if(x.equals("Feb")){
            return  1;
        }

        else if(x.equals("May")){

            return  4;
        }

        else if(x.equals("Jun")){
            return  5;
        }

        else if(x.equals("Jul")){
            return  6;

        }


        else if(x.equals("Aug")){
            return  7;
        }

        else if(x.equals("Sep")){
            return  8;}

        else if(x.equals("Oct")){
            return  9;}

        else if(x.equals("Nov")){
            return  10;}

        else{
            return  11;}
    }

    public void DailySchedule(String date){
        Bookings.clear();
        int current_val=Integer.parseInt(current_date);
        int checked_val=Integer.parseInt(checked_date);
        ContentValues Params=new ContentValues();
        Params.put("DATE",date);

        if(current_val-checked_val<0) {
            AsyncHTTPPost Schedule = new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1611821/ConsultationSearch.php", Params) {
                @Override
                protected void onPostExecute(String output) {
                    try {
                        JSONArray results = new JSONArray(output);
                        for (int i = 0; i < results.length(); ++i) {
                            JSONObject obj = results.getJSONObject(i);
                            String Name = obj.getString("NAME");
                            String Surname = obj.getString("SURNAME");
                            String Identity = obj.getString("ID_NUMBER");
                            String Email = obj.getString("EMAIL_ADDRESS");
                            String Contact = obj.getString("CONTACT_NO");
                            String Date = obj.getString("DATE");
                            String Time = obj.getString("TIME").substring(0, 5);
                            int State = obj.getInt("STATE");
                            Booking temp = new Booking(Name, Surname, Identity, Contact, Email, Date, Time, State);
                            Bookings.add(temp);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    for (int j = 0; j < Bookings.size(); ++j) {
                        Bookings.get(j).OccupySlots(TimeSlots);
                    }

                }
            };

            Schedule.execute();
        }

    }

    public void PopUp(View V){
        V=(TextView)V;
        TextView timeDetails=(TextView)booking.findViewById(R.id.timedetails);
        TextView Patient=(TextView)booking.findViewById(R.id.patient);
        TextView Patientemail=(TextView)booking.findViewById(R.id.email);
        String time=((TextView) V).getHint().toString();
        int value=Integer.parseInt(time.substring(3,5))+15;

        timeDetails.setText(DayOfWeek+" , "+DayOfMonth+" "+MonthOfYear+"\n\nDuration "+time+"-"+time.substring(0,3)+""+value);

        if(((TextView) V).getText().toString().equals("Appointment")){

            Booking viewing=FindBooking(time);

            if(viewing!=null){

                String Date=DayOfMonth;
                String Day=MatchDay(DayOfWeek);
                String Name=viewing.getName();
                String Surname=viewing.getSurname();
                String Email=viewing.getEmail();
                Patient.setText(Name+"  "+Surname);
                Patientemail.setText(Email);
                Cancel.setText("Cancel");
                booking.show();

            }
        }


        else if(((TextView) V).getText().toString().equals("Free")){
            Patientemail.setText("");
            Patient.setText("");
            Checkout.setVisibility(View.INVISIBLE);
            Cancel.setText("Block");
            booking.show();
        }

        else if(((TextView) V).getText().toString().equals("Blocked")){
            timeDetails.setVisibility(View.INVISIBLE);
            Patient.setVisibility(View.INVISIBLE);
            Patientemail.setVisibility(View.INVISIBLE);
            Checkout.setVisibility(View.INVISIBLE);
            Cancel.setText("Free");
            booking.show();
        }
    }

    public Booking FindBooking(String time){
        for(int i=0;i<Bookings.size();++i){
            Booking t=Bookings.get(i);
            if(t.getTime().equals(time)){
                return  t;
            }
        }

        return  null;

    }



    public  String MatchDay(String line){
        String WeekDays[]={"Monday","TuesDay","Wednesday","Thursday","Friday","Saturday","Sunday"};

        for(int i=0;i<WeekDays.length;++i){
            if(WeekDays[i].substring(0,3).equals(line)){
                return WeekDays[i];
            }
        }
        return  line;
    }


    public  void BlockSlot(){

    }


    public void CancelSlot(){

    }


    }