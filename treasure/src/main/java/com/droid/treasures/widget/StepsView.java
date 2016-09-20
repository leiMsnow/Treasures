package com.droid.treasures.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.droid.treasures.R;

import java.util.List;


public class StepsView extends LinearLayout implements StepsViewIndicator.OnDrawListener {

    private StepsViewIndicator mStepsViewIndicator;
    private FrameLayout mLabelsLayout;
    private int mProgressColorIndicator = Color.GREEN;
    private int mLabelColorIndicator = Color.DKGRAY;
    private int mBarColorIndicator = Color.DKGRAY;
    private int mCompletedPosition = 0;
    private List<StepEntity> mSteps;
    private int maxStep;


    public StepsView(Context context) {
        this(context, null);
    }

    public StepsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsView(Context context, AttributeSet attrs,
                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_steps_view, this);
        mStepsViewIndicator = (StepsViewIndicator) rootView.findViewById(R.id.steps_indicator_view);
        mStepsViewIndicator.setDrawListener(this);
        mLabelsLayout = (FrameLayout) rootView.findViewById(R.id.labels_container);
    }


    public StepsView setSteps(List<StepEntity> steps) {
        steps.add(0, new StepEntity("", 0));
        mSteps = steps;
        mStepsViewIndicator.setSteps(steps);
        return this;
    }

    public StepsView setProgressColorIndicator(int progressColorIndicator) {
        mProgressColorIndicator = progressColorIndicator;
        mStepsViewIndicator.setProgressColor(mProgressColorIndicator);
        return this;
    }

    public StepsView setLabelColorIndicator(int labelColorIndicator) {
        mLabelColorIndicator = labelColorIndicator;
        return this;
    }

    public StepsView setBarColorIndicator(int barColorIndicator) {
        mBarColorIndicator = barColorIndicator;
        mStepsViewIndicator.setBarColor(mBarColorIndicator);
        return this;
    }

    public StepsView setCompletedPosition(int completedPosition) {
        mCompletedPosition = completedPosition;
        mStepsViewIndicator.setCompletedPosition(mCompletedPosition);
        return this;
    }

    public void drawView() {
        if (mSteps == null) {
            throw new IllegalArgumentException("mSteps must not be null.");
        }

        mStepsViewIndicator.invalidate();
    }

    @Override
    public void onReady() {
        drawLabels();
    }

    private void drawLabels() {
        List<StepEntity> indicatorPosition = mStepsViewIndicator.getTextXPosition();

        if (mSteps != null) {
            for (int i = 0; i < mSteps.size(); i++) {
                TextView textView = new TextView(getContext());
                textView.setText(mSteps.get(i).getLabel());
                textView.setTextColor(mSteps.get(i).getStep() <= mCompletedPosition
                        ? mLabelColorIndicator : mBarColorIndicator);
                textView.setTextSize(12);
                textView.setGravity(Gravity.CENTER);
                textView.setX(indicatorPosition.get(i).getLabelPosition());
                textView.setLayoutParams(
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                mLabelsLayout.addView(textView);

            }
        }
    }

    public static class StepEntity {
        private int step;
        private String label;
        private float stepPosition;
        private float labelPosition;

        public StepEntity(String stepLabel, int stepNum) {
            this.label = stepLabel;
            this.step = stepNum;
        }

        int getStep() {
            return step;
        }

        String getLabel() {
            return label;
        }

        float getStepPosition() {
            return stepPosition;
        }

        void setStepPosition(float stepPosition) {
            this.stepPosition = stepPosition;
        }

        float getLabelPosition() {
            return labelPosition;
        }

        void setLabelPosition(float labelPosition) {
            this.labelPosition = labelPosition;
        }
    }
}
