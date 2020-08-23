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
    private  final String MACHINE_IP =null;

    @Value("${machine.passwordOne}")
    private  final String PASSWORD_ONE=null;

    @Value("${machine.passwordTwo}")
    private  final String PASSWORD_TWO=null;

    private HttpClient httpClient= HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

//    @Autowired
//    public ServerConnectionImpl(HttpClient httpClient) {
//        this.httpClient = httpClient;
//    }

    @Override
    public void sendRequest(String targetIp, String port, String time, String method, int servers) throws URISyntaxException, IOException, InterruptedException {

        //v1
        String url = String.format("%s?ip=%s&port=%s&time=%s&method=%s&pass=%s&pass2=%s",MACHINE_IP,targetIp
                ,port,time,method,PASSWORD_ONE,PASSWORD_TWO);

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();

        //v2
//        String url = MACHINE_IP;
//        HttpRequest request = HttpRequest.newBuilder().
//                headers("targetIp",targetIp,"port",port,"time",time,"method",method,"servers",String.valueOf(servers)
//                ,"pass",PASSWORD_ONE,"pass2",PASSWORD_TWO)
//                .uri(new URI(url))
//                .build();


        httpClient.send(request, HttpResponse.BodyHandlers.discarding());

    }
}
