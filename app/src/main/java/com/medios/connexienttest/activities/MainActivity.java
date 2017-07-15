package com.medios.connexienttest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.medios.connexienttest.R;
import com.medios.connexienttest.adapters.UserListAdapter;
import com.medios.connexienttest.model.apiconnection.APIRequest;
import com.medios.connexienttest.model.objectsmodel.LocationModel;
import com.medios.connexienttest.model.objectsmodel.UserObjectModel;
import com.medios.connexienttest.model.persistence.DataManager;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.user_list_recycler_view)
    RecyclerView userListRecyclerView;

    private final String LOG_TAG = "MainActivity";
    private final String API_URL = "http://api.randomuser.me/?results=%1$s&nat=us";
    private final int INITIAL_RECORDS_NUMBER = 30;
    private final int RECORDS_TO_FETCH = 20;
    private final int VISIBLE_THRESHOLD = 5;
    private ArrayList<UserObjectModel> userList;
    private boolean isLoading = false;
    private int lastVisibleItem, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        ButterKnife.bind(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        userListRecyclerView.setLayoutManager(layoutManager);
        userListRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        userListRecyclerView.addItemDecoration(dividerItemDecoration);

        userListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    getData(RECORDS_TO_FETCH);
                    isLoading = true;
                }
            }
        });

        userList = new ArrayList<>();

        getData(INITIAL_RECORDS_NUMBER);
    }

    /**
     * Gets the data from web service or locally if no internet connection is available
     * @param numberOfResults An integer that specifies how many records are retrieved from service
     */

    private void getData(int numberOfResults) {
        isLoading = true;
        if (APIRequest.getInstance().isNetworkAvailable(this)) {
            APIRequest.getInstance().fetchDataFrom(String.format(API_URL, numberOfResults), new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Toast.makeText(MainActivity.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String jsonString = response.body().string();
                    Log.d(LOG_TAG, jsonString);
                    try {
                        JSONObject results = new JSONObject(jsonString);
                        userList.addAll(parseJSON(results.getJSONArray("results")));
                        DataManager.getInstance().saveDataIntoLocalStorage(userList);
                        loadDataInList(userList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            loadDataInList(DataManager.getInstance().readDataFromLocalStorage());
        }
    }

    /**
     * This method parses a JSON object previously gotten from the web service to an ArrayList of
     * UserObjectModel objects
     * @param jsonArray JSONArray object with information of the users
     * @return A list containing users' info
     * @throws JSONException Exception thrown when reading from JSON failed
     */

    private ArrayList<UserObjectModel> parseJSON(JSONArray jsonArray) throws JSONException {
        ArrayList<UserObjectModel> userList = new ArrayList<>();
        String firstName;
        String lastName;
        String imageUrl;
        char gender;
        LocationModel location;

        for (int i = 0; i < jsonArray.length(); i++) {
            firstName = jsonArray.getJSONObject(i).getJSONObject("name").getString("first");
            lastName = jsonArray.getJSONObject(i).getJSONObject("name").getString("last");
            imageUrl = jsonArray.getJSONObject(i).getJSONObject("picture").getString("thumbnail");
            gender = jsonArray.getJSONObject(i).getString("gender").toUpperCase().charAt(0);

            String street = jsonArray.getJSONObject(i).getJSONObject("location").getString("street");
            String city = jsonArray.getJSONObject(i).getJSONObject("location").getString("city");
            String state = jsonArray.getJSONObject(i).getJSONObject("location").getString("state");
            location = new LocationModel(street, city, state);

            UserObjectModel user = new UserObjectModel(firstName, lastName, imageUrl, gender, location);

            userList.add(user);
        }

        return userList;
    }

    /**
     * Method to load the data into a recycler view
     * @param userList A list containing users' info
     */
    private void loadDataInList(final ArrayList<UserObjectModel> userList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (userListRecyclerView.getAdapter() == null) {
                    UserListAdapter adapter = new UserListAdapter(MainActivity.this, userList);
                    userListRecyclerView.setAdapter(adapter);
                } else {
                    userListRecyclerView.getAdapter().notifyDataSetChanged();
                }
                isLoading = false;
            }
        });
    }
}
