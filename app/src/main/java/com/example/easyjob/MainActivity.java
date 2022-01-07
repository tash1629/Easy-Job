package com.example.easyjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
//import org.w3c.dom.Element;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Job> jobs = new ArrayList<>();
    private RecyclerView recView;
    private JobRecViewAdapter adapter;
    private ProgressBar progressBar;
    private String searchTerms;
    private ArrayList<Job> checkedJobs;
    private ArrayList<String> checkedJobsLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get search terms from SearchActivity
        Intent intent = getIntent();
        searchTerms = intent.getStringExtra(SearchActivity.EXTRA_TEXT);

        progressBar = findViewById(R.id.progressBar);
        recView = findViewById(R.id.recView);

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new JobRecViewAdapter(this, jobs, checkedJobs);
        recView.setAdapter(adapter);



        Content content = new Content();
        content.execute();
    }

    // parse data from websites
    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                String url;// = "https://www.linkedin.com/jobs/jobs-in-san-pablo-ca?trk=homepage-basic_intent-module-jobs&position=1&pageNum=0";
                //url = "https://www.linkedin.com/jobs/search?keywords=tech&location=new%20york&position=1&pageNum=0";
                //Log.d("search term", searchTerms);
                url = "https://www.linkedin.com/jobs/search?keywords="+searchTerms;

                Document doc = (Document) Jsoup.connect(url).get();
                Elements data = doc.select("ul.jobs-search__results-list");//"div.base-card base-card--link base-search-card base-search-card--link job-search-card job-search-card--active");//doc.select("a.base-card__full-link");
                Elements imgUrl = doc.select("img.artdeco-entity-image.artdeco-entity-image--square-4.lazy-loaded");//doc.select("div.search-entity-media");

                Elements li = data.select("li");

                for (Element item : li){
                    String jobTitle = item.getElementsByClass("base-search-card__title").text();
                    String area = item.getElementsByClass("job-search-card__location").text();
                    String compName = item.getElementsByClass("base-search-card__subtitle").text();
                    String imageUrl = item.select("img.artdeco-entity-image").attr("data-delayed-url");
                    String jobLink = item.select("a.base-card__full-link").attr("href");

                    checkedJobsLink.add(jobLink);
                    //jobs.add(new Job(jobTitle, area, compName, "https://media-exp1.licdn.com/dms/image/C4D0BAQFsTOeDD1vYRw/company-logo_100_100/0/1530537679784?e=1636588800&v=beta&t=JRnT3wVsdSE67SlDrHjt7flSD7klhZZ41HNmLB2dRvk"));
                    jobs.add(new Job(jobTitle, area, compName, imageUrl));
                }


                int size = data.size();




                /*for (int i=0; i<5; i++){

                    String title = data.select("div.base-search-card__info")
                            .select("h3")
                            .eq(i)
                            .text();


                    String compName = data.select("div.base-search-card__info")
                            .select("h4")
                            .eq(i)
                            .text();

                    String area = data.select("div.base-search-card__metadata")
                            .select("span.job-search-card__location")
                            .eq(i)
                            .text();

                    String jobLink = data.select("a.base-card__full-link")
                            //.select("a.base-card__full-link")
                            .eq(i)
                            .attr("href");



                    String imageUrl = data.select("img.artdeco-entity-image")//.artdeco-entity-image.artdeco-entity-image--square-4.lazy-loaded")
                            //.select("alt")//artdeco-entity-image artdeco-entity-image--square-4 lazy-loaded")
                            .eq(i)
                            .attr("data-delayed-url");


                    //jobs.add(new Job(title, "hi", "hi", "https://static.toiimg.com/photo/msid-80725675/80725675.jpg"));
                    //jobs.add(new Job(title, area, compName, "https://media-exp1.licdn.com/dms/image/C4D0BAQFsTOeDD1vYRw/company-logo_100_100/0/1530537679784?e=1636588800&v=beta&t=JRnT3wVsdSE67SlDrHjt7flSD7klhZZ41HNmLB2dRvk"));//imageUrl));
                    checkedJobsLink.add(jobLink);
                    jobs.add(new Job(title, area, compName, imageUrl));
                    Log.d("items", "title: " + title);
                    //Log.d("items", "image: " + imageUrl + " . title: " + title + " . company name: " + compName + " . area: " + area);
                }**/
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}