package edu.aku.hassannaqvi.covid_sero.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_sero.R;
import edu.aku.hassannaqvi.covid_sero.contracts.FormsContract;
import edu.aku.hassannaqvi.covid_sero.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_sero.core.MainApp;
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionPiaBinding;
import edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt;
import edu.aku.hassannaqvi.covid_sero.utils.JSONUtils;

import static edu.aku.hassannaqvi.covid_sero.core.MainApp.form;

public class SectionPIAActivity extends AppCompatActivity {

    ActivitySectionPiaBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_pia);
        bi.setCallback(this);
        setupSkips();
    }


    private void setupSkips() {

        /*bi.h04.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.h0402.getId()) {
                Clear.clearAllFields(bi.fldGrpCVh05);
            }
        }));*/

        /*bi.h06.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.h0602.getId()) {
                Clear.clearAllFields(bi.fldGrpSecH01);
            }
        }));*/

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
            startActivity(new Intent(this, SectionPIBActivity.class));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SINFO, MainApp.form.getsInfo());
        if (updcount > 0) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("pa01", bi.pa01.getText().toString());

        json.put("pa02", bi.pa021.isChecked() ? "1"
                : bi.pa022.isChecked() ? "2"
                : "-1");

        json.put("pa03_dd", bi.pa03Dd.getText().toString());
        json.put("pa03_mm", bi.pa03Mm.getText().toString());
        json.put("pa03_yy", bi.pa03Yy.getText().toString());

        json.put("pa04y", bi.pa04y.getText().toString());
        json.put("pa04m", bi.pa04m.getText().toString());

        json.put("pa06", bi.pa06.getText().toString());

        json.put("pa07", bi.pa071.isChecked() ? "1"
                : bi.pa072.isChecked() ? "2"
                : bi.pa073.isChecked() ? "3"
                : bi.pa074.isChecked() ? "4"
                : bi.pa07x.isChecked() ? "96"
                : "-1");

        json.put("pa08", bi.pa081.isChecked() ? "1"
                : bi.pa082.isChecked() ? "2"
                : "-1");

        //    json.put("pa09a", bi.pa09a.getText().toString());
        json.put("pa09b", bi.pa09b.getText().toString());

        //    json.put("pa10a", bi.pa10a.getText().toString());
        json.put("pa10b", bi.pa10b.getText().toString());

        //    json.put("pa11a", bi.pa11a.getText().toString());
        json.put("pa11b", bi.pa11b.getText().toString());


        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(form.getsInfo()), json);

            form.setsInfo(String.valueOf(json_merge));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this);
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionA);

    }

}