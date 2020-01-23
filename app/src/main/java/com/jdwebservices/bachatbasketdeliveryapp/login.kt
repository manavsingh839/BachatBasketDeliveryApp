package com.jdwebservices.bachatbasketdeliveryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jdwebservices.bachatbasketdeliveryapp.data_files.loginsave
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    var BASE_URL = "https://mybachatbasket.xyz/bachatbasket/"
    lateinit var session: loginsave

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = loginsave(applicationContext)
        //  var pass = this.findViewById(R.id.password) as EditText


        if (session.isLoggedIn()) {
            val i: Intent = Intent(applicationContext, order_list::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
            finish()
        }


        val driver_mobile = driver_mobile.text
        val passwor = password.text

        loginn.setOnClickListener {

            login_form.visibility = View.GONE
            progress.visibility = View.VISIBLE
            val cat_url="delivery_boy_login.php?driver_login=submit&mobile="+driver_mobile+"&password="+passwor
            Log.d("url", cat_url)

            val cat_rq: RequestQueue = Volley.newRequestQueue(this)
            val cat_jar= JsonObjectRequest(Request.Method.GET,BASE_URL+cat_url,null, Response.Listener { response ->


                var driver_id =  response.getString("driver_id")
                var driver_message = response.getString("driver_message")

                if(driver_id == "")
                {
                    if(driver_message == "send"){
                        password.visibility = View.VISIBLE
                        login_form.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                    }else {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage(driver_message)
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                        dialog.setOnDismissListener {
                            login_form.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                        }
                    }

                }
                else{
                    var driver_name = response.getString("driver_name")
                    Toast.makeText(this, driver_message, Toast.LENGTH_LONG).show()
                    session.createLoginSession(driver_name, driver_id)
                    val a = Intent(this, order_list::class.java)
                    startActivity(a)
                    finish()
                }


            }, Response.ErrorListener {
                login_form.visibility = View.VISIBLE
                progress.visibility = View.GONE

                /*val builder = AlertDialog.Builder(this)
                builder.setMessage("Please check Your Internet Connection\n")
                val dialog: AlertDialog = builder.create()
                dialog.show()*/
            })

            cat_rq.add(cat_jar)
            // val a = Intent(this, all_booking::class.java)
            // startActivity(a)
            // finish()
        }

    }
}
