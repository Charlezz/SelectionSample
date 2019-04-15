package com.charlezz.selectionsample;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.selection.SelectionTracker;

public class PhotoAdapter extends RecyclerView.Adapter<LookUpViewHolder> {

    private List<Photo> photoList;
    private Context context;

    @Nullable
    private SelectionTracker<Long> selectionTracker;

    protected PhotoAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
        photoList = getPhotoList(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LookUpViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_photo,viewGroup,false);
        return new LookUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LookUpViewHolder lookUpViewHolder, int position) {
        Photo photo = photoList.get(position);
        lookUpViewHolder.setPhoto(photo);
        lookUpViewHolder.setSelectionTracker(selectionTracker);

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private List<Photo> getPhotoList(Context context){
        String[] projection = new String[]{MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        ArrayList<Photo> photoList = new ArrayList<>();
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(projection[0]));
            String path = cursor.getString(cursor.getColumnIndex(projection[1]));;
            Photo photo = new Photo(name, path);
            photoList.add(photo);
        }
        cursor.close();
        return photoList;
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }
}
