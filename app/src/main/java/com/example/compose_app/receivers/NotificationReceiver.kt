package com.example.compose_app.receivers
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.compose_app.MainActivity
import com.example.compose_app.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        private const val REQUEST_TIMER1 = 1
        private fun getIntent(context: Context, requestCode: Int): PendingIntent? {
            val intent = Intent(context, NotificationReceiver::class.java)

            return PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        }

        fun startAlarm(context: Context, day: Int, month: Int, year: Int, hour: Int, minute: Int) {

            val pendingIntent = getIntent(context, REQUEST_TIMER1)
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)

            now = now.withHour(hour)
                .withMinute(minute)
                .withDayOfMonth(day)
                .withMonth(month)
                .withYear(year)

            val utc = now.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime()
            val triggerAtMillis = utc.atZone(ZoneOffset.UTC)!!.toInstant()!!.toEpochMilli()

            alarm.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        }
    }


    override fun onReceive(context: Context, intent: Intent) {
        showNotification(
            context = context,
            "your notification is here",
            "hope you are happy",
            123
        )
    }


    private fun showNotification(
        context: Context,
        title: String?,
        message: String?,
        reqCode: Int,
    ) {

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT
            )
        }

        val CHANNEL_ID = "main_chanel" // The id of the channel.
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val name: CharSequence = "Main Notifications Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        notificationManager.createNotificationChannel(mChannel)
        notificationManager.notify(
            reqCode,
            notificationBuilder.build()
        )
//        Log.d("mohamed", "showNotification: $reqCode")
    }
}