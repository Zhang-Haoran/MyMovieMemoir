package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;

public class AdvancedFeatureFragment extends Fragment {
    private TextView textView;
    public AdvancedFeatureFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.advancedfeature_fragment,container,false);
        textView = view.findViewById(R.id.advancedfeaturesTextView);
        textView.setText("This is Advanced Features Screen");
        return view;    }
}
