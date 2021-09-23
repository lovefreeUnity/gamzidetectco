package com.pcm.gamzigamzi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.pcm.gamzigamzi.R

class SendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)

        val num = findViewById<EditText>(R.id.editText)
        val mass = findViewById<EditText>(R.id.editText2)
        val button = findViewById<Button>(R.id.button)


        button.setOnClickListener {
            //전화 번호
            val inputText = num.text.toString()

            //이부분이 내용
            val inputText2 = mass.text.toString()

            if (inputText.length > 0 && inputText2.length > 0) {
                sendSMS(inputText, inputText2)
                Toast.makeText(
                    baseContext, """
     $inputText
     $inputText2
     """.trimIndent(), Toast.LENGTH_SHORT
                )
                    .show()
            } else Toast.makeText(baseContext, "전화번호와 메시지를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)
    }


}
