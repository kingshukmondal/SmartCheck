package com.smartcheck.smartcheck.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcheck.smartcheck.ModalClass.medicinemodalclass;
import com.smartcheck.smartcheck.R;

import java.util.ArrayList;
import java.util.List;

public class Medicineadapter extends RecyclerView.Adapter<Medicineadapter.viewholder> {

    Context mcon;
    private List<medicinemodalclass> list;

    public Medicineadapter(Context mcon, List<medicinemodalclass> list) {
        this.mcon = mcon;
        this.list = list;
    }

    @NonNull
    @Override
    public Medicineadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowmedicine, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Medicineadapter.viewholder holder, int position) {

        String name = list.get(position).getName();
        String price = list.get(position).getPrice();
        holder.setData(name, price);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setlist(ArrayList<medicinemodalclass> list1) {
        list=list1;
    }







    class viewholder extends RecyclerView.ViewHolder
    {


        private TextView name, price;


        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.medicinename);
            price = itemView.findViewById(R.id.medicineprice);
        }

        public void setData(String n, String p) {
            name.setText(n);
            price.setText(p);
        }
    }


}



