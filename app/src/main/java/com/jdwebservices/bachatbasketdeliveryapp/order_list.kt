package com.jdwebservices.bachatbasketdeliveryapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.jdwebservices.bachatbasketdeliveryapp.adapters.booking_adapter
import com.jdwebservices.bachatbasketdeliveryapp.data_files.booking
import com.jdwebservices.bachatbasketdeliveryapp.data_files.loginsave

import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.content_order_list.*
import java.util.HashMap

class order_list : AppCompatActivity() {
    lateinit var driverloginsave: loginsave

    var BASE_URL = "https://mybachatbasket.xyz/bachatbasket/"
    var textView_msg = ""
    var driver_id = ""
    lateinit var session: loginsave
    var user_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        setSupportActionBar(toolbar)
        title = "ORDER LIST"


        session = loginsave(applicationContext)

        session.checkLogin()

        val user: HashMap<String, String> = session.getUserDetails()
        // val name: String = user.get(client_login_save.KEY_NAME)!!
        user_id = user.get(loginsave.KEY_EMAIL)!!



        //  nodata.visibility = View.GONE

        val cat_url1="delivery_boy_order.php?delivery_boy_id="+user_id
        cat_url1.replace(" ", "%20")
        Log.d("url", cat_url1)
        var bookings=ArrayList<booking>()

        val cat_rq1: RequestQueue = Volley.newRequestQueue(this)
        val cat_jar1= JsonArrayRequest(Request.Method.GET,BASE_URL+cat_url1,null, Response.Listener { response ->
            if (response.length() == 0)
            {
                // no_result.visibility = View.VISIBLE
                sub_cat_rv.visibility = View.GONE
                progress.visibility = View.GONE
                nodata.visibility = View.VISIBLE
            }

            for(cat in 0..response.length()-1)
                bookings.add(booking (response.getJSONObject(cat).getInt("order_id"),response.getJSONObject(cat).getInt("bill_no"),response.getJSONObject(cat).getString("Client_name"),response.getJSONObject(cat).getString("Clinet_mobile"),response.getJSONObject(cat).getString("ammount"),response.getJSONObject(cat).getString("shippingAddress"),response.getJSONObject(cat).getString("shippingCity"),response.getJSONObject(cat).getString("order_detail")))

            val cat_adp= booking_adapter(this,bookings)
            sub_cat_rv.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL ,false)
            sub_cat_rv.adapter = cat_adp

            sub_cat_rv.visibility = View.VISIBLE
            progress.visibility = View.GONE

        }, Response.ErrorListener {
            sub_cat_rv.visibility = View.VISIBLE
            progress.visibility = View.GONE

            /*val builder = AlertDialog.Builder(this)
            builder.setMessage("Please check Your Internet Connection\n")
            val dialog: AlertDialog = builder.create()
            dialog.show()*/
        })

        cat_rq1.add(cat_jar1)

        fab.setOnClickListener { view ->
            val catUrl1="delivery_boy_order.php?delivery_boy_id="+user_id
            catUrl1.replace(" ", "%20")
            Log.d("url", catUrl1)
            val bookingss=ArrayList<booking>()

            val catRq1: RequestQueue = Volley.newRequestQueue(this)
            val catJar1= JsonArrayRequest(Request.Method.GET,BASE_URL+catUrl1,null, Response.Listener { response ->
                if (response.length() == 0)
                {
                    // no_result.visibility = View.VISIBLE
                    sub_cat_rv.visibility = View.GONE
                    progress.visibility = View.GONE
                    nodata.visibility = View.VISIBLE
                }

                for(cat in 0..response.length()-1)
                    bookingss.add(booking (response.getJSONObject(cat).getInt("order_id"),response.getJSONObject(cat).getInt("bill_no"),response.getJSONObject(cat).getString("Client_name"),response.getJSONObject(cat).getString("Clinet_mobile"),response.getJSONObject(cat).getString("ammount"),response.getJSONObject(cat).getString("shippingAddress"),response.getJSONObject(cat).getString("shippingCity"),response.getJSONObject(cat).getString("order_detail")))

                val cat_adp= booking_adapter(this,bookingss)
                sub_cat_rv.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL ,false)
                sub_cat_rv.adapter = cat_adp

                sub_cat_rv.visibility = View.VISIBLE
                progress.visibility = View.GONE

            }, Response.ErrorListener {
                sub_cat_rv.visibility = View.VISIBLE
                progress.visibility = View.GONE

                /*val builder = AlertDialog.Builder(this)
                builder.setMessage("Please check Your Internet Connection\n")
                val dialog: AlertDialog = builder.create()
                dialog.show()*/
            })

            catRq1.add(catJar1)/*
            val a = Intent(this, order_list::class.java)
            startActivity(a)
            finish()*/
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home2, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.logout ->{
               session.LogoutUser()
                return true
            }
            else ->   return false //super.onOptionsItemSelected(item)
        }
        return false
    }

}
