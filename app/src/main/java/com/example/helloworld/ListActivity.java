package com.example.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListActivity extends AppCompatActivity {

    DataSet dataSet = new DataSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //セルの管理（アダプター）を生成
        MyAdapter adapter = new MyAdapter(dataSet);

        //リストを生成
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //ドラッグ＆ドロップ操作
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView view, @NonNull RecyclerView.ViewHolder holder, @NonNull RecyclerView.ViewHolder target) {
                        final int fromPos = holder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        dataSet.move(fromPos, toPos);
                        adapter.notifyItemMoved(fromPos, toPos);
                        return true; // true if moved, false otherwise
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int pos = viewHolder.getLayoutPosition();
                        dataSet.remove(pos);
                        adapter.notifyItemRemoved(pos);
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * データの管理
     */
    static class DataSet {
        public final List<String> list = new ArrayList<>();

        public DataSet() {
            for (int i = 0; i < 20; ++i) {
                list.add(String.format(Locale.getDefault(), "Data_0%d", i));
            }
        }

        public void move(int fromPos, int toPos) {
            String tmp = list.get(fromPos);
            list.remove(fromPos);
            list.add(toPos, tmp);
        }

        public void remove(int pos) {
            list.remove(pos);
        }
    }

    /**
     * セルの管理（アダプター）
     */
    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final DataSet dataSetRef;

        public MyAdapter(DataSet dataSet) {
            dataSetRef = dataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.list_cell, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.textView.setText(dataSetRef.list.get(position));
            viewHolder.imageView.setImageResource(R.drawable.boy);
        }

        @Override
        public int getItemCount() {
            return dataSetRef.list.size();
        }

        /**
         * セル
         */
        static class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView textView;
            public final ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                textView = view.findViewById(R.id.text_view);
                imageView = view.findViewById(R.id.image_view);
            }
        }
    }
}
