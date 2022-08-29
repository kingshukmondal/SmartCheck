package com.smartcheck.smartcheck;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;


public class DonorPager extends Fragment {

    TextInputEditText name, contact, location, age;
    RadioGroup radiogroup;
    Spinner SpinnerBlood, SpinnerState;
    String k = "",k1="";
    TextView tv_submit, LastDonation;
    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_donor_pager, container, false);
        pd=new ProgressDialog(getContext());
        pd.setCancelable(false);

        name = v.findViewById(R.id.name);
        contact = v.findViewById(R.id.contact);
        location = v.findViewById(R.id.location);
        radiogroup = v.findViewById(R.id.radiogroup);
        SpinnerBlood = v.findViewById(R.id.SpinnerBlood);
        SpinnerState = v.findViewById(R.id.SpinnerState);
        tv_submit = v.findViewById(R.id.tv_submit);
        LastDonation = v.findViewById(R.id.LastDonation);
        age = v.findViewById(R.id.age);


        LastDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LastDonation.setError(null);
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                k1=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                LastDonation.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm=name.getText().toString(),
                        cntct=contact.getText().toString(),
                        lcn=location.getText().toString(),
                        ag=age.getText().toString(),
                        ld=k1;
               // String gender=Wecome.gende;
                String group=SpinnerBlood.getSelectedItem().toString();
                String state=SpinnerState.getSelectedItem().toString();

                if(nm.equals("")|| cntct.equals("")|| lcn.equals("")|| ag.equals("")|| ld.equals(""))
                {
                    Toast.makeText(getContext(), "Please fill all the details!", Toast.LENGTH_SHORT).show();
                }


                else if(Integer.valueOf(ag)<18)
                    Toast.makeText(getContext(), "Your are not eligible to donate blood !!", Toast.LENGTH_SHORT).show();
                else
                {

                    HashMap<String,Object> hash=new HashMap<>();
                    hash.put("Name",name.getText().toString());
                    hash.put("Contact",cntct);
                    hash.put("Location",lcn);
                    hash.put("Group",group);
                    hash.put("State",state);
                    hash.put("Age",ag);
                    hash.put("LastDonation",ld);
                    hash.put("Name",nm);
                   //  hash.put("Gender",Wecome.gende);
                     loadtodatabase(hash);

                }



            }
        });

        return v;
    }



    private void loadtodatabase(HashMap<String,Object> hash) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Blood");
        myRef.push().setValue(hash);
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Successfully Uploaded!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .show();


    }




//
//            public String onRadioButtonClicked(RadioGroup) {
//            // Is the button now checked?
//            boolean checked = ((RadioButton) view).isChecked();
//
//            // Check which radio button was clicked
//            switch(view.getId()) {
//                case R.id.male:
//                    if (checked) {
//                        return "male";
//
//                    }
//                case R.id.female:
//                    if (checked) {
//                        return "Female";
//
//                    }
//
//                case R.id.others:
//                    if (checked) {
//                        return "Others";
//                    }
//            }
//            return "male";
//        }
}

