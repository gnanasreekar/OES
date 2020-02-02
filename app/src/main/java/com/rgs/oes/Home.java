package com.rgs.oes;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rgs.oes.Auth.Login;
import com.rgs.oes.Event.Events;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    NavigationView navView;
    DrawerLayout drawerLayout;
    LinearLayout warninglayout;
    TextView nav_namec, nav_emailc, nav_rollno, today_powerusage_tv, months_powerusage_tv, today_cost, month_cost, generator_usagetv, date_tv, warnings, generator_today,costfortodat;
    int dpb, flag = 0;
    Integer TEC;
    float Todayscos, Version;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    String generatorusage;
    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
    static Home instance;
    CountDownTimer mCountDownTimer;
    View parent_view;
    long date_ship_millis;
    RelativeLayout nav_layout;
    Menu menuList;
    MenuItem item;
    DatabaseReference databaseReference;
    CharSequence s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        instance = this;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        {

            toolbar = findViewById(R.id.toolbar);
            navView = findViewById(R.id.nav_view);
            drawerLayout = findViewById(R.id.drawer_layout);
            setSupportActionBar(toolbar);
        }

        {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            navView.setNavigationItemSelectedListener(this);

        }

        sharedPreferences = getApplicationContext().getSharedPreferences("sp", 0);

        navviewdata();

        Date d = new Date();
        s  = DateFormat.format("MMMM d, yyyy HH:mm:ss", d.getTime());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Last_used_on/" + sharedPreferences.getString("uid","Not aval"));
        databaseReference.child("Name").setValue(sharedPreferences.getString("name","Not aval"));
        databaseReference.child("Email").setValue(sharedPreferences.getString("email","Not aval"));
        databaseReference.child("Date").setValue(s);

    }

    public static Home getInstance() {
        return instance;
    }

    public void navviewdata() {
        View nav_view = navView.getHeaderView(0);
        nav_emailc = nav_view.findViewById(R.id.nav_email);
        nav_namec = nav_view.findViewById(R.id.nav_name);
        nav_rollno = nav_view.findViewById(R.id.nav_rollno);
        String fb_name_main = sharedPreferences.getString("name", "NO data found");
        String fb_email_main = sharedPreferences.getString("email", "NO data found");
        nav_rollno.setText(sharedPreferences.getString("rollno", "NO data found"));
        nav_namec.setText(fb_name_main);
        nav_emailc.setText(fb_email_main);
        setTitle("Hi, " + fb_name_main);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();l
            showexitDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(Home.this , Profile.class));
        } else if (id == R.id.nav_events){
            startActivity(new Intent(Home.this , Events.class));
        } else if (id == R.id.nav_Statistics){

        } else if (id == R.id.nav_notification){

        } else if (id == R.id.nav_signout){
            showsignoutDialog();
        }
        else if (id == R.id.nav_test){
            startActivity(new Intent(Home.this , Test.class));
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Exit Dialog
    private void showexitDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.exitdialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void showsignoutDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.signout);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialog.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this, Login.class));
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    static int i = 0;

    public static String getFormattedDateSimple(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
        return newFormat.format(new Date(dateTime));
    }

}