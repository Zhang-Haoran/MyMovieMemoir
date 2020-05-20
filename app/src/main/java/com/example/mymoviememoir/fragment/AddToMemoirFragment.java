package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;

public class AddToMemoirFragment extends Fragment {
    private TextView textView;
    public AddToMemoirFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addtomemoir_fragment,container,false);
        textView = view.findViewById(R.id.addtomemoirTextView);
        textView.setText("This is Add To Memoir Screen");
        return view;    }
}
