package com.example.herambtinder.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.herambtinder.Adapters.swipeAdapter;
import com.example.herambtinder.CustomSwipeFlingAdapterView;
import com.example.herambtinder.Models.swipeProfile;
import com.example.herambtinder.R;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


public class swipeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private swipeAdapter adapter;
    private List<swipeProfile> profiles;


    private String mParam1;
    private String mParam2;

    public swipeFragment() {
        // Required empty public constructor
    }

    public static swipeFragment newInstance(String param1, String param2) {
        swipeFragment fragment = new swipeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_swipe, container, false);

        //write code here
        profiles = new ArrayList<>();
        profiles.add(new swipeProfile(R.drawable.rashmi, "Rashmi Limaye,","23","I like chocolate"));
        profiles.add(new swipeProfile(R.drawable.apoorva, "Apoorva Shete,","20","I like to travel"));
        profiles.add(new swipeProfile(R.drawable.heramb, "Heramb patil","20","I like businnessss"));
        profiles.add(new swipeProfile(R.drawable.aarti, "Aarti,","21","I like making new friends"));
        profiles.add(new swipeProfile(R.drawable.ayushi, "Ayushi","22","I like boys"));
        profiles.add(new swipeProfile(R.drawable.gauri, "Gauri","34","I like men"));
        profiles.add(new swipeProfile(R.drawable.babarsisters, "babar Mrunali","19","I like kalpesh"));
        profiles.add(new swipeProfile(R.drawable.jaswandi, "Jaswandi","20","I like africans"));
        profiles.add(new swipeProfile(R.drawable.shruti, "Shruti","24","I like to travel with boys"));
        profiles.add(new swipeProfile(R.drawable.siddhi, "Siddhi","23","I like Mumbai"));




        adapter = new swipeAdapter(profiles,getContext());

        CustomSwipeFlingAdapterView flingContainer = view.findViewById(R.id.swipeLayout);
        flingContainer.setAdapter(adapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                profiles.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(getContext(), "Rejected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // You can load more profiles here
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                // Handle card scrolling (optional)
            }
        });

        flingContainer.setOnItemClickListener((itemPosition, dataObject) ->
                Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_SHORT).show()
        );


    return view;
}
}