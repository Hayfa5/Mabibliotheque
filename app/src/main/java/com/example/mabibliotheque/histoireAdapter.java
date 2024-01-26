package com.example.mabibliotheque;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import android.content.Intent;

public class histoireAdapter extends RecyclerView.Adapter<histoireAdapter.ViewHolder> {

    private List<histoire> histoireList;
    private List<histoire> filteredList;
    private Context context;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private Home mFragment;

    public histoireAdapter(List<histoire> histoireList, Context context, Home fragment) {
        this.histoireList = histoireList;
        this.context = context;
        this.mFragment = fragment;
    }
    public void setFilter(List<histoire> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }
    private void downloadPdf(Context context, String pdfUrl) {
        if (pdfUrl != null) {
            Uri uri = Uri.parse(pdfUrl);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    mFragment.requestStoragePermission();
                    return;
                }
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfUrl));
            request.setTitle("Downloading PDF");
            request.setDescription("Please wait while the PDF is being downloaded...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "your_pdf_filename.pdf");

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
        } else {

            Toast.makeText(context, "URL is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void startDownloading(String pdfUrl) {
        if (pdfUrl != null) {

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Uri uri = Uri.parse(pdfUrl);
                downloadPdf(context,pdfUrl);
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }
        } else {
            Toast.makeText(context, "L'URL du PDF est nulle.", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        histoire histoireItem;

        if (filteredList != null && position < filteredList.size()) {
            histoireItem = filteredList.get(position);
        } else {
            histoireItem = histoireList.get(position);
        }
        if (histoireItem != null) {
            if (histoireItem.getNom() != null && holder.textViewNom != null) {
                holder.textViewNom.setText(histoireItem.getNom());
            }

            if (histoireItem.getDescription() != null && holder.textViewDescription != null) {
                holder.textViewDescription.setText(histoireItem.getDescription());
            }

            if (histoireItem.getURL() != null && holder.textViewURL != null) {
                holder.textViewURL.setText(histoireItem.getURL());
            }
        }

        holder.textViewNom.setText(histoireItem.getNom());
        holder.textViewDescription.setText("Voir Résume " );


        Glide.with(holder.itemView.getContext())
                .load(histoireItem.getImage())
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_camera)
                .into(holder.movieImage);

        holder.telechargerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = histoireItem.getURL().toString().trim();
                startDownloading(URL);
            }
        });
        holder.textViewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLIMAGe=histoireItem.getImage();
                String Nom= histoireItem.getNom();
                String description= histoireItem.getDescription();
                String URL = histoireItem.getURL().toString().trim();
                Intent intent = new Intent(context, DescriptionHistoire.class);
                intent.putExtra("Nom", Nom);
                intent.putExtra("description", description);
                intent.putExtra("URLIMAGe", URLIMAGe);
                intent.putExtra("URL", URL);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return histoireList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView textViewNom;
        TextView textViewDescription;
        TextView textViewURL;
        public Button telechargerButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.imageview);
            textViewNom = itemView.findViewById(R.id.textNom);
            textViewDescription = itemView.findViewById(R.id.textDescription);
            textViewURL = itemView.findViewById(R.id.URL);
            telechargerButton = itemView.findViewById(R.id.telecharger);
        }
    }
}
