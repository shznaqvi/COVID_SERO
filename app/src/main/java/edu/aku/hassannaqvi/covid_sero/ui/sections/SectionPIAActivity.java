package edu.aku.hassannaqvi.covid_sero.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import edu.aku.hassannaqvi.covid_sero.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_sero.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_sero.core.MainApp;
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionPiaBinding;
import edu.aku.hassannaqvi.covid_sero.models.HHModel;
import edu.aku.hassannaqvi.covid_sero.models.Personal;
import edu.aku.hassannaqvi.covid_sero.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_sero.utils.app_utils.AppUtilsKt;
import edu.aku.hassannaqvi.covid_sero.utils.app_utils.EndSectionActivity;
import edu.aku.hassannaqvi.covid_sero.utils.date_utils.DateRepository;
import edu.aku.hassannaqvi.covid_sero.utils.date_utils.model.AgeModel;

import static edu.aku.hassannaqvi.covid_sero.core.MainApp.form;
import static edu.aku.hassannaqvi.covid_sero.core.MainApp.personal;

public class SectionPIAActivity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionPiaBinding bi;
    boolean dtFlag = false;
    LocalDate calculatedDOB;

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
        btnPressed(SectionPIB01Activity.class);
    }

    private void btnPressed(Class<?> routeClass) {
        if (!formValidation()) return;
        try {
            saveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (updateDB()) {
            finish();
            startActivity(new Intent(this, routeClass));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean updateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        long updcount = db.addPersonal(personal);
        personal.set_ID(String.valueOf(updcount));
        if (updcount > 0) {
            personal.set_UID(personal.getDeviceID() + personal.get_ID());
            db.updatesPersonalColumn(PersonalContract.PersonalTable.COLUMN_UID, personal.get_UID());
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void saveDraft() throws JSONException {

        personal = new Personal();
        personal.setSysdate(new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime()));
        personal.setA03(MainApp.userName);
        personal.setDeviceID(MainApp.appInfo.getDeviceID());
        personal.setDevicetagID(MainApp.appInfo.getTagName());
        personal.setAppversion(MainApp.appInfo.getAppVersion());
        personal.setHh12(form.getHh12());
        personal.setHh13(form.getHh13());

        JSONObject json = new JSONObject();

        personal.setMemberName(bi.pa01.getText().toString());
        json.put("pa01", bi.pa01.getText().toString());

        String gender = bi.pa021.isChecked() ? "1" : bi.pa022.isChecked() ? "2" : "-1";
        personal.setGender(gender);
        json.put("pa02", gender);

        json.put("pa03_dd", bi.pa03dd.getText().toString());
        json.put("pa03_mm", bi.pa03mm.getText().toString());
        json.put("pa03_yy", bi.pa03yy.getText().toString());

        personal.setAgey(bi.pa04y.getText().toString());
        json.put("pa04y", bi.pa04y.getText().toString());
        personal.setAgem(bi.pa04m.getText().toString());
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

        personal.setsA(json.toString());

        personal.setHhModel(new HHModel(form.getHh12(), form.getHh13(), Integer.parseInt(bi.pa04y.getText().toString()), bi.pa022.isChecked()));

    }

    public void BtnEnd() {
        AppUtilsKt.contextEndActivity(this);
    }

    private boolean formValidation() {
        if (!Validator.emptyCheckingContainer(this, bi.fldGrpSectionA)) return false;
        if (!dtFlag) {
            return Validator.emptyCustomTextBox(this, bi.pa03yy, "Invalid date!");
        }
        if (Integer.parseInt(bi.pa04m.getText().toString()) == 0 && Integer.parseInt(bi.pa04y.getText().toString()) == 0)
            return Validator.emptyCustomTextBox(this, bi.pa04y, "Both Month & Year don't be zero!!", false);
        return true;
    }

    public void pa04yOnTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(bi.pa04y.getText())) return;
        if (Integer.parseInt(bi.pa04y.getText().toString()) < 5)
            bi.fldGrpSectionA01.setVisibility(View.GONE);
        else bi.fldGrpSectionA01.setVisibility(View.VISIBLE);
    }

    public void pa03yyOnTextChanged(CharSequence s, int start, int before, int count) {
        bi.pa04m.setEnabled(false);
        bi.pa04m.setText(null);
        bi.pa04y.setEnabled(false);
        bi.pa04y.setText(null);
        calculatedDOB = null;
        dtFlag = false;
        if (TextUtils.isEmpty(bi.pa03dd.getText()) || TextUtils.isEmpty(bi.pa03mm.getText()) || TextUtils.isEmpty(bi.pa03yy.getText()))
            return;
        if (!bi.pa03dd.isRangeTextValidate() || !bi.pa03mm.isRangeTextValidate() || !bi.pa03yy.isRangeTextValidate())
            return;
        if (bi.pa03dd.getText().toString().equals("98") && bi.pa03mm.getText().toString().equals("98") && bi.pa03yy.getText().toString().equals("9998")) {
            bi.pa04m.setEnabled(true);
            bi.pa04y.setEnabled(true);
            dtFlag = true;
            return;
        }
        int day = bi.pa03dd.getText().toString().equals("98") ? 15 : Integer.parseInt(bi.pa03dd.getText().toString());
        int month = Integer.parseInt(bi.pa03mm.getText().toString());
        int year = Integer.parseInt(bi.pa03yy.getText().toString());

        AgeModel age;
        if (form.getLocalDate() != null)
            age = DateRepository.Companion.getCalculatedAge(form.getLocalDate(), year, month, day);
        else
            age = DateRepository.Companion.getCalculatedAge(year, month, day);
        if (age == null) {
            bi.pa03yy.setError("Invalid date!!");
            dtFlag = false;
            return;
        }
        dtFlag = true;
        bi.pa04m.setText(String.valueOf(age.getMonth()));
        bi.pa04y.setText(String.valueOf(age.getYear()));

        //Setting Date
        try {
            Instant instant = Instant.parse(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(
                    bi.pa03dd.getText().toString() + "-" + bi.pa03mm.getText().toString() + "-" + bi.pa03yy.getText().toString()
            )) + "T06:24:01Z");
            calculatedDOB = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endSecActivity(boolean flag) {
        btnPressed(PIEndingActivity.class);
    }
}