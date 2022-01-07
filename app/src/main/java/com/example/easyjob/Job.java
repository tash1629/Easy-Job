package com.example.easyjob;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Job {

    private String jobTitle, area, companyName, logo;

    public Job(String jobTitle, String area, String companyName, String logo){
        this.jobTitle = jobTitle;
        this.area = area;
        this.companyName = companyName;
        this.logo = logo;
    }

    public void setJobTitle(String jobTitle){ this.jobTitle = jobTitle ; }

    public void setArea(String area) { this.area = area; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public void setLogo(String logo) { this.logo = logo; }

    public String getArea() { return area; }

    public String getCompanyName() { return companyName; }

    public String getJobTitle() { return jobTitle; }

    public String getLogo() { return logo; }


    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "Job{" +
                "jobTitle='" + jobTitle + '\'' +
                ", area='" + area + '\'' +
                ", companyName='" + companyName + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
