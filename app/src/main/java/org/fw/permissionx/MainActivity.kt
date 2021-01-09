package org.fw.permissionx

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            PermissionX.request(this,
                    Manifest.permission.CALL_PHONE
                    ,Manifest.permission.READ_CONTACTS
            ){ allGranted: Boolean, deniedList: List<String> ->
                if(allGranted){
                    call()
                }else{
                    Toast.makeText(this, "$deniedList", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun call(){
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:10086")
        startActivity(intent)
    }
}