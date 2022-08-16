package com.smartcheck.smartcheck;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Mediciinecostpage extends AppCompatActivity {

    RecyclerView medicinerecyclerview;
    Medicineadapter adapter;
    List<medicinemodalclass> list;
    LinearLayoutManager manager;
    ProgressDialog progress;
    ImageView iv_back;
    EditText et_searchmedicine;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediciinecostpage);
        getSupportActionBar().hide();
        AlphaAnimation click_animation = new AlphaAnimation(1f, 0.8f);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);





        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.specialgreen));
        }

        medicinerecyclerview = findViewById(R.id.medicinerecyclerview);
        manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        medicinerecyclerview.setLayoutManager(manager);
        list = new ArrayList<>();
        progress.show();
        progress.setContentView(R.layout.loadingprogess);
        progress.getWindow().setBackgroundDrawableResource(cn.pedant.SweetAlert.R.color.float_transparent);



        et_searchmedicine=findViewById(R.id.et_searchmedicine);
        et_searchmedicine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String k=et_searchmedicine.getText().toString();
                update(k);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(click_animation);
                Intent i=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);
            }
        });
       updation();

      /*  try {
            JSONObject obj1=new JSONObject(fromassets());
            JSONArray arr = obj1.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject j = arr.getJSONObject(i);
                int productid = 10;
                String name = j.getString("name");
//                        packsize = j.getString("packsize"),
//                        rating_count = j.getString("rating_count"),
                        String
                       mrp = j.getString("mrp");
//                int rating = j.getInt("rating");

         //       Toast.makeText(Mediciinecostpage.this, name+" "+price, Toast.LENGTH_SHORT).show();
                medicinemodalclass obj = new medicinemodalclass();
                obj.setPrice(mrp);
                obj.setName(name);
                list.add(obj);
            }
            Collections.sort(list, new Comparator<medicinemodalclass>()


            {

                @Override
                public int compare(medicinemodalclass o1, medicinemodalclass o2) {
                    return o1.getName().compareTo(o2.getName());
                }


            });
            adapter = new Medicineadapter(getApplicationContext(), list);
            medicinerecyclerview.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


    }

    private String fromassets() {
        String json=null;
        try {
            InputStream inputStream=getAssets().open("medicine.json");
            int sizeOfFile=inputStream.available();
            byte[] bufferdata=new   byte[sizeOfFile];
            inputStream.read(bufferdata);
            inputStream.close();
            json=new String(bufferdata,"UTF-8");
        }
        catch (Exception e)
        {

        }

        return json;

    }

    private void updation() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        String url="https://script.googleusercontent.com/macros/echo?user_content_key=HQVZazu-akScag6nVOqrM1nz_mZ4W6ju4YtPDLfWMv80x_flbwtxDM-oeidDl7pRgzxjFUxW_VPHfE7Ug37FXEj_SEXBLSQBm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnLMpe9PPOiTJXPiHErD31V4eCaKywyuC54jAdq_Z56yDt73wDRC5JIsI5qk4-4QuuQ95sulpjK0lcrHTlrt-hWNlwUY8g53pOA&lib=MS1grv2iTBdgI56mVStgEuAPAnCuUMyA7";
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);
                    JSONArray arr = js.getJSONArray("data");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject j = arr.getJSONObject(i);

                        String name = j.getString("name"),
                                mrp = j.getString("mrp");

                        progress.setProgress(i);
                        medicinemodalclass obj = new medicinemodalclass();
                        obj.setPrice(mrp);
                        obj.setName(name);
                        list.add(obj);
                    }
                    adapter = new Medicineadapter(getApplicationContext(), list);
                    medicinerecyclerview.setAdapter(adapter);
                    progress.dismiss();


                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mediciinecostpage.this, "error", Toast.LENGTH_SHORT).show();
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


/*
int productid = j.getInt("productid");
                        String name = j.getString("name"),
                                packsize = j.getString("packsize"),
                                rating_count = j.getString("rating_count"),
                                price = j.getString("price"),
                                mrp = j.getString("mrp");
                        int rating = j.getInt("rating");
 */



}
