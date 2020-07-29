package com.angus.menu

data class Transaction(
    var account : String,
    var date : String,
    var amount : Int,
    var type : Int){
    constructor() : this("", "", 0, 0)

    override fun toString(): String {
        return "$account $date $amount $type"
    }
}