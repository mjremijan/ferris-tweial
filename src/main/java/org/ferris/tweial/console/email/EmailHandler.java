package org.ferris.tweial.console.email;

import java.util.Set;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import org.apache.log4j.Logger;

/**
 * This class contains the business logic for handling email related data.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailHandler {

    @Inject
    protected Logger log;

    @Inject
    protected EmailPropertiesFile emailProperties;

    @Inject
    protected ValidatorFactory validatorFactory;

    /**
     * Get email account information
     * 
     * @return Return an {@link EmailAccount} instance. Never returns
     * {@code null}. If no account data is found, an {@link EmailAccount}
     * instance is returned with all its properties set to null.
     */
    public EmailAccount getEmailAccount() {
        log.info("Create EmailAccount from properties");
        return new EmailAccount(emailProperties.toProperties());
    }

    /**
     * Validates the email account data and returns the constraints.  If there
     * are no constraint violations, an empty set is returned.
     *
     * @param validateMe The EmailAccount data to be validated.
     * @return Returns constraint violations or an empty set if none.    
     */
    public Set<ConstraintViolation<EmailAccount>> validate(EmailAccount validateMe) {
        log.info("Validate the EmailAccount object");
        Set<ConstraintViolation<EmailAccount>> violations
                = validatorFactory.getValidator().validate(validateMe);
        return violations;
    }

    /**
     * The purpose of this method is to provide the application an abstraction
     * about the source of the email account data. The specifics of what this
     * source is doesn't matter (database, file, ldap, etc.).
     * 
     * @return {@link EmailDataSource}
     */
    public EmailDataSource getEmailAccountDataSource() {
        return new EmailDataSource(
                emailProperties.getAbsolutePath(), emailProperties.exists()
        );
    }
}
