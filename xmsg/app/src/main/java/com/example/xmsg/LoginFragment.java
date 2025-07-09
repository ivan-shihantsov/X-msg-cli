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

import java.security.MessageDigest;
import java.math.BigInteger;


public class LoginFragment extends Fragment {

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

        // new HTTPReqTask().execute();

        signInBtn = binding.signInBtn;
        loginPgUsername = binding.loginPgUsername;
        loginPgPass = binding.loginPgPass;

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = String.valueOf(loginPgUsername.getText());
                String pass = String.valueOf(loginPgPass.getText());

                if (checkLogin(login, pass)) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    loginPgPass.setText(""); // clear the password field
                    Toast.makeText(getParentFragment().getContext(), "Login failed. Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkLogin (String login, String pass) {
        HTTPReqTask req = new HTTPReqTask();
        req.setUSER_ID("7"); // now it's magic. later we'll know our user_id
        req.setUSER_KEY(sha1(pass));
        req.setENDPOINT("/signin");
        req.execute();
        String responseLine = req.getRESPONSE_LINE();
        System.out.println("checkLogin response: " + responseLine);

        // TODO: replace dummy with check above
        if (login.equals("hello") && pass.equals("world")) {
            // login ok
            return true;
        } else {
            // login failed
            return false;
        }
    }

    public String sha1(String input) {
        String hashtext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes("UTF-8"));

            BigInteger no = new BigInteger(1, messageDigest);

            hashtext = no.toString(16);
            while (hashtext.length() < 40) {
                hashtext = "0" + hashtext;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashtext;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}