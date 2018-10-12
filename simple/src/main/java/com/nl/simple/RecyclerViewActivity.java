package com.nl.simple;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends RefreshActivity {
    private RecyclerView recyclerView;

    @Override
    protected View generateView() {
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("str " + i);
        }
        recyclerView.setAdapter(new SimpleRecyclerAdapter(strings));
        return recyclerView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        super.onCheckedChanged(buttonView, isChecked);
        if (isChecked) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
