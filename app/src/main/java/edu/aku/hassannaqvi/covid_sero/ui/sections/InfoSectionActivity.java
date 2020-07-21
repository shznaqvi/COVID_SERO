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
import edu.aku.hassannaqvi.covid_sero.models.HHModel;
import edu.aku.hassannaqvi.covid_sero.ui.other.EndingActivity;
import edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt;
import edu.aku.hassannaqvi.covid_sero.utils.EndSectionActivity;

import static edu.aku.hassannaqvi.covid_sero.core.MainApp.form;

public class InfoSectionActivity extends AppCompatActivity implements EndSectionActivity {

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
        btnSavingWorking(SectionSubInfoActivity.class, true);
    }

    public void BtnEnd() {
        AppUtilsKt.contextEndActivity(this, false);
    }

    private void btnSavingWorking(Class<?> activity, Boolean flag) {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, activity).putExtra("complete", flag));
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean UpdateDB() {

        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        long updcount = db.addForm(form);
        form.set_ID(String.valueOf(updcount));
        if (updcount > 0) {
            form.set_UID(form.getDeviceID() + form.get_ID());
            db.updatesFormColumn(FormsContract.FormsTable.COLUMN_UID, form.get_UID());
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
                : "-1");

        json.put("hh08", bi.hh0801.isChecked() ? "1"
                : bi.hh0802.isChecked() ? "2"
                : bi.hh0803.isChecked() ? "3"
                : bi.hh0804.isChecked() ? "4"
                : bi.hh0805.isChecked() ? "5"
                : bi.hh0806.isChecked() ? "6"
                : bi.hh0807.isChecked() ? "7"
                : "-1");

        json.put("hh09", bi.hh09.getText().toString());

        json.put("hh10", bi.hh10.getText().toString());

        json.put("hh12", bi.hh12.getText().toString());

        json.put("hh13", bi.hh13.getText().toString());

        json.put("hh11", bi.hh11.getText().toString());

        MainApp.form.setsInfo(json.toString());

        form.setHhModel(new HHModel(bi.hh12.getText().toString(), bi.hh13.getText().toString()));
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void endSecActivity(boolean flag) {
        btnSavingWorking(EndingActivity.class, flag);
    }
}