package com.nl.simple;

import android.graphics.Color;
import android.view.View;

public class ViewActivity extends RefreshActivity {
    @Override
    protected View generateView() {
        View view = new View(this){
            @Override
            public boolean canScrollHorizontally(int direction) {
                return !isChecked;
            }

            @Override
            public boolean canScrollVertically(int direction) {
                return isChecked;
            }
        };
        view.setClickable(true);
        view.setBackgroundColor(Color.GREEN);
        return view;
    }
}
