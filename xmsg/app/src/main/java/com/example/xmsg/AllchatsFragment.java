package com.example.xmsg;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.xmsg.databinding.FragmentAllchatsBinding;


public class AllchatsFragment extends Fragment {

    private FragmentAllchatsBinding binding;
    private TableLayout table;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAllchatsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void showChat (String chatIcon, String chatName) {
        TableRow row = new TableRow(getActivity());
        TextView tv = new TextView(getActivity());
        tv.setText(chatIcon);
        row.addView(tv);

        tv = new TextView(getActivity());
        tv.setText(chatName);
        row.addView(tv);

        table.addView(row);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // temporary: open
        binding.chatsTable.setOnClickListener(v ->
                NavHostFragment.findNavController(AllchatsFragment.this)
                        .navigate(R.id.action_First2Fragment_to_Second2Fragment)
        );

        // int columnNumber = 3;
        // int rowNumber = 3;
        // table = (TableLayout) binding.chatsTable;
        // table.removeAllViews();

        // for (int i=0; i<rowNumber; i++) {
        //    TableRow row = new TableRow(getActivity());
        //    for (int j=0; i<columnNumber; i++) {
        //        int value = 42;
        //        TextView tv = new TextView(getActivity());
        //        tv.setText(String.valueOf(value));
        //        row.addView(tv);
        //    }
        //    table.addView(row);
        //}

        table = (TableLayout) binding.chatsTable;
        table.removeAllViews();
        showChat ("  A (#f06359)     ", "Alex Family chat");
        showChat (" ", " ");
        showChat ("  T (#f06359)     ", "Terminator T-800");
        showChat (" ", " ");
        showChat ("  m (#f06359)     ", "mad mad boys");
        showChat (" ", " ");
        showChat ("  u (#f06359)     ", "user2001");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}