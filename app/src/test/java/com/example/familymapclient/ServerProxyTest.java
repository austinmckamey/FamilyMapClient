package com.example.familymapclient;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import com.example.familymapclient.model.DataCache;
import com.example.familymapclient.model.ServerProxy;
import com.example.shared.requests.LoginRequest;
import com.example.shared.requests.RegisterRequest;
import com.example.shared.results.LoginResult;
import com.example.shared.results.RegisterResult;
import com.google.gson.Gson;

public class ServerProxyTest {

    private String serverHost;
    private String serverPort;

    private ServerProxy serverProxy;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    private DataCache dataCache;

    @BeforeEach
    public void setUp() {
        serverHost = "localhost";
        serverPort = "8080";

        dataCache = DataCache.getInstance();
        serverProxy = new ServerProxy();
    }

    @Test
    public void loginPass() {
        loginRequest = new LoginRequest("sheila","parker");
        String result = serverProxy.login(serverHost, serverPort, loginRequest);

        Gson gson = new Gson();
        LoginResult loginResult = gson.fromJson(result, LoginResult.class);

        assertTrue(loginResult.success);
        assertEquals("sheila",loginResult.getUsername());
    }

    @Test
    public void loginFail() {
        loginRequest = new LoginRequest("austin","mckamey");
        String result = serverProxy.login(serverHost, serverPort, loginRequest);

        Gson gson = new Gson();
        LoginResult loginResult = gson.fromJson(result, LoginResult.class);

        assertFalse(loginResult.success);
        assertNull(loginResult.getUsername());
    }

    @Test
    public void registerPass() {
        registerRequest = new RegisterRequest("amaks007","password", "amaks@gmail",
                "Austin","McKamey","m");
        String result = serverProxy.register(serverHost, serverPort, registerRequest);

        Gson gson = new Gson();
        RegisterResult registerResult = gson.fromJson(result, RegisterResult.class);

        assertTrue(registerResult.success);
        assertEquals("amaks007",registerResult.getUsername());
    }

    @Test
    public void registerFail() {
        registerRequest = new RegisterRequest("sheila","parker", "sparker@gmail",
                "Sheila","Parker","f");
        String result = serverProxy.register(serverHost, serverPort, registerRequest);

        Gson gson = new Gson();
        RegisterResult registerResult = gson.fromJson(result, RegisterResult.class);

        assertFalse(registerResult.success);
        assertNull(registerResult.getUsername());
    }

    @Test
    public void getFamilyPass() {
        dataCache.setAuthToken("1fhptnc6");
        serverProxy.getFamily(serverHost,serverPort);

        assertNotEquals(0,dataCache.getPeople().size());
    }

    @Test
    public void getFamilyFail() {
        dataCache.setAuthToken("random");
        serverProxy.getFamily(serverHost,serverPort);

        assertNull(dataCache.getFamily());
    }

    @Test
    public void getEventsPass() {
        dataCache.setAuthToken("1fhptnc6");
        serverProxy.getEvents(serverHost,serverPort);

        assertNotEquals(0,dataCache.getEvents().size());
    }

    @Test
    public void getEventsFail() {
        dataCache.setAuthToken("whatever");
        serverProxy.getEvents(serverHost,serverPort);

        assertNull(dataCache.getEvents());
    }
}
