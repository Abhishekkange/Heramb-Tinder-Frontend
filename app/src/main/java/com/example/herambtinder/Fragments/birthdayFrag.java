package com.example.herambtinder.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.herambtinder.R;
import com.google.android.material.datepicker.MaterialDatePicker;


public class birthdayFrag extends Fragment {


    Button selectBirthdate;
    TextView birthDateText;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public birthdayFrag() {

    }


    public static birthdayFrag newInstance(String param1, String param2) {
        birthdayFrag fragment = new birthdayFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_birthday, container, false);

        birthDateText  = view.findViewById(R.id.birthdatTextView);
        selectBirthdate  = view.findViewById(R.id.selectBirthdateBtn);


        selectBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Select Your Birthday");
                MaterialDatePicker<Long> datePicker = builder.build();

                datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");

                datePicker.addOnPositiveButtonClickListener(selection -> {
                    // Date is returned as a Long, which represents milliseconds since the epoch
                    String selectedDate = datePicker.getHeaderText(); // Format: MMM DD, YYYY
                    birthDateText.setText(selectedDate);
                });





            }
        });


        return view;
    }
}