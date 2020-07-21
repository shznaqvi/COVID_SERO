package edu.aku.hassannaqvi.covid_sero.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import edu.aku.hassannaqvi.covid_sero.contracts.BLRandomContract.BLRandomTable;
import edu.aku.hassannaqvi.covid_sero.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.covid_sero.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_sero.contracts.UsersContract.UsersTable;
import edu.aku.hassannaqvi.covid_sero.contracts.VersionAppContract;
import edu.aku.hassannaqvi.covid_sero.contracts.VersionAppContract.VersionAppTable;
import edu.aku.hassannaqvi.covid_sero.models.BLRandom;
import edu.aku.hassannaqvi.covid_sero.models.Form;
import edu.aku.hassannaqvi.covid_sero.models.Personal;
import edu.aku.hassannaqvi.covid_sero.models.Users;
import edu.aku.hassannaqvi.covid_sero.models.VersionApp;

import static edu.aku.hassannaqvi.covid_sero.core.MainApp.form;
import static edu.aku.hassannaqvi.covid_sero.utils.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.covid_sero.utils.CreateTable.DATABASE_VERSION;
import static edu.aku.hassannaqvi.covid_sero.utils.CreateTable.SQL_CREATE_BL_RANDOM;
import static edu.aku.hassannaqvi.covid_sero.utils.CreateTable.SQL_CREATE_FORMS;
import static edu.aku.hassannaqvi.covid_sero.utils.CreateTable.SQL_CREATE_PERSONALS;
import static edu.aku.hassannaqvi.covid_sero.utils.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.covid_sero.utils.CreateTable.SQL_CREATE_VERSIONAPP;


/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_FORMS);
        db.execSQL(SQL_CREATE_PERSONALS);
        db.execSQL(SQL_CREATE_BL_RANDOM);
        db.execSQL(SQL_CREATE_VERSIONAPP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int syncBLRandom(JSONArray blList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BLRandomTable.TABLE_NAME, null, null);

        int insertCount = 0;
        for (int i = 0; i < blList.length(); i++) {
            JSONObject jsonObjectCC = null;
            try {
                jsonObjectCC = blList.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            BLRandom Vc = new BLRandom();
            Vc.Sync(jsonObjectCC);
            Log.d(TAG, "syncBLRandom: " + Vc.get_ID());
            ContentValues values = new ContentValues();

            values.put(BLRandomTable.COLUMN_ID, Vc.get_ID());
            values.put(BLRandomTable.COLUMN_LUID, Vc.getLUID());
            values.put(BLRandomTable.COLUMN_STRUCTURE_NO, Vc.getStructure());
            values.put(BLRandomTable.COLUMN_FAMILY_EXT_CODE, Vc.getExtension());
            values.put(BLRandomTable.COLUMN_HH, Vc.getHh());
            values.put(BLRandomTable.COLUMN_EB_CODE, Vc.getEbcode());
            values.put(BLRandomTable.COLUMN_P_CODE, Vc.getpCode());
            values.put(BLRandomTable.COLUMN_RANDOMDT, Vc.getRandomDT());
            values.put(BLRandomTable.COLUMN_HH_HEAD, Vc.getHhhead());
            values.put(BLRandomTable.COLUMN_CONTACT, Vc.getContact());
            values.put(BLRandomTable.COLUMN_HH_SELECTED_STRUCT, Vc.getSelStructure());
            values.put(BLRandomTable.COLUMN_SNO_HH, Vc.getSno());

            long row = db.insert(BLRandomTable.TABLE_NAME, null, values);
            if (row != -1) insertCount++;
        }
        return insertCount;
    }

    public Integer syncVersionApp(JSONObject VersionList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VersionAppContract.VersionAppTable.TABLE_NAME, null, null);
        long count = 0;
        try {
            JSONObject jsonObjectCC = ((JSONArray) VersionList.get(VersionAppContract.VersionAppTable.COLUMN_VERSION_PATH)).getJSONObject(0);
            VersionApp Vc = new VersionApp();
            Vc.Sync(jsonObjectCC);

            ContentValues values = new ContentValues();

            values.put(VersionAppContract.VersionAppTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            count = db.insert(VersionAppContract.VersionAppTable.TABLE_NAME, null, values);
            if (count > 0) count = 1;

        } catch (Exception ignored) {
        } finally {
            db.close();
        }

        return (int) count;
    }

    public VersionApp getVersionApp() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VersionAppTable._ID,
                VersionAppTable.COLUMN_VERSION_CODE,
                VersionAppTable.COLUMN_VERSION_NAME,
                VersionAppTable.COLUMN_PATH_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = null;

        VersionApp allVC = new VersionApp();
        try {
            c = db.query(
                    VersionAppTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allVC.hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }

    public int syncUser(JSONArray userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < userList.length(); i++) {

                JSONObject jsonObjectUser = userList.getJSONObject(i);

                Users user = new Users();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersTable.COLUMN_USERNAME, user.getUserName());
                values.put(UsersTable.COLUMN_PASSWORD, user.getPassword());
                values.put(UsersTable.DIST_ID, user.getDistId());
                long rowID = db.insert(UsersTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + UsersTable.TABLE_NAME + " WHERE " + UsersTable.COLUMN_USERNAME + "=? AND " + UsersTable.COLUMN_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {

                if (mCursor.moveToFirst()) {
//                    MainApp.DIST_ID = mCursor.getString(mCursor.getColumnIndex(Users.UsersTable.ROW_USERNAME));
                }
                return true;
            }
        }
        return false;
    }

    public Long addPersonal(Personal personal) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PersonalContract.PersonalTable.COLUMN_PROJECT_NAME, personal.getProjectName());
        values.put(PersonalContract.PersonalTable.COLUMN_UID, personal.get_UID());
        values.put(PersonalContract.PersonalTable.COLUMN_SYSDATE, personal.getSysdate());
        values.put(PersonalContract.PersonalTable.COLUMN_A01, personal.getA01());
        values.put(PersonalContract.PersonalTable.COLUMN_A02, personal.getA02());
        values.put(PersonalContract.PersonalTable.COLUMN_A03, personal.getA03());
        values.put(PersonalContract.PersonalTable.COLUMN_A04, personal.getA04());
        values.put(PersonalContract.PersonalTable.COLUMN_A05, personal.getA05());
        values.put(PersonalContract.PersonalTable.COLUMN_REFNO, personal.getRefno());
        values.put(PersonalContract.PersonalTable.COLUMN_ISTATUS, personal.getIstatus());
        values.put(PersonalContract.PersonalTable.COLUMN_ISTATUS96x, personal.getIstatus96x());
        values.put(PersonalContract.PersonalTable.COLUMN_ENDINGDATETIME, personal.getEndingdatetime());
        values.put(PersonalContract.PersonalTable.COLUMN_SA, personal.getsA());
        values.put(PersonalContract.PersonalTable.COLUMN_SB, personal.getsB());
        values.put(PersonalContract.PersonalTable.COLUMN_SC, personal.getsC());
        values.put(PersonalContract.PersonalTable.COLUMN_SI, personal.getsI());
        values.put(PersonalContract.PersonalTable.COLUMN_GPSLAT, personal.getGpsLat());
        values.put(PersonalContract.PersonalTable.COLUMN_GPSLNG, personal.getGpsLng());
        values.put(PersonalContract.PersonalTable.COLUMN_GPSDATE, personal.getGpsDT());
        values.put(PersonalContract.PersonalTable.COLUMN_GPSACC, personal.getGpsAcc());
        values.put(PersonalContract.PersonalTable.COLUMN_DEVICETAGID, personal.getDevicetagID());
        values.put(PersonalContract.PersonalTable.COLUMN_DEVICEID, personal.getDeviceID());
        values.put(PersonalContract.PersonalTable.COLUMN_APPVERSION, personal.getAppversion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                PersonalContract.PersonalTable.TABLE_NAME,
                PersonalContract.PersonalTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addForm(Form form) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECT_NAME, form.getProjectName());
        values.put(FormsTable.COLUMN_UID, form.get_UID());
        values.put(FormsTable.COLUMN_SYSDATE, form.getSysdate());
        values.put(FormsTable.COLUMN_A01, form.getA01());
        values.put(FormsTable.COLUMN_A02, form.getA02());
        values.put(FormsTable.COLUMN_A03, form.getA03());
        values.put(FormsTable.COLUMN_A04, form.getA04());
        values.put(FormsTable.COLUMN_A05, form.getA05());
        values.put(FormsTable.COLUMN_REFNO, form.getRefno());
        values.put(FormsTable.COLUMN_ISTATUS, form.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS96x, form.getIstatus96x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, form.getEndingdatetime());
        values.put(FormsTable.COLUMN_SINFO, form.getsInfo());
        values.put(FormsTable.COLUMN_SH3, form.getsH3());
        values.put(FormsTable.COLUMN_SH4, form.getsH4());
        values.put(FormsTable.COLUMN_GPSLAT, form.getGpsLat());
        values.put(FormsTable.COLUMN_GPSLNG, form.getGpsLng());
        values.put(FormsTable.COLUMN_GPSDATE, form.getGpsDT());
        values.put(FormsTable.COLUMN_GPSACC, form.getGpsAcc());
        values.put(FormsTable.COLUMN_DEVICETAGID, form.getDevicetagID());
        values.put(FormsTable.COLUMN_DEVICEID, form.getDeviceID());
        values.put(FormsTable.COLUMN_APPVERSION, form.getAppversion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsTable.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public int updateFormID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_UID, form.get_UID());

// Which row to update, based on the ID
        String selection = FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(form.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public Collection<Form> getAllForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_A01,
                FormsTable.COLUMN_A02,
                FormsTable.COLUMN_A03,
                FormsTable.COLUMN_A04,
                FormsTable.COLUMN_A05,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SH3,
                FormsTable.COLUMN_SH4,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<Form>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                allForms.add(form.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public Collection<Personal> getAllPersonal() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalContract.PersonalTable._ID,
                PersonalContract.PersonalTable.COLUMN_UID,
                PersonalContract.PersonalTable.COLUMN_SYSDATE,
                PersonalContract.PersonalTable.COLUMN_A01,
                PersonalContract.PersonalTable.COLUMN_A02,
                PersonalContract.PersonalTable.COLUMN_A03,
                PersonalContract.PersonalTable.COLUMN_A04,
                PersonalContract.PersonalTable.COLUMN_A05,
                PersonalContract.PersonalTable.COLUMN_REFNO,
                PersonalContract.PersonalTable.COLUMN_ISTATUS,
                PersonalContract.PersonalTable.COLUMN_SA,
                PersonalContract.PersonalTable.COLUMN_SB,
                PersonalContract.PersonalTable.COLUMN_SC,
                PersonalContract.PersonalTable.COLUMN_SI,
                PersonalContract.PersonalTable.COLUMN_GPSLAT,
                PersonalContract.PersonalTable.COLUMN_GPSLNG,
                PersonalContract.PersonalTable.COLUMN_GPSDATE,
                PersonalContract.PersonalTable.COLUMN_GPSACC,
                PersonalContract.PersonalTable.COLUMN_DEVICETAGID,
                PersonalContract.PersonalTable.COLUMN_DEVICEID,
                PersonalContract.PersonalTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                PersonalContract.PersonalTable.COLUMN_ID + " ASC";

        Collection<Personal> allPersonal = new ArrayList<Personal>();
        try {
            c = db.query(
                    PersonalContract.PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Personal personal = new Personal();
                allPersonal.add(personal.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }

    public Collection<Form> checkFormExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_A01,
                FormsTable.COLUMN_A02,
                FormsTable.COLUMN_A03,
                FormsTable.COLUMN_A04,
                FormsTable.COLUMN_A05,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SH3,
                FormsTable.COLUMN_SH4,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<Form>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                allForms.add(form.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public Collection<Personal> checkPersonalExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalContract.PersonalTable._ID,
                PersonalContract.PersonalTable.COLUMN_UID,
                PersonalContract.PersonalTable.COLUMN_SYSDATE,
                PersonalContract.PersonalTable.COLUMN_A01,
                PersonalContract.PersonalTable.COLUMN_A02,
                PersonalContract.PersonalTable.COLUMN_A03,
                PersonalContract.PersonalTable.COLUMN_A04,
                PersonalContract.PersonalTable.COLUMN_A05,
                PersonalContract.PersonalTable.COLUMN_REFNO,
                PersonalContract.PersonalTable.COLUMN_ISTATUS,
                PersonalContract.PersonalTable.COLUMN_SA,
                PersonalContract.PersonalTable.COLUMN_SB,
                PersonalContract.PersonalTable.COLUMN_SC,
                PersonalContract.PersonalTable.COLUMN_SI,
                PersonalContract.PersonalTable.COLUMN_GPSLAT,
                PersonalContract.PersonalTable.COLUMN_GPSLNG,
                PersonalContract.PersonalTable.COLUMN_GPSDATE,
                PersonalContract.PersonalTable.COLUMN_GPSACC,
                PersonalContract.PersonalTable.COLUMN_DEVICETAGID,
                PersonalContract.PersonalTable.COLUMN_DEVICEID,
                PersonalContract.PersonalTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                PersonalContract.PersonalTable.COLUMN_ID + " ASC";

        Collection<Personal> allPersonal = new ArrayList<Personal>();
        try {
            c = db.query(
                    PersonalContract.PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Personal personal = new Personal();
                allPersonal.add(personal.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }

    public Collection<Form> getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_A01,
                FormsTable.COLUMN_A02,
                FormsTable.COLUMN_A03,
                FormsTable.COLUMN_A04,
                FormsTable.COLUMN_A05,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS96x,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SH3,
                FormsTable.COLUMN_SH4,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
        };


        String whereClause = FormsTable.COLUMN_SYNCED + " is null AND " + FormsTable.COLUMN_ISTATUS + " != '' ";
        //String whereClause = FormsTable.COLUMN_ISTATUS +" != '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<Form>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                allForms.add(form.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public Collection<Personal> getUnsyncedPersonal() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalContract.PersonalTable._ID,
                PersonalContract.PersonalTable.COLUMN_UID,
                PersonalContract.PersonalTable.COLUMN_SYSDATE,
                PersonalContract.PersonalTable.COLUMN_A01,
                PersonalContract.PersonalTable.COLUMN_A02,
                PersonalContract.PersonalTable.COLUMN_A03,
                PersonalContract.PersonalTable.COLUMN_A04,
                PersonalContract.PersonalTable.COLUMN_A05,
                PersonalContract.PersonalTable.COLUMN_REFNO,
                PersonalContract.PersonalTable.COLUMN_ISTATUS,
                PersonalContract.PersonalTable.COLUMN_ISTATUS96x,
                PersonalContract.PersonalTable.COLUMN_ENDINGDATETIME,
                PersonalContract.PersonalTable.COLUMN_SA,
                PersonalContract.PersonalTable.COLUMN_SB,
                PersonalContract.PersonalTable.COLUMN_SC,
                PersonalContract.PersonalTable.COLUMN_SI,
                PersonalContract.PersonalTable.COLUMN_GPSLAT,
                PersonalContract.PersonalTable.COLUMN_GPSLNG,
                PersonalContract.PersonalTable.COLUMN_GPSDATE,
                PersonalContract.PersonalTable.COLUMN_GPSACC,
                PersonalContract.PersonalTable.COLUMN_DEVICETAGID,
                PersonalContract.PersonalTable.COLUMN_DEVICEID,
                PersonalContract.PersonalTable.COLUMN_APPVERSION,
        };


        String whereClause = PersonalContract.PersonalTable.COLUMN_SYNCED + " is null AND " + PersonalContract.PersonalTable.COLUMN_ISTATUS + " != '' ";
        //String whereClause = PersonalTable.COLUMN_ISTATUS +" != '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                PersonalContract.PersonalTable.COLUMN_ID + " ASC";

        Collection<Personal> allPersonal = new ArrayList<Personal>();
        try {
            c = db.query(
                    PersonalContract.PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Personal personal = new Personal();
                allPersonal.add(personal.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }

    public Collection<Form> getTodayForms(String sysdate) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_A01,
                FormsTable.COLUMN_A02,
                FormsTable.COLUMN_A04,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_A01 + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                form.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                form.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                form.setSysdate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                form.setA01(c.getString(c.getColumnIndex(FormsTable.COLUMN_A01)));
                form.setA02(c.getString(c.getColumnIndex(FormsTable.COLUMN_A02)));
                form.setRefno(c.getString(c.getColumnIndex(FormsTable.COLUMN_REFNO)));
                form.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                form.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allForms.add(form);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public Collection<Form> getFormsByCluster(String cluster) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_A01,
                FormsTable.COLUMN_A02,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_REFNO + " = ? ";
        String[] whereArgs = new String[]{cluster};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                form.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                form.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                form.setSysdate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                form.setA01(c.getString(c.getColumnIndex(FormsTable.COLUMN_A01)));
                form.setA02(c.getString(c.getColumnIndex(FormsTable.COLUMN_A02)));
                form.setRefno(c.getString(c.getColumnIndex(FormsTable.COLUMN_REFNO)));
                form.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                form.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allForms.add(form);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public ArrayList<Form> getUnclosedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_A01,
                FormsTable.COLUMN_A02,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,
        };
        String whereClause = FormsTable.COLUMN_ISTATUS + " = ''";
        String[] whereArgs = null;
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable.COLUMN_ID + " ASC";
        ArrayList<Form> allForms = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                form.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                form.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                form.setSysdate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                form.setA01(c.getString(c.getColumnIndex(FormsTable.COLUMN_A01)));
                form.setA02(c.getString(c.getColumnIndex(FormsTable.COLUMN_A02)));
                form.setRefno(c.getString(c.getColumnIndex(FormsTable.COLUMN_REFNO)));
                form.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                form.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allForms.add(form);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_ISTATUS, form.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS96x, form.getIstatus96x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, form.getEndingdatetime());

        // Which row to update, based on the ID
        String selection = FormsTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(form.get_ID())};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Get BLRandom data
    public BLRandom getHHFromBLRandom(String subAreaCode, String hh) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        String[] columns = {
                BLRandomTable.COLUMN_ID,
                BLRandomTable.COLUMN_LUID,
                BLRandomTable.COLUMN_STRUCTURE_NO,
                BLRandomTable.COLUMN_FAMILY_EXT_CODE,
                BLRandomTable.COLUMN_HH,
                BLRandomTable.COLUMN_P_CODE,
                BLRandomTable.COLUMN_EB_CODE,
                BLRandomTable.COLUMN_RANDOMDT,
                BLRandomTable.COLUMN_HH_SELECTED_STRUCT,
                BLRandomTable.COLUMN_CONTACT,
                BLRandomTable.COLUMN_HH_HEAD,
                BLRandomTable.COLUMN_SNO_HH
        };

        String whereClause = BLRandomTable.COLUMN_P_CODE + "=? AND " + BLRandomTable.COLUMN_HH + "=?";
        String[] whereArgs = new String[]{subAreaCode, hh};
        String groupBy = null;
        String having = null;

        String orderBy =
                BLRandomTable.COLUMN_ID + " ASC";

        BLRandom allBL = null;
        try {
            c = db.query(
                    BLRandomTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allBL = new BLRandom().hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allBL;
    }

    //Get Form already exist
    public Form getFilledForm(String district, String refno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_A01,
                FormsTable.COLUMN_A02,
                FormsTable.COLUMN_A03,
                FormsTable.COLUMN_A04,
                FormsTable.COLUMN_A05,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS96x,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SH3,
                FormsTable.COLUMN_SH4,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION
        };

//        String whereClause = "(" + FormsTable.COLUMN_ISTATUS + " is null OR " + FormsTable.COLUMN_ISTATUS + "='') AND " + FormsTable.COLUMN_CLUSTERCODE + "=? AND " + FormsTable.COLUMN_HHNO + "=?";
        String whereClause = FormsTable.COLUMN_A05 + "=? AND " + FormsTable.COLUMN_REFNO + "=?";
        String[] whereArgs = {district, refno};
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable._ID + " ASC";
        Form allForms = null;
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allForms = new Form().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    //Get Personal already exist
    public Personal getFilledPersonal(String district, String refno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalContract.PersonalTable._ID,
                PersonalContract.PersonalTable.COLUMN_UID,
                PersonalContract.PersonalTable.COLUMN_SYSDATE,
                PersonalContract.PersonalTable.COLUMN_A01,
                PersonalContract.PersonalTable.COLUMN_A02,
                PersonalContract.PersonalTable.COLUMN_A03,
                PersonalContract.PersonalTable.COLUMN_A04,
                PersonalContract.PersonalTable.COLUMN_A05,
                PersonalContract.PersonalTable.COLUMN_REFNO,
                PersonalContract.PersonalTable.COLUMN_ISTATUS,
                PersonalContract.PersonalTable.COLUMN_ISTATUS96x,
                PersonalContract.PersonalTable.COLUMN_ENDINGDATETIME,
                PersonalContract.PersonalTable.COLUMN_SA,
                PersonalContract.PersonalTable.COLUMN_SB,
                PersonalContract.PersonalTable.COLUMN_SC,
                PersonalContract.PersonalTable.COLUMN_SI,
                PersonalContract.PersonalTable.COLUMN_GPSLAT,
                PersonalContract.PersonalTable.COLUMN_GPSLNG,
                PersonalContract.PersonalTable.COLUMN_GPSDATE,
                PersonalContract.PersonalTable.COLUMN_GPSACC,
                PersonalContract.PersonalTable.COLUMN_DEVICETAGID,
                PersonalContract.PersonalTable.COLUMN_DEVICEID,
                PersonalContract.PersonalTable.COLUMN_APPVERSION
        };

//        String whereClause = "(" + FormsTable.COLUMN_ISTATUS + " is null OR " + FormsTable.COLUMN_ISTATUS + "='') AND " + FormsTable.COLUMN_CLUSTERCODE + "=? AND " + FormsTable.COLUMN_HHNO + "=?";
        String whereClause = PersonalContract.PersonalTable.COLUMN_A05 + "=? AND " + PersonalContract.PersonalTable.COLUMN_REFNO + "=?";
        String[] whereArgs = {district, refno};
        String groupBy = null;
        String having = null;
        String orderBy = PersonalContract.PersonalTable._ID + " ASC";
        Personal allPersonal = null;
        try {
            c = db.query(
                    PersonalContract.PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allPersonal = new Personal().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }

    //Generic update FormColumn
    public int updatesFormColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormsTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(form.get_ID())};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    // ANDROID DATABASE MANAGER
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

    //Generic Un-Synced Forms
    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }
}