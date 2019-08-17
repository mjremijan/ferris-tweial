package org.ferris.tweial.console.io;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import org.ferris.tweial.console.text.i18n.LocalizedString;
import org.ferris.tweial.console.text.i18n.LocalizedStringKey;
import org.ferris.tweial.console.text.i18n.LocalizedStringList;

public class Console {

    private ConsoleWriter writer;

    @Inject
    @LocalizedStringKey("Violation.Message")
    protected LocalizedString violationMessage;

    @Inject
    @LocalizedStringKey("Violation.Bullet")
    protected LocalizedString violationBullet;

    @Inject
    protected void setConsoleWriter(ConsoleWriter writer) {
        this.writer = writer;
    }

    public void h1(LocalizedString format, Object... args) {
        writer.println();
        writer.printf("[%s]", String.format(format.toString(), args));
        writer.println();
    }

    public void h1(LocalizedString heading) {
        h1(heading, (Object[]) null);
    }

    public void h2(LocalizedString format, Object... args) {
        writer.println();
        writer.printf("++ %s", String.format(format.toString(), args));
        writer.println();
    }

    public void h2(LocalizedString heading) {
        h2(heading, (Object[]) null);
    }

    public void p(LocalizedStringList paragraph) {
        writer.println();
        for (String s : paragraph) {
            writer.printf("%s", s);
            writer.println();
        }
    }

    public void p(LocalizedString format, Object... args) {
        writer.println();
        writer.printf("%s", String.format(format.toString(), args));
        writer.println();
    }

    public void p(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        sw.flush();

        writer.println();
        writer.printf(sw.toString());
        writer.println();
    }

    public <T> void print(Set<ConstraintViolation<T>> violations) {
        writer.println();
        writer.printf("%s", violationMessage);
        writer.println();
        for (ConstraintViolation<T> violation : violations) {
            writer.printf(" %s %s", violationBullet, violation.getMessage());
            writer.println();
        }
    }
}
