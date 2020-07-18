package edu.aku.hassannaqvi.covid_sero.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
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
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionH3Binding;
import edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt.contextBackActivity;

public class SectionH3Activity extends AppCompatActivity {

    ActivitySectionH3Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h3);
        bi.setCallback(this);

        bi.nh303.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.nh303b || i == R.id.nh303c) {
                formValidation();
                Clear.clearAllFields(bi.fldGrnh304, false);
            } else {
                Clear.clearAllFields(bi.fldGrnh304, true);
            }
        });

        bi.nh30498.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bi.nh30499.setChecked(false);
                bi.nh304.setEnabled(false);
                bi.nh304.setText(null);
                bi.nh304.setError(null);
            } else {
                bi.nh304.setEnabled(true);
            }
        });

        bi.nh30499.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bi.nh30498.setChecked(false);
                bi.nh304.setEnabled(false);
                bi.nh304.setText(null);
                bi.nh304.setError(null);
            } else {
                bi.nh304.setEnabled(true);
            }
        });

        bi.nh305.setOnCheckedChangeListener((group, checkedId) -> {
            formValidation();
            if (checkedId == R.id.nh305b) {
                Clear.clearAllFields(bi.fldGrpnh306, false);
            } else {
                Clear.clearAllFields(bi.fldGrpnh306, true);
                bi.nh30696x.setEnabled(false);
            }
        });

//        nh307
        bi.nh307.setOnCheckedChangeListener((radioGroup, i) -> {
            formValidation();
            if (i == R.id.nh307h || i == R.id.nh307i) {
                Clear.clearAllFields(bi.fldGrpnh308, false);
                //  Clear.clearAllFields(binding.fldGrpnh309,true);
            } else {
                Clear.clearAllFields(bi.fldGrpnh308, true);
            }
        });

//        nh308
        bi.nh308.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nh308b) {
                    Clear.clearAllFields(bi.fldGrpnh309, false);
                } else {
//                    binding.fldGrpnh309.setVisibility(View.VISIBLE);
                    Clear.clearAllFields(bi.fldGrpnh309, true);

                }
            }
        });

        bi.nh315.setOnCheckedChangeListener((group, checkedId) -> {
            formValidation();
            if (bi.nh315a.isChecked()) {
                Clear.clearAllFields(bi.fldGrpnh316, true);
            } else {
                Clear.clearAllFields(bi.fldGrpnh316, false);
            }
        });

//        nh321
        bi.nh321.setOnCheckedChangeListener((radioGroup, i) -> {
            formValidation();
            if (i == R.id.nh321b) {
                Clear.clearAllFields(bi.fldGrpnh322, false);

            } else {
                Clear.clearAllFields(bi.fldGrpnh322, true);
                bi.nh322acr.setEnabled(false);
                bi.nh322can.setEnabled(false);
            }
        });

//        nh323
        bi.nh323.setOnCheckedChangeListener((radioGroup, i) -> {
            formValidation();
            if (i == R.id.nh323b) {
                Clear.clearAllFields(bi.fldGrpnh324, false);
            } else {
                Clear.clearAllFields(bi.fldGrpnh324, true);
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
                startActivity(new Intent(this, SectionH4Activity.class));
            } else {
                Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this);
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpH301);
    }

    private void saveDraft() throws JSONException {
        JSONObject sH3 = new JSONObject();

        sH3.put("nh301", bi.nh301a.isChecked() ? "1"
                : bi.nh301b.isChecked() ? "2"
                : bi.nh301c.isChecked() ? "3"
                : bi.nh301d.isChecked() ? "4"
                : bi.nh301e.isChecked() ? "5"
                : bi.nh301f.isChecked() ? "6"
                : bi.nh301g.isChecked() ? "7"
                : bi.nh30196.isChecked() ? "96"
                : "0");
        sH3.put("nh30196x", bi.nh30196x.getText().toString());

        sH3.put("nh302", bi.nh302a.isChecked() ? "1"
                : bi.nh302b.isChecked() ? "2"
                : bi.nh30296.isChecked() ? "96" : "0");
        sH3.put("nh30296x", bi.nh30296x.getText().toString());

        sH3.put("nh303a", bi.nh303a1.isChecked() ? "1"
                : bi.nh303a2.isChecked() ? "2"
                : "0");

        sH3.put("nh303", bi.nh303b.isChecked() ? "1"
                : bi.nh303c.isChecked() ? "2"
                : bi.nh303d.isChecked() ? "3"
                : bi.nh303e.isChecked() ? "4"
                : bi.nh303f.isChecked() ? "5"
                : bi.nh303g.isChecked() ? "6"
                : bi.nh303h.isChecked() ? "7"
                : bi.nh303i.isChecked() ? "8"
                : bi.nh303j.isChecked() ? "9"
                : bi.nh303k.isChecked() ? "10"
                : bi.nh303l.isChecked() ? "11"
                : bi.nh303m.isChecked() ? "12"
                : bi.nh303n.isChecked() ? "13"
                : bi.nh303o.isChecked() ? "14"
                : bi.nh303p.isChecked() ? "15"
                : bi.nh303q.isChecked() ? "16"
                : bi.nh30396.isChecked() ? "96"
                : "0");
        sH3.put("nh30396x", bi.nh30396x.getText().toString());

        sH3.put("nh304", bi.nh30498.isChecked() ? "998" : bi.nh30499.isChecked() ? "000" : bi.nh304.getText().toString());

        sH3.put("nh305", bi.nh305a.isChecked() ? "1"
                : bi.nh305b.isChecked() ? "2"
                : "0");

        sH3.put("nh306", bi.nh306a.isChecked() ? "1"
                : bi.nh306b.isChecked() ? "2"
                : bi.nh306c.isChecked() ? "3"
                : bi.nh306d.isChecked() ? "4"
                : bi.nh306e.isChecked() ? "5"
                : bi.nh306f.isChecked() ? "6"
                : bi.nh30696.isChecked() ? "96"
                : "0");
        sH3.put("nh30696x", bi.nh30696x.getText().toString());


        sH3.put("nh307", bi.nh307a.isChecked() ? "1"
                : bi.nh307b.isChecked() ? "2"
                : bi.nh307c.isChecked() ? "3"
                : bi.nh307d.isChecked() ? "4"
                : bi.nh307e.isChecked() ? "5"
                : bi.nh307f.isChecked() ? "6"
                : bi.nh307g.isChecked() ? "7"
                : bi.nh307h.isChecked() ? "8"
                : bi.nh307i.isChecked() ? "9"
                : bi.nh30796.isChecked() ? "96"
                : "0");

        sH3.put("nh30796x", bi.nh30796x.getText().toString());

        sH3.put("nh308", bi.nh308a.isChecked() ? "1"
                : bi.nh308b.isChecked() ? "2"
                : "0");

        sH3.put("nh309", bi.nh309.getText().toString());
//        nh310
        sH3.put("nh310", bi.nh310a.isChecked() ? "1"
                : bi.nh310b.isChecked() ? "2"
                : bi.nh310c.isChecked() ? "3"
                : bi.nh310d.isChecked() ? "4"
                : bi.nh310e.isChecked() ? "5"
                : bi.nh310f.isChecked() ? "6"
                : bi.nh310g.isChecked() ? "7"
                : bi.nh31096.isChecked() ? "96"
                : "0");
        sH3.put("nh31096x", bi.nh31096x.getText().toString());
//        nh311
        sH3.put("nh31101", bi.nh31101a.isChecked() ? "1"
                : bi.nh31101b.isChecked() ? "2"
                : "0");

        sH3.put("nh31102", bi.nh31102a.isChecked() ? "1"
                : bi.nh31102b.isChecked() ? "2"
                : "0");

        sH3.put("nh31103", bi.nh31103a.isChecked() ? "1"
                : bi.nh31103b.isChecked() ? "2"
                : "0");

        sH3.put("nh31104", bi.nh31104a.isChecked() ? "1"
                : bi.nh31104b.isChecked() ? "2"
                : "0");

        sH3.put("nh31105", bi.nh31105a.isChecked() ? "1"
                : bi.nh31105b.isChecked() ? "2"
                : "0");
        sH3.put("nh31106", bi.nh31106a.isChecked() ? "1"
                : bi.nh31106b.isChecked() ? "2"
                : "0");
        sH3.put("nh31107", bi.nh31107a.isChecked() ? "1"
                : bi.nh31107b.isChecked() ? "2"
                : "0");
        sH3.put("nh31108", bi.nh31108a.isChecked() ? "1"
                : bi.nh31108b.isChecked() ? "2"
                : "0");
        sH3.put("nh31109", bi.nh31109a.isChecked() ? "1"
                : bi.nh31109b.isChecked() ? "2"
                : "0");
        sH3.put("nh31110", bi.nh31110a.isChecked() ? "1"
                : bi.nh31110b.isChecked() ? "2"
                : "0");
        sH3.put("nh31111", bi.nh31111a.isChecked() ? "1"
                : bi.nh31111b.isChecked() ? "2"
                : "0");
        sH3.put("nh31112", bi.nh31112a.isChecked() ? "1"
                : bi.nh31112b.isChecked() ? "2"
                : "0");
        sH3.put("nh31113", bi.nh31113a.isChecked() ? "1"
                : bi.nh31113b.isChecked() ? "2"
                : "0");
        sH3.put("nh31114", bi.nh31114a.isChecked() ? "1"
                : bi.nh31114b.isChecked() ? "2"
                : "0");
        sH3.put("nh31115", bi.nh31115a.isChecked() ? "1"
                : bi.nh31115b.isChecked() ? "2"
                : "0");
        sH3.put("nh31116", bi.nh31116a.isChecked() ? "1"
                : bi.nh31116b.isChecked() ? "2"
                : "0");
        sH3.put("nh31117", bi.nh31117a.isChecked() ? "1"
                : bi.nh31117b.isChecked() ? "2"
                : "0");
        sH3.put("nh31118", bi.nh31118a.isChecked() ? "1"
                : bi.nh31118b.isChecked() ? "2"
                : "0");
        sH3.put("nh31119", bi.nh31119a.isChecked() ? "1"
                : bi.nh31119b.isChecked() ? "2"
                : "0");

//        nh312
        sH3.put("nh312a", bi.nh312a1.isChecked() ? "1" : bi.nh312a2.isChecked() ? "2" : "0");
        sH3.put("nh312b", bi.nh312b1.isChecked() ? "1" : bi.nh312b2.isChecked() ? "2" : "0");
        sH3.put("nh312c", bi.nh312c1.isChecked() ? "1" : bi.nh312c2.isChecked() ? "2" : "0");
        sH3.put("nh312d", bi.nh312d1.isChecked() ? "1" : bi.nh312d2.isChecked() ? "2" : "0");
        sH3.put("nh312e", bi.nh312e1.isChecked() ? "1" : bi.nh312e2.isChecked() ? "2" : "0");
        sH3.put("nh312f", bi.nh312f1.isChecked() ? "1" : bi.nh312f2.isChecked() ? "2" : "0");
        sH3.put("nh312g", bi.nh312g1.isChecked() ? "1" : bi.nh312g2.isChecked() ? "2" : "0");
        sH3.put("nh312h", bi.nh312h1.isChecked() ? "1" : bi.nh312h2.isChecked() ? "2" : "0");
        sH3.put("nh312i", bi.nh312i1.isChecked() ? "1" : bi.nh312i2.isChecked() ? "2" : "0");

//        nh313
        sH3.put("nh313a", bi.nh313a.isChecked() ? "1" : "0");
        sH3.put("nh313b", bi.nh313b.isChecked() ? "2" : "0");
        sH3.put("nh313c", bi.nh313c.isChecked() ? "3" : "0");
        sH3.put("nh313d", bi.nh313d.isChecked() ? "4" : "0");
        sH3.put("nh313e", bi.nh313e.isChecked() ? "5" : "0");
        sH3.put("nh313f", bi.nh313f.isChecked() ? "6" : "0");
        sH3.put("nh31396", bi.nh31396.isChecked() ? "96" : "0");
        sH3.put("nh31396x", bi.nh31396x.getText().toString());

//        nh314
        sH3.put("nh314", bi.nh314a.isChecked() ? "1"
                : bi.nh314b.isChecked() ? "2"
                : bi.nh314c.isChecked() ? "3"
                : bi.nh314d.isChecked() ? "4"
                : bi.nh314e.isChecked() ? "5"
                : bi.nh314f.isChecked() ? "6"
                : bi.nh314g.isChecked() ? "7"
                : bi.nh314h.isChecked() ? "8"
                : bi.nh314i.isChecked() ? "9"
                : bi.nh314j.isChecked() ? "10"
                : bi.nh314k.isChecked() ? "11"
                : bi.nh31496.isChecked() ? "96"
                : "0");
        sH3.put("nh31496x", bi.nh31496x.getText().toString());
//       nh315
        sH3.put("nh315", bi.nh315a.isChecked() ? "1"
                : bi.nh315b.isChecked() ? "2"
                : bi.nh315c.isChecked() ? "3"
                : bi.nh31596.isChecked() ? "96"
                : "0");
        sH3.put("nh31596x", bi.nh31596x.getText().toString());
//        nh316
        sH3.put("nh316", bi.nh316a.isChecked() ? "1"
                : bi.nh316b.isChecked() ? "2"
                : "0");

//        nh317
        sH3.put("nh317", bi.nh317a.isChecked() ? "1"
                : bi.nh317b.isChecked() ? "2"
                : bi.nh317c.isChecked() ? "3"
                : bi.nh317d.isChecked() ? "4"
                : bi.nh317e.isChecked() ? "5"
                : bi.nh317f.isChecked() ? "6"
                : bi.nh317g.isChecked() ? "7"
                : bi.nh317h.isChecked() ? "8"
                : bi.nh317i.isChecked() ? "9"
                : bi.nh317j.isChecked() ? "10"
                : bi.nh317k.isChecked() ? "11"
                : bi.nh31796.isChecked() ? "96"
                : "0");
        sH3.put("nh31796x", bi.nh31796x.getText().toString());

//        nh318

        sH3.put("nh318", bi.nh318a.isChecked() ? "1"
                : bi.nh318b.isChecked() ? "2"
                : bi.nh318c.isChecked() ? "3"
                : bi.nh318d.isChecked() ? "4"
                : bi.nh318e.isChecked() ? "5"
                : bi.nh318f.isChecked() ? "6"
                : bi.nh318g.isChecked() ? "7"
                : bi.nh318h.isChecked() ? "8"
                : bi.nh318i.isChecked() ? "9"
                : bi.nh318j.isChecked() ? "10"
                : bi.nh318k.isChecked() ? "11"
                : bi.nh318l.isChecked() ? "12"
                : bi.nh318m.isChecked() ? "13"
                : bi.nh318n.isChecked() ? "14"
                : bi.nh31896.isChecked() ? "96"
                : "0");
        sH3.put("nh31896x", bi.nh31896x.getText().toString());

//          nh319
        sH3.put("nh319", bi.nh319a.isChecked() ? "1"
                : bi.nh319b.isChecked() ? "2"
                : bi.nh319c.isChecked() ? "3"
                : bi.nh319d.isChecked() ? "4"
                : bi.nh319e.isChecked() ? "5"
                : bi.nh319f.isChecked() ? "6"
                : bi.nh319g.isChecked() ? "7"
                : bi.nh319h.isChecked() ? "8"
                : bi.nh319i.isChecked() ? "9"
                : bi.nh319j.isChecked() ? "10"
                : bi.nh319k.isChecked() ? "11"
                : bi.nh319l.isChecked() ? "12"
                : bi.nh319m.isChecked() ? "13"
                : bi.nh319n.isChecked() ? "14"
                : bi.nh319o.isChecked() ? "15"
                : bi.nh319p.isChecked() ? "16"
                : bi.nh31996.isChecked() ? "96"
                : "0");
        sH3.put("nh31996x", bi.nh31996x.getText().toString());

//        nh320
        sH3.put("nh320", bi.nh320.getText().toString());

//        nh321
        sH3.put("nh321", bi.nh321a.isChecked() ? "1"
                : bi.nh321b.isChecked() ? "2"
                : "0");

//        nh322
        sH3.put("nh322", bi.nh322a.isChecked() ? "1"
                : bi.nh322b.isChecked() ? "2"
                : bi.nh32298.isChecked() ? "98"
                : "0");
        sH3.put("nh322acr", bi.nh322acr.getText().toString());
        sH3.put("nh322can", bi.nh322can.getText().toString());

//        nh323
        sH3.put("nh323", bi.nh323a.isChecked() ? "1"
                : bi.nh323b.isChecked() ? "2"
                : "0");
//        nh324
        sH3.put("nh324a", bi.nh324a.getText().toString());
        sH3.put("nh324b", bi.nh324b.getText().toString());
        sH3.put("nh324c", bi.nh324c.getText().toString());
        sH3.put("nh324d", bi.nh324d.getText().toString());
        sH3.put("nh324e", bi.nh324e.getText().toString());
        sH3.put("nh324f", bi.nh324f.getText().toString());
        sH3.put("nh324g", bi.nh324g.getText().toString());

        MainApp.form.setsH3(String.valueOf(sH3));
    }

    private boolean updateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SH3, MainApp.form.getsH3());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }

}
