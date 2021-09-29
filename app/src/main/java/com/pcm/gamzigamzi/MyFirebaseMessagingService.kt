package com.pcm.gamzigamzi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val TAG = "FCM"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        //token저장
        MyApplication.prefs.setString("token",token)

    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(TAG, "From: " + p0!!.from)

        // Notification 메시지를 수신할 경우는
        // remoteMessage.notification?.body!! 여기에 내용이 저장되어있다.
        // Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)

        if(p0.data.isNotEmpty()){
//            val number = MyApplication.prefs.getString("num", "")
//            if(p0.data["body"].toString().equals("감지감지 심각")&&number != null){
//                val address = MyApplication.prefs.getString("address","")
//                val messages = "감지감지\n지인분의 $address 에 일산화탄소 수치가 높습니다."
//                if (number.length > 0 && messages.length > 0) {
//                    sendSMS(number, messages)
//                }
//            }
            sendNotification(p0)
        }
        else {
            Log.i("수신에러: ", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.i("data값: ", p0.data.toString())
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시되도록 함


        // 알림 채널 이름
        val channelId = "fcm_default_channel"

        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보와 작업을 지정한다.
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.mainicon) // 아이콘 설정
            .setContentTitle(remoteMessage.data["body"].toString()) // 제목
            .setContentText(remoteMessage.data["title"].toString()) // 메시지 내용
            .setAutoCancel(true)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI) // 알림 소리
//            .setContentIntent(pendingIntent) // 알림 실행 시 Intent

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요하다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
    private fun sendSMS(phoneNumber: String, message: String) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)
    }
}