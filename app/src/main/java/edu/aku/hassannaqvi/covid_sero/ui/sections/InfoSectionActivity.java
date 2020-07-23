package edu.aku.hassannaqvi.covid_sero.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.aku.hassannaqvi.covid_sero.R;
import edu.aku.hassannaqvi.covid_sero.contracts.FormsContract;
import edu.aku.hassannaqvi.covid_sero.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_sero.core.MainApp;
import edu.aku.hassannaqvi.covid_sero.databinding.ActivityInfoSectionBinding;
import edu.aku.hassannaqvi.covid_sero.models.Form;
import edu.aku.hassannaqvi.covid_sero.ui.other.EndingActivity;
import edu.aku.hassannaqvi.covid_sero.utils.app_utils.EndSectionActivity;

import static edu.aku.hassannaqvi.covid_sero.core.MainApp.form;

public class InfoSectionActivity extends AppCompatActivity implements EndSectionActivity {

    ActivityInfoSectionBinding bi;
    LocalDate localDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_info_section);
        bi.setCallback(this);
        setupSkips();
    }

    private void setupSkips() {
        bi.hh04.setText(MainApp.user.getFullname().toUpperCase());
    }

    public void BtnContinue() {
        btnSavingWorking(SectionSubInfoActivity.class, true);
    }

    public void BtnEnd() {
        btnSavingWorking(EndingActivity.class, false);
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
        form = new Form();
        form.setSysdate(new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime()));
        form.setHh03(MainApp.userName);
        form.setDeviceID(MainApp.appInfo.getDeviceID());
        form.setDevicetagID(MainApp.appInfo.getTagName());
        form.setAppversion(MainApp.appInfo.getAppVersion());
        form.setHh01(bi.hh01.getText().toString());
        form.setHh03(MainApp.user.getUserName());
        form.setHh12(bi.hh12.getText().toString());
        form.setHh13(bi.hh13.getText().toString());

        JSONObject json = new JSONObject();
        json.put("hh04", bi.hh04.getText().toString());

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

        json.put("hh11", bi.hh11.getText().toString());

        form.setsInfo(json.toString());

        form.setLocalDate(localDate);

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void endSecActivity(boolean flag) {
        btnSavingWorking(EndingActivity.class, flag);
    }


    public void hh01OnTextChanged(CharSequence s, int start, int before, int count) {
        //Setting Date
        try {
            Instant instant = Instant.parse(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(bi.hh01.getText().toString())) + "T06:24:01Z");
            localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}