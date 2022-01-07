package com.example.easyjob;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class JobRecViewAdapter extends RecyclerView.Adapter<JobRecViewAdapter.ViewHolder> {

    // create list of job items
    private ArrayList<Job> jobs;
    private ArrayList<Job> checkedJobs;
    private Context context;
    private TextToSpeech mTTS;

    public JobRecViewAdapter(Context context, ArrayList<Job> jobs, ArrayList<Job> checkedJobs){
        this.jobs = jobs;
        this.context = context;
        this.checkedJobs = checkedJobs;
    }

    @NonNull
    @NotNull
    @Override
    //create view holder
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JobRecViewAdapter.ViewHolder holder, int position) {
        holder.jobTitle.setText(jobs.get(position).getJobTitle());
        holder.companyName.setText(jobs.get(position).getCompanyName());
        holder.area.setText(jobs.get(position).getArea());
        Picasso.get().load(jobs.get(position).getLogo()).into(holder.logo);

        // initialize text to speech engine
        mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

            // check if language is supported
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                }
            }
        });


        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (holder.parent.isChecked() == false){
                    holder.parent.setChecked(true);
                    checkedJobs.add(new Job(jobs.get(position).getJobTitle(), jobs.get(position).getCompanyName(), jobs.get(position).getArea(), jobs.get(position).getLogo()));
                }
                else {
                    holder.parent.setChecked(false);
                }

                return true;
            }
        });



        // set onClick listeners for card views
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String jobTitleTxt = holder.jobTitle.getText().toString();
                String companyNameTxt = holder.companyName.getText().toString();
                String areaTxt = holder.area.getText().toString();

                Voice voice = mTTS.getDefaultVoice();
                mTTS.setVoice(voice);

                mTTS.speak(jobTitleTxt, TextToSpeech.QUEUE_FLUSH, null);
                mTTS.speak(companyNameTxt, TextToSpeech.QUEUE_ADD, null);
                mTTS.speak(areaTxt, TextToSpeech.QUEUE_ADD, null);



            }
        });




    }




    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void setJobs(ArrayList<Job> jobs){
        this.jobs = jobs;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        private TextView jobTitle, area, companyName;
        private ImageView logo;
        private MaterialCardView parent;


        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            // instantiate variables
            parent = itemView.findViewById(R.id.parent);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            area = itemView.findViewById(R.id.area);
            companyName = itemView.findViewById(R.id.companyName);
            logo = itemView.findViewById(R.id.logo);

        }
    }

}
