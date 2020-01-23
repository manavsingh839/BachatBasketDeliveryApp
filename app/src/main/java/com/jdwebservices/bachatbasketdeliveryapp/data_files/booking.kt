package com.jdwebservices.bachatbasketdeliveryapp.data_files


class booking{

    var order_id:Int
    var bill_no:Int
    var Client_name: String
    var Clinet_mobile: String
    var ammount: String
    var shippingAddress: String
    var shippingCity: String
    var order_detail: String

    constructor(
        order_id:Int,
        bill_no:Int,
            Client_name:String,
        Clinet_mobile:String,
        ammount:String,
        shippingAddress:String,
        shippingCity:String,
        order_detail:String)
    {
        this.order_id = order_id
        this.bill_no = bill_no
        this.Client_name = Client_name
        this.Clinet_mobile = Clinet_mobile
        this.ammount = ammount
        this.shippingAddress = shippingAddress
        this.shippingCity = shippingCity
        this.order_detail = order_detail
    }

}
