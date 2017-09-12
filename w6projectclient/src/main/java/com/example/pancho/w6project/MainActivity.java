package com.example.pancho.w6project;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> randomStringList;
    RecyclerView rvRandomStrings;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Client App");
        rvRandomStrings = (RecyclerView) findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();
        rvRandomStrings.setLayoutManager(layoutManager);
        rvRandomStrings.setItemAnimator(itemAnimator);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.pancho.w6project","com.example.pancho.w6project.service.CalService"));
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

    }
    ServiceConnection serviceConnection =  new ServiceConnection() {
        private ICalService iCalService;
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            iCalService = ICalService.Stub.asInterface(service);
            try {
                randomStringList = iCalService.getRandomData();
                listAdapter = new ListAdapter(randomStringList);
                rvRandomStrings.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            unbindService(serviceConnection);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
