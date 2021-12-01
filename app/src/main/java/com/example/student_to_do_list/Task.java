package com.example.student_to_do_list;

public class Task {

    private String title;
    private String description;
    private int date;
    public boolean expanded;    // State of the item

    public Task(String title, String genre, int year) {
        this.title = title;
        this.description = genre;
        this.date = year;
    }

    // Getters and setters
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public int getDate() { return this.date; }

    public void setTitle(String pTitle) { this.title = pTitle;}
    public void setDescription(String pDescription) { this.description = pDescription;}
    public void setDate(int pDate) { this.date = pDate;}

}
