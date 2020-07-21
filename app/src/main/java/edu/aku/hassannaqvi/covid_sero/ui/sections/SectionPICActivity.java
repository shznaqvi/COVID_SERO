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
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionPicBinding;
import edu.aku.hassannaqvi.covid_sero.ui.other.EndingActivity;
import edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt.contextBackActivity;

public class SectionPICActivity extends AppCompatActivity {

    ActivitySectionPicBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_pic);
        bi.setCallback(this);
        setupSkips();
    }

    private void setupSkips() {

        bi.pc01.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pc012.getId()) {
                Clear.clearAllFields(bi.fldGrpSectionC01);
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
            startActivity(new Intent(this, EndingActivity.class));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SC, MainApp.form.getsC());
        if (updcount > 0) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("pc01", bi.pc011.isChecked() ? "1"
                : bi.pc012.isChecked() ? "2"
                : "-1");
        json.put("pc02", bi.pc02.getText().toString());
        json.put("pc02a", bi.pc02a.getText().toString());
        json.put("pc03", bi.pc03.getText().toString());
        json.put("pc04", bi.pc04.getText().toString());
        json.put("pc04a", bi.pc04a.getText().toString());
        json.put("pc05", bi.pc05.getText().toString());
        json.put("pc06", bi.pc06.getText().toString());
        MainApp.form.setsC(json.toString());
    }

    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this);
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionC);

    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }
}