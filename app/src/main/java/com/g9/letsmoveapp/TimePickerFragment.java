package com.g9.letsmoveapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private int horaLlegada;
    private int minuLlegada;
    private int horaSalida;
    private int minuSalida;
    private int type;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    //TODO hacer dos timepickerfragment para poder leer fecha y día de llegada y salida
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (type == 0) {
            horaLlegada = hourOfDay;
            minuLlegada = minute;
            TextView text_llegada = (TextView) getActivity().findViewById(R.id.horaLlegada);
            text_llegada.setText(hourOfDay + ":" + minute);
        } else {
            horaSalida = hourOfDay;
            minuSalida = minute;
            TextView text_salida = (TextView) getActivity().findViewById(R.id.horaSalida);
            text_salida.setText(hourOfDay + ":" + minute);
        }


    }
}
