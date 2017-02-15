package com.iotek.fgs.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by fgs on 2016/1/2.
 */
public class SelGroupActivity extends Activity {
    ListView lv;
    String[] groups = { "助教", "家人", "朋友" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectgroup);
        lv = (ListView)findViewById(R.id.selectgroup_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.selectgroupitem,R.id.selectgroupitem_tv,groups);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("groupId",position);
                setResult(1,intent);
                finish();
            }
        });
    }
}
