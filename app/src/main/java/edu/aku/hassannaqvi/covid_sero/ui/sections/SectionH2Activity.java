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
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionH2Binding;
import edu.aku.hassannaqvi.covid_sero.utils.app_utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_sero.CONSTANTS.ROUTE_SUBINFO;
import static edu.aku.hassannaqvi.covid_sero.utils.app_utils.AppUtilsKt.contextBackActivity;

public class SectionH2Activity extends AppCompatActivity {

    ActivitySectionH2Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h2);
        bi.setCallback(this);
        setupSkips();
    }

    private void setupSkips() {

        bi.hb09.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.hb0902.getId()) {
                Clear.clearAllFields(bi.fldGrpCVhb10);
            }
        }));

        bi.hb11.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.hb1102.getId()) {
                Clear.clearAllFields(bi.fldGrpCVhb12);
            }
        }));

        bi.hb13.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.hb1302.getId()) {
                Clear.clearAllFields(bi.fldGrpCVhb14);
            }
        }));

        bi.hb15.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.hb1502.getId()) {
                Clear.clearAllFields(bi.fldGrpCVhb16);
            }
        }));

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
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionH2);
    }

    private boolean updateDB() {
        /*DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SH4, MainApp.form.getsH4());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this, SectionSubInfoActivity.class, ROUTE_SUBINFO, 99);
    }

    private void saveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("hb01", bi.hb01.getText().toString());

        json.put("hb02", bi.hb02.getText().toString());

        json.put("hb03", bi.hb03.getText().toString());

        json.put("hb04", bi.hb0401.isChecked() ? "1"
                : bi.hb0402.isChecked() ? "2"
                : "-1");

        json.put("hb05", bi.hb05.getText().toString());

        json.put("hb06", bi.hb06.getText().toString());

        json.put("hb07", bi.hb0701.isChecked() ? "1"
                : bi.hb0702.isChecked() ? "2"
                : "-1");

        //    json.put("hb08", bi.hb08.getText().toString());

        json.put("hb08present", bi.hb08present.getText().toString());
        json.put("hb09", bi.hb0901.isChecked() ? "1"
                : bi.hb0902.isChecked() ? "2"
                : "-1");

        json.put("hb10", bi.hb10.getText().toString());

        json.put("hb11", bi.hb1101.isChecked() ? "1"
                : bi.hb1102.isChecked() ? "2"
                : "-1");

        json.put("hb12", bi.hb12.getText().toString());

        json.put("hb13", bi.hb1301.isChecked() ? "1"
                : bi.hb1302.isChecked() ? "2"
                : "-1");

        json.put("hb14", bi.hb14.getText().toString());

        json.put("hb15", bi.hb1501.isChecked() ? "1"
                : bi.hb1502.isChecked() ? "2"
                : "-1");

        json.put("hb16", bi.hb16.getText().toString());


        //    MainApp.form.setsH4((String.valueOf(sH4)));

    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }
}