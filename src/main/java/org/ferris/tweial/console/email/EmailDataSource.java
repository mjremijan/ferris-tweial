package org.ferris.tweial.console.email;

/**
 * The purpose of this class is to provide a small abstraction to how the
 * application is to get email account data.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailDataSource {

    private String description;
    private boolean exists;

    /**
     * Constructor w/o error checking
     *
     * @param description Not error checked
     * @param exists Not error checked
     */
    public EmailDataSource(String description, boolean exists) {
        this.description = description;
        this.exists = exists;
    }

    /**
     * Basic getter for description
     * @return {@link String}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Basic getter for exists
     * @return {@link String}
     */
    public boolean exists() {
        return exists;
    }
}
