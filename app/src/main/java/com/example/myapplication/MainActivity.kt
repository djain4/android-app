package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var listOfVideos: MutableList<String>
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkForPermission()
        setAdapter()

    }

    private fun setAdapter() {
        recyclerAdapter = RecyclerAdapter(listOfVideos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter
    }

    private fun checkForPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )

        } else {
            readVideosFromExternalStorage()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    readVideosFromExternalStorage()
                }
            }
        }
    }


    private fun readVideosFromExternalStorage() {
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Video.VideoColumns.DATA)
        val c = getContentResolver().query(uri, projection, null, null, null)
        var vidsCount = 0
        if (c != null) {
            vidsCount = c!!.getCount()
            listOfVideos = ArrayList()
            while (c!!.moveToNext()) {

                listOfVideos.add(c!!.getString(0))
            }
            c!!.close()
        }
    }

    /*fun printNamesToLogCat(context: Context) {
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf<String>(MediaStore.Video.VideoColumns.DATA)
        val c = context.getContentResolver().query(uri, projection, null, null, null)
        var vidsCount = 0
        if (c != null) {
            vidsCount = c!!.getCount()
            while (c!!.moveToNext()) {
                Log.d("VIDEO", c!!.getString(0))
            }
            c!!.close()
        }
    }*/

    companion object {
        private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1
        private val TAG = MainActivity::class.qualifiedName
    }
}
