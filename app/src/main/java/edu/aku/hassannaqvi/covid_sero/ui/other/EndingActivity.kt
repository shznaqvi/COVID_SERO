package edu.aku.hassannaqvi.covid_sero.ui.other

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import edu.aku.hassannaqvi.covid_sero.R
import edu.aku.hassannaqvi.covid_sero.core.MainApp
import edu.aku.hassannaqvi.covid_sero.databinding.ActivityEndingBinding
import edu.aku.hassannaqvi.covid_sero.validator.ValidatorClass
import java.text.SimpleDateFormat
import java.util.*

class EndingActivity : AppCompatActivity() {
    lateinit var bi: ActivityEndingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending)
        bi.callback = this

        val check = intent.getBooleanExtra("complete", false)
        if (check) {
            bi.istatus01.isEnabled = true
            bi.istatus02.isEnabled = false
            bi.istatus03.isEnabled = false
            bi.istatus04.isEnabled = false
            bi.istatus05.isEnabled = false
            bi.istatus06.isEnabled = false
            bi.istatus07.isEnabled = false
            bi.istatus96.isEnabled = false
        } else {
            bi.istatus01.isEnabled = false
            bi.istatus02.isEnabled = true
            bi.istatus03.isEnabled = true
            bi.istatus04.isEnabled = true
            bi.istatus05.isEnabled = true
            bi.istatus06.isEnabled = true
            bi.istatus07.isEnabled = true
            bi.istatus96.isEnabled = true
        }
    }

    fun BtnEnd() {
        if (!formValidation()) return
        saveDraft()
        if (updateDB()) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDraft() {
        val statusValue = if (bi.istatus01.isChecked) "1" else if (bi.istatus02.isChecked) "2" else if (bi.istatus03.isChecked) "3" else if (bi.istatus04.isChecked) "4" else if (bi.istatus05.isChecked) "5" else if (bi.istatus06.isChecked) "6" else if (bi.istatus07.isChecked) "96" else "0"
        MainApp.form.istatus = statusValue
        MainApp.form.istatus96x = bi.istatus96x.text.toString()
        MainApp.form.endingdatetime = SimpleDateFormat("dd-MM-yy HH:mm").format(Date().time)
    }


    private fun updateDB(): Boolean {
        val db = MainApp.appInfo.dbHelper
        val updcount = db.updateEnding()
        return if (updcount == 1) {
            true
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun formValidation(): Boolean {
        return ValidatorClass.EmptyRadioButton(this, bi.istatus, bi.istatus96, bi.istatus96x, getString(R.string.istatus))
    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "You Can't go back", Toast.LENGTH_LONG).show()
    }
}