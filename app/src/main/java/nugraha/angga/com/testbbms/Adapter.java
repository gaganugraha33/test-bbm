package nugraha.angga.com.testbbms;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

import nugraha.angga.com.testbbms.model.DummyData;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterHolder>{
    private Context mContext;
    private ArrayList<DummyData> dataList;
    private boolean statusDownload = false;

    public Adapter(Context mContext, ArrayList<DummyData> dataList){
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_layout, parent, false);
        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterHolder adapterHolder, final int position) {
        //set data to UI example
        adapterHolder.tvTitle.setText(dataList.get(position).getTitle());
        adapterHolder.tvDesc.setText(dataList.get(position).getDesc());
        adapterHolder.tvDate.setText(dataList.get(position).getDate());
        adapterHolder.ratingBar.setRating(dataList.get(position).getRating());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image);
        requestOptions.error(R.drawable.ic_image);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(dataList.get(position).getUrlImage())
                .into(adapterHolder.ivImage);

        adapterHolder.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterHolder.ivDownload.setVisibility(View.INVISIBLE);
                adapterHolder.ivDownloading.setVisibility(View.VISIBLE);
                new DownloadDataBackgroundTask(dataList.get(position).getUrlImage(), adapterHolder.ivDownload, adapterHolder.ivDownloading).execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        //for set size data show to UI
        return dataList.size();
    }


    public class AdapterHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private ImageView ivDownload;
        private ImageView ivDownloading;
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvDesc;
        private RatingBar ratingBar;

        public AdapterHolder(View view) {
            super(view);
            ivImage = (ImageView)view.findViewById(R.id.ivPhoto);
            ivDownload = (ImageView)view.findViewById(R.id.ivDownload);
            ivDownloading = (ImageView)view.findViewById(R.id.ivDownloading);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            ratingBar = (RatingBar) view.findViewById(R.id.rateBar);
        }
    }

    private class DownloadDataBackgroundTask extends AsyncTask<String, Void, String> {
        private String urlImage;
        private ImageView ivDownloading;
        private ImageView ivDownload;

        public DownloadDataBackgroundTask(String urlImage, ImageView ivDownload,ImageView ivDownloading){
            this.urlImage  = urlImage;
            this.ivDownload = ivDownload;
            this.ivDownloading = ivDownloading;
        }

        @Override
        protected String doInBackground(String... url) {
            boolean flag = true;
            boolean downloading =true;
            try{
                DownloadManager mManager = (DownloadManager)mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request mRqRequest = new DownloadManager.Request(
                        Uri.parse(urlImage));
                mRqRequest.setTitle("Data Download");
                mRqRequest.setDescription("Android Data download using DownloadManager.");
                mRqRequest.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS,"datadummy.jpg");
                long idDownLoad = mManager.enqueue(mRqRequest);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(idDownLoad);
                Cursor c = mManager.query(query);
                if(query!=null) {
                    query.setFilterByStatus(DownloadManager.STATUS_FAILED|DownloadManager.STATUS_PAUSED|DownloadManager.STATUS_SUCCESSFUL|DownloadManager.STATUS_RUNNING|DownloadManager.STATUS_PENDING);
                } else {
                    return String.valueOf(flag);
                }

                while (downloading) {
                    c = mManager.query(query);
                    if(c.moveToFirst()) {
                        int status =c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        Log.i ("FLAG","Downloading "+status);
                        if (status==DownloadManager.STATUS_SUCCESSFUL) {
                            flag=false;
                            statusDownload = true;
                            break;
                        }

                        if (status==DownloadManager.STATUS_FAILED) {
                            Log.i ("FLAG","failed");
                            flag=false;
                            statusDownload = false;
                            break;
                        }
                    }
                }

                return String.valueOf(flag);
            }catch (Exception e) {
                flag = false;
                Log.d("cekk boosss", "doInBackground erro ngga "+e.getMessage());
                return String.valueOf(flag);
            }
        }


        @Override
        protected void onPostExecute(String result) {

            if (statusDownload){
                Toast.makeText(mContext, R.string.download_success, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(mContext, R.string.download_failed, Toast.LENGTH_SHORT).show();
            }
            ivDownloading.setVisibility(View.INVISIBLE);
            ivDownload.setVisibility(View.VISIBLE);
            notifyDataSetChanged();

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


}
