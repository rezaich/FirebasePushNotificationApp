package com.zaich.firebasepushnotificationapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.zaich.firebasepushnotificationapp.databinding.ActivityMainBinding
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the clipboard system service
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result!!


            Log.d(TAG, "TOKEN : $token")
            Toast.makeText(baseContext, "masuk", Toast.LENGTH_SHORT).show()
//            binding.etPaste.setText(token)
          binding.btCopy.setOnClickListener {
                val clip = ClipData.newPlainText("copy" , token)
                clipboard.setPrimaryClip(clip)
//            Toast.makeText(baseContext, "test copy", Toast.LENGTH_LONG).show()
            }

 /*           binding.btPaste.setOnClickListener {
                // Paste clipboard text to edit text
                val clipData:ClipData? = clipboard.primaryClip

                clipData?.apply {
                    val textToPaste:String = this.getItemAt(0).text.toString().trim()
                    binding.etPaste.setText(textToPaste)
                }
            }*/
        })

        FirebaseMessaging.getInstance().subscribeToTopic("all")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Successfully send to all")
                } else {
                    //next
                }
            }
    }

    companion object {
        const val TAG = "USER"
    }
}