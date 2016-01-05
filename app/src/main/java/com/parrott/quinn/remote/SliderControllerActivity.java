package com.parrott.quinn.remote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SliderControllerActivity extends AppCompatActivity {

    private TextView leftView;
    private TextView rightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_controller);

        leftView = (TextView) findViewById(R.id.leftView);
        rightView = (TextView) findViewById(R.id.rightView);

        leftView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int width = leftView.getWidth();
                float speed = event.getY();
                if (speed < 0) {
                    speed = 0;
                } else if (speed > width) {
                    speed = width;
                }

                speed = speed * (255.0f / width);

                leftView.setText(Integer.toString((int)speed));
                Networker.setLeftSpeed((int) speed);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    leftView.setText("128");
                    Networker.setLeftSpeed(128);
                }
                return true;
            }
        });

        rightView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int width = rightView.getWidth();
                float speed = event.getY();
                if (speed < 0) {
                    speed = 0;
                } else if (speed > width) {
                    speed = width;
                }

                speed = speed * (255.0f / width);

                rightView.setText(Integer.toString((int) speed));
                Networker.setRightSpeed((int) speed);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    rightView.setText("128");
                    Networker.setRightSpeed(128);
                }
                return true;
            }
        });
    }
}
