package android.first.app.finalprojectandroid2.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.first.app.finalprojectandroid2.Service.NotificationService;
import android.first.app.finalprojectandroid2.Service.ReadNewsService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyReceiverConnection extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo info =  manager.getActiveNetworkInfo();
       if (info != null && info.isConnected()){
           /*WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
           WifiInfo wifiInfo = wifiManager.getConnectionInfo();*/
           Intent intent2 = new Intent(context, ReadNewsService.class);
           context.startService(intent2);
           Intent intent3 = new Intent(context, NotificationService.class);
           context.startService(intent3);


       }else{
           Intent intent2 = new Intent(context, ReadNewsService.class);
           context.stopService(intent2);
           Intent intent3 = new Intent(context, NotificationService.class);
           context.stopService(intent3);
       }

    }
}
