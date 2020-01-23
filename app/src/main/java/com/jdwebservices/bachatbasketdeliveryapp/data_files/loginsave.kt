package com.jdwebservices.bachatbasketdeliveryapp.data_files

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.jdwebservices.bachatbasketdeliveryapp.login

public class loginsave{

    var pref:SharedPreferences
    var editor: SharedPreferences.Editor
    var con: Context
    var PRIVATE_MODE: Int = 0
    constructor(con: Context){
        this.con = con
        pref = con.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {
        val PREF_NAME: String = "Client"
        val IS_LOGIN: String = "isLoggedIndriver"
        val KEY_NAME: String = "name"
        val KEY_EMAIL: String = "id"
    }

    fun createLoginSession(name: String,email: String)
    {
        editor.putBoolean(IS_LOGIN,true)
        editor.putString(KEY_NAME,name)
        editor.putString(KEY_EMAIL,email)
        editor.commit()
    }

    fun checkLogin()
    {
        if(!this.isLoggedIn())
        {
            var i: Intent = Intent(con, login::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            con.startActivity(i)
        }
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGIN,false)
    }

    fun getUserDetails(): HashMap<String,String>
    {
        var user: Map<String,String> = HashMap<String,String>()
        (user as HashMap).put(KEY_NAME, pref.getString(KEY_NAME,null).toString())
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,null).toString())
        return user
    }

    fun LogoutUser()
    {
        editor.clear()
        editor.commit()
        var i: Intent = Intent(con,login::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        con.startActivity(i)
    }



}