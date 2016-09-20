package com.droid.ray.driver.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.ray.driver.R;
import com.droid.treasures.widget.StepsView;

import java.util.ArrayList;
import java.util.List;


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
        final String[] labels = {"拼团满1件\n$99.00", "拼团满10件\n$88.00", "拼团满20件\n$77.00"};
        List<StepsView.StepEntity> stepEntities = new ArrayList<>();
        stepEntities.add(new StepsView.StepEntity(labels[0], 1));
        stepEntities.add(new StepsView.StepEntity(labels[1], 10));
        stepEntities.add(new StepsView.StepEntity(labels[2], 20));

        mStepsView = (StepsView) view.findViewById(R.id.stepsView);

        mStepsView.setCompletedPosition(19).setSteps(stepEntities)
                .setBarColorIndicator(
                        getContext().getResources().getColor(android.R.color.darker_gray))
                .setProgressColorIndicator(getContext().getResources().getColor(android.R.color.holo_orange_dark))
                .setLabelColorIndicator(getContext().getResources().getColor(android.R.color.holo_orange_dark))
                .drawView();
    }
}
