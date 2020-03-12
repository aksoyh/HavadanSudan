package com.arel.havadansudan.ui.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    EditText edtSearchBox;
    ImageView ivSearch;
    TextView tvUrl, tvResultJson, tvErrorMesage;
    ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        edtSearchBox = findViewById(R.id.ac_main_edt_search_box);
        ivSearch = findViewById(R.id.ac_main_iv_search);
        tvUrl = findViewById(R.id.ac_main_tv_url);
        tvResultJson = findViewById(R.id.ac_main_tv_search_result_json);
        tvErrorMesage = findViewById(R.id.ac_main_error_message);
        pbLoading = findViewById(R.id.ac_main_progress_bar);

        initButtonWorks();
    }

    private void initButtonWorks() {
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (!edtSearchBox.getText().toString().equals("")) {
                    tvUrl.setText(edtSearchBox.getText());
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen değer giriniz.", Toast.LENGTH_LONG).show();
                }
                 */
                getGithubSearchQuery();
            }
        });
    }

    private void getGithubSearchQuery() {
        String githubQuery = edtSearchBox.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        tvUrl.setText(githubSearchUrl.toString());
        new GithubQueryTask().execute(githubSearchUrl);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return githubSearchResults;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String githubSearchResult) {
            super.onPostExecute(githubSearchResult);
            pbLoading.setVisibility(View.GONE);

            if (githubSearchResult != null && !githubSearchResult.equals("")) {
                showJsonDataView();
                tvResultJson.setText(githubSearchResult);
            } else {
                showErrorMessage();
            }
        }
    }

    private void showJsonDataView() {
        tvErrorMesage.setVisibility(View.INVISIBLE);
        tvResultJson.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        tvErrorMesage.setVisibility(View.VISIBLE);
        tvResultJson.setVisibility(View.INVISIBLE);
    }


}
