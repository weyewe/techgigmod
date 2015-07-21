package weyewe.com.techgig;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import weyewe.com.techgig.database.DBApp;
import weyewe.com.techgig.network.VolleySingleton;
import weyewe.com.techgig.pojo.SubReddit;


public class MainActivity  extends AppCompatActivity {
    private static final String TAG = "RecyclerViewExample";
    private List<SubReddit> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private ProgressBar progressBar;

    private String urlJsonObj = "http://nsfwapp-weyewe1.c9.io/api2/sub_reddits.json";
    private VolleySingleton volleySingleton;
    private ArrayList<SubReddit> subRedditArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);

        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);


        feedsList  = TechgigApplication.getWritableDatabase().readMovies(1);


        adapter = new MyRecyclerAdapter(MainActivity.this , feedsList);
        mRecyclerView.setAdapter(adapter);


        if( feedsList.size() == 0 ){
            loadNSFWData();
        }


    }

    private void loadNSFWData(){

        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonBody = new JSONObject();
        JSONObject userLogin = new JSONObject();


        try {
            userLogin.put("email", "willy@gmail.com");
            userLogin.put("password", "willy1234");
            jsonBody.put("user_login",  userLogin );
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                urlJsonObj,
                jsonBody,
                createMyReqSuccessListener(),
                createMyReqErrorListener()
        )
        {
        };

        // Adding request to request queue
//        volleySingleton.getRequestQueue().add(jsonObjReq) ;
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);
    }


    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("BOOM", response.toString());
//                ArrayList<SubReddit> subRedditList = new ArrayList<SubReddit>();
                try {
//                    String auth_token = response.getString("auth_token");
//                    String email = response.getString("email");
                    JSONArray subRedditsArray = response.getJSONArray("sub_reddits");

                    for (int i = 0; i < subRedditsArray.length(); i++) {
                        JSONObject row = subRedditsArray.getJSONObject(i);
                        long server_id = row.getLong("id");
                        String name = row.getString("name");
                        String urlImage = row.getString("image_url");

                        String jsonElementText  = "\n";
                        jsonElementText += "ServerId: " + server_id + "\n\n";
                        jsonElementText += "AuthToken: " + name + "\n\n";
                        jsonElementText += "Email: " + urlImage + "\n\n";

//                        Log.d( "element " + i, jsonElementText);

                        SubReddit newObject= new SubReddit();
                        newObject.setId( server_id );
                        newObject.setName(name) ;
                        newObject.setUrlImage(urlImage);

                        feedsList.add( newObject );

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText( getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                Log.d("elementxxx ", " >>> hahaha. Total size: " + feedsList.size() );
                Log.d("theWritableDatabase: ", TechgigApplication.getWritableDatabase().toString());
                TechgigApplication.getWritableDatabase().insertSubReddits(DBApp.BOX_OFFICE, feedsList, true);
//                hidepDialog();

//                adapter = new MyRecyclerAdapter(MainActivity.this , feedsList);
                adapter.notifyDataSetChanged();
//                mRecyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
            }
        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                hidepDialog();
                progressBar.setVisibility(View.GONE);
            }
        };
    }


//
//    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
//
//        @Override
//        protected void onPreExecute() {
//            setProgressBarIndeterminateVisibility(true);
//        }
//
//        @Override
//        protected Integer doInBackground(String... params) {
//            Integer result = 0;
//            HttpURLConnection urlConnection;
//            try {
//                URL url = new URL(params[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                int statusCode = urlConnection.getResponseCode();
//
//                // 200 represents HTTP OK
//                if (statusCode == 200) {
//                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = r.readLine()) != null) {
//                        response.append(line);
//                    }
//                    parseResult(response.toString());
//                    result = 1; // Successful
//                } else {
//                    result = 0; //"Failed to fetch data!";
//                }
//            } catch (Exception e) {
//                Log.d(TAG, e.getLocalizedMessage());
//            }
//            return result; //"Failed to fetch data!";
//        }
//
//        @Override
//        protected void onPostExecute(Integer result) {
//            // Download complete. Let us update UI
//            progressBar.setVisibility(View.GONE);
//
//            if (result == 1) {
//                adapter = new MyRecyclerAdapter(MainActivity.this , feedsList);
//                mRecyclerView.setAdapter(adapter);
//            } else {
//                Toast.makeText( MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void parseResult(String result) {
//        try {
//            JSONObject response = new JSONObject(result);
//            JSONArray posts = response.optJSONArray("posts");
//            feedsList = new ArrayList<>();
//
//            for (int i = 0; i < posts.length(); i++) {
//                JSONObject post = posts.optJSONObject(i);
//                FeedItem item = new FeedItem();
//                item.setTitle(post.optString("title"));
//                item.setThumbnail(post.optString("thumbnail"));
//
//                feedsList.add(item);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
