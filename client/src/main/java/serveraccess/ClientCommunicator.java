package serveraccess;

import com.google.gson.Gson;
import dataaccess.exception.ResponseException;
import model.UserData;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ClientCommunicator {
    String BASE_URL;
    Gson gson;

    public ClientCommunicator(int port) {
        gson = new Gson();
        BASE_URL = "http://localhost:" + port;
    }

    public ClientCommunicator(){
        this(8080);
        // hopefully this doesn't mess stuff up.....
    }

   public ResponseObj register(UserData userData) throws MalformedURLException, ResponseException {
       URL url = new URL(BASE_URL + "/user");
       try {
           HttpURLConnection http = (HttpURLConnection) url.openConnection();
           return getResponseObjLogin(userData, http, "POST");

       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

    private ResponseObj getResponseObjLogin(UserData userData, HttpURLConnection http, String method) throws IOException, ResponseException {
        http.setRequestMethod(method);
        http.setDoOutput(true);
        http.addRequestProperty("Content-Type", "application/json");
        String body = gson.toJson(userData);
        OutputStream os = http.getOutputStream();
        os.write(body.getBytes());
        return receiveResponse(http);
    }
    // makes a response object to return to client
   private ResponseObj receiveResponse(HttpURLConnection http) throws IOException, ResponseException {
        int status = http.getResponseCode();
        String msg = http.getResponseMessage();
        if (status == 200 || status == 201) {
            return new ResponseObj(status, msg, readResponseBody(http));
        } else {
            throw new ResponseException(status, msg);
        }
   }

    // read all the body in as a string - converting to an object will be done closer to where the objects are used
    private static String readResponseBody(HttpURLConnection http) throws IOException {
        String responseBody = "";
        try (InputStream respBody = http.getInputStream()) {
            Scanner scanner = new Scanner(respBody);
            scanner.useDelimiter("\\A");
            responseBody = scanner.hasNext() ? scanner.next() : "";
            return responseBody;
        }
    }

    public ResponseObj login(UserData userData) throws MalformedURLException, ResponseException {
        URL url = new URL(BASE_URL + "/session");
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            return getResponseObjLogin(userData, http, "POST");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseObj logout(String authToken) throws MalformedURLException, ResponseException {
        URL url = new URL(BASE_URL + "/session");
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("DELETE");
            http.setDoOutput(true);
            http.setRequestProperty("authorization", authToken);
            // return a serveraccess.ResponseObj -- body should be null, all we care about is that status code
            return receiveResponse(http);
        } catch (IOException e) {
            throw new ResponseException(500, "Problem logging out: " + e.getMessage());
        }
    }

    public void clear() throws MalformedURLException, ResponseException {
        URL url = new URL(BASE_URL + "/db");
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("DELETE");
            if (http.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new ResponseException(500, "Problem clearing db");
            }
            // return a serveraccess.ResponseObj -- body should be null, all we care about is that status code
        } catch (IOException e) {
            throw new ResponseException(500, "Problem clearing out: " + e.getMessage());
        }
    }




}
