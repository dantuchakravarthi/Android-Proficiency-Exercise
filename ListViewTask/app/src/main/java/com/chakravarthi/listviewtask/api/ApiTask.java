package com.chakravarthi.listviewtask.api;

import android.os.AsyncTask;

import com.chakravarthi.listviewtask.interfaces.OnApiTaskCompleted;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiTask extends AsyncTask<String, Void, JSONObject> {
    private OnApiTaskCompleted listener;
    private int apiRequestCode;

    public ApiTask(OnApiTaskCompleted listener, int apiRequestCode) {
        this.listener = listener;
        this.apiRequestCode = apiRequestCode;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String response;
        try {
            URL u = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "ISO-8859-1"));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            response = stringBuilder.toString();
            return new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(JSONObject apiResult) {
        listener.onTaskCompleted(apiResult, apiRequestCode);
    }
}