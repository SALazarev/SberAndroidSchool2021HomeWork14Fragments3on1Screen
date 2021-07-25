package com.salazarev.hw14fragments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements SecondFragment.Callback {

    public static final String FIRST_FRAGMENT = "FIRST_FRAGMENT";
    public static final String SECOND_FRAGMENT = "SECOND_FRAGMENT";
    private static final String THIRD_FRAGMENT = "THIRD_FRAGMENT";

    public static final String ID = "ID";

    private ViewGroup mThirdContainer;

    private int id;

    private FirstFragment ff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            id = savedInstanceState.getInt(ID);
        }

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

        ThirdFragment tf = (ThirdFragment) fragmentManager.findFragmentByTag(THIRD_FRAGMENT);
        if (tf != null) {
            createThirdFragment();
        }

    }


    public void createThirdFragment() {
        createFragment2();
        Fragment f3 = getSupportFragmentManager().findFragmentByTag(THIRD_FRAGMENT);
        if (f3 != null) getSupportFragmentManager().beginTransaction().replace(id, f3, THIRD_FRAGMENT).commit();
    }


    @Override
    public void updateThirdFragment() {
        createFragment();
        Fragment f3 = getSupportFragmentManager().findFragmentByTag(THIRD_FRAGMENT);
        if (f3 == null) {
            f3 = ThirdFragment.newInstance(ff.getMessage());
        } else {
            ((ThirdFragment) f3).setText(ff.getMessage());
        }

        getSupportFragmentManager().beginTransaction().replace(mThirdContainer.getId(), f3, THIRD_FRAGMENT).commit();
    }

    private void createFragment2() {
        ViewGroup root = findViewById(R.id.root);
        if (mThirdContainer == null) {
            createContainerThirdView(id);
            root.addView(mThirdContainer);
        }
    }

    private void createFragment() {
        ViewGroup root = findViewById(R.id.root);
        if (mThirdContainer == null) {
            createContainerThirdView();
            root.addView(mThirdContainer);
        }
    }

    private void createContainerThirdView() {
        mThirdContainer = new FragmentContainerView(this);
        id = View.generateViewId();
        mThirdContainer.setId(id);
        mThirdContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
    }

    private void createContainerThirdView(int id) {
        mThirdContainer = new FragmentContainerView(this);
        mThirdContainer.setId(id);
        mThirdContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ID, id);
    }
}