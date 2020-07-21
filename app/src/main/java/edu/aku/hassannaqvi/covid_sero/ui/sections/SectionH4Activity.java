package edu.aku.hassannaqvi.covid_sero.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
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
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionH4Binding;
import edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_sero.CONSTANTS.ROUTE_SUBINFO;
import static edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt.contextBackActivity;


public class SectionH4Activity extends AppCompatActivity {

    ActivitySectionH4Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h4);
        bi.setCallback(this);

        bi.nh401.setOnCheckedChangeListener((group, checkedId) -> {
            if (!(checkedId == R.id.nh401a)) {
                Clear.clearAllFields(bi.fldGrpnh402, false);
            } else {
                formValidation();
                Clear.clearAllFields(bi.fldGrpnh402, true);
            }
        });

        CheckBox.OnCheckedChangeListener check = (buttonView, isChecked) -> {
            if (bi.nh403a.isChecked() || bi.nh403b.isChecked() || bi.nh403c.isChecked()) {
                Clear.clearAllFields(bi.fldGrpCVnh404, false);
                Clear.clearAllFields(bi.fldGrpnh405check, false);
            } else {
                Clear.clearAllFields(bi.fldGrpCVnh404, true);
                Clear.clearAllFields(bi.fldGrpnh405check, true);
            }
        };

        bi.nh403a.setOnCheckedChangeListener(check);
        bi.nh403b.setOnCheckedChangeListener(check);
        bi.nh403c.setOnCheckedChangeListener(check);

        bi.nh403e.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                bi.nh403a.setEnabled(false);
                bi.nh403b.setEnabled(false);
                bi.nh403c.setEnabled(false);
                bi.nh403d.setEnabled(false);

                bi.nh403a.setChecked(false);
                bi.nh403b.setChecked(false);
                bi.nh403c.setChecked(false);
                bi.nh403d.setChecked(false);

            } else {


                bi.nh403a.setEnabled(true);
                bi.nh403b.setEnabled(true);
                bi.nh403c.setEnabled(true);
                bi.nh403d.setEnabled(true);
            }
        });

        bi.nh404.setOnCheckedChangeListener((group, checkedId) -> {
            formValidation();
            if (checkedId == R.id.nh404b) {
                Clear.clearAllFields(bi.fldGrpnh405check, false);

            } else {
                Clear.clearAllFields(bi.fldGrpnh405check, true);
            }
        });

        bi.nh405e.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bi.nh405a.setEnabled(false);
                bi.nh405b.setEnabled(false);
                bi.nh405c.setEnabled(false);
                bi.nh405d.setEnabled(false);

                bi.nh405a.setChecked(false);
                bi.nh405b.setChecked(false);
                bi.nh405c.setChecked(false);
                bi.nh405d.setChecked(false);

            } else {
                bi.nh405a.setEnabled(true);
                bi.nh405b.setEnabled(true);
                bi.nh405c.setEnabled(true);
                bi.nh405d.setEnabled(true);
            }
        });

    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                saveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (updateDB()) {
                finish();
                startActivity(new Intent(this, SectionSubInfoActivity.class).putExtra(ROUTE_SUBINFO, 1));
            } else {
                Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpH401);
    }

    private boolean updateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SH4, MainApp.form.getsH4());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this, SectionSubInfoActivity.class, ROUTE_SUBINFO, 99);
    }

    private void saveDraft() throws JSONException {

        JSONObject sH4 = new JSONObject();

        sH4.put("nh401", bi.nh401a.isChecked() ? "1"
                : bi.nh401b.isChecked() ? "2"
                : bi.nh401c.isChecked() ? "3"
                : bi.nh401d.isChecked() ? "4"
                : "0");

        sH4.put("nh402", bi.nh402a.isChecked() ? "1"
                : bi.nh402b.isChecked() ? "2"
                : "0");

        sH4.put("nh403a", bi.nh403a.isChecked() ? "1" : "0");
        sH4.put("nh403b", bi.nh403b.isChecked() ? "2" : "0");
        sH4.put("nh403c", bi.nh403c.isChecked() ? "3" : "0");
        sH4.put("nh403d", bi.nh403d.isChecked() ? "4" : "0");
        sH4.put("nh403e", bi.nh403e.isChecked() ? "5" : "0");

        sH4.put("nh404", bi.nh404a.isChecked() ? "1"
                : bi.nh404b.isChecked() ? "2"
                : "0");

        sH4.put("nh405a", bi.nh405a.isChecked() ? "1" : "0");
        sH4.put("nh405b", bi.nh405b.isChecked() ? "2" : "0");
        sH4.put("nh405c", bi.nh405c.isChecked() ? "3" : "0");
        sH4.put("nh405d", bi.nh405d.isChecked() ? "4" : "0");
        sH4.put("nh405e", bi.nh405e.isChecked() ? "5" : "0");


        sH4.put("nh40601", bi.nh40601a.isChecked() ? "1"
                : bi.nh40601b.isChecked() ? "2"
                : "0");


        sH4.put("nh40602", bi.nh40602a.isChecked() ? "1"
                : bi.nh40602b.isChecked() ? "2"
                : "0");

        sH4.put("nh40603", bi.nh40603a.isChecked() ? "1"
                : bi.nh40603b.isChecked() ? "2"
                : "0");

        sH4.put("nh40604", bi.nh40604a.isChecked() ? "1"
                : bi.nh40604b.isChecked() ? "2"
                : "0");

        sH4.put("nh40605", bi.nh40605a.isChecked() ? "1"
                : bi.nh40605b.isChecked() ? "2"
                : "0");

        sH4.put("nh40696", bi.nh40696a.isChecked() ? "1"
                : bi.nh40696b.isChecked() ? "2"
                : "0");

        sH4.put("nh40696x", bi.nh40696x.getText().toString());

        MainApp.form.setsH4((String.valueOf(sH4)));
    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }
}
