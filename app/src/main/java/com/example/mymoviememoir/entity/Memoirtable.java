package com.example.mymoviememoir.entity;

import java.util.Date;

public class Memoirtable {

    private Integer memoirid;

    private String moviename;

    private Date moviereleasedate;

    private Date watchdate;

    private Date watchtime;

    private String comment;

    private Integer ratingscore;

    private Cinematable cinemaid;
    private Usertable userid;

    public Memoirtable() {
    }

    public Integer getMemoirid() {
        return memoirid;
    }

    public void setMemoirid(Integer memoirid) {
        this.memoirid = memoirid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public Date getMoviereleasedate() {
        return moviereleasedate;
    }

    public void setMoviereleasedate(Date moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }

    public Date getWatchdate() {
        return watchdate;
    }

    public void setWatchdate(Date watchdate) {
        this.watchdate = watchdate;
    }

    public Date getWatchtime() {
        return watchtime;
    }

    public void setWatchtime(Date watchtime) {
        this.watchtime = watchtime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRatingscore() {
        return ratingscore;
    }

    public void setRatingscore(Integer ratingscore) {
        this.ratingscore = ratingscore;
    }

    public Cinematable getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Cinematable cinemaid) {
        this.cinemaid = cinemaid;
    }

    public Usertable getUserid() {
        return userid;
    }

    public void setUserid(Usertable userid) {
        this.userid = userid;
    }

    public Memoirtable(Integer memoirid, String moviename, Date moviereleasedate, Date watchdate, Date watchtime, String comment, Integer ratingscore, Cinematable cinemaid, Usertable userid) {
        this.memoirid = memoirid;
        this.moviename = moviename;
        this.moviereleasedate = moviereleasedate;
        this.watchdate = watchdate;
        this.watchtime = watchtime;
        this.comment = comment;
        this.ratingscore = ratingscore;
        this.cinemaid = cinemaid;
        this.userid = userid;
    }
}
