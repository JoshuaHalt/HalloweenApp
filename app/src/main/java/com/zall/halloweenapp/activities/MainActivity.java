package com.zall.halloweenapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zall.halloweenapp.HalloweenApplication;
import com.zall.halloweenapp.R;
import com.zall.halloweenapp.adapters.SimpleTextItemAdapter;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

    HalloweenApplication theApp = null;

    RelativeLayout rlBackground = null;

    TextView tvHalloweenGreeting = null;
    TextView tvYear = null;
    Button btnUp = null;
    TextView tvCounter = null;
    Button btnDown = null;

    LinearLayout llPastYearsArea = null;
    TextView tvPastYearsLabel = null;
    ImageView ivPastYearsArrow = null;
    ListView lvPastYears = null;

    Button btnSwitchMode = null;
    ImageView ivPumpkin = null;

    private int _thisYear;
    private int _currentYearCount;

    private ArrayList<String> _pastYears;
    private boolean isShowingPastYears = false;
    private boolean isNightMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        theApp = (HalloweenApplication) getApplicationContext();

        rlBackground = (RelativeLayout) findViewById(R.id.rlMainBackground);

        tvHalloweenGreeting = (TextView) findViewById(R.id.tvMainHalloweenGreeting);
        tvYear = (TextView) findViewById(R.id.tvMainYear);
        btnUp = (Button) findViewById(R.id.btnMainUp);
        tvCounter = (TextView) findViewById(R.id.tvMainCount);
        btnDown = (Button) findViewById(R.id.btnMainDown);

        llPastYearsArea = (LinearLayout) findViewById(R.id.llMainPastYearsArea);
        tvPastYearsLabel = (TextView) findViewById(R.id.tvMainPastYearLabel);
        lvPastYears = (ListView) findViewById(R.id.lvMainPastYears);
        ivPastYearsArrow = (ImageView) findViewById(R.id.ivMainPastYearArrow);

        btnSwitchMode = (Button) findViewById(R.id.btnMainDayNightMode);
        ivPumpkin = (ImageView) findViewById(R.id.ivMainPumpkin);

        _thisYear = new Date().getYear() + 1900;
        _currentYearCount = theApp.ReadCountForYear(_thisYear);

        tvYear.setText("" + _thisYear);
        tvCounter.setText("" + _currentYearCount);

        View.OnClickListener onArrowClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    _currentYearCount = Integer.parseInt(tvCounter.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                _currentYearCount = v.equals(btnUp) ? _currentYearCount + 1 : _currentYearCount - 1;

                theApp.WriteCountForYear(_thisYear, _currentYearCount);
                tvCounter.setText("" + _currentYearCount);
            }
        };

        btnUp.setOnClickListener(onArrowClick);
        btnDown.setOnClickListener(onArrowClick);

        _pastYears = theApp.GetPastYears();

        if (_pastYears.size() == 0)
            llPastYearsArea.setVisibility(View.GONE);

        lvPastYears.setAdapter(new SimpleTextItemAdapter<>(this, _pastYears));
        lvPastYears.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer pastYear = 0;
                try {
                    pastYear = Integer.parseInt(_pastYears.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tvYear.setText("" + pastYear);
                tvCounter.setText("" + theApp.ReadCountForYear(pastYear));

                if (pastYear.equals(_thisYear)) {
                    btnUp.setVisibility(View.VISIBLE);
                    btnDown.setVisibility(View.VISIBLE);
                } else {
                    btnUp.setVisibility(View.GONE);
                    btnDown.setVisibility(View.GONE);
                }

                hidePastYears();
            }
        });

        llPastYearsArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowingPastYears)
                    hidePastYears();
                else
                    showPastYears();
            }
        });

        btnSwitchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNightMode)
                    showDayMode();
                else
                    showNightMode();
            }
        });

        ivPumpkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotateAnim = new RotateAnimation(0, 360,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                rotateAnim.setDuration(1000);
                rotateAnim.setInterpolator(new LinearInterpolator());
                ivPumpkin.startAnimation(rotateAnim);
            }
        });
    }

    private void hidePastYears() {
        lvPastYears.setVisibility(View.GONE);

        RotateAnimation rotateAnim = new RotateAnimation(180, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(500);
        rotateAnim.setFillAfter(true);
        rotateAnim.setInterpolator(new LinearInterpolator());
        ivPastYearsArrow.startAnimation(rotateAnim);

        isShowingPastYears = false;
    }

    private void showPastYears() {
        lvPastYears.setVisibility(View.VISIBLE);

        RotateAnimation rotateAnim = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(500);
        rotateAnim.setFillAfter(true);
        rotateAnim.setInterpolator(new LinearInterpolator());
        ivPastYearsArrow.startAnimation(rotateAnim);

        isShowingPastYears = true;
    }

    private void showDayMode() {
        if (isShowingPastYears)
            hidePastYears();

        rlBackground.setBackgroundColor(getResources().getColor(R.color.day_background));
        tvHalloweenGreeting.setTextColor(getResources().getColor(R.color.day_text));
        tvYear.setTextColor(getResources().getColor(R.color.day_text));

        btnUp.setBackgroundResource(R.drawable.arrow_up_day);
        tvCounter.setTextColor(getResources().getColor(R.color.day_text));
        btnDown.setBackgroundResource(R.drawable.arrow_down_day);

        tvPastYearsLabel.setTextColor(getResources().getColor(R.color.day_text));
        ivPastYearsArrow.setBackgroundResource(R.drawable.arrow_up_day);

        btnSwitchMode.setBackgroundResource(R.drawable.day_theme_button);

        isNightMode = false;
    }

    private void showNightMode() {
        if (isShowingPastYears)
            hidePastYears();

        rlBackground.setBackgroundColor(getResources().getColor(R.color.night_background));
        tvHalloweenGreeting.setTextColor(getResources().getColor(R.color.night_text));
        tvYear.setTextColor(getResources().getColor(R.color.night_text));

        btnUp.setBackgroundResource(R.drawable.arrow_up_night);
        tvCounter.setTextColor(getResources().getColor(R.color.night_text));
        btnDown.setBackgroundResource(R.drawable.arrow_down_night);

        tvPastYearsLabel.setTextColor(getResources().getColor(R.color.night_text));
        ivPastYearsArrow.setBackgroundResource(R.drawable.arrow_up_night);

        btnSwitchMode.setBackgroundResource(R.drawable.night_theme_button);

        isNightMode = true;
    }
}