package com.example.carcarcarcar;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private ArrayList<Data> listData = new ArrayList<>();
    private ArrayList<Data> listXml = new ArrayList<>();
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    RecyclerAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public RecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ItemViewHolder(view, parent);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position), listXml.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    public void addXml(Data data) { // 해당 이미지와 동일한 xml을 넣어줌.
        listXml.add(data);
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(RecyclerAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private Data data;
        private Data xml;
        ViewGroup parent;



        ItemViewHolder(View itemView, ViewGroup parent) {
            super(itemView);
            this.parent = parent;
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }

        void onBind(Data data, Data xml) {
            this.data = data;
            this.xml = xml;

            imageView.setImageResource(data.getResId());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if ( mSelectedItems.get(pos, false) ){
//                        mSelectedItems.put(pos, false);
//                        v.setBackgroundColor(Color.BLUE);
                    } else {
                        mSelectedItems.put(pos, true);
                        v.setBackgroundColor(Color.BLUE);
                    }
                    if(pos!=RecyclerView.NO_POSITION){
                        if(listener!=null){
                            listener.onItemClick(v,pos);
                        }
                    }


                }
            });

        }

    }
}