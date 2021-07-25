package com.salazarev.hw14fragments;

import android.os.Bundle;
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

    private ViewGroup mThirdContainer;

    private FirstFragment mFirstFragment;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        mFirstFragment = (FirstFragment) mFragmentManager.findFragmentByTag(FIRST_FRAGMENT);
        if (mFirstFragment == null) {
            mFirstFragment = new FirstFragment();
            mFragmentManager.beginTransaction().add(R.id.first_fragment, mFirstFragment, FIRST_FRAGMENT).commit();
        }

        SecondFragment secondFragment = (SecondFragment) mFragmentManager.findFragmentByTag(SECOND_FRAGMENT);
        if (secondFragment == null) {
            secondFragment = new SecondFragment();
            mFragmentManager.beginTransaction().add(R.id.second_fragment, secondFragment, SECOND_FRAGMENT).commit();
        }

        ThirdFragment tf = (ThirdFragment) mFragmentManager.findFragmentByTag(THIRD_FRAGMENT);
        if (tf != null) {
            createThirdFragmentContainer();
        }
    }

    @Override
    public void setThirdFragment() {
        createThirdFragmentContainer();
        Fragment f3 = mFragmentManager.findFragmentByTag(THIRD_FRAGMENT);

        if (f3 == null) {
            f3 = ThirdFragment.newInstance(mFirstFragment.getMessage());
        } else {
            ((ThirdFragment) f3).setText(mFirstFragment.getMessage());
        }

        mFragmentManager.beginTransaction().replace(R.id.third_fragment, f3, THIRD_FRAGMENT).commit();
    }

    private void createThirdFragmentContainer() {
        ViewGroup root = findViewById(R.id.root);
        if (mThirdContainer == null) {
            createContainerThirdView();
            root.addView(mThirdContainer);
        }
    }

    private void createContainerThirdView() {
        mThirdContainer = new FragmentContainerView(this);
        mThirdContainer.setId(R.id.third_fragment);
        mThirdContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
    }
}