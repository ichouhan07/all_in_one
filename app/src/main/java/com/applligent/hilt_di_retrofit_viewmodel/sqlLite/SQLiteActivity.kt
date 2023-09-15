package com.applligent.hilt_di_retrofit_viewmodel.sqlLite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivitySqliteBinding
import com.applligent.hilt_di_retrofit_viewmodel.MainActivity
import com.applligent.hilt_di_retrofit_viewmodel.sqlLite.Database.DatabaseHelper

class SQLiteActivity : AppCompatActivity() {
    private val binding : ActivitySqliteBinding by lazy { ActivitySqliteBinding.inflate(layoutInflater) }
    private var listUsers: ArrayList<SqlUser>? = null
    private var userAdapter: UserAdapter? = null
    private var databaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setCustomToolBar()
        binding.ivSqlUpload.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SqlAddUserActivity::class.java)
            startActivity(intent)
        })
        initObjects()
    }
    private fun initObjects() {
        listUsers = ArrayList()
        userAdapter = UserAdapter(this, listUsers!!)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        binding.sqlRecyclerview.layoutManager = mLayoutManager
        binding.sqlRecyclerview.itemAnimator = DefaultItemAnimator()
        binding.sqlRecyclerview.setHasFixedSize(true)
        binding.sqlRecyclerview.adapter = userAdapter
        databaseHelper = DatabaseHelper(this)
        getDataFromSQLite()
    }

    @SuppressLint("StaticFieldLeak")
    private fun getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        object : AsyncTask<Void?, Void?, Void?>() {
            @Deprecated("Deprecated in Java")
            @SuppressLint("NotifyDataSetChanged")
            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                userAdapter!!.notifyDataSetChanged()
            }

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg p0: Void?): Void? {
                listUsers!!.clear()
                listUsers!!.addAll(databaseHelper!!.allUser)
                return null
            }
        }.execute()
    }

    @SuppressLint("SetTextI18n")
    private fun setCustomToolBar() {
        binding.customToolbar.ivBack.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }
        binding.customToolbar.ivNotification.setOnClickListener {
            //Toast.makeText(this, "Clicked Notification", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.ivSetting.setOnClickListener {
            //Toast.makeText(this, "Clicked Setting", Toast.LENGTH_SHORT).show()
        }
        binding.customToolbar.tvTitle.text = "SQL_LITE"
    }
}