package ApiTests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdk.internal.org.objectweb.asm.Type;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UsersSettings {
    HttpClient client;
    HttpResponse httpResponse;
    UsersSettings (){
        client = HttpClients.createDefault();

    }

    public void checkApiTest() throws IOException {
        HttpGet getRequest = new HttpGet("http://192.168.1.79:20007/api/users");
        httpResponse = client.execute(getRequest);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
        InputStream stream = httpResponse.getEntity().getContent();
        httpResponse.addHeader("content-type", "application/json");
        String jsonContent = IOUtils.toString(stream);
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<ArrayList<User>>() {
        }.getType();
        List<User> userList = gson.fromJson(jsonContent, listType);
        Assert.assertEquals(3, userList.size());
        User user2 = userList.get(1);
        user2.name = "22";
        HttpPut putRequest = new HttpPut("http://192.168.1.79:20007/api/users/" + user2.id);
        StringEntity params = new StringEntity(gson.toJson(user2));
        putRequest.addHeader("content-type", "application/json");
        putRequest.setEntity(params);
        httpResponse = client.execute(putRequest);

    }

    //Create user
    public void createCorrectUser() throws IOException {
        User user4 = new User();
        user4.name = "Test";
        user4.phone = "0938382383";
        user4.role = "Student";
        Gson gson = new Gson();
        String userJSON = gson.toJson(user4);
        StringEntity requestEntity = new StringEntity(userJSON);
        HttpPost postMethod = new HttpPost("http://192.168.1.79:20007/api/users");
        postMethod.setEntity(requestEntity);
        postMethod.addHeader("content-type", "application/json");
        httpResponse = client.execute(postMethod);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);

    }
    public void createIncorrectUser() throws IOException {
        User user5 = new User();
        user5.name = "Test";
        user5.phone = "0938382393";
        user5.role = "Moderator";
        Gson gson = new Gson();
        String userJSON = gson.toJson(user5);
        StringEntity requestEntity = new StringEntity(userJSON, ContentType.APPLICATION_JSON);
        HttpPost postMethod = new HttpPost("http://192.168.1.79:20007/api/users");
        postMethod.addHeader("content-type", "application/json");
        postMethod.setEntity(requestEntity);
        postMethod.addHeader("content-type", "application/json");
        httpResponse = client.execute(postMethod);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 401);

    }


    public void deleteExistUser() throws IOException {
        HttpDelete httpDelete = new HttpDelete("http://192.168.1.79:20007/api/users/2");
        httpResponse = client.execute(httpDelete);
        httpDelete.addHeader("content-type", "application/json");
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);

    }

    public void deleteNonExistUser() throws IOException {
        HttpDelete httpDelete = new HttpDelete("http://192.168.1.79/api/users/8");
        httpResponse = client.execute(httpDelete);
        httpDelete.addHeader("content-type", "application/json");
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 404);
    }
}


