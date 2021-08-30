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
import edu.aku.hassannaqvi.covid_sero.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_sero.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_sero.core.MainApp;
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionPidBinding;
import edu.aku.hassannaqvi.covid_sero.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_sero.utils.app_utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_sero.utils.app_utils.AppUtilsKt.contextBackActivity;

public class SectionPIDActivity extends AppCompatActivity {

    ActivitySectionPidBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_pid);
        bi.setCallback(this);
        setupSkips();
    }


    private void setupSkips() {
        bi.pd06d97.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.llpd06d2, !b));
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
            startActivity(new Intent(this, SectionPIB02Activity.class));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesPersonalColumn(PersonalContract.PersonalTable.COLUMN_SB, MainApp.personal.getsB());
        if (updcount > 0) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("pd01", bi.pd01a.isChecked() ? "1"
                : bi.pd01b.isChecked() ? "2"
                : "-1");

        json.put("pd02a", bi.pd02a.isChecked() ? "1" : "-1");
        json.put("pd02b", bi.pd02b.isChecked() ? "2" : "-1");
        json.put("pd02c", bi.pd02c.isChecked() ? "3" : "-1");
        json.put("pd02d", bi.pd02d.isChecked() ? "4" : "-1");

        json.put("pd03a", bi.pd03aa.isChecked() ? "1"
                : bi.pd03ab.isChecked() ? "2"
                : "-1");

        json.put("pd03b", bi.pd03ba.isChecked() ? "1"
                : bi.pd03bb.isChecked() ? "2"
                : "-1");

        json.put("pd03c", bi.pd03ca.isChecked() ? "1"
                : bi.pd03cb.isChecked() ? "2"
                : "-1");

        json.put("pd03d", bi.pd03da.isChecked() ? "1"
                : bi.pd03db.isChecked() ? "2"
                : "-1");

        json.put("pd03e", bi.pd03ea.isChecked() ? "1"
                : bi.pd03eb.isChecked() ? "2"
                : "-1");

        json.put("pd03f", bi.pd03fa.isChecked() ? "1"
                : bi.pd03fb.isChecked() ? "2"
                : "-1");

        json.put("pd03g", bi.pd03ga.isChecked() ? "1"
                : bi.pd03gb.isChecked() ? "2"
                : "-1");

        json.put("pd03h", bi.pd03ha.isChecked() ? "1"
                : bi.pd03hb.isChecked() ? "2"
                : "-1");

        json.put("pd03i", bi.pd03ia.isChecked() ? "1"
                : bi.pd03ib.isChecked() ? "2"
                : "-1");

        json.put("pd03j", bi.pd03ja.isChecked() ? "1"
                : bi.pd03jb.isChecked() ? "2"
                : "-1");

        json.put("pd03k", bi.pd03ka.isChecked() ? "1"
                : bi.pd03kb.isChecked() ? "2"
                : "-1");

        json.put("pd03l", bi.pd03la.isChecked() ? "1"
                : bi.pd03lb.isChecked() ? "2"
                : "-1");

        json.put("pd03m", bi.pd03ma.isChecked() ? "1"
                : bi.pd03mb.isChecked() ? "2"
                : "-1");

        json.put("pd03n", bi.pd03na.isChecked() ? "1"
                : bi.pd03nb.isChecked() ? "2"
                : "-1");

        json.put("pd03o", bi.pd03oa.isChecked() ? "1"
                : bi.pd03ob.isChecked() ? "2"
                : "-1");

        json.put("pd04", bi.pd04a.isChecked() ? "1"
                : bi.pd04b.isChecked() ? "2"
                : "-1");

        json.put("pd05", bi.pd05a.isChecked() ? "1"
                : bi.pd05b.isChecked() ? "2"
                : bi.pd05c.isChecked() ? "3"
                : bi.pd05d.isChecked() ? "4"
                : bi.pd05e.isChecked() ? "5"
                : bi.pd05f.isChecked() ? "6"
                : bi.pd05g.isChecked() ? "7"
                : bi.pd0596.isChecked() ? "96"
                : "-1");

        json.put("pd0596x", bi.pd0596x.getText().toString());

        json.put("pd06d1d", bi.pd06d1d.getText().toString());
        json.put("pd06d1m", bi.pd06d1m.getText().toString());
        json.put("pd06d1y", bi.pd06d1y.getText().toString());

        json.put("pd06d2d", bi.pd06d2d.getText().toString());
        json.put("pd06d2m", bi.pd06d2m.getText().toString());
        json.put("pd06d2y", bi.pd06d2y.getText().toString());
        json.put("pd06d97", bi.pd06d97.isChecked() ? "97" : "-1");

        json.put("pd07", bi.pd07a.isChecked() ? "1"
                : bi.pd07b.isChecked() ? "2"
                : "-1");

        json.put("pd08", bi.pd08a.isChecked() ? "1"
                : bi.pd08b.isChecked() ? "2"
                : "-1");

        json.put("pd09a", bi.pd09a.isChecked() ? "1" : "-1");
        json.put("pd09b", bi.pd09b.isChecked() ? "2" : "-1");
        json.put("pd09c", bi.pd09c.isChecked() ? "3" : "-1");
        json.put("pd09d", bi.pd09d.isChecked() ? "4" : "-1");
        json.put("pd09e", bi.pd09e.isChecked() ? "5" : "-1");
        json.put("pd09f", bi.pd09f.isChecked() ? "6" : "-1");
        json.put("pd09g", bi.pd09g.isChecked() ? "7" : "-1");
        json.put("pd09h", bi.pd09h.isChecked() ? "8" : "-1");
        json.put("pd09i", bi.pd09i.isChecked() ? "9" : "-1");
        json.put("pd09j", bi.pd09j.isChecked() ? "10" : "-1");
        json.put("pd09k", bi.pd09k.isChecked() ? "11" : "-1");
        json.put("pd09l", bi.pd09l.isChecked() ? "12" : "-1");
        json.put("pd09m", bi.pd09m.isChecked() ? "13" : "-1");
        json.put("pd09o", bi.pd09o.isChecked() ? "14" : "-1");

        json.put("pd10a", bi.pd10a.isChecked() ? "1" : "-1");
        json.put("pd10b", bi.pd10b.isChecked() ? "2" : "-1");
        json.put("pd10c", bi.pd10c.isChecked() ? "3" : "-1");
        json.put("pd10d", bi.pd10d.isChecked() ? "4" : "-1");
        json.put("pd10e", bi.pd10e.isChecked() ? "5" : "-1");
        json.put("pd10f", bi.pd10f.isChecked() ? "6" : "-1");
        json.put("pd10g", bi.pd10g.isChecked() ? "7" : "-1");
        json.put("pd10h", bi.pd10h.isChecked() ? "8" : "-1");
        json.put("pd10i", bi.pd10i.isChecked() ? "9" : "-1");
        json.put("pd10j", bi.pd10j.isChecked() ? "10" : "-1");
        json.put("pd10k", bi.pd10k.isChecked() ? "11" : "-1");
        json.put("pd10l", bi.pd10l.isChecked() ? "12" : "-1");
        json.put("pd10m", bi.pd10m.isChecked() ? "13" : "-1");
        json.put("pd10n", bi.pd10n.isChecked() ? "14" : "-1");

        json.put("pd11a", bi.pd11a.isChecked() ? "1" : "-1");
        json.put("pd11b", bi.pd11b.isChecked() ? "2" : "-1");
        json.put("pd11c", bi.pd11c.isChecked() ? "3" : "-1");
        json.put("pd11d", bi.pd11d.isChecked() ? "4" : "-1");
        json.put("pd11e", bi.pd11e.isChecked() ? "5" : "-1");
        json.put("pd11f", bi.pd11f.isChecked() ? "6" : "-1");
        json.put("pd1196", bi.pd1196.isChecked() ? "96" : "-1");
        json.put("pd1196x", bi.pd1196x.getText().toString());


        //personal.setsB(json.toString());

    }


    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this, PIEndingActivity.class);
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }


    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }
}