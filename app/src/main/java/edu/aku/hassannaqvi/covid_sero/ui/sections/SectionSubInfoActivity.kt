package edu.aku.hassannaqvi.covid_sero.ui.sections

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import edu.aku.hassannaqvi.covid_sero.CONSTANTS
import edu.aku.hassannaqvi.covid_sero.R
import edu.aku.hassannaqvi.covid_sero.core.MainApp
import edu.aku.hassannaqvi.covid_sero.databinding.ActivitySectionSubInfoBinding
import edu.aku.hassannaqvi.covid_sero.ui.other.EndingActivity
import edu.aku.hassannaqvi.covid_sero.utils.EndSectionActivity
import edu.aku.hassannaqvi.covid_sero.utils.contextEndActivity
import ru.whalemare.sheetmenu.ActionItem
import ru.whalemare.sheetmenu.SheetMenu
import ru.whalemare.sheetmenu.layout.GridLayoutProvider

class SectionSubInfoActivity : AppCompatActivity(), EndSectionActivity {

    private lateinit var bi: ActivitySectionSubInfoBinding
    private var flagNewForm = false
    private var flagInCompleteForm = false
    private var hhFlag = false
    private var memFlag = false
    private var istatusFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_sub_info)
        bi.callback = this
        bi.formScroll.callback = this
        bi.txtCluster.text = MainApp.form.hhModel.clusterCode
        bi.txtHHNo.text = MainApp.form.hhModel.hhno
        istatusFlag = intent.getIntExtra(CONSTANTS.ROUTE_SUBINFO, 0)
    }

    override fun onResume() {
        super.onResume()
        setUI()
    }

    fun onHHViewClick() {
        if (!hhFlag) return
        startActivity(Intent(this, SectionH301Activity::class.java))
    }

    fun onChildViewClick() {
        if (!memFlag) return
        startActivity(Intent(this, SectionPIAActivity::class.java))
    }

    fun onFabBtnMenuClick() {
        SheetMenu(
                "Select your Action",
                listOf(
                        ActionItem(
                                0,
                                "Force Stop",
                                getDrawable(R.drawable.ic_warning_black_24dp)
                        )
                        ,
                        ActionItem(
                                1,
                                "End Household",
                                getDrawable(R.drawable.ic_closed_caption_black_24dp)
                        )
                ),
                onClick = { item: ActionItem ->
                    run {
                        when (item.id) {
                            0 -> {
                                if (flagNewForm || flagInCompleteForm) return@run
                                contextEndActivity(this, false)
                            }
                            else -> {
                                if (flagNewForm) return@run
                                endActivityStatus()
                            }
                        }
                    }

                }, layoutProvider = GridLayoutProvider()
        ).show(this)

    }

    private fun setUI() {
        when (istatusFlag) {
            0 -> {
                hhFlag = true
                memFlag = false
                flagNewForm = true
                bi.instruction.text = getString(R.string.hhformInfo)
            }
            1 -> {
                bi.formScroll.hhScroll.name.text = "HOUSEHOLD FORM COMPLETED"
                bi.formScroll.hhScroll.status.visibility = View.VISIBLE
                hhFlag = false
                memFlag = true
                flagNewForm = false
                bi.instruction.text = getString(R.string.memberforminfo)
            }
            2 -> {
                bi.formScroll.hhScroll.name.text = "HOUSEHOLD FORM COMPLETED"
                bi.formScroll.hhScroll.status.visibility = View.VISIBLE
                bi.formScroll.childScroll.name.text = "CHILD FORM COMPLETED"
                bi.formScroll.childScroll.status.visibility = View.VISIBLE
                hhFlag = false
                memFlag = false
                flagNewForm = false
                bi.instruction.text = getString(R.string.end_interview)
            }
            99 -> {
                bi.formScroll.hhScroll.name.text = "HOUSEHOLD FORM COMPLETED"
                bi.formScroll.childScroll.name.text = "MEMBER FORM IS BLOCKED\nContact Team Leader"
                bi.formScroll.hhScroll.status.visibility = View.VISIBLE
                hhFlag = false
                memFlag = false
                flagNewForm = false
                flagInCompleteForm = true
                bi.instruction.text = getString(R.string.end_interview)
            }
        }


    }

    override fun endSecActivity(flag: Boolean) {
        finish()
        startActivity(Intent(this, EndingActivity::class.java)
                .putExtra("complete", flag)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun endActivityStatus() {
        contextEndActivity(this@SectionSubInfoActivity, istatusFlag != 99)
    }

}
