/**
 * Copyright (C) futuretek AG 2016
 * All Rights Reserved
 *
 * @author Artan Veliju
 */
package survey.android.futuretek.ch.ft_survey;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import survey.android.futuretek.ch.ft_survey.events.SkillDialogListener;

public class SkillsActivity extends BaseActivity {
    private ListView listview;
    public List<String> _productlist = new ArrayList<String>();
    private ListAdapter adapter;

    private final String EMPTY_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        listview = (ListView) findViewById(R.id.listView);
        final View mainTextView = findViewById(R.id.textLayout);
        mainTextView.setOnClickListener(new SkillDialogListener(this, getLayoutInflater(), this::insertSkill));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((ViewGroup)findViewById(R.id.textLayout)).removeAllViews();
        List<String> textArray = new ArrayList<>(1);
        textArray.add("Click to add a developer skill");
        animateText(textArray);
        _productlist.clear();
        _productlist = getDatabase().getAllSkills();
        adapter = new ListAdapter(this);
        listview.setAdapter(adapter);
    }

    private class ListAdapter extends BaseAdapter {
        LayoutInflater inflater;
        ViewHolder viewHolder;

        public ListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return _productlist.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_row, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textView1);
                viewHolder.textView.setOnClickListener(new SkillDialogListener(SkillsActivity.this, getLayoutInflater(), skillName -> {
                    final String id = viewHolder.textView.getText().toString();
                    updateSkill(id, skillName);
                }));
                viewHolder.delBtn = (Button) convertView.findViewById(R.id.deleteBtn);
                viewHolder.delBtn.setOnClickListener(v -> {
                    ViewGroup row = ((ViewGroup)v.getParent());
                    String id = ((TextView)row.findViewById(R.id.textView1)).getText().toString();
                    getDatabase().deleteSkill(id);
                    _productlist.remove(id);
                    adapter.notifyDataSetChanged();
                });
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(_productlist.get(position));
            return convertView;
        }
    }

    private class ViewHolder {
        TextView textView;
        Button delBtn;
    }

    private void insertSkill(final String skill){
        try {
            getDatabase().putSkill(skill);
            _productlist = getDatabase().getAllSkills();
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a skill's name
     * @param key the skill's key
     * @param value the skill's value
     */
    private void updateSkill(final String key, final String value){
        getDatabase().update(key, value);
        _productlist = getDatabase().getAllSkills();
        adapter.notifyDataSetChanged();
    }
}