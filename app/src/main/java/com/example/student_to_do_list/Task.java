package com.example.student_to_do_list;

public class Task {

    private long id;
    private String title;
    private String description;
    private String deadline;
    private long projectId;
    private boolean expanded;    // State of task expansion (description visible or not)

    public Task(String title, String genre, String deadline, long projectId) {
        this.title = title;
        this.description = genre;
        this.deadline = deadline;
        this.projectId = projectId;
    }

    // Getters and setters
    public long getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getDeadline() { return this.deadline; }
    public long getProjectId() { return this.projectId; }
    public boolean isExpanded() {
        return expanded;
    }

    public void setId(long pId) { this.id = pId;}
    public void setTitle(String pTitle) { this.title = pTitle;}
    public void setDescription(String pDescription) { this.description = pDescription;}
    public void setDeadline(String pDeadline) { this.deadline = pDeadline;}
    public void setProjectId(long pProjectId) { this.projectId = pProjectId;}
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

}
