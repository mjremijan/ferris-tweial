package org.ferris.tweial.console.main;

public interface StartupPriority {

    public static final int EXCEPTION = 10;

    public static final int LOG4J = 20;

    public static final int SPASH_SCREEN = 30;

    public static final int EMAIL_DATASOURCE_VERIFICATION = 40;

    public static final int EMAIL_ACCOUNT_VERIFICATION = 45;

    public static final int TWITTER_DATASOURCE_VERIFICATION = 50;

    public static final int TWITTER_ACCOUNT_VERIFICATION = 60;

}
