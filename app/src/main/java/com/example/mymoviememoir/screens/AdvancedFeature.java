package com.example.mymoviememoir.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;

public class AdvancedFeature extends Fragment {
    private TextView textView;
    public AdvancedFeature(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.advancedfeature_fragment,container,false);
        textView = view.findViewById(R.id.advancedfeaturesTextView);
        textView.setText("Advanced Features Screen");
        return view;    }
}
