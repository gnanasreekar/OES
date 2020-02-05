package com.rgs.oes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alexfu.countdownview.CountDownView;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Test extends AppCompatActivity {

    private CountDownView countDown;
    private TextView textView;
    private TextInputEditText question;
    private ProgressBar progressBar;
    private TextView quest_tv;
    int MAX_STEP, selectedId, pos = 0;
    int current_step = 1;
    ArrayList<String> que = new ArrayList<String>();
    ArrayList<String> A = new ArrayList<String>();
    ArrayList<String> B = new ArrayList<String>();
    ArrayList<String> C = new ArrayList<String>();
    ArrayList<String> D = new ArrayList<String>();
    ArrayList<String> ans = new ArrayList<String>();
    ArrayList<String> key = new ArrayList<String>();
    private RadioGroup radioquestionGroup;
    private RadioButton radioSexButton;
    RadioButton Ar,Br,Cr,Dr;
    String keyi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbartest);
        setSupportActionBar(toolbar);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.dark));
        setTitle("Progress");
        getSupportActionBar().setSubtitle("");

        Intent intent = getIntent();
        keyi = intent.getStringExtra("testid");

        Ar = findViewById(R.id.a);
        Br = findViewById(R.id.b);
        Cr = findViewById(R.id.c);
        Dr = findViewById(R.id.d);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Tests/"+ keyi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // get total available quest
                MAX_STEP = (int) dataSnapshot.getChildrenCount();
                Toast.makeText(Test.this, MAX_STEP+"", Toast.LENGTH_SHORT).show();

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    que.add(childDataSnapshot.child("q").getValue().toString());
                    A.add(childDataSnapshot.child("a").getValue().toString());
                    B.add(childDataSnapshot.child("b").getValue().toString());
                    C.add(childDataSnapshot.child("c").getValue().toString());
                    D.add(childDataSnapshot.child("d").getValue().toString());
                    ans.add(childDataSnapshot.child("ans").getValue().toString());
                    key.add(childDataSnapshot.getKey());
                }
                steppedprogress();
                starttest(0);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void starttest(int pos){

        if (pos <= MAX_STEP-1) {
            quest_tv.setText(que.get(pos));
            Ar.setText(A.get(pos));
            Br.setText(B.get(pos));
            Cr.setText(C.get(pos));
            Dr.setText(D.get(pos));
        } else {
        }
    }

    public void setans(int pro){
        radioquestionGroup = findViewById(R.id.radioGroup);
        selectedId=radioquestionGroup.getCheckedRadioButtonId();
        radioSexButton=(RadioButton)findViewById(selectedId);
        if (selectedId == -1){
        } else {
            if (radioSexButton.getText().equals(ans.get(pro))){
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            }
        }

        radioquestionGroup.clearCheck();
    }

    private void steppedprogress() {
        quest_tv = (TextView) findViewById(R.id.question);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setMax(MAX_STEP);
        progressBar.setProgress(current_step);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        ((LinearLayout) findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backStep(current_step);
            }
        });

        ((LinearLayout) findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep(current_step);
            }
        });

        String str_progress = String.format(getString(R.string.step_of), current_step, MAX_STEP);
        quest_tv.setText(str_progress);
    }

    private void nextStep(int progress) {
        if (progress <= MAX_STEP) {
            progress++;
            current_step = progress;
            ViewAnimation.fadeOutIn(quest_tv);
        }

        if (current_step >= MAX_STEP+1){
            Toast.makeText(this, "Last question", Toast.LENGTH_SHORT).show();
        } else {
            pos++;
        }
        progressBar.setProgress(current_step);
        starttest(pos);
        setans(pos-1);
    }

    private void backStep(int progress) {
        if (progress > 1) {
            progress--;
            current_step = progress;
            ViewAnimation.fadeOutIn(quest_tv);
        }

        Log.d("Fasakcurrentstep" , current_step+"");
        Log.d("Fasakmaxstep" , MAX_STEP+"");

        if (current_step <= 1){
            Toast.makeText(this, "Last question", Toast.LENGTH_SHORT).show();
        } else {
            pos--;
        }

        progressBar.setProgress(current_step);
        if (pos >= 0){
            starttest(pos);
            setans(pos-1);
        } else {
            Toast.makeText(this, "First Question", Toast.LENGTH_SHORT).show();
        }

    }
}
