package com.iotek.fgs.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iotek.fgs.list.PersonIcList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgs on 2016/1/11.
 */
public class PersonIcActivity extends Activity{
    ListView lv;
    List<Integer> list = new ArrayList<>();
    int [] a= {R.drawable.shaosiming1,R.drawable.shaosiming2,R.drawable.shaosiming3,R.drawable.shaosiming4,R.drawable.shaosiming5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personic);
        for (int i=0;i<a.length;i++){
            list.add(a[i]);
        }
        lv = (ListView)findViewById(R.id.personic_lv);
        lv.setAdapter(new PersonIcList(list,PersonIcActivity.this));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("person",position);
                setResult(2,intent);
                finish();
            }
        });
    }
}
