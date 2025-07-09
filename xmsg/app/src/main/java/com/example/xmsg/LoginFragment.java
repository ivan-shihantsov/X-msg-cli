package com.example.xmsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.xmsg.databinding.FragmentLoginBinding;

import java.util.Map;
import java.util.HashMap;


public class LoginFragment extends Fragment implements HTTPReqTask.CustomCallback {

    private FragmentLoginBinding binding;
    private Button signInBtn;
    private EditText loginPgUsername;
    private EditText loginPgPass;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginPgLinkToReg.setOnClickListener(v ->
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

        signInBtn = binding.signInBtn;
        loginPgUsername = binding.loginPgUsername;
        loginPgPass = binding.loginPgPass;

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = String.valueOf(loginPgUsername.getText());
                String pass = String.valueOf(loginPgPass.getText());
                checkLogin(login, pass);
            }
        });
    }

    private void checkLogin (String login, String pass) {
        // must discover user_id corresponding to this login
        String myUserID = "1"; // fix it later
        String passHash = HTTPReqTask.sha1(pass);

        // it's data to just send to server
        // Map<String, String> postData = new HashMap<>();
        // postData.put("chat_id", "000");
        // postData.put("last_msg_id", "000");
        // HTTPReqTask reqTask = new HTTPReqTask(postData, this);

        // null: '/signin' endpoint needn't additional data to send
        HTTPReqTask reqTask = new HTTPReqTask(null, this);
        // params: reqType, endpoint, user_id, password
        reqTask.execute("POST", "/signin", myUserID, passHash);
    }


    public void finishCheckLogin(String response) {
        loginPgPass.setText(""); // clear the password field

        if (response.equals("<p>access allowed</p>")) {
            // login ok
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        } else {
            // login failed
            Toast.makeText(getParentFragment().getContext(), "Login failed. Try again", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void completionHandler (String endpoint, String response) {
        switch (endpoint) {
            case "/signin":
                finishCheckLogin(response);
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