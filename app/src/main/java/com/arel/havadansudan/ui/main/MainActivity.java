package com.arel.havadansudan.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arel.havadansudan.R;
import com.arel.havadansudan.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private AppCompatEditText edtQuery;
    private EditText secondEdT;
    private TextView tvResult, tvUrl;
    private Button btnUygula;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        edtQuery = findViewById(R.id.ac_main_ac_ed_first);
        secondEdT = findViewById(R.id.ac_main_ed_second);
        tvResult = findViewById(R.id.ac_main_tv_title);
        tvUrl = findViewById(R.id.ac_main_tv_url);
        btnUygula = findViewById(R.id.ac_main_btn_uygula);
        recyclerView = findViewById(R.id.ac_main_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<String> input = new ArrayList<>();
        for (int i = 1; i <= 122; i++) {
            input.add("Test " + i);
        }

        mAdapter = new MyAdapter(input);
        recyclerView.setAdapter(mAdapter);

        initButtonWorks();
    }

    private void initButtonWorks() {

        btnUygula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GithubSearchQuery();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void GithubSearchQuery() {
        String githubQuery = edtQuery.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        tvUrl.setText("URL: " + githubSearchUrl.toString());

        String githubSearchResult = null;

        try {
            githubSearchResult = NetworkUtils.getResponseFromHttpUrl(githubSearchUrl);
            tvResult.setText(githubSearchResult);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Hata! \n\n" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> values;

        class ViewHolder extends RecyclerView.ViewHolder {
            // her veri öğesi bu durumda sadece bir dizedir
            ImageView ivPicture;
            TextView txtHeader;
            TextView txtFooter;
            View layout;

            ViewHolder(View v) {
                super(v);
                layout = v;
                ivPicture = v.findViewById(R.id.row_icon);
                txtHeader = v.findViewById(R.id.firstLine);
                txtFooter = v.findViewById(R.id.secondLine);
            }
        }

        void add(int position, String item) {
            values.add(position, item);
            notifyItemInserted(position);
        }

        void remove(int position) {
            values.remove(position);
            notifyItemRemoved(position);
        }

        // Uygun bir kurucu sağlayın (veri kümesinin türüne bağlıdır)
        MyAdapter(List<String> myDataset) {
            values = myDataset;
        }

        // Yeni görünümler oluşturma (düzen yöneticisi tarafından çağrılır)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // yeni bir görünüm oluştur
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.row_layout, parent, false);
            // görünümün boyutunu, kenar boşluklarını, dolguları ve düzen parametrelerini ayarlama
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Bir görünümün içeriğini değiştirme (düzen yöneticisi tarafından çağrılır)
        @Override
        public void onBindViewHolder(ViewHolder bagla, final int position) {

            // - bu konumdaki veri kümenizden öğe alın
            // - görünümün içeriğini bu öğeyle değiştir
            final String name = values.get(position);
            //bagla.ivPicture.setImageDrawable(getDrawable(R.drawable.ic_card_visit));
            bagla.txtHeader.setText(name);

            bagla.txtFooter.setText("Footer: " + name);

            bagla.ivPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(position);
                }
            });

            bagla.txtHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add(position, "NEW");
                }
            });
        }

        // Veri kümenizin boyutunu döndürme (düzen yöneticisi tarafından çağrılır)
        @Override
        public int getItemCount() {
            return values.size();
        }

    }
}
