package com.littlezuo.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import medusa.theone.waterdroplistview.view.WaterDropListView;

public class Main2Activity extends Activity implements WaterDropListView.IWaterDropListViewListener {

private Handler handler = new Handler(){
    public void handleMessage(Message msg){
        switch (msg.what){
            case 1:
                mWaterListView.stopRefresh();
                break;
            case 2:
                mWaterListView.stopLoadMore();
                break;
        }
    }
};
    private ArrayList<String> array = new ArrayList<String>();
    private WaterDropListView mWaterListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initdata();
        mWaterListView = (WaterDropListView) findViewById(R.id.waterdrop_listview);
        mWaterListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,array));
        mWaterListView.setWaterDropListViewListener(this);
        mWaterListView.setPullLoadEnable(true);
        mWaterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Main2Activity.this, "点击了"+position, Toast.LENGTH_SHORT).show();//位置从1开始
            }
        });
    }

    public void initdata() {

        for (char i = 'a'; i <= 'z'; i++) {
            array.add(String.valueOf(i));
            System.out.print(i);
        }
    }


    @Override
    public void onRefresh() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onLoadMore() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

