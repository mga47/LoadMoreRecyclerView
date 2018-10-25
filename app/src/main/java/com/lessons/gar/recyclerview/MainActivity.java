package com.lessons.gar.recyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mList;
    private ArrayList<ListItemModel> mItems = new ArrayList<>();
    private AdapterItems mAdapter;
    private int lastItem = 1;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        mList = findViewById(R.id.list);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        loadData();
        mAdapter = new AdapterItems(getApplicationContext(), mList);
        mAdapter.setmItems(mItems);
        mAdapter.setOnLoadMoreListener(new AdapterItems.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mItems.add(null);
                mAdapter.notifyItemInserted(mItems.size() - 1);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mItems.remove(mItems.size() - 1);
                        loadData();
                        mAdapter.setmItems(mItems);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                    }
                }, 1000);
            }
        });
        mList.setAdapter(mAdapter);
    }

    private void loadData() {
        for (int i = lastItem; i < lastItem + 10; ++i) {
            mItems.add(new ListItemModel(R.drawable.ic_android_black_24dp, "" + i, "" + i));
        }
        lastItem = mItems.size();
    }
}
