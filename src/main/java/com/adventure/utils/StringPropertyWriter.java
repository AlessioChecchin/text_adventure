package com.adventure.utils;


import javafx.application.Platform;
import javafx.beans.property.StringProperty;

import java.io.*;

/**
 * Writer used for handling a StringProperty.
 */
public class StringPropertyWriter extends StringWriter
{
    /**
     * Constructor.
     * @param prop Target object.
     */
    public StringPropertyWriter(StringProperty prop)
    {
        this.prop = prop;
    }

    @Override
    public void write(int c) {
        super.write(c);
        this.out();
    }

    @Override
    public void write(char[] buf, int off, int len) {
        super.write(buf, off, len);
        this.out();
    }

    @Override
    public void write(String str)
    {
        super.write(str);
        this.out();
    }

    @Override
    public void write(String str, int off, int len)
    {
        super.write(str, off, len);
        this.out();
    }

    @Override
    public StringWriter append(CharSequence csq) {
        StringWriter sw = super.append(csq);
        this.out();
        return sw;
    }

    @Override
    public StringWriter append(CharSequence csq, int start, int end) {
        StringWriter sw = super.append(csq, start, end);
        this.out();
        return sw;
    }

    @Override
    public StringWriter append(char c) {
        StringWriter sw = super.append(c);
        this.out();
        return sw;
    }

    /**
     * Prints the content of the string property to the text property.
     */
    protected void out()
    {
        Platform.runLater(() -> prop.set(this.toString()));
    }

    /**
     * Resets the stream.
     */
    @Override
    public void flush() {
        this.out();
        this.getBuffer().setLength(0);
    }

    //
    // VARIABLES.
    //

    private final StringProperty prop;
}
