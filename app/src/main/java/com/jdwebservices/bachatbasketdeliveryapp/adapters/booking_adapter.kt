package com.jdwebservices.bachatbasketdeliveryapp.adapters

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jdwebservices.bachatbasketdeliveryapp.R
import com.jdwebservices.bachatbasketdeliveryapp.data_files.booking
import com.jdwebservices.bachatbasketdeliveryapp.order_list
import kotlinx.android.synthetic.main.all_booking_row.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class booking_adapter(var contet:Context, var catlist: ArrayList<booking>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v:View=LayoutInflater.from(contet).inflate(R.layout.all_booking_row,parent,false)
        return clientHolders(v)
    }

    override fun getItemCount(): Int {
        return catlist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { }
        (holder as clientHolders).bind(
                catlist[position].order_id,
                catlist[position].bill_no,
                catlist[position].Client_name,
                catlist[position].Clinet_mobile,
                catlist[position].ammount,
                catlist[position].shippingAddress,
                catlist[position].shippingCity,
                catlist[position].order_detail
        )
    }

    class clientHolders(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var BASE_URL = "https://mybachatbasket.xyz/bachatbasket/"
        fun bind(
            order_id:Int,
            bill_no:Int,
            Client_name:String,
            Clinet_mobile:String,
            ammount:String,
            shippingAddress:String,
            shippingCity:String,
            order_detail:String)
        {
            itemView.order_id.text = order_id.toString()
            itemView.bill_no.text = bill_no.toString()
            itemView.price.text = ammount
            itemView.client_name.text = Client_name
            itemView.mobile.text = Clinet_mobile
            itemView.address.text = shippingAddress+", "+shippingCity

            itemView.detail.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(itemView.context)
                builder.setMessage(order_detail)
                val dialog: android.app.AlertDialog = builder.create()
                dialog.show()

            }
            itemView.otp.text.clear()
            itemView.submit.setOnClickListener {
                itemView.progress.visibility = View.VISIBLE
                itemView.submit.visibility = View.GONE
                var otpp = itemView.otp.text.toString()
                val cat_url="delivery_boy_otp_submit.php?delivery_boy_otp_submit=submit&otp="+otpp+"&order_id="+order_id
                Log.d("url", cat_url)

                val cat_rq: RequestQueue = Volley.newRequestQueue(itemView.context)
                val cat_jar= JsonObjectRequest(Request.Method.GET,BASE_URL+cat_url,null, Response.Listener { response ->


                    var driver_id =  response.getString("driver_id")
                    var driver_message = response.getString("driver_message")

                    if(driver_id == "")
                    {
                            val builder = AlertDialog.Builder(itemView.context)
                            builder.setMessage(driver_message)
                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                        itemView.progress.visibility = View.GONE
                        itemView.submit.visibility = View.VISIBLE

                    }
                    else{
                        Toast.makeText(itemView.context, driver_message, Toast.LENGTH_LONG).show()
                        val a = Intent(itemView.context, order_list::class.java)
                        itemView.context.startActivity(a)
                        itemView.progress.visibility = View.GONE
                        itemView.submit.visibility = View.VISIBLE
                    }


                }, Response.ErrorListener {
                    itemView.progress.visibility = View.GONE
                    itemView.submit.visibility = View.VISIBLE
                    /*val builder = AlertDialog.Builder(this)
                    builder.setMessage("Please check Your Internet Connection\n")
                    val dialog: AlertDialog = builder.create()
                    dialog.show()*/
                })

                cat_rq.add(cat_jar)

            }
        }
    }




}

