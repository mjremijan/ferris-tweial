package org.ferris.tweial.console.application;

import java.io.File;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationDirectory extends File {

    private static final long serialVersionUID = 7491901906021288631L;

    public ApplicationDirectory(String path) {
        super(path);
    }

}
