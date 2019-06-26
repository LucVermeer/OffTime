/*
*   TimerService
*   Whenever a Siesta is started a Service is also started to notify users of the time
*   they have left to stay productive. For future work this Service could be used to send
*   warning to users if they use their phone anyway.
* */
package vermeer.luc.siesta;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import vermeer.luc.siesta.MainActivity;
import vermeer.luc.siesta.R;

public class TimerService extends Service {
    private CountDownTimer timer;
    private Notification notification;
    private PendingIntent pendingIntent;

    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // When the Service is started first retrieve the millis from the intent.
        int millis = intent.getIntExtra("int_millis", 0);

        // Start the notification channel and create a pending intent for the notification.
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        // Build the first notification, the user probably won't see this as it is overwritten
        // very fast by the timer.
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Siesta")
                .setContentText("Timer is starting...")
                .setSmallIcon(R.drawable.ic_action_timer_notification)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        startTimer(millis);
        return START_NOT_STICKY;
    }

    private void startTimer(int millis) {
        // Start a second timer (in addition to the one on the TimerFragment) to keep track
        // of time in the notification bar so you can see the time from the lock screen.
        timer = new CountDownTimer(millis, 1000) {
            public void onTick(long millisUntilFinished) {
                int secondsUntilFinished = (int) millisUntilFinished / 1000;
                int minutesUntilFinished = secondsUntilFinished / 60;
                int secondsWithoutMinute = secondsUntilFinished % 60;
                notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setContentTitle("Siesta")
                        .setContentText("Time left: " +
                                String.format("%02d:%02d", minutesUntilFinished, secondsWithoutMinute)
                        + ". Stay focused!")
                        .setSmallIcon(R.drawable.ic_action_timer_notification)
                        .setContentIntent(pendingIntent)
                        .build();
                startForeground(1, notification);

            }
            public void onFinish() {
            }
        };
        timer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        // Create notification channel if necessary.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}