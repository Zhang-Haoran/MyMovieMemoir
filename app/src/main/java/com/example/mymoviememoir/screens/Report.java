package com.example.mymoviememoir.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;

public class Report extends Fragment {
    private TextView textView;
    public Report(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.report_fragment,container,false);
        textView = view.findViewById(R.id.reportTextView);
        textView.setText("Report Screen");
        return view;    }
}
