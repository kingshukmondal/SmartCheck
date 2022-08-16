package com.smartcheck.smartcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartcheck.smartcheck.ModalClass.ComplainModalClass;

import java.util.ArrayList;
import java.util.List;

public class ComplainStatus extends AppCompatActivity {

    RecyclerView recyclerviewcards;
    LinearLayoutManager manager;
    List<ComplainModalClass> list;
    ComplainAdapter adapter;
    TextView totalcount,setelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_status);
        if (Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().hide();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.specialgreen));
        }


        recyclerviewcards=findViewById(R.id.recyclerviewcards);
        manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        recyclerviewcards.setLayoutManager(manager);
        list=new ArrayList<>();
        totalcount=findViewById(R.id.totalcount);
        setelled=findViewById(R.id.setelled);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("ComplainDetails").child(Constant.key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                int count=0;
                for(DataSnapshot i:snapshot.getChildren()) {
                    ComplainModalClass c = i.getValue(ComplainModalClass.class);
                    if(c!=null && c.getStatus().equals("1"))
                        count++;
                    list.add(c);
                }
                totalcount.setText(String.valueOf(list.size()));
                setelled.setText(String.valueOf(count));
                adapter=new ComplainAdapter(getApplicationContext(),list);
                recyclerviewcards.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ComplainStatus.this, "Error in loading!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}



class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.viewholder>{

    Context com;
    List<ComplainModalClass>list;
    ComplainAdapter(Context mcom,List<ComplainModalClass>list)
    {
        this.com=mcom;
        this.list=list;
    }
    @NonNull
    @Override
    public ComplainAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.stylecard,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplainAdapter.viewholder holder, int position) {
        String name=list.get(position).getName();
        String dateofbirth=list.get(position).getDateofBirth();
        String status=list.get(position).getStatus();
        String submission=list.get(position).getDateOfDocuments();
        String charged =list.get(position).getCharged();
        holder.setData(name,dateofbirth,status,submission,charged);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder
    {
        ImageView statusimage;
        TextView name,datebirth,dateofissue,amount;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            statusimage=itemView.findViewById(R.id.statusimage);
            name=itemView.findViewById(R.id.name);
            datebirth=itemView.findViewById(R.id.datebirth);
            dateofissue=itemView.findViewById(R.id.dateofissue);
            amount=itemView.findViewById(R.id.amount);
        }

        public void setData(String name1, String dateofbirth1, String status1, String submission1, String charged1) {
            name.setText(name1);
            datebirth.setText("DOB :"+dateofbirth1);
            dateofissue.setText("Submission : "+submission1);
            amount.setText("Rs "+charged1);
            if(status1.contains("0"))
                statusimage.setImageResource(R.drawable.pending);
            else
                statusimage.setImageResource(R.drawable.approved);

        }
    }
}