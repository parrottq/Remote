package com.parrott.quinn.remote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class ConnectActivity extends AppCompatActivity {

    private Button button;

    private TextView ipText;
    private CheckBox ipBox;

    private TextView portText;
    private CheckBox portBox;

    private CheckBox networkBox;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        button = (Button) findViewById(R.id.goButton);

        ipText = (TextView) findViewById(R.id.ipText);
        ipBox = (CheckBox) findViewById(R.id.ipBox);

        portText = (TextView) findViewById(R.id.portText);
        portBox = (CheckBox) findViewById(R.id.portBox);

        networkBox = (CheckBox) findViewById(R.id.networkBox);

        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        ipText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ipBox.setChecked(Utils.validIP(s.toString()));
            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        portText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    portBox.setChecked(Utils.validPort(Integer.parseInt(s.toString())));
                } else {
                    portBox.setChecked(false);
                }


            }

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        CompoundButton.OnCheckedChangeListener checker = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                button.setEnabled(ipBox.isChecked() && portBox.isChecked());
            }
        };

        ipBox.setOnCheckedChangeListener(checker);
        portBox.setOnCheckedChangeListener(checker);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                ipText.setEnabled(false);
                ipBox.setEnabled(false);
                portText.setEnabled(false);
                portBox.setEnabled(false);
                button.setEnabled(false);
                networkBox.setEnabled(false);

                getPreferences(Context.MODE_PRIVATE).edit().putString("com.parrott.quinn.LAST_IP", ipText.getText().toString()).commit();
                getPreferences(Context.MODE_PRIVATE).edit().putString("com.parrott.quinn.LAST_PORT", portText.getText().toString()).commit();

                CheckBox checkBox = (CheckBox) findViewById(R.id.networkBox);
                if (checkBox.isChecked()) {
                    Networker.start(ipText.getText().toString(), Integer.parseInt(portText.getText().toString()));
                }
                //startSlider();
            }
        });

        Log.i("Remote", "String :" + getPreferences(Context.MODE_PRIVATE).getString("com.parrott.quinn.LAST_IP", ""));
        ipText.setText(getPreferences(Context.MODE_PRIVATE).getString("com.parrott.quinn.LAST_IP", ""));
        Log.i("Remote", "Int :" + getPreferences(Context.MODE_PRIVATE).getString("com.parrott.quinn.LAST_PORT", ""));
        portText.setText(getPreferences(Context.MODE_PRIVATE).getString("com.parrott.quinn.LAST_PORT", ""));
    }

    public static void startSlider(){
        Intent e = new Intent(ConnectActivity.context, SliderControllerActivity.class);
        context.startActivity(e);
    }

}
