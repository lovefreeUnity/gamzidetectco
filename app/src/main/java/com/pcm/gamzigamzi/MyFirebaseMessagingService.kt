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

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        val data = remoteMessage.data
        if (title != null && body != null) {
            sendNotification(title, body, data)
            //전화 번호
            val inputText = MyApplication.prefs.getString("num","")
            //이부분이 내용
            val address = MyApplication.prefs.getString("address","")
            val inputText2 = "감지감지\n지인분의 $address 에 일산화탄소 수치가 높습니다."

            if (inputText.length > 0 && inputText2.length > 0) {
                sendSMS(inputText, inputText2)
            }
        } else {
            Log.e(TAG, "onMessageReceived: title: $title, body: $body, data: $data")
        }
    }

    private fun sendNotification(title: String?, body: String?, data: Map<String, String>){
        val channelId = "fcm_default_channel" //getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.mainicon)
//            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background))
            .setContentTitle(title)
            .setContentText(body)
            .setSound(defaultSoundUri)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // android Oreo 알림 채널이 필요합니다
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                " FCM",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
    private fun sendSMS(phoneNumber: String, message: String) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)
    }
}