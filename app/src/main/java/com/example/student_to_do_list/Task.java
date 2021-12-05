package com.example.student_to_do_list;

public class Task {

    private int id;
    private String title;
    private String description;
    private String deadline;
    private boolean expanded;    // State of task expansion (description visible or not)

    public Task(String title, String genre, String deadline) {
        this.title = title;
        this.description = genre;
        this.deadline = deadline;
    }

    // Getters and setters
    public int getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getDeadline() { return this.deadline; }
    public boolean isExpanded() {
        return expanded;
    }

    public void setId(int pId) { this.id = pId;}
    public void setTitle(String pTitle) { this.title = pTitle;}
    public void setDescription(String pDescription) { this.description = pDescription;}
    public void setDeadline(String pDeadline) { this.deadline = pDeadline;}
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

}
