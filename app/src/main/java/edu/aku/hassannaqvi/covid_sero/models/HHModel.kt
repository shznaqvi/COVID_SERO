package edu.aku.hassannaqvi.covid_sero.models

data class HHModel @JvmOverloads constructor(var clusterCode: String, var hhno: String, var memAge: Int, var genderFemale: Boolean, var married: Boolean = false)