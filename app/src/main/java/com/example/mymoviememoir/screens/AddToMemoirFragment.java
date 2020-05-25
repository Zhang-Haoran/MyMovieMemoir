package com.example.mymoviememoir.screens;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddToMemoirFragment extends Fragment {
    private TextView textView;
    private String movieID;
    private String movieName;
    private String releaseDate;
    private String addingDatetime;
    private Bitmap bitmap;
    private String spinnerResult;
    private String watchedDate;

    public AddToMemoirFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addtomemoir_fragment,container,false);
        textView = view.findViewById(R.id.addtomemoirTextView);
        textView.setText("Add To Memoir Screen");

        ImageView movieImage = view.findViewById(R.id.movieImage);
        TextView movieNameTextView = view.findViewById(R.id.addAMemoirMovieName);
        TextView releaseDateTextView = view.findViewById(R.id.addaMemoirReleaseDate);

        Bundle bundle = getArguments();
        movieID = bundle.getString("movieID");
        movieName = bundle.getString("movieName");
        releaseDate = bundle.getString("releaseDate");
        bitmap = bundle.getParcelable("Image");

        movieNameTextView.setText(movieName);
        releaseDateTextView.setText(releaseDate);
        movieImage.setImageBitmap(bitmap);


        Calendar calendar = Calendar.getInstance();// get a calendar using the current time zone and locale of the system. For example today is 10/05/2020, it will get 10,4, 2020
        final int calendarYear = calendar.get(Calendar.YEAR);
        final int calendarMonth = calendar.get(Calendar.MONTH);// month from calendar starts from 0 end in 11. it needs to + 1 to represent current month
        final int calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
        Button datetimePickerButton = view.findViewById(R.id.datetimePickerButton);
        final TextView watchedDateTextView = view.findViewById(R.id.watchedDate);
        //date time picker
        datetimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Day of birth
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        watchedDate = dateFormatting(year,month,day);
                        watchedDateTextView.setText(watchedDate);
                    }
                },calendarYear,calendarMonth,calendarDay); //set current day, month, year into datepicker

                DatePicker dp = datePickerDialog.getDatePicker();//initialize datepicker to set max date, get the datetime that user picked
                dp.setMaxDate(System.currentTimeMillis()); //dob is no more than current date
                datePickerDialog.show();
            }

        });






        Spinner CinemaSpinner = view.findViewById(R.id.CinemaSpinner);
        //spinner
        List<String> cinemalist = new ArrayList<>();
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item,cinemalist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        CinemaSpinner.setAdapter(spinnerAdapter);
        CinemaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerResult = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;    }
    //formatting date
    public String dateFormatting(int year, int month, int day){
        String result ="";
        int currentmonth = month + 1; //month from datepicker is current month -1, therefore, we need to add 1 back
        String monthFormatting = String.valueOf(currentmonth);
        String dayFormatting = String.valueOf(day);
        if (monthFormatting.length() == 1){
            monthFormatting = "0" + monthFormatting;//append the month at the end of stringBuffer which is after 0
        }
        if (dayFormatting.length() == 1){
            dayFormatting = 0 + dayFormatting;
        }
        result = year + "-" + monthFormatting + '-' + dayFormatting + "T00:00:00+00:00";
        return result;
    }
}
