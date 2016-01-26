package eu.ict.traveltimeregistration;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BluetoothReceiver extends BroadcastReceiver {

    private static final String TAG= BluetoothReceiver.class.getName();

    public BluetoothReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED:
                Bundle intentExtras= intent.getExtras();
                int currentState = intentExtras.getInt(BluetoothAdapter.EXTRA_CONNECTION_STATE);
                int previousState = intentExtras.getInt(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE);
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(currentState == BluetoothAdapter.STATE_CONNECTED && previousState == BluetoothAdapter.STATE_DISCONNECTED) {
                    CsvFileWriter.writeConnectEvent(bluetoothDevice.getName(),System.currentTimeMillis());
                }
                else if(currentState ==  BluetoothAdapter.STATE_DISCONNECTED && previousState == BluetoothAdapter.STATE_CONNECTED) {
                    Log.d(TAG, "Disconnected from " + bluetoothDevice.getName());
                    CsvFileWriter.writeDisconnectEvent(bluetoothDevice.getName(), System.currentTimeMillis());
                }

                break;
            default:
                Log.e(TAG, "Hmm, that's  strange, an unsupported intent");
                break;
        }
    }
}
