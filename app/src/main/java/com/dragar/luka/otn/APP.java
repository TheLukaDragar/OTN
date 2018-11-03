package com.dragar.luka.otn;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;



public class APP extends Application {
    public  static  final  String CHANNEL_ID3 = "exampleServiceChannel";
    public  static  final  String CHANNEL_ID4 = "konec";
    public static final String CHANNEL_ID8 = "finalno";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        createNotificationChannel2();
        createNotificationChannel8();
    }

    private void createNotificationChannel2() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {

            CharSequence name = "Personal Notifications2";
            String description = "Include all the personal notifications2";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel serviceChannel2 = new NotificationChannel(CHANNEL_ID4,name,importance);

            serviceChannel2.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(serviceChannel2);


        }


    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {

            CharSequence name = "Personal Notifications";
            String description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID3,name,importance);

            serviceChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(serviceChannel);


        }

    }

    private void createNotificationChannel8() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {

            CharSequence name = "Personal Notifications10";
            String description = "Include all the personal notifications10";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel serviceChannel8 = new NotificationChannel(CHANNEL_ID8,name,importance);

            serviceChannel8.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(serviceChannel8);


        }


    }

}

