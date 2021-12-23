package com.example.student_to_do_list;

//Classe ayant des méthodes permettant d'obtenir divers informations à partir l'ID de l'item (projet), nous pouvons obtenir le nom, description, deadline ...
public class Project {

    private long id;
    private String title;
    private String description;
    private String deadline;
    private String[] collaborators = {};
    private Task[] listOfTasks = {};

    public Project(String title, String desc, String deadline) {
        this.title = title;
        this.description = desc;
        this.deadline = deadline;
    }

    // Getters and setters
    public long getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getDeadline() { return this.deadline; }
    public String[] getCollaborators() { return this.collaborators; }
    public Task[] getListOfTasks() { return this.listOfTasks; }

    public void setId(long pId) { this.id = pId;}
    public void setTitle(String pTitle) { this.title = pTitle;}
    public void setDescription(String pDescription) { this.description = pDescription;}
    public void setDeadline(String pDeadline) { this.deadline = pDeadline;}
    public void setCollaborators(String[] pCollaborators) { this.collaborators = pCollaborators;}
    public void setListOfTasks(Task[] pListOfTasks) { this.listOfTasks = pListOfTasks;}

}
