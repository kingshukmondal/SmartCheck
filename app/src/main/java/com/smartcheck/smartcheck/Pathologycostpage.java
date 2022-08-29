package com.smartcheck.smartcheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smartcheck.smartcheck.Adapter.Medicineadapter;
import com.smartcheck.smartcheck.ModalClass.medicinemodalclass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pathologycostpage extends AppCompatActivity {

    ImageView iv_search,end_Search;
    LinearLayout searchbox;
    RecyclerView medicinerecyclerview;
    Medicineadapter adapter;
    List<medicinemodalclass> list;
    LinearLayoutManager manager;
    ProgressDialog progress;
    EditText et_searchmedicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathologycostpage);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.specialgreen));
        }

        medicinerecyclerview = findViewById(R.id.pathologyrv);
        manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        medicinerecyclerview.setLayoutManager(manager);
        list = new ArrayList<>();
        iv_search=findViewById(R.id.iv_search);
        searchbox=findViewById(R.id.searchbox);
        end_Search=findViewById(R.id.end_Search);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        et_searchmedicine=findViewById(R.id.et_searchmedicine);
        et_searchmedicine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String g=et_searchmedicine.getText().toString();
                search(g);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ImageView iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);
            }
        });


        end_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbox.setVisibility(View.GONE);
                iv_search.setVisibility(View.VISIBLE);
                et_searchmedicine.setText("");
            }
        });
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbox.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.GONE);
                et_searchmedicine.requestFocus();

            }
        });
        progress.show();
        progress.setContentView(R.layout.loadingprogess);
        progress.getWindow().setBackgroundDrawableResource(cn.pedant.SweetAlert.R.color.float_transparent);

        updation();






    }

    private void search(String g) {
        ArrayList<medicinemodalclass> list2=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            String p=list.get(i).getName();
            if(p.toLowerCase(Locale.ROOT).contains(g.toLowerCase(Locale.ROOT)))
            {
                list2.add(list.get(i));
            }
        }

        adapter.setlist(list2);
        medicinerecyclerview.setAdapter(adapter);
    }


    private void updation() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        String url="https://script.google.com/macros/s/AKfycbzZYjhM6Mre1WY_i-NJ0C-X9uUbW4KCSpA-dkIelaiOLTxrjrPnd_DofFyPWpOhrkEWbg/exec";
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject js = new JSONObject(response);
                    JSONArray arr = js.getJSONArray("data");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject j = arr.getJSONObject(i);
                        String name = j.getString("name"),
                                mrp = j.getString("price");


                        medicinemodalclass obj = new medicinemodalclass();
                        obj.setPrice(mrp);
                        obj.setName(name);
                        list.add(obj);
                        progress.dismiss();
                    }
                    adapter = new Medicineadapter(getApplicationContext(), list);
                    medicinerecyclerview.setAdapter(adapter);


                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pathologycostpage.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(mStringRequest);
    }



    void update(String k)
    {
        ArrayList<medicinemodalclass> list1=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            String name=list.get(i).getName();
            if(name.toLowerCase(Locale.ROOT).contains(k.toLowerCase(Locale.ROOT)))
            {
                list1.add(list.get(i));
            }
            adapter.setlist(list1);
            medicinerecyclerview.setAdapter(adapter);
        }
    }
}