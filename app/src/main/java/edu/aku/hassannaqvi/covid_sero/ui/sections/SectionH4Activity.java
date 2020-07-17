package edu.aku.hassannaqvi.covid_sero.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import edu.aku.hassannaqvi.covid_sero.R;
import edu.aku.hassannaqvi.covid_sero.contracts.FormsContract;
import edu.aku.hassannaqvi.covid_sero.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_sero.core.MainApp;
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionH4Binding;
import edu.aku.hassannaqvi.covid_sero.ui.other.EndingActivity;

import static edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt.contextBackActivity;


public class SectionH4Activity extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    //    static int deceasedCounter = 0;
    private final long DELAY = 1000;
    ActivitySectionH4Binding bi;
    public CheckBox.OnCheckedChangeListener check = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (bi.nh403a.isChecked() || bi.nh403b.isChecked() || bi.nh403c.isChecked()) {
                Clear.clearAllFields(bi.fldGrnh404, false);
                Clear.clearAllFields(bi.fldGrpnh405, false);
            } else {
                Clear.clearAllFields(bi.fldGrnh404, true);
                Clear.clearAllFields(bi.fldGrpnh405, true);
            }
        }
    };
    DatabaseHelper db;
    int recipientCounter = 0;
    int prevRecipientCounter = 0;
    Boolean backPressed = false;
    int prevDeceasedCounter = 0;
    String nh801, nh802;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h4);
        db = new DatabaseHelper(this);

//        Assigning data to UI binding
        bi.setCallback(this);

        this.setTitle(getResources().getString(R.string.na5heading));

        bi.nh401.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //formValidation();
                if (!(checkedId == R.id.nh401a)) {

                    Clear.clearAllFields(bi.fldGrpnh402, false);

                } else {
                    formValidation();
                    Clear.clearAllFields(bi.fldGrpnh402, true);
                }
            }
        });

        bi.nh403a.setOnCheckedChangeListener(check);
        bi.nh403b.setOnCheckedChangeListener(check);
        bi.nh403c.setOnCheckedChangeListener(check);

        bi.nh403e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
            }
        });

        bi.nh404.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nh404b) {
                    Clear.clearAllFields(bi.fldGrpnh405, false);

                } else {
                    Clear.clearAllFields(bi.fldGrpnh405, true);
                }
            }
        });
        bi.nh405e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
            }
        });


//        Listeners
        bi.nh402.setOnCheckedChangeListener(this);
        bi.nh40601.setOnCheckedChangeListener(this);
        bi.nh40602.setOnCheckedChangeListener(this);
        bi.nh40603.setOnCheckedChangeListener(this);
        bi.nh40604.setOnCheckedChangeListener(this);
        bi.nh40605.setOnCheckedChangeListener(this);
        bi.nh40696.setOnCheckedChangeListener(this);

    }


    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
            } else {
                Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }

    public boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpH401);
    }

    public void BtnEnd() {
        MainApp.endActivity(this, this);
    }


    private void SaveDraft() throws JSONException {

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


        // Section A6

        sH4.put("nh501", bi.nh501a.isChecked() ? "1"
                : bi.nh501b.isChecked() ? "2"
                : bi.nh501c.isChecked() ? "3"
                : bi.nh501d.isChecked() ? "4"
                : bi.nh50196.isChecked() ? "96"
                : "0");

        sH4.put("nh50196x", bi.nh50196x.getText().toString());

        sH4.put("nh502", bi.nh502a.isChecked() ? "1"
                : bi.nh502b.isChecked() ? "2"
                : bi.nh502c.isChecked() ? "3"
                : "0");

        sH4.put("nh503", bi.nh503a.isChecked() ? "1"
                : bi.nh503b.isChecked() ? "2"
                : bi.nh503c.isChecked() ? "3"
                : bi.nh503d.isChecked() ? "4"
                : "0");

        sH4.put("nh504", bi.nh504a.isChecked() ? "1"
                : bi.nh504b.isChecked() ? "2" : "0");

        sH4.put("nh505", bi.nh505a.isChecked() ? "1"
                : bi.nh505b.isChecked() ? "2" : "0");

        //Section A7

        sH4.put("nh601", bi.nh601a.isChecked() ? "1"
                : bi.nh601b.isChecked() ? "2"
                : bi.nh60198.isChecked() ? "98"
                : bi.nh60199.isChecked() ? "99"
                : "0");

        sH4.put("nh602", bi.nh602a.isChecked() ? "1"
                : bi.nh602b.isChecked() ? "2"
                : bi.nh60298.isChecked() ? "98"
                : bi.nh60299.isChecked() ? "99"
                : "0");

        sH4.put("nh603", bi.nh603a.isChecked() ? "1"
                : bi.nh603b.isChecked() ? "2"
                : bi.nh60398.isChecked() ? "98"
                : bi.nh60399.isChecked() ? "99"
                : "0");

        sH4.put("nh604", bi.nh604a.isChecked() ? "1"
                : bi.nh604b.isChecked() ? "2"
                : bi.nh60498.isChecked() ? "98"
                : bi.nh60499.isChecked() ? "99"
                : "0");

        sH4.put("nh605", bi.nh605a.isChecked() ? "1"
                : bi.nh605b.isChecked() ? "2"
                : bi.nh60598.isChecked() ? "98"
                : bi.nh60599.isChecked() ? "99"
                : "0");

        sH4.put("nh606", bi.nh606a.isChecked() ? "1"
                : bi.nh606b.isChecked() ? "2"
                : bi.nh60698.isChecked() ? "98"
                : bi.nh60699.isChecked() ? "99"
                : "0");

        sH4.put("nh607", bi.nh607a.isChecked() ? "1"
                : bi.nh607b.isChecked() ? "2"
                : bi.nh60798.isChecked() ? "98"
                : bi.nh60799.isChecked() ? "99"
                : "0");

        sH4.put("nh608", bi.nh608a.isChecked() ? "1"
                : bi.nh608b.isChecked() ? "2"
                : bi.nh60898.isChecked() ? "98"
                : bi.nh60899.isChecked() ? "99"
                : "0");

        sH4.put("nh609", bi.nh609a.isChecked() ? "1"
                : bi.nh609b.isChecked() ? "2"
                : bi.nh60998.isChecked() ? "98"
                : bi.nh60999.isChecked() ? "99"
                : "0");

        // Section A8

        sH4.put("nh701", bi.nh701a.isChecked() ? "1"
                : bi.nh701b.isChecked() ? "2"
                : "0");

        sH4.put("nh702", bi.nh702.getText().toString());

        if (bi.nh701a.isChecked()) {
            recipientCounter = Integer.valueOf(bi.nh702.getText().toString());
        }


        MainApp.form.setsH4(String.valueOf(sH4));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SH4, MainApp.form.getsH4());

        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                formValidation();
                            }
                        });

                    }
                },
                DELAY
        );
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        formValidation();
    }


}
