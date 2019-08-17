/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.tweial.console.twitter;

/**
 * The purpose of this class is to provide a small abstraction to how Twitter is
 * getting its data.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TwitterDataSource {

    private String description;
    private boolean exists;

    public TwitterDataSource(String description, boolean exists) {
        this.description = description;
        this.exists = exists;
    }

    public String getDescription() {
        return description;
    }

    public boolean exists() {
        return exists;
    }
}
