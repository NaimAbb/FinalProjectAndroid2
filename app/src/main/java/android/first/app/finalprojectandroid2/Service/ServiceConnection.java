package android.first.app.finalprojectandroid2.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.first.app.finalprojectandroid2.Receiver.MyReceiverConnection;
import android.os.IBinder;

public class ServiceConnection extends Service {
    public ServiceConnection() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyReceiverConnection myReceiver = new MyReceiverConnection();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver,filter);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
