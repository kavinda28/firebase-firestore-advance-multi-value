package com.example.firestore_project;

import com.google.firebase.firestore.Exclude;

public class note {
    String title;
    String description;
    String documentid;
    int priority;

    public note() {
    }
    public note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }
    @Exclude
    public String getDocumentid() {
        return documentid;
    }

    public void setDocumentid(String documentid) {
        this.documentid = documentid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
