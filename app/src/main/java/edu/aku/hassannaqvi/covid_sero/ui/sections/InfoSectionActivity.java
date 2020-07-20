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
import edu.aku.hassannaqvi.covid_sero.databinding.ActivityInfoSectionBinding;
import edu.aku.hassannaqvi.covid_sero.ui.other.EndingActivity;
import edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt.contextBackActivity;

public class InfoSectionActivity extends AppCompatActivity {

  ActivityInfoSectionBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_info_section);
        bi.setCallback(this);
        setupSkips();
    }

    private void setupSkips() {

    }

    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
          startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this);
    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SINFO, MainApp.form.getsInfo());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("hh03", bi.hh03.getText().toString());

        json.put("hh05", bi.hh05.getText().toString());

        json.put("hh07", bi.hh0701.isChecked() ? "1"
                :  "-1");

        json.put("hh08", bi.hh0801.isChecked() ? "1"
                : bi.hh0802.isChecked() ? "2"
                : bi.hh0803.isChecked() ? "3"
                : bi.hh0804.isChecked() ? "4"
                : bi.hh0805.isChecked() ? "5"
                : bi.hh0806.isChecked() ? "6"
                : bi.hh0807.isChecked() ? "7"
                :  "-1");

        json.put("hh09", bi.hh09.getText().toString());

        json.put("hh10", bi.hh10.getText().toString());

        json.put("hh12", bi.hh12.getText().toString());

        json.put("hh13", bi.hh13.getText().toString());

        json.put("hh11", bi.hh11.getText().toString());

        json.put("hh14", bi.hh1401.isChecked() ? "1"
                : bi.hh1402.isChecked() ? "2"
                : bi.hh1403.isChecked() ? "3"
                : bi.hh1404.isChecked() ? "4"
                : bi.hh1405.isChecked() ? "5"
                : bi.hh1406.isChecked() ? "6"
                : bi.hh1407.isChecked() ? "7"
                : bi.hh1496.isChecked() ? "96"
                :  "-1");

        json.put("hh1496x", bi.hh1496x.getText().toString());

        MainApp.form.setsInfo(json.toString());
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }
}