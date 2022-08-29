package com.smartcheck.smartcheck;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Raiseacomplain extends AppCompatActivity {

    EditText dateEdt,dateofdouments;
    Button btSelect;
    TextView docid,submit,submit_disable;
    ActivityResultLauncher<Intent> resultLauncher;
    CardView uploaddocuments;
    Uri sUri;

    ProgressDialog pd;
    EditText patientname,hospitalname,pathname,amount1,amount2 ,aadhar;
    CheckBox check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raiseacomplain);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.pd));
        }


        docid=findViewById(R.id.docid);
        patientname=findViewById(R.id.patientname);
        hospitalname=findViewById(R.id.hospitalname);
        pathname=findViewById(R.id.pathname);
        amount1=findViewById(R.id.amount1);
        amount2=findViewById(R.id.amount2);
        aadhar=findViewById(R.id.aadhar);
        check=findViewById(R.id.check);
        submit_disable=findViewById(R.id.submit_disable);

        submit=findViewById(R.id.submit);
        pd=new ProgressDialog(this);
        pd.setCancelable(false);



        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    submit.setVisibility(View.VISIBLE);
                    submit_disable.setVisibility(View.GONE);
                }
                else
                {
                    submit.setVisibility(View.GONE);
                    submit_disable.setVisibility(View.VISIBLE);
                }

            }
        });


        submit_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Raiseacomplain.this, "Accept the Terms & Conditions", Toast.LENGTH_SHORT).show();
            }
        });



        ImageView  iv_back=findViewById(R.id.back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);
            }
        });












        docid.setText("");


        dateEdt=findViewById(R.id.dateofbirth);
            dateEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dateEdt.setError(null);
                    // on below line we are getting
                    // the instance of our calendar.
                    final Calendar c = Calendar.getInstance();

                    // on below line we are getting
                    // our day, month and year.
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    // on below line we are creating a variable for date picker dialog.
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            // on below line we are passing context.
                            Raiseacomplain.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // on below line we are setting date to our edit text.
                                    dateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            },
                            // on below line we are passing year,
                            // month and day for selected date in our date picker.
                            year, month, day);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    // at last we are calling show to
                    // display our date picker dialog.
                    datePickerDialog.show();
                }
            });


        dateofdouments=findViewById(R.id.dateofdouments);
        dateofdouments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateofdouments.setError(null);
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Raiseacomplain.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                dateofdouments.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);




                Calendar today = Calendar.getInstance();
                Calendar twoDaysAgo = (Calendar) today.clone();
                twoDaysAgo.add(Calendar.YEAR, -9);

                datePickerDialog.getDatePicker().setMinDate(twoDaysAgo.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });


        resultLauncher = registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(
                            ActivityResult result)
                    {
                        // Initialize result data
                        Intent data = result.getData();
                        // check condition
                        if (data != null) {

                            sUri = data.getData();


                            // Get PDF path
                            String sPath = sUri.getPath();
                            Toast.makeText(Raiseacomplain.this, sPath, Toast.LENGTH_SHORT).show();
                            // Set path on text view
                      docid.setText(Html.fromHtml(
                                    "<big><b>PDF Path</b></big><br>"
                                            + sPath));
                        }
                    }
                });


        uploaddocuments=findViewById(R.id.uploaddocuments);
        uploaddocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(
                        Raiseacomplain.this,
                        Manifest.permission
                                .READ_EXTERNAL_STORAGE)
                        != PackageManager
                        .PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Result permission
                    ActivityCompat.requestPermissions(
                            Raiseacomplain.this,
                            new String[] {
                                    Manifest.permission
                                            .READ_EXTERNAL_STORAGE },
                            1);
                }
                else {
                    // When permission is granted
                    // Create method
                    selectPDF();
                }
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.animate();
                pd.show();
                pd.setContentView(R.layout.progess_anim);
                pd.getWindow().setBackgroundDrawableResource(cn.pedant.SweetAlert.R.color.float_transparent);
                uploadData();

            }
        });



    }

    private void uploadData() {
        final String timestamp = "" + System.currentTimeMillis();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final String messagePushID = timestamp;
       // Toast.makeText(Raiseacomplain.this, sUri.toString(), Toast.LENGTH_SHORT).show();

        final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");


if(sUri!=null)
        filepath.putFile(sUri).continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri uri = task.getResult();
                    ////////////////////////////////////////////////////////

                    String name1=patientname.getText().toString();
                    String hospitalname1=hospitalname.getText().toString();
                    String pathology=pathname.getText().toString();
                    String amount_1=amount1.getText().toString();
                    String amount_2=amount2.getText().toString();
                    String aadhar1=aadhar.getText().toString();
                    String DOB=dateEdt.getText().toString();
                    String DateofDocuments=dateofdouments.getText().toString();
                    if(name1.length()==0) patientname.requestFocus();
                    else if(hospitalname1.length()==0) hospitalname.requestFocus();
                    else if(amount_1.length()==0) amount1.requestFocus();
                    else if(amount_2.length()==0) amount2.requestFocus();
                    else if(pathology.length()==0) pathname.requestFocus();
                    else if(aadhar1.length()==0) aadhar.requestFocus();
                    else if(DOB.length()==0) {
                        dateEdt.requestFocus();
                        dateEdt.setError("Enter the Date");
                    }
                    else if(DateofDocuments.length()==0) {
                        dateofdouments.setError("Enter the date");
                    }


                    else
                    {

                        String pattern = "dd-MM-yyyy";
                        String dateInString =new SimpleDateFormat(pattern).format(new Date());
                        HashMap<String,Object> hash=new HashMap<>();
                        hash.put("name",name1);
                        hash.put("Hospitalname",hospitalname1);
                        hash.put("pathologyname",pathology);
                        hash.put("Charged",amount_1);
                        hash.put("Original",amount_2);
                        hash.put("Aadhar",aadhar1);
                        hash.put("DateofBirth",DOB);
                        hash.put("DateOfDocuments",DateofDocuments);
                        hash.put("Status","0");
                        hash.put("date",dateInString);
                        hash.put("url",uri.toString());
                        loadtodatabase(hash);
                    }








                    ////////////////////////////////////////////////////////////

                } else {
                    pd.dismiss();
                    new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong!")
                            .show();
                }

            }
        });
else {
    pd.dismiss();
    Toast.makeText(this, "Please Upload Documents!!", Toast.LENGTH_SHORT).show();
}
    }




    private void loadtodatabase(HashMap<String,Object> hash) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ComplainDetails");
        myRef.child(Constant.key).push().setValue(hash);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Successfully Uploaded!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                    }
                })
                .show();


    }


    private void selectPDF()
    {
        // Initialize intent
        Intent intent
                = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/pdf");
        // Launch intent
        resultLauncher.launch(intent);
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

        // check condition
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            selectPDF();
            Toast.makeText(this, sUri.toString(), Toast.LENGTH_SHORT).show();
        }
        else {
            // When permission is denied
            // Display toast
            Toast
                    .makeText(getApplicationContext(),
                            "Permission Denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pd.dismiss();
    }
}
