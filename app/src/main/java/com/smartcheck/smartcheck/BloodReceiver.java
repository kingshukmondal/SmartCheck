package com.smartcheck.smartcheck;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartcheck.smartcheck.ModalClass.BloodDonorModalClass;
import com.smartcheck.smartcheck.ModalClass.ComplainModalClass;

import java.util.ArrayList;
import java.util.List;


public class BloodReceiver extends Fragment {

    RecyclerView recv_donors;
    ArrayList<BloodDonorModalClass> arr;
    BloodAdapter adapter;
    LinearLayoutManager linearLayoutManager;
   TextView btnSearch;
    ProgressDialog pd;
    Spinner btngetBloodGroup;
    TextView nodata;
    ImageView listofblood;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_blood_receiver, container, false);
        recv_donors=v.findViewById(R.id.recv_donors);
        arr=new ArrayList<>();
        pd=new ProgressDialog(getContext());
        btngetBloodGroup=v.findViewById(R.id.btngetBloodGroup);
        pd.setCancelable(false);
        btnSearch=v.findViewById(R.id.btnSearch);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recv_donors.setLayoutManager(linearLayoutManager);
        nodata=v.findViewById(R.id.nodata);
        nodata.setVisibility(View.GONE);
        listofblood=v.findViewById(R.id.listofblood);
        listofblood.setVisibility(View.GONE);
        pd.show();
        pd.setContentView(R.layout.progess_anim);
        pd.getWindow().setBackgroundDrawableResource(cn.pedant.SweetAlert.R.color.float_transparent);




        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BloodDonorModalClass> arr1=new ArrayList<>();
                String k=btngetBloodGroup.getSelectedItem().toString();
                for(int i=0;i<arr.size();i++)
                {
                    if(arr.get(i).getGroup().equals(k))
                        arr1.add(arr.get(i));
                }

                if(arr1.size()==0)
                {
                    nodata.setVisibility(View.VISIBLE);
                    listofblood.setVisibility(View.VISIBLE);
                }
                else
                {
                    nodata.setVisibility(View.GONE);
                    listofblood.setVisibility(View.GONE);
                }

                adapter.optimize(arr1);
                recv_donors.setAdapter(adapter);
            }
        });


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Blood");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr.clear();
                for (DataSnapshot i : snapshot.getChildren()) {
                    BloodDonorModalClass c = i.getValue(BloodDonorModalClass.class);
                    arr.add(c);
                }
                if(arr.size()==0)
                {
                    nodata.setVisibility(View.VISIBLE);
                    listofblood.setVisibility(View.VISIBLE);
                }
                else
                {
                    nodata.setVisibility(View.GONE);
                    listofblood.setVisibility(View.GONE);
                }
                adapter = new BloodAdapter(getContext(), arr);
                recv_donors.setAdapter(adapter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
            }
        });

        return v;

    }
}




class BloodAdapter extends RecyclerView.Adapter<BloodAdapter.viewholder>{

    Context com;
    List<BloodDonorModalClass> list;
    BloodAdapter(Context mcom,List<BloodDonorModalClass>list)
    {
        this.com=mcom;
        this.list=list;
    }
    @NonNull
    @Override
    public BloodAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_donor_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodAdapter.viewholder holder, int position) {
        String name=list.get(position).getName();
        String  group=list.get(position).getGroup();
        String  age=list.get(position).getAge();
        String  state=list.get(position).getState();
        String location=list.get(position).getLocation();
        String last=list.get(position).getLastDonation();
        String contact=list.get(position).getContact();


        holder.setData(name,group,age,state,location,last,contact);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void optimize(ArrayList<BloodDonorModalClass> arr1) {
        list=arr1;
    }

    class viewholder extends RecyclerView.ViewHolder
    {
TextView name,contact,dateoflast,location ,age;
ImageView   bloodgrp;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            contact=itemView.findViewById(R.id.contact);
            dateoflast=itemView.findViewById(R.id.dateoflast);
            location=itemView.findViewById(R.id.location);
            age=itemView.findViewById(R.id.age);
            bloodgrp=itemView.findViewById(R.id.bloodgrp);

        }


        public void setData(String name1, String group, String age1, String state, String location1, String last, String contact1) {
            name.setText(name1);
            age.setText(age1+" years");
            contact.setText(contact1);
            dateoflast.setText(last);
            location.setText(location1+", "+state);
            if(group.equals("A+")) bloodgrp.setImageResource(R.drawable.a_pos);
            if(group.equals("B+")) bloodgrp.setImageResource(R.drawable.b_pos);
            if(group.equals("AB+")) bloodgrp.setImageResource(R.drawable.ab_pos);
            if(group.equals("O+")) bloodgrp.setImageResource(R.drawable.o_pos);

            if(group.equals("A-")) bloodgrp.setImageResource(R.drawable.a_neg);
            if(group.equals("B-")) bloodgrp.setImageResource(R.drawable.b_neg);
            if(group.equals("AB-")) bloodgrp.setImageResource(R.drawable.ab_neg);
            if(group.equals("O-")) bloodgrp.setImageResource(R.drawable.o_neg);

        }
    }
}
