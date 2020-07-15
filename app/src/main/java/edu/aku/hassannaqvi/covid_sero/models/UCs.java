package edu.aku.hassannaqvi.covid_sero.models;


import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_sero.contracts.UCsContract.UCsTable;

public class UCs {

    private static final String TAG = "UCs_CONTRACT";
    String uccode;
    String ucs;
    String taluka_code;

    public UCs() {
        // Default Constructor
    }


    public String getUccode() {
        return uccode;
    }

    public void setUccode(String uccode) {
        this.uccode = uccode;
    }

    public String getUcs() {
        return ucs;
    }

    public void setUcs(String ucs) {
        this.ucs = ucs;
    }

    public String getTaluka_code() {
        return taluka_code;
    }

    public void setTaluka_code(String taluka_code) {
        this.taluka_code = taluka_code;
    }

    public JSONObject toJSONObject() {

        JSONObject json = new JSONObject();
        try {
            json.put(UCsTable.COLUMN_UCCODE, this.uccode == null ? JSONObject.NULL : this.uccode);
            json.put(UCsTable.COLUMN_UCS, this.ucs == null ? JSONObject.NULL : this.ucs);
            json.put(UCsTable.COLUMN_TALUKA_CODE, this.taluka_code == null ? JSONObject.NULL : this.taluka_code);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UCs Sync(JSONObject jsonObject) throws JSONException {
        this.uccode = jsonObject.getString(UCsTable.COLUMN_UCCODE);
        this.ucs = jsonObject.getString(UCsTable.COLUMN_UCS);
        this.taluka_code = jsonObject.getString(UCsTable.COLUMN_TALUKA_CODE);
        return this;
    }

    public UCs HydrateUCs(Cursor cursor) {
        this.uccode = cursor.getString(cursor.getColumnIndex(UCsTable.COLUMN_UCCODE));
        this.ucs = cursor.getString(cursor.getColumnIndex(UCsTable.COLUMN_UCS));
        this.taluka_code = cursor.getString(cursor.getColumnIndex(UCsTable.COLUMN_TALUKA_CODE));
        return this;
    }
}