/*
 *   NotificationHandler
 *   This class makes the notifications to remind when to take a siesta.
 * */


package vermeer.luc.siesta;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class NotificationHandler extends Worker {
    private static DbHelper db;

    public NotificationHandler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = DbHelper.getInstance(getApplicationContext());
    }


    public static void scheduleReminder(DbHelper db) {
        WorkManager instance = WorkManager.getInstance();
        int productivity = db.getProductivity();
        // Check if productivity is not 0, if it indeed isn't we start the notifier by enqueueing
        // a periodicWorkRequest.
        if (productivity != 0) {
            int interval = repeatInterval(productivity);
            PeriodicWorkRequest periodicWorkRequest =
                    new PeriodicWorkRequest.Builder(NotificationHandler.class,
                            interval, TimeUnit.MINUTES).build();

            instance.enqueue(periodicWorkRequest);
        }
    }

    private static int repeatInterval(int productivity) {
        // Converts productivity indicator to repeat interval.
        int interval;
        switch (productivity) {
            case 1:
                interval = 30;
                break;
            case 2:
                interval = 25;
                break;
            case 3:
                interval = 20;
                break;
            default:
                interval = 15;
                break;
        };
        return interval;
    }


    public static void cancelReminder(String tag) {
        WorkManager instance = WorkManager.getInstance();
        instance.cancelAllWorkByTag(tag);
    }


    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        String title = getInputData().getString(Constants.EXTRA_TITLE);
        String text = getInputData().getString(Constants.EXTRA_TEXT);
        int id = (int) getInputData().getLong(Constants.EXTRA_ID, 0);
        sendNotification(title, text, id);
        return Result.success();
    }


    private void sendNotification(String title, String text, int id) {
        // Here the pending intent for the notification is made.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.EXTRA_ID, id);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        // Notification manager is initialized
        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Build the notification
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle("Siesta")
                .setContentText("Do you want to plan a siesta?")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_timer)
                .setAutoCancel(false);
        Objects.requireNonNull(notificationManager).notify(id, notification.build());
    }
}