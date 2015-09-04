package com.fengnanyue.turingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HttpGetDataListener,View.OnClickListener {

    private HttpData httpData;
    private List<Data> lists;
    private ListView lv;
    private EditText sendtext;
    private Button sendbtn;
    private String content_str;
    private TextAdapter adapter;
    private String[] welcome_array;
    private double currentTime,oldTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        lv = (ListView) findViewById(R.id.lv);
        sendtext = (EditText) findViewById(R.id.sendText);
        sendbtn = (Button) findViewById(R.id.sendbtn);
        lists = new ArrayList<Data>();
        sendbtn.setOnClickListener(this);
        adapter = new TextAdapter(lists,this);
        lv.setAdapter(adapter);
        Data data;
        data = new Data(getRandomWelcomeTips(),Data.RECEIVER,getTime());
        lists.add(data);
    }

    private String getRandomWelcomeTips(){
        String welcome_tip = null;
        welcome_array = this.getResources().getStringArray(R.array.welcome_tips);
        int index = (int)(Math.random()*(welcome_array.length-1));
        welcome_tip = welcome_array[index];
        return welcome_tip;
    }
    @Override
    public void GetDataUrl(String data) {
//        System.out.println(data);
        parseText(data);
    }

    public void parseText(String str){

        try {
            JSONObject jb = new JSONObject(str);
//            System.out.println(jb.getString("code"));
//            System.out.println(jb.getString("text"));
            Data listData;
            listData = new Data(jb.getString("text"),Data.RECEIVER,getTime());

            lists.add(listData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        getTime();
        content_str = sendtext.getText().toString();
        sendtext.setText("");
        String dropk = content_str.replace(" ","");
        String droph = dropk.replace("\n","");

        Data Data = null;
        Data = new Data(content_str,Data.SEND,getTime());
        lists.add(Data);
        if(lists.size()>30){
            for (int i =0;i<lists.size();i++){
                lists.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
        httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=abd243570d33a1cf32db69d2bdc69a82&info="+droph,this).execute();

    }
    private String getTime(){
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date curDate =new Date();
        String str = format.format(curDate);
        if(currentTime- oldTime>=5*60*1000 ){
            oldTime = currentTime;
            return str;
        }else{
            return "";

        }
    }
}
