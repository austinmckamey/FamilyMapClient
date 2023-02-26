package com.example.familymapclient.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.familymapclient.R;
import com.example.familymapclient.model.DataCache;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        DataCache dataCache = DataCache.getInstance();

        Switch lifeStory = (Switch) findViewById(R.id.switch1);
        lifeStory.setChecked(dataCache.isLifeStory());
        Switch familyTree = (Switch) findViewById(R.id.switch2);
        familyTree.setChecked(dataCache.isFamilyTree());
        Switch spouseLine = (Switch) findViewById(R.id.switch3);
        spouseLine.setChecked(dataCache.isSpouseLines());
        Switch fatherSide = (Switch) findViewById(R.id.switch4);
        fatherSide.setChecked(dataCache.isFatherSide());
        Switch motherSide = (Switch) findViewById(R.id.switch5);
        motherSide.setChecked(dataCache.isMotherSide());
        Switch maleEvent = (Switch) findViewById(R.id.switch6);
        maleEvent.setChecked(dataCache.isMaleEvent());
        Switch femaleEvent = (Switch) findViewById(R.id.switch7);
        femaleEvent.setChecked(dataCache.isFemaleEvent());

        LinearLayout logout = findViewById(R.id.logout);

        lifeStory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setLifeStory(b);
            }
        });
        familyTree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setFamilyTree(b);
            }
        });
        spouseLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setSpouseLines(b);
            }
        });
        fatherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setFatherSide(b);
            }
        });
        motherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setMotherSide(b);
            }
        });
        maleEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setMaleEvent(b);
            }
        });
        femaleEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dataCache.setFemaleEvent(b);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataCache.setLifeStory(true);
                dataCache.setFamilyTree(true);
                dataCache.setSpouseLines(true);
                dataCache.setMaleEvent(true);
                dataCache.setFemaleEvent(true);
                dataCache.setFatherSide(true);
                dataCache.setMotherSide(true);
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent= new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
}