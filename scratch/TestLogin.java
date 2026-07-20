import java.net.*;
import java.io.*;
import java.util.*;

public class TestLogin {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080/session/start");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false); // don't follow redirect
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
        String data = "email=employee@demo.local&password=123456";
        try (OutputStream os = conn.getOutputStream()) {
            os.write(data.getBytes("UTF-8"));
        }
        
        System.out.println("Status: " + conn.getResponseCode());
        System.out.println("Location: " + conn.getHeaderField("Location"));
        System.out.println("Set-Cookie: " + conn.getHeaderField("Set-Cookie"));
        
        // Now try to access /attendance with the cookie
        String cookie = conn.getHeaderField("Set-Cookie");
        if (cookie != null) {
            cookie = cookie.split(";")[0];
            URL url2 = new URL("http://localhost:8080/attendance");
            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setInstanceFollowRedirects(false);
            conn2.setRequestProperty("Cookie", cookie);
            System.out.println("Status 2: " + conn2.getResponseCode());
            System.out.println("Location 2: " + conn2.getHeaderField("Location"));
        }
    }
}
