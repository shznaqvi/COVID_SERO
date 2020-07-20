package edu.aku.hassannaqvi.covid_sero.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_sero.R;
import edu.aku.hassannaqvi.covid_sero.contracts.FormsContract;
import edu.aku.hassannaqvi.covid_sero.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_sero.core.MainApp;
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionPib02Binding;
import edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt.contextBackActivity;

public class SectionPIB02Activity extends AppCompatActivity {

    ActivitySectionPib02Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_pib02);
        bi.setCallback(this);
        setupSkips();
    }


    private void setupSkips() {

        bi.pb13.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb132.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpb14);
            }
        }));

        bi.pb17.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb17b.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpb18);
            }
        }));

    }


    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, SectionPICActivity.class));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SB, MainApp.form.getsB());
        if (updcount > 0) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("pb1101", bi.pb1101a.isChecked() ? "1"
                : bi.pb1101b.isChecked() ? "2"
                : "-1");

        json.put("pb1102", bi.pb1102a.isChecked() ? "1"
                : bi.pb1102b.isChecked() ? "2"
                : "-1");

        json.put("pb1103", bi.pb1103a.isChecked() ? "1"
                : bi.pb1103b.isChecked() ? "2"
                : "-1");

        json.put("pb1104", bi.pb1104a.isChecked() ? "1"
                : bi.pb1104b.isChecked() ? "2"
                : "-1");

        json.put("pb12", bi.pb12.getText().toString());

        json.put("pb13", bi.pb131.isChecked() ? "1"
                : bi.pb132.isChecked() ? "2"
                : "-1");

        json.put("pb14dist", bi.pb14dist.getText().toString());
        json.put("pb14city", bi.pb14city.getText().toString());
        json.put("pb14country", bi.pb14country.getText().toString());

        json.put("pb15", bi.pb15a.isChecked() ? "1"
                : bi.pb15b.isChecked() ? "2"
                : "-1");

        json.put("pb16", bi.pb16.getText().toString());

        json.put("pb17", bi.pb17a.isChecked() ? "1"
                : bi.pb17b.isChecked() ? "2"
                : "-1");

        json.put("pb18n", bi.pb18n.getText().toString());
        json.put("pb1801n", bi.pb1801n.getText().toString());
        json.put("pb1801ad", bi.pb1801ad.getText().toString());

        json.put("pb1802n", bi.pb1802n.getText().toString());
        json.put("pb1802ad", bi.pb1802ad.getText().toString());

        json.put("pb1803n", bi.pb1803n.getText().toString());
        json.put("pb1803ad", bi.pb1803ad.getText().toString());

        json.put("pb1804n", bi.pb1804n.getText().toString());
        json.put("pb1804ad", bi.pb1804ad.getText().toString());

        json.put("pb1805n", bi.pb1805n.getText().toString());
        json.put("pb1805ad", bi.pb1805ad.getText().toString());

        json.put("pb1806n", bi.pb1806n.getText().toString());
        json.put("pb1806ad", bi.pb1806ad.getText().toString());

        json.put("pb1807n", bi.pb1807n.getText().toString());
        json.put("pb1807ad", bi.pb1807ad.getText().toString());

        json.put("pb1808n", bi.pb1808n.getText().toString());
        json.put("pb1808ad", bi.pb1808ad.getText().toString());


        MainApp.form.setsB(json.toString());

    }


    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this);
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionB);

    }


    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }
}