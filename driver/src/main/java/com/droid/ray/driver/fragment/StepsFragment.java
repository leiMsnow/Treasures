package com.droid.ray.driver.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.ray.driver.R;
import com.droid.treasures.widget.StepsView;


public class StepsFragment extends Fragment {

    StepsView mStepsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String[] labels = {"预定满三件\n$99.00", "预定满十件\n$88.00", "预定满二十件\n$77.00"};

        mStepsView = (StepsView) view.findViewById(R.id.stepsView);

        mStepsView.setCompletedPosition(2).setLabels(labels)
                .setBarColorIndicator(
                        getContext().getResources().getColor(android.R.color.darker_gray))
                .setProgressColorIndicator(getContext().getResources().getColor(android.R.color.holo_orange_dark))
                .setLabelColorIndicator(getContext().getResources().getColor(android.R.color.holo_orange_dark))
                .drawView();
    }

}
