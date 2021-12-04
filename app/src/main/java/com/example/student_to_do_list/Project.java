package com.example.student_to_do_list;

public class Project {

    private String title;
    private String description;
    private String deadline;
    private String[] collaborators = {};
    private Task[] listOfTasks = {};

    public Project(String title, String genre, String year) {
        this.title = title;
        this.description = genre;
        this.deadline = year;
    }

    // Getters and setters
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getDeadline() { return this.deadline; }
    public String[] getCollaborators() { return this.collaborators; }
    public Task[] getListOfTasks() { return this.listOfTasks; }

    public void setTitle(String pTitle) { this.title = pTitle;}
    public void setDescription(String pDescription) { this.description = pDescription;}
    public void setDeadline(String pDeadline) { this.deadline = pDeadline;}
    public void setDescription(String[] pCollaborators) { this.collaborators = pCollaborators;}
    public void setDescription(Task[] pListOfTasks) { this.listOfTasks = pListOfTasks;}

}
