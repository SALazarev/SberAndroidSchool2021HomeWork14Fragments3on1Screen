package com.salazarev.hw14fragments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements SecondFragment.Callback {

    public static final String FIRST_FRAGMENT = "FIRST_FRAGMENT";
    public static final String SECOND_FRAGMENT = "SECOND_FRAGMENT";
    private static final String THIRD_FRAGMENT = "THIRD_FRAGMENT";

    private String mMessage;
    private ViewGroup mThirdContainer;

    private FirstFragment ff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        ff = (FirstFragment) fragmentManager.findFragmentByTag(FIRST_FRAGMENT);
        if (ff == null) {
            ff = FirstFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.first_fragment, ff, FIRST_FRAGMENT).commit();
        }

        SecondFragment sf = (SecondFragment) fragmentManager.findFragmentByTag(SECOND_FRAGMENT);
        if (sf == null) {
            sf = SecondFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.second_fragment, sf, SECOND_FRAGMENT).commit();
        }

    }

    @Override
    public void updateThirdFragment() {
        mMessage = ff.getMessage();
        createFragment();
        Fragment f3 = getSupportFragmentManager().findFragmentByTag(THIRD_FRAGMENT);
        if (f3 == null) {
            f3 = ThirdFragment.newInstance(mMessage);
        } else {
            ((ThirdFragment) f3).setText(mMessage);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(mThirdContainer.getId(), f3, THIRD_FRAGMENT)
                .commit();
    }

    private void createFragment() {
        ViewGroup root = findViewById(R.id.root);
        if (mThirdContainer == null) {
            mThirdContainer = new FragmentContainerView(this);
            mThirdContainer.setId(View.generateViewId());
            mThirdContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            root.addView(mThirdContainer);
        }
    }
}