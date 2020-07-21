package edu.aku.hassannaqvi.covid_sero.models

data class HHModel @JvmOverloads constructor(var clusterCode: String, var hhno: String, var memAge: Int = 0, var genderFemale: Boolean = false, var married: Boolean = false)