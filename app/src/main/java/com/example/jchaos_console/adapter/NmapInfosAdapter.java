package com.example.jchaos_console.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jchaos_console.R;
import com.example.jchaos_console.activity.TerminalControlActivity;
import com.example.jchaos_console.bundleBean.TerminalBundle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class NmapInfosAdapter extends BaseAdapter {

    private Set<String> set;
    private Context mContext;
    private ArrayList<String> list;
    private static final int VIEW_COUNT = 2;
    NmapHolder holder = null;


    public NmapInfosAdapter(Set<String> set, Context mContext) {
        this.set = set;
        this.mContext = mContext;
        list = new ArrayList<>();
        initList();
    }

    public void initList() {
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().toString());
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new NmapHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ternimal_listview, null);

            holder.mText = convertView.findViewById(R.id.list_item_text);
            holder.mText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TerminalControlActivity.class);
                    Bundle bundle = new Bundle();
                    TerminalBundle terminalBundle = new TerminalBundle();
                    terminalBundle.setTerminalID(holder.mText.getText().toString());
                    bundle.putSerializable("TerminalInfo", terminalBundle);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (NmapHolder) convertView.getTag();
        }
        holder.mText.setText(list.get(position));
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    class NmapHolder {
        private TextView mText;
    }
}
