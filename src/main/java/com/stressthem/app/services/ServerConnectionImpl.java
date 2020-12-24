package com.stressthem.app.services;

import com.stressthem.app.services.interfaces.ServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ServerConnectionImpl implements ServerConnection {

    @Value("${machine.ip}")
    private final String MACHINE_IP = null;


    private HttpClient httpClient;

    @Autowired
    public ServerConnectionImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void sendRequest(String targetIp, String port, String time, String method, int servers,String token) throws URISyntaxException, IOException, InterruptedException {
        //todo token
        //v1
        String url = String.format("%s?target=%s&port=%s&time=%s&method=%s&token=%s", MACHINE_IP, targetIp
                , port, time, method, token);

        HttpRequest request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_1_1).uri(new URI(url)).build();


        httpClient.send(request, HttpResponse.BodyHandlers.discarding());

    }
}
