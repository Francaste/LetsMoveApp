package com.g9.letsmoveapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public int datePickerType = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (datePickerType == 0) {
            //fecha llegada
            TextView text_llegada = (TextView) getActivity().findViewById(R.id.fechaLlegada);
            text_llegada.setText(dayOfMonth + "-" + month + "-" + year);
        } else {
            //fecha salida
            TextView text_llegada = (TextView) getActivity().findViewById(R.id.fechaSalida);
            text_llegada.setText(dayOfMonth + "-" + month + "-" + year);
        }
    }
}
