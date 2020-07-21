package edu.aku.hassannaqvi.covid_sero.models;

import android.database.Cursor;

import androidx.lifecycle.LiveData;

import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import edu.aku.hassannaqvi.covid_sero.contracts.PersonalContract.PersonalTable;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class Personal extends LiveData<Personal> {

    private String _ID = "";
    private String _UID = "";
    private String sysdate = "";
    private String a01 = ""; // Date
    private String a02 = ""; // Time
    private String a03 = ""; // Interviewer
    private String a04 = ""; // Province
    private String a05 = ""; // District
    private String refno = ""; // Reference Number
    private String istatus = ""; // Interview Status
    private String istatus96x = ""; // Interview Status
    private String endingdatetime = "";
    private String gpsLat = "";
    private String gpsLng = "";
    private String gpsDT = "";
    private String gpsAcc = "";
    private String deviceID = "";
    private String devicetagID = "";
    private String synced = "";
    private String synced_date = "";
    private String appversion = "";
    private String sA = "";
    private String sB = "";
    private String sC = "";
    private String sI = "";

    private HHModel hhModel;

    //Date Settings
    private LocalDate localDate = null, calculatedDOB = null;

    public Personal() {
    }

    public HHModel getHhModel() {
        return hhModel;
    }

    public void setHhModel(HHModel hhModel) {
        this.hhModel = hhModel;
    }

    public String getSysdate() {
        return sysdate;
    }

    public void setSysdate(String sysdate) {
        this.sysdate = sysdate;
    }


    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }


    public LocalDate getCalculatedDOB() {
        return calculatedDOB;
    }

    public void setCalculatedDOB(LocalDate calculatedDOB) {
        this.calculatedDOB = calculatedDOB;
    }


    public String getA04() {
        return a04;
    }

    public void setA04(String a04) {
        this.a04 = a04;
    }


    public String getA05() {
        return a05;
    }

    public void setA05(String a05) {
        this.a05 = a05;
    }

    public String getsA() {
        return sA;
    }

    public void setsA(String sA) {
        this.sA = sA;
    }

    public String getsB() {
        return sB;
    }

    public void setsB(String sB) {
        this.sB = sB;
    }

    public String getsC() {
        return sC;
    }

    public void setsC(String sC) {
        this.sC = sC;
    }


    public String getsI() {
        return sI;
    }

    public void setsI(String sI) {
        this.sI = sI;
    }


    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }


    public String getProjectName() {
        return "covid_sero";
    }


    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }


    public String get_UID() {
        return _UID;
    }

    public void set_UID(String _UID) {
        this._UID = _UID;
    }


    public String getA01() {
        return a01;
    }

    public void setA01(String a01) {
        this.a01 = a01;
    }


    public String getA02() {
        return a02;
    }

    public void setA02(String a02) {
        this.a02 = a02;
    }


    public String getA03() {
        return a03;
    }

    public void setA03(String a03) {
        this.a03 = a03;
    }


    public String getIstatus() {
        return istatus;
    }

    public void setIstatus(String istatus) {
        this.istatus = istatus;
    }


    public String getIstatus96x() {
        return istatus96x;
    }

    public void setIstatus96x(String istatus96x) {
        this.istatus96x = istatus96x;
    }


    public String getEndingdatetime() {
        return endingdatetime;
    }

    public void setEndingdatetime(String endingdatetime) {
        this.endingdatetime = endingdatetime;
    }


    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }


    public String getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(String gpsLng) {
        this.gpsLng = gpsLng;
    }


    public String getGpsDT() {
        return gpsDT;
    }

    public void setGpsDT(String gpsDT) {
        this.gpsDT = gpsDT;
    }


    public String getGpsAcc() {
        return gpsAcc;
    }

    public void setGpsAcc(String gpsAcc) {
        this.gpsAcc = gpsAcc;
    }


    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }


    public String getDevicetagID() {
        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }


    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }


    public String getSynced_date() {
        return synced_date;
    }

    public void setSynced_date(String synced_date) {
        this.synced_date = synced_date;
    }


    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }


    public Personal Sync(JSONObject jsonObject) throws JSONException {
        this._ID = jsonObject.getString(PersonalTable.COLUMN_ID);
        this._UID = jsonObject.getString(PersonalTable.COLUMN_UID);
        this.sysdate = jsonObject.getString(PersonalTable.COLUMN_SYSDATE);
        this.a01 = jsonObject.getString(PersonalTable.COLUMN_A01);
        this.a02 = jsonObject.getString(PersonalTable.COLUMN_A02);
        this.a03 = jsonObject.getString(PersonalTable.COLUMN_A03);
        this.a04 = jsonObject.getString(PersonalTable.COLUMN_A04);
        this.a05 = jsonObject.getString(PersonalTable.COLUMN_A05);
        this.refno = jsonObject.getString(PersonalTable.COLUMN_REFNO);
        this.istatus = jsonObject.getString(PersonalTable.COLUMN_ISTATUS);
        this.istatus96x = jsonObject.getString(PersonalTable.COLUMN_ISTATUS96x);
        this.endingdatetime = jsonObject.getString(PersonalTable.COLUMN_ENDINGDATETIME);
        this.gpsLat = jsonObject.getString(PersonalTable.COLUMN_GPSLAT);
        this.gpsLng = jsonObject.getString(PersonalTable.COLUMN_GPSLNG);
        this.gpsDT = jsonObject.getString(PersonalTable.COLUMN_GPSDATE);
        this.gpsAcc = jsonObject.getString(PersonalTable.COLUMN_GPSACC);
        this.deviceID = jsonObject.getString(PersonalTable.COLUMN_DEVICEID);
        this.devicetagID = jsonObject.getString(PersonalTable.COLUMN_DEVICETAGID);
        this.synced = jsonObject.getString(PersonalTable.COLUMN_SYNCED);
        this.synced_date = jsonObject.getString(PersonalTable.COLUMN_SYNCED_DATE);
        this.appversion = jsonObject.getString(PersonalTable.COLUMN_SYNCED_DATE);
        this.sA = jsonObject.getString(PersonalTable.COLUMN_SA);
        this.sB = jsonObject.getString(PersonalTable.COLUMN_SB);
        this.sC = jsonObject.getString(PersonalTable.COLUMN_SC);
        this.sI = jsonObject.getString(PersonalTable.COLUMN_SI);

        return this;
    }

    public Personal Hydrate(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_ID));
        this._UID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_UID));
        this.sysdate = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SYSDATE));
        this.a01 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_A01));
        this.a02 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_A02));
        this.a03 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_A03));
        this.a04 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_A04));
        this.a05 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_A05));
        this.refno = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_REFNO));
        this.istatus = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_ISTATUS));
        this.istatus96x = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_ISTATUS96x));
        this.endingdatetime = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_ENDINGDATETIME));
        this.gpsLat = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_GPSLAT));
        this.gpsLng = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_GPSLNG));
        this.gpsDT = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_GPSDATE));
        this.gpsAcc = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_GPSACC));
        this.deviceID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_DEVICEID));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_DEVICETAGID));
        this.appversion = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_APPVERSION));
        this.sA = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SA));
        this.sB = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SB));
        this.sC = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SC));
        this.sI = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SI));


        return this;
    }

    //TODO: Try this instead of toJSONObject
    @NotNull
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, Personal.class);
    }

    public JSONObject toJSONObject() {

        JSONObject json = new JSONObject();

        try {
            json.put(PersonalTable.COLUMN_ID, this._ID == null ? JSONObject.NULL : this._ID);
            json.put(PersonalTable.COLUMN_SYSDATE, this.sysdate == null ? JSONObject.NULL : this.sysdate);
            json.put(PersonalTable.COLUMN_UID, this._UID == null ? JSONObject.NULL : this._UID);
            json.put(PersonalTable.COLUMN_A01, this.a01 == null ? JSONObject.NULL : this.a01);
            json.put(PersonalTable.COLUMN_A02, this.a02 == null ? JSONObject.NULL : this.a02);
            json.put(PersonalTable.COLUMN_A03, this.a03 == null ? JSONObject.NULL : this.a03);
            json.put(PersonalTable.COLUMN_A04, this.a04 == null ? JSONObject.NULL : this.a04);
            json.put(PersonalTable.COLUMN_A05, this.a05 == null ? JSONObject.NULL : this.a05);
            json.put(PersonalTable.COLUMN_REFNO, this.refno == null ? JSONObject.NULL : this.refno);
            json.put(PersonalTable.COLUMN_ISTATUS, this.istatus == null ? JSONObject.NULL : this.istatus);
            json.put(PersonalTable.COLUMN_ISTATUS96x, this.istatus96x == null ? JSONObject.NULL : this.istatus96x);
            json.put(PersonalTable.COLUMN_ENDINGDATETIME, this.endingdatetime == null ? JSONObject.NULL : this.endingdatetime);

            if (this.sA != null && !this.sA.equals("")) {
                json.put(PersonalTable.COLUMN_SA, new JSONObject(this.sA));
            }
            if (this.sB != null && !this.sB.equals("")) {
                json.put(PersonalTable.COLUMN_SB, new JSONObject(this.sB));
            }
            if (this.sC != null && !this.sC.equals("")) {
                json.put(PersonalTable.COLUMN_SC, new JSONObject(this.sC));
            }
            if (this.sI != null && !this.sI.equals("")) {
                json.put(PersonalTable.COLUMN_SI, new JSONObject(this.sI));
            }

            json.put(PersonalTable.COLUMN_GPSLAT, this.gpsLat == null ? JSONObject.NULL : this.gpsLat);
            json.put(PersonalTable.COLUMN_GPSLNG, this.gpsLng == null ? JSONObject.NULL : this.gpsLng);
            json.put(PersonalTable.COLUMN_GPSDATE, this.gpsDT == null ? JSONObject.NULL : this.gpsDT);
            json.put(PersonalTable.COLUMN_GPSACC, this.gpsAcc == null ? JSONObject.NULL : this.gpsAcc);
            json.put(PersonalTable.COLUMN_DEVICEID, this.deviceID == null ? JSONObject.NULL : this.deviceID);
            json.put(PersonalTable.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
            json.put(PersonalTable.COLUMN_APPVERSION, this.appversion == null ? JSONObject.NULL : this.appversion);

            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
