package edu.aku.hassannaqvi.covid_sero.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_sero.R;
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionDBinding;
import edu.aku.hassannaqvi.covid_sero.ui.other.MainActivity;
import edu.aku.hassannaqvi.covid_sero.utils.AppUtilsKt;

public class SectionDActivity extends AppCompatActivity {

    ActivitySectionDBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d);
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
            startActivity(new Intent(this, MainActivity.class));
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

        json.put("im01", bi.im011.isChecked() ? "1"
                : bi.im012.isChecked() ? "2"
                : bi.im013.isChecked() ? "3"
                : bi.im014.isChecked() ? "4"
                : "-1");

        json.put("im02", bi.im021.isChecked() ? "1"
                : bi.im022.isChecked() ? "2"
                : bi.im023.isChecked() ? "3"
                : bi.im024.isChecked() ? "4"
                : "-1");

        json.put("im03", bi.im031.isChecked() ? "1"
                : bi.im032.isChecked() ? "2"
                : "-1");

        json.put("im04", bi.im041.isChecked() ? "1"
                : bi.im042.isChecked() ? "2"
                : bi.im043.isChecked() ? "3"
                : bi.im044.isChecked() ? "4"
                : "-1");

        json.put("im0501d", bi.im0501d.getText().toString());
        json.put("im0501m", bi.im0501m.getText().toString());
        json.put("im0501y", bi.im0501y.getText().toString());

        json.put("im0502d", bi.im0502d.getText().toString());
        json.put("im0502m", bi.im0502m.getText().toString());
        json.put("im0502y", bi.im0502y.getText().toString());

        json.put("im0503d", bi.im0503d.getText().toString());
        json.put("im0503m", bi.im0503m.getText().toString());
        json.put("im0503y", bi.im0503y.getText().toString());

        json.put("im0504d", bi.im0504d.getText().toString());
        json.put("im0504m", bi.im0504m.getText().toString());
        json.put("im0504y", bi.im0504y.getText().toString());

        json.put("im0505d", bi.im0505d.getText().toString());
        json.put("im0505m", bi.im0505m.getText().toString());
        json.put("im0505y", bi.im0505y.getText().toString());

        json.put("im0506d", bi.im0506d.getText().toString());
        json.put("im0506m", bi.im0506m.getText().toString());
        json.put("im0506y", bi.im0506y.getText().toString());

        json.put("im0507d", bi.im0507d.getText().toString());
        json.put("im0507m", bi.im0507m.getText().toString());
        json.put("im0507y", bi.im0507y.getText().toString());

        json.put("im0508d", bi.im0508d.getText().toString());
        json.put("im0508m", bi.im0508m.getText().toString());
        json.put("im0508y", bi.im0508y.getText().toString());

        json.put("im0509d", bi.im0509d.getText().toString());
        json.put("im0509m", bi.im0509m.getText().toString());
        json.put("im0509y", bi.im0509y.getText().toString());

        json.put("im0510d", bi.im0510d.getText().toString());
        json.put("im0510m", bi.im0510m.getText().toString());
        json.put("im0510y", bi.im0510y.getText().toString());

        json.put("im0511d", bi.im0511d.getText().toString());
        json.put("im0511m", bi.im0511m.getText().toString());
        json.put("im0511y", bi.im0511y.getText().toString());

        json.put("im0512d", bi.im0512d.getText().toString());
        json.put("im0512m", bi.im0512m.getText().toString());
        json.put("im0512y", bi.im0512y.getText().toString());

        json.put("im0513d", bi.im0513d.getText().toString());
        json.put("im0513m", bi.im0513m.getText().toString());
        json.put("im0513y", bi.im0513y.getText().toString());

        json.put("im0514d", bi.im0514d.getText().toString());
        json.put("im0514m", bi.im0514m.getText().toString());
        json.put("im0514y", bi.im0514y.getText().toString());

        json.put("im06", bi.im061.isChecked() ? "1"
                : bi.im062.isChecked() ? "2"
                : "-1");

        json.put("im07", bi.im071.isChecked() ? "1"
                : bi.im072.isChecked() ? "2"
                : bi.im0798.isChecked() ? "98"
                : "-1");

        json.put("im08", bi.im081.isChecked() ? "1"
                : bi.im082.isChecked() ? "2"
                : bi.im083.isChecked() ? "3"
                : bi.im084.isChecked() ? "4"
                : bi.im0898.isChecked() ? "98"
                : "-1");

        json.put("im09", bi.im091.isChecked() ? "1"
                : bi.im092.isChecked() ? "2"
                : bi.im0998.isChecked() ? "98"
                : "-1");

        json.put("im10", bi.im101.isChecked() ? "1"
                : bi.im102.isChecked() ? "2"
                : bi.im103.isChecked() ? "98"
                : "-1");

        json.put("im11", bi.im111.isChecked() ? "1"
                : bi.im112.isChecked() ? "2"
                : bi.im113.isChecked() ? "98"
                : "-1");

        json.put("im12", bi.im12.getText().toString());

        json.put("im13", bi.im131.isChecked() ? "1"
                : bi.im132.isChecked() ? "2"
                : bi.im1398.isChecked() ? "98"
                : "-1");

        json.put("im14", bi.im14.getText().toString());

        json.put("im15", bi.im151.isChecked() ? "1"
                : bi.im152.isChecked() ? "2"
                : bi.im153.isChecked() ? "98"
                : "-1");


        //    MainApp.form.setsH(json.toString());

    }


    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this);
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionD);

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View backDialogView = LayoutInflater.from(this).inflate(R.layout.back_dialog, null, false);
        builder.setView(backDialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        backDialogView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SectionDActivity.super.onBackPressed();
            }
        });
        backDialogView.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        // Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
        //backDialogView.findViewById<View>(R.id.btnOk).setOnClickListener


    }
}