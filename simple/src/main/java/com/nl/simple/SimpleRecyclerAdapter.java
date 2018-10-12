package com.nl.simple;

import java.util.List;

/**
 * 简单适配器
 */

public class SimpleRecyclerAdapter<T> extends com.nl.recyclerview.SimpleRecyclerAdapter<T> {
    public SimpleRecyclerAdapter(List<T> data) {
        super(R.layout.list_item, data);
    }
}
