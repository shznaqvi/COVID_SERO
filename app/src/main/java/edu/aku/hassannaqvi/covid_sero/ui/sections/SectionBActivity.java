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
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionBBinding;
import edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt.contextBackActivity;

public class SectionBActivity extends AppCompatActivity {

    ActivitySectionBBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b);
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
            startActivity(new Intent(this, SectionCActivity.class));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean UpdateDB() {
        /*DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SH, MainApp.form.getsH());
        if (updcount > 0) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }


    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("pb011", bi.pb011a.isChecked() ? "1"
                : bi.pb011b.isChecked() ? "2"
                : "-1");

        json.put("pb012", bi.pb012a.isChecked() ? "1"
                : bi.pb012b.isChecked() ? "2"
                : "-1");

        json.put("pb013", bi.pb013a.isChecked() ? "1"
                : bi.pb013b.isChecked() ? "2"
                : "-1");

        json.put("pb13c", bi.pb13ca.isChecked() ? "1"
                : bi.pb13cb.isChecked() ? "2"
                : "-1");

        json.put("pb014", bi.pb014a.isChecked() ? "1"
                : bi.pb014b.isChecked() ? "2"
                : "-1");

        json.put("pb015", bi.pb015a.isChecked() ? "1"
                : bi.pb015b.isChecked() ? "2"
                : "-1");

        json.put("pb016", bi.pb016a.isChecked() ? "1"
                : bi.pb016b.isChecked() ? "2"
                : "-1");

        json.put("pb017", bi.pb017a.isChecked() ? "1"
                : bi.pb017b.isChecked() ? "2"
                : "-1");

        json.put("pb018", bi.pb018a.isChecked() ? "1"
                : bi.pb018b.isChecked() ? "2"
                : "-1");

        json.put("pb019", bi.pb019a.isChecked() ? "1"
                : bi.pb019b.isChecked() ? "2"
                : "-1");

        json.put("pb0110", bi.pb0110a.isChecked() ? "1"
                : bi.pb0110b.isChecked() ? "2"
                : "-1");

        json.put("pb0111", bi.pb0111a.isChecked() ? "1"
                : bi.pb0111b.isChecked() ? "2"
                : "-1");

        json.put("pb0112", bi.pb0112a.isChecked() ? "1"
                : bi.pb0112b.isChecked() ? "2"
                : "-1");

        json.put("pb0113", bi.pb0113a.isChecked() ? "1"
                : bi.pb0113b.isChecked() ? "2"
                : "-1");

        json.put("pb0114", bi.pb0114a.isChecked() ? "1"
                : bi.pb0114b.isChecked() ? "2"
                : "-1");

        json.put("pb0115", bi.pb0115a.isChecked() ? "1"
                : bi.pb0115b.isChecked() ? "2"
                : "-1");

        json.put("pb021", bi.pb021a.isChecked() ? "1"
                : bi.pb021b.isChecked() ? "2"
                : "-1");

        json.put("pb022", bi.pb022a.isChecked() ? "1"
                : bi.pb022b.isChecked() ? "2"
                : "-1");

        json.put("pb023", bi.pb023a.isChecked() ? "1"
                : bi.pb023b.isChecked() ? "2"
                : "-1");

        json.put("pb024", bi.pb024a.isChecked() ? "1"
                : bi.pb024b.isChecked() ? "2"
                : "-1");

        json.put("pb025", bi.pb025a.isChecked() ? "1"
                : bi.pb025b.isChecked() ? "2"
                : "-1");

        json.put("pb026", bi.pb026a.isChecked() ? "1"
                : bi.pb026b.isChecked() ? "2"
                : "-1");

        json.put("pb027", bi.pb027a.isChecked() ? "1"
                : bi.pb027b.isChecked() ? "2"
                : "-1");

        json.put("pb028", bi.pb028a.isChecked() ? "1"
                : bi.pb028b.isChecked() ? "2"
                : bi.pb02x.isChecked() ? "96"
                : "-1");

        json.put("pb03", bi.pb0301.isChecked() ? "1"
                : bi.pb0302.isChecked() ? "2"
                : bi.pb0303.isChecked() ? "3"
                : bi.pb0304.isChecked() ? "4"
                : "-1");

        json.put("pb04", bi.pb0401.isChecked() ? "1"
                : bi.pb0402.isChecked() ? "2"
                : "-1");

        json.put("pb05", bi.pb0501.isChecked() ? "1"
                : bi.pb0502.isChecked() ? "2"
                : bi.pb0503.isChecked() ? "3"
                : "-1");

        json.put("pb06", bi.pb0601.isChecked() ? "1"
                : bi.pb0602.isChecked() ? "2"
                : "-1");

        json.put("pb07", bi.pb0701.isChecked() ? "1"
                : bi.pb0702.isChecked() ? "2"
                : "-1");

        json.put("pb08", bi.pb0801.isChecked() ? ""
                : bi.pb0802.isChecked() ? ""
                : "-1");

        json.put("pb09", bi.pb0901.isChecked() ? "1"
                : bi.pb0902.isChecked() ? "2"
                : "-1");

        json.put("pb10", bi.pb10.getText().toString());

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


        //    MainApp.form.setsH(json.toString());

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