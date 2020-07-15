package edu.aku.hassannaqvi.covid_sero.models;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_sero.contracts.UsersContract.UsersTable;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class Users {

    private static final String TAG = "Users_CONTRACT";
    Long id;
    String username;
    String password;
    String distId;
//    String REGION_DSS;

    public Users() {
        // Default Constructor
    }

    public Users(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public Long getUserID() {
        return this.id;
    }

    public void setId(int id) {
        this.id = Long.valueOf(id);
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

/*    public String getREGION_DSS() {
        return REGION_DSS;
    }

    public void setREGION_DSS(String REGION_DSS) {
        this.REGION_DSS = REGION_DSS;
    }*/

    public Users Sync(JSONObject jsonObject) throws JSONException {
        this.username = jsonObject.getString(UsersTable.COLUMN_USERNAME);
        this.password = jsonObject.getString(UsersTable.COLUMN_PASSWORD);
        this.distId = jsonObject.getString(UsersTable.DIST_ID);
//        this.REGION_DSS = jsonObject.getString(singleUser.REGION_DSS);
        return this;

    }

    public Users Hydrate(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(UsersTable._ID));
        this.username = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_USERNAME));
        this.password = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_PASSWORD));
        this.distId = cursor.getString(cursor.getColumnIndex(UsersTable.DIST_ID));
//        this.REGION_DSS = cursor.getString(cursor.getColumnIndex(singleUser.REGION_DSS));
        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(UsersTable._ID, this.id == null ? JSONObject.NULL : this.id);
        json.put(UsersTable.COLUMN_USERNAME, this.username == null ? JSONObject.NULL : this.username);
        json.put(UsersTable.COLUMN_PASSWORD, this.password == null ? JSONObject.NULL : this.password);
        json.put(UsersTable.DIST_ID, this.distId == null ? JSONObject.NULL : this.distId);
//        json.put(singleUser.REGION_DSS, this.REGION_DSS == null ? JSONObject.NULL : this.REGION_DSS);
        return json;
    }

}