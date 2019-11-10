package com.easyimmo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ad {

    @Id
    private Integer id;
    private AdStatus status;
    private int authorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AdStatus getStatus() {
        return status;
    }

    public void setStatus(AdStatus status) {
        this.status = status;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }


    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", status=" + status +
                ", authorId=" + authorId +
                '}';
    }
}
