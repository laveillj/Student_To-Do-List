// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list;

// Classe définissant les objets Projet avec différents attributs
// Elle possède des méthodes permettant d'obtenir ces différentes informations
// Nous pouvons obtenir l'id, le nom, description, deadline... ou les modififer (getters et setters)
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
