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
        int millis = intent.getIntExtra("int_millis", 0);
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Siesta")
                .setContentText("Timer is starting...")
                .setSmallIcon(R.drawable.ic_action_timer_notification)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        startTimer(millis);

        //do heavy work on a background thread

        //stopSelf();

        return START_NOT_STICKY;
    }

    private void startTimer(int millis) {
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