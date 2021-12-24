// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list;

// Classe définissant les objets Task avec différents attributs
// Elle possède des méthodes permettant d'obtenir ces différentes informations
// Nous pouvons obtenir l'id, le nom, description, deadline... ou les modififer (getters et setters)
public class Task {

    private long id;
    private String title;
    private String description;
    private String deadline;
    private long projectId;      // Id du projet auquel le tache est associée (0 si associée à aucun projet)
    private boolean expanded;    // Status de l'expansion de la tache (description visible ou non)

    public Task(String title, String desc, String deadline, long projectId) {
        this.title = title;
        this.description = desc;
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
