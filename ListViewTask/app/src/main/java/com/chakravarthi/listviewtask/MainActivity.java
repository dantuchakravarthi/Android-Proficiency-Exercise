package com.chakravarthi.listviewtask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.chakravarthi.listviewtask.adapter.RowListAdapter;
import com.chakravarthi.listviewtask.api.ApiTask;
import com.chakravarthi.listviewtask.constants.ApiConstants;
import com.chakravarthi.listviewtask.interfaces.OnApiTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnApiTaskCompleted {

    private ProgressBar dataLoading;
    private ListView rowsListView;
    private String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataLoading = (ProgressBar) findViewById(R.id.loading);
        rowsListView = (ListView) findViewById(R.id.rows_view);

        loadDataFromApi();
    }

    private void loadDataFromApi() {
        ApiTask apitask = new ApiTask(this, getResources().getInteger(R.integer.api_request_code));
        apitask.execute(ApiConstants.API_URL);
    }

    @Override
    public void onTaskCompleted(JSONObject apiResult, int apiRequestCode) {
        try {
            if(apiRequestCode == getResources().getInteger(R.integer.api_request_code)) {
                Log.e(LOG_TAG, apiResult.toString());

                ArrayList<JSONObject> rowItemsList = new ArrayList<>();
                JSONArray rowsArray = apiResult.has("rows") ? apiResult.getJSONArray("rows") : new JSONArray();

                for(int i=0; i < rowsArray.length(); i++) {
                    rowItemsList.add(rowsArray.getJSONObject(i));
                }

                RowListAdapter rowListAdapter = new RowListAdapter(getApplicationContext(), rowItemsList);
                rowsListView.setAdapter(rowListAdapter);
                dataLoading.setVisibility(View.GONE);
                rowsListView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showErrorLayout() {
    }
}