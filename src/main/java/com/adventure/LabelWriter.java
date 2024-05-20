package com.adventure;


import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.*;

public class LabelWriter extends StringWriter
{
    private final Label label;

    public LabelWriter(Label label)
    {
        this.label = label;
    }

    public void write(int c) {
        super.write(c);
        this.flush();
    }

    public void write(char[] cbuf, int off, int len) {
        super.write(cbuf, off, len);
        this.flush();
    }

    public void write(String str)
    {
        super.write(str);
        this.flush();
    }

    public void write(String str, int off, int len)
    {
        super.write(str, off, len);
        this.flush();
    }

    public StringWriter append(CharSequence csq) {
        StringWriter sw = super.append(csq);
        this.flush();
        return sw;
    }

    public StringWriter append(CharSequence csq, int start, int end) {
        StringWriter sw = super.append(csq, start, end);
        this.flush();
        return sw;
    }

    public StringWriter append(char c) {
        StringWriter sw = super.append(c);
        this.flush();
        return sw;
    }

    @Override
    public void flush() {
        Platform.runLater(() -> label.setText(this.toString()));
    }
}
