package com.chakravarthi.listviewtask.interfaces;

import org.json.JSONObject;

public interface OnApiTaskCompleted {
    void onTaskCompleted(JSONObject apiResult, int apiRequestCode);
}