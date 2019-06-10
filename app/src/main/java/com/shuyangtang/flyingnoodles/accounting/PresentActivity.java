package com.shuyangtang.flyingnoodles.accounting;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PresentActivity extends Activity{
    Button back;
    private MyDBHandler dbHandler;
    List<Transaction> record;
    private boolean delState;
    private int lastPress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present);
        back = (Button) findViewById(R.id.back);
        dbHandler = new MyDBHandler(this);
        record = dbHandler.saveToList();
        final ListView list = (ListView) findViewById(R.id.listview);
        final MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (delState) {
                    if (lastPress < parent.getCount()) {
                        View delview = parent.getChildAt(lastPress).findViewById(R.id.linear_del);
                        if (null != delview) {
                            delview.setVisibility(View.GONE);
                        }
                    }
                    delState = false;
                    return;
                } else {
                    Log.d("click:", position + "");
                }
//                lastPress = position;
            }

        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            private View delview;
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int p=position;
                if (lastPress < parent.getCount()) {
                    delview = parent.getChildAt(lastPress).findViewById(R.id.linear_del);
                    if (null != delview) {
                        delview.setVisibility(View.GONE);
                    }
                }
                delview = view.findViewById(R.id.linear_del);
                delview.setVisibility(View.VISIBLE);
                delview.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delview.setVisibility(View.GONE);
                        record.remove(p);
                        adapter.notifyDataSetChanged();
                    }
                });
                delview.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delview.setVisibility(View.GONE);
                    }
                });
                lastPress = position;
                delState = true;
                return true;
            }
        });
    }

    public void backButtonClicked(View view){
        Intent intent = new Intent(PresentActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return record.size();
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
            LayoutInflater _LayoutInflater=LayoutInflater.from(PresentActivity.this);
            convertView=_LayoutInflater.inflate(R.layout.item_view, null);
            if(convertView!=null)

            {

                TextView ca=(TextView)convertView.findViewById(R.id.icategory);

                TextView na=(TextView)convertView.findViewById(R.id.iname);
                TextView co=(TextView)convertView.findViewById(R.id.icost);

                ca.setText(record.get(position).get_category());
                na.setText(record.get(position).get_productname());
                String cost = "- $" + String.valueOf(record.get(position).get_price());
                co.setText(cost);
            }

            return
                    convertView;
        }

    }
}
