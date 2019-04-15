package com.charlezz.selectionsample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQ_CODE_PERMISSION = 0;

    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;

    SelectionTracker<Long> selectionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_CODE_PERMISSION );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_CODE_PERMISSION){
            for(int grantResult :grantResults){
                if(grantResult == PackageManager.PERMISSION_DENIED){
                    finish();
                    return;
                }
            }
            setupUI();
        }
    }

    private void setupUI(){
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this);
        recyclerView.setAdapter(photoAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        setupSelectionTracker();
        photoAdapter.setSelectionTracker(selectionTracker);
    }

    private void setupSelectionTracker(){
        selectionTracker = new SelectionTracker.Builder<>(
                "selection_id",
                recyclerView,
                new StableIdKeyProvider(recyclerView),
                new PhotoDetailsLookUp(recyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.<Long>createSelectAnything())
                .build();
    }


}
