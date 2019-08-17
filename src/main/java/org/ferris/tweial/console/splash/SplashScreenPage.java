package org.ferris.tweial.console.splash;

import java.util.Enumeration;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.ferris.tweial.console.application.ApplicationDirectory;
import org.ferris.tweial.console.io.Console;
import org.ferris.tweial.console.text.i18n.LocalizedString;
import org.ferris.tweial.console.text.i18n.LocalizedStringBuilder;
import org.ferris.tweial.console.text.i18n.LocalizedStringKey;
import org.ferris.tweial.console.text.i18n.LocalizedStringList;
import org.ferris.tweial.console.text.i18n.qualifier.Welcome;
import org.ferris.tweial.console.util.version.Version;
import org.ferris.tweial.console.view.page.AbstractPage;

public class SplashScreenPage extends AbstractPage {

    @Inject
    protected Logger log;

    @Inject
    protected Console console;

    @Inject
    protected ApplicationDirectory applicationDirectory;

    @Inject
    protected Version version;

    @Inject
    @LocalizedStringKey("SplashScreenPage.Properties")
    protected LocalizedStringList applicationProperties;

    @Inject
    @Welcome
    @LocalizedStringBuilder({
        @LocalizedStringKey(buildId = "message", value = "SplashScreenPage.Message"),
        @LocalizedStringKey(buildId = "width", value = "SplashScreenPage.Width"),
        @LocalizedStringKey(buildId = "bullet", value = "SplashScreenPage.Bullet")
    })
    protected LocalizedString welcome;

    @PostConstruct
    protected void postConstruct() {
        replaceUser(applicationProperties);
        replaceJava(applicationProperties);
        replaceLoggers(applicationProperties);
        replaceTweial(applicationProperties);
    }

    protected void replaceUser(LocalizedStringList properties) {
        properties.replace("t{user.name}", System.getProperty("user.name"));
        properties.replace("t{user.home}", System.getProperty("user.home"));
        properties.replace("t{user.directory}", System.getProperty("user.dir"));
    }

    protected void replaceJava(LocalizedStringList properties) {
        properties.replace("t{java.home}", System.getProperty("java.home"));
        properties.replace("t{java.vendor}", System.getProperty("java.vendor"));
        properties.replace("t{java.version}", System.getProperty("java.version"));
    }

    protected void replaceLoggers(LocalizedStringList properties) {
        StringBuilder sp = new StringBuilder();

        @SuppressWarnings("rawtypes")
        Enumeration appenders
                = Logger.getRootLogger().getAllAppenders();

        for (int i = 1; appenders.hasMoreElements(); i++) {
            Object o = appenders.nextElement();
            if (i > 1) {
                sp.append("\n");
            }
            sp.append(String.format("#%d\n%s", i, ToStringBuilder.reflectionToString(o)));
        }

        properties.replace("t{logger.appenders}", sp.toString());
    }

    protected void replaceTweial(LocalizedStringList properties) {
        {
            properties.replace("t{tweial.title}", version.getImplementationTitle());
            properties.replace("t{tweial.vender}", version.getImplementationVendor());
            properties.replace("t{tweial.version}", version.getImplementationVersion());
            properties.replace("t{tweial.directory}", applicationDirectory.getAbsolutePath());
        }
    }

    public void view() {
        log.info("view()");
        log.info(applicationProperties.toString());
        console.p(applicationProperties);
        console.p(welcome);
    }
}
