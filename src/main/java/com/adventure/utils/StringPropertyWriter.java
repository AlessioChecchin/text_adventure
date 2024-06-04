package com.adventure.utils;


import javafx.application.Platform;
import javafx.beans.property.StringProperty;

import java.io.*;

public class StringPropertyWriter extends StringWriter
{
    private final StringProperty prop;

    public StringPropertyWriter(StringProperty prop)
    {
        this.prop = prop;
    }

    public void write(int c) {
        super.write(c);
        this.out();
    }

    public void write(char[] buf, int off, int len) {
        super.write(buf, off, len);
        this.out();
    }

    public void write(String str)
    {
        super.write(str);
        this.out();
    }

    public void write(String str, int off, int len)
    {
        super.write(str, off, len);
        this.out();
    }

    public StringWriter append(CharSequence csq) {
        StringWriter sw = super.append(csq);
        this.out();
        return sw;
    }

    public StringWriter append(CharSequence csq, int start, int end) {
        StringWriter sw = super.append(csq, start, end);
        this.out();
        return sw;
    }

    public StringWriter append(char c) {
        StringWriter sw = super.append(c);
        this.out();
        return sw;
    }

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
}
