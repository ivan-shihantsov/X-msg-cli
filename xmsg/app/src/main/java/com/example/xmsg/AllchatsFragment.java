package com.example.xmsg;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import org.json.JSONObject;
import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.xmsg.databinding.FragmentAllchatsBinding;


public class AllchatsFragment extends Fragment implements HTTPReqTask.CustomCallback {

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
        row.setPadding(0,50,0,50);

        TextView tv = new TextView(getActivity());
        tv.setText(chatIcon);
        row.addView(tv);

        tv = new TextView(getActivity());
        tv.setText(chatName);
        row.addView(tv);

        table.addView(row);
    }

    public void updateChatsList () {
        String myUsername = "qwerty"; // fix it later
        String myUserID = "1"; // fix it later
        String pass = "qwerty"; // fix it later
        String passHash = HTTPReqTask.sha1(pass); // fix it later

        // null: '/getchats' endpoint needn't additional data to send
        HTTPReqTask reqTask = new HTTPReqTask(null, this);

        // params: reqType, endpoint, user_id, password
        reqTask.execute("POST", "/getchats", myUserID, passHash);
    }

    public void finishUpdateChatsList (String response) {
        table = (TableLayout) binding.chatsTable;
        table.removeAllViews();
        int chatsNum = 0;

        try {
            JSONArray jsonArray = new JSONArray(response);
            chatsNum = jsonArray.length();
            for (int i = 0; i < chatsNum; i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String admin = obj.getString("admin");
                String chat_id = obj.getString("chat_id");
                String chat_name = obj.getString("chat_name");
                String color = obj.getString("color");
                String created_at = obj.getString("created_at");

                String chatLogoLetter = chat_name.substring(0,1);
                showChat ("  " + chatLogoLetter +  " (" + color + ")     ", chat_name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // temporary: open
        binding.chatsTable.setOnClickListener(v ->
                NavHostFragment.findNavController(AllchatsFragment.this)
                        .navigate(R.id.action_First2Fragment_to_Second2Fragment)
        );

        updateChatsList();
    }

    @Override
    public void completionHandler (String endpoint, String response) {
        switch (endpoint) {
            case "/getchats":
                finishUpdateChatsList(response);
                break;
            default: break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}