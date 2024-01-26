package com.example.mabibliotheque;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.Manifest;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private List<histoire> histoireList;
    private SearchView Search;
    private histoireAdapter histoireAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        Search = rootView.findViewById(R.id.search);
        Search.clearFocus();
        recyclerView.setHasFixedSize(true);
        bdhelperhistoire dbHelper = new bdhelperhistoire(getActivity());
        histoireList = dbHelper.getAllHistoires();

        histoireAdapter = new histoireAdapter(histoireList, getActivity(), this);
        recyclerView.setAdapter(histoireAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (histoireAdapter != null && histoireList != null) {
                    List<histoire> filteredList = filter(histoireList, query);
                    histoireAdapter.setFilter(filteredList);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (histoireAdapter != null) {
                    List<histoire> filteredList = dbHelper.getFilteredHistoires(newText);
                    histoireAdapter.setFilter(filteredList);
                }
                return false;
            }
        });



        return rootView;
    }



    private List<histoire> filter(List<histoire> histoires, String query) {
        query = query.toLowerCase();
        List<histoire> filteredList = new ArrayList<>();
        for (histoire histoire : histoires) {
            String nom = histoire.getNom();
            if (nom != null && nom.toLowerCase().contains(query)) {
                filteredList.add(histoire);
            }
        }
        return filteredList;
    }

    public void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }
        }
    }

    public void startDownloading(String pdfUrl) {
        histoireAdapter.startDownloading(pdfUrl);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
        }
    }
}
