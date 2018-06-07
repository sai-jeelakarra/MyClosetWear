package com.example.androiddeveloper.firsttask.activities

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.androiddeveloper.firsttask.MainActivity
import com.example.androiddeveloper.firsttask.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*
import android.provider.MediaStore
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import java.io.File


class DashboardActivity : AppCompatActivity() {


    private var auth: FirebaseAuth? = null
    private var authListener: FirebaseAuth.AuthStateListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //get firebase auth instance
        auth = FirebaseAuth.getInstance()

        //get current user
        val user = FirebaseAuth.getInstance().currentUser

        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                finish()
            }
        }



        history.setOnClickListener {
            history.setBackgroundResource(R.color.myclosetwear)
            history.setTextColor(Color.WHITE)

            camera.setBackgroundResource(R.color.bg_color)
            camera.setTextColor(Color.BLACK)
        }


        camera.setOnClickListener {
            camera.setBackgroundResource(R.color.myclosetwear)
            camera.setTextColor(Color.WHITE)

            history.setBackgroundResource(R.color.bg_color)
            history.setTextColor(Color.BLACK)


            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            startActivityForResult(intent, 0)

        }



    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            val result = data.toURI()
            startActivity(Intent(this,MainActivity::class.java))

        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_page, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                return true
            }
            R.id.logout -> {
                auth!!.signOut()
                startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



}