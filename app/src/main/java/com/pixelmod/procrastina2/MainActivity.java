package com.pixelmod.procrastina2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvBier;
    private JSONArray tempBiers;
    private ArrayList<String> tempBiers2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tempBiers2.add("kro");
        tempBiers2.add("heineken");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvBier = findViewById(R.id.rv_bier);
        rvBier.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvBier.setAdapter(new BiersAdapter(tempBiers2));
    }

    public JSONArray getBiersFromFile(){
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8")); // construction du tableau
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {

        private List<String> biers;

        public BiersAdapter(List<String> biers) {
            this.biers = biers;
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View v = layoutInflater.inflate(R.layout.rv_biere_element, rvBier, false);
            return new BierHolder(v);
        }

        @Override
        public void onBindViewHolder(BierHolder holder, int position) {
            try {
                holder.name.setText(biers.get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return biers.size();
        }

        class BierHolder extends RecyclerView.ViewHolder {

            public TextView name;

            public BierHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.rv_biere_element_name);
            }
        }
    }
}
