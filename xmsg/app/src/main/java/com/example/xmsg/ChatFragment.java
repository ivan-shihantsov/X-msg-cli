package com.example.xmsg;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.xmsg.databinding.FragmentChatBinding;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private TableLayout table;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void showMessage (String time, String name, String message) {
        TableRow row = new TableRow(getActivity());
        TextView tv = new TextView(getActivity());
        tv.setText(time);
        row.addView(tv);

        tv = new TextView(getActivity());
        tv.setText(name);
        row.addView(tv);

        tv = new TextView(getActivity());
        tv.setText(message);
        row.addView(tv);

        table.addView(row);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.msgsTable.setOnClickListener(v ->
                NavHostFragment.findNavController(ChatFragment.this)
                        .navigate(R.id.action_Second2Fragment_to_First2Fragment)
        );

        // next work with time
        String chatCreated = "2025-07-10 13:56:38 +0000"; // UTC+0 - we get from server

        DateTimeFormatter chatCreatedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        ZonedDateTime zonedDateTimeUTC = ZonedDateTime.parse(chatCreated, chatCreatedFormatter);

        // adapt for client
        ZoneId myTZ = ZoneId.systemDefault();
        ZonedDateTime zonedDateTimeMy = zonedDateTimeUTC.withZoneSameInstant(myTZ);
        // now we can use it in the UI
        // zonedDateTimeMy.getHour() + ":" + zonedDateTimeMy.getMinute()

        table = (TableLayout) binding.msgsTable;
        table.removeAllViews();
        showMessage ("  18:25  |  ", "qwerty:  ", "hello");
        showMessage (" ", " ", " ");
        showMessage ("  18:25  |  ", "qwerty:  ", "i'm going to play dota. will U join?");
        showMessage (" ", " ", " ");
        showMessage ("  20:51  |  ", "proger:  ", "Sorry, I'm very busy today");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}