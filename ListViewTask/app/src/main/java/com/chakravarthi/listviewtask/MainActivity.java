package com.chakravarthi.listviewtask;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chakravarthi.listviewtask.adapter.RowListAdapter;
import com.chakravarthi.listviewtask.api.ApiTask;
import com.chakravarthi.listviewtask.constants.ApiConstants;
import com.chakravarthi.listviewtask.interfaces.OnApiTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnApiTaskCompleted {

    private ProgressBar dataLoading;
    private ListView rowsListView;
    private RelativeLayout errorLayout;
    private RowListAdapter rowListAdapter;
    private boolean refreshItems = false;
    private String LOG_TAG = getClass().getSimpleName();
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataLoading = (ProgressBar) findViewById(R.id.loading);
        rowsListView = (ListView) findViewById(R.id.rows_view);
        errorLayout = (RelativeLayout) findViewById(R.id.error_layout);

        actionBar = getActionBar();

        loadDataFromApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.btn_refresh:
                Toast.makeText(getBaseContext(), "You selected refresh", Toast.LENGTH_SHORT).show();
                refreshListItems();
                break;
        }
        return true;
    }

    private void refreshListItems() {
        refreshItems = true;
        rowsListView.setVisibility(View.GONE);
        dataLoading.setVisibility(View.VISIBLE);
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

                if(apiResult.has("title")) {
                    actionBar.setTitle(apiResult.getString("title"));
                }

                for(int i=0; i < rowsArray.length(); i++) {
                    rowItemsList.add(rowsArray.getJSONObject(i));
                }

                if(!refreshItems) {
                    rowListAdapter = new RowListAdapter(getApplicationContext(), rowItemsList);
                    rowsListView.setAdapter(rowListAdapter);
                    dataLoading.setVisibility(View.GONE);
                    rowsListView.setVisibility(View.VISIBLE);
                } else {
                    rowListAdapter.refreshEvents(rowItemsList);
                    rowsListView.setVisibility(View.VISIBLE);
                    dataLoading.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorLayout();
        }
    }

    private void showErrorLayout() {
        try {
            dataLoading.setVisibility(View.GONE);
            rowsListView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}