package com.example.familymapclient.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.familymapclient.R;
import com.example.familymapclient.model.DataCache;
import com.example.familymapclient.model.ServerProxy;
import com.example.shared.models.Person;
import com.example.shared.requests.*;
import com.example.shared.results.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginFragment extends Fragment {

    private Listener listener;

    public interface Listener {
        void notifyDone();
    }

    public void registerListener(Listener listener) {
        this.listener = listener;
    }

    private String uServerHost = "";
    private String uServerPort = "";
    private String uUsername = "";
    private String uPassword = "";
    private String uFirstName = "";
    private String uLastName = "";
    private String uEmail = "";
    private String uGender = "";

    String KEY = "key";

    public Context context;

    boolean success = false;

    public LoginFragment() {

    }

    public LoginFragment(Context context) {
        this.context = context;
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button signButton = view.findViewById(R.id.signButton);
        Button registerButton = view.findViewById(R.id.registerButton);

        signButton.setEnabled(false);
        registerButton.setEnabled(false);

        EditText serverHost = view.findViewById(R.id.serverHostField);
        EditText serverPort = view.findViewById(R.id.serverPortField);
        EditText username = view.findViewById(R.id.usernameField);
        EditText password = view.findViewById(R.id.passwordField);
        EditText firstName = view.findViewById(R.id.firstNameField);
        EditText lastName = view.findViewById(R.id.lastNameField);
        EditText email = view.findViewById(R.id.emailField);
        RadioGroup gender = view.findViewById(R.id.genderGroup);

        serverHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                uServerHost = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                signButton.setEnabled(updateSignButton());
                registerButton.setEnabled(updateRegisterButton());
            }
        });
        serverPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                uServerPort = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                signButton.setEnabled(updateSignButton());
                registerButton.setEnabled(updateRegisterButton());
            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                uUsername = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                signButton.setEnabled(updateSignButton());
                registerButton.setEnabled(updateRegisterButton());
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                uPassword = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                signButton.setEnabled(updateSignButton());
                registerButton.setEnabled(updateRegisterButton());
            }
        });
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                uFirstName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                signButton.setEnabled(updateSignButton());
                registerButton.setEnabled(updateRegisterButton());
            }
        });
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                uLastName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                signButton.setEnabled(updateSignButton());
                registerButton.setEnabled(updateRegisterButton());
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                uEmail = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                signButton.setEnabled(updateSignButton());
                registerButton.setEnabled(updateRegisterButton());
            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int genderButtonID = group.getCheckedRadioButtonId();
                switch (genderButtonID) {
                    case R.id.male:
                        uGender = "m";
                        break;
                    case R.id.female:
                        uGender = "f";
                        break;
                }
                signButton.setEnabled(updateSignButton());
                registerButton.setEnabled(updateRegisterButton());
            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signButtonClicked();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButtonClicked();
            }
        });

        return view;
    }

    private boolean updateSignButton() {
        if(!uServerHost.equals("") &&
            !uServerPort.equals("") &&
            !uUsername.equals("") &&
            !uPassword.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean updateRegisterButton() {
        if(!uServerHost.equals("") &&
                !uServerPort.equals("") &&
                !uUsername.equals("") &&
                !uPassword.equals("") &&
                !uFirstName.equals("") &&
                !uLastName.equals("") &&
                !uEmail.equals("") &&
                !uGender.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void signButtonClicked() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler threadMessageHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Bundle bundle = message.getData();
                String result = bundle.getString(KEY);

                if(result.contains("false")) {
                    Toast.makeText(context,
                            "Login Failed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if(listener != null) {
                        listener.notifyDone();
                    }
                }
            }
        };
        LoginTask loginTask = new LoginTask(threadMessageHandler);

        executor.submit(loginTask);
    }

    private void registerButtonClicked() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler threadMessageHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Bundle bundle = message.getData();
                String result = bundle.getString(KEY);

                if(result.contains("false")) {
                    Toast.makeText(context,
                            "Register Failed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if(listener != null) {
                        listener.notifyDone();
                    }
                }
            }
        };
        RegisterTask registerTask = new RegisterTask(threadMessageHandler);

        executor.submit(registerTask);
    }

    public class LoginTask implements Runnable {

        private final Handler messageHandler;

        public LoginTask(Handler handler) {
            this.messageHandler = handler;
        }

        @Override
        public void run() {
            LoginRequest loginRequest = new LoginRequest(uUsername,uPassword);

            ServerProxy serverProxy = new ServerProxy();
            String result = serverProxy.login(uServerHost, uServerPort, loginRequest);

            Gson gson = new Gson();
            LoginResult loginResult = gson.fromJson(result, LoginResult.class);

            if(loginResult.success) {
                DataCache dataCache = DataCache.getInstance();
                dataCache.setAuthToken(loginResult.getAuthToken());
                dataCache.setPersonID(loginResult.getPersonID());

                serverProxy.getFamily(uServerHost, uServerPort);
                serverProxy.getEvents(uServerHost, uServerPort);
            }
            sendMessage(result);
        }

        public void sendMessage(String result) {
            Message message = Message.obtain();
            Bundle messageBundle = new Bundle();
            messageBundle.putString(KEY, result);
            message.setData(messageBundle);
            messageHandler.sendMessage(message);
        }
    }

    public class RegisterTask implements Runnable {

        private final Handler messageHandler;

        public RegisterTask(Handler handler) {
            this.messageHandler = handler;
        }

        @Override
        public void run() {
            RegisterRequest registerRequest = new RegisterRequest(uUsername,uPassword,uEmail,
                    uFirstName,uLastName,uGender);

            ServerProxy serverProxy = new ServerProxy();
            String result = serverProxy.register(uServerHost, uServerPort, registerRequest);

            Gson gson = new Gson();
            RegisterResult registerResult = gson.fromJson(result, RegisterResult.class);

            if(registerResult.success) {
                DataCache dataCache = DataCache.getInstance();
                dataCache.setAuthToken(registerResult.getAuthToken());
                dataCache.setPersonID(registerResult.getPersonID());

                serverProxy.getFamily(uServerHost, uServerPort);
                serverProxy.getEvents(uServerHost, uServerPort);
            }
            sendMessage(result);
        }

        public void sendMessage(String result) {
            Message message = Message.obtain();
            Bundle messageBundle = new Bundle();
            messageBundle.putString(KEY, result);
            message.setData(messageBundle);
            messageHandler.sendMessage(message);
        }
    }
}