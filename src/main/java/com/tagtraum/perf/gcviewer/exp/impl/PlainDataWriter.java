package com.tagtraum.perf.gcviewer.exp.impl;

import com.tagtraum.perf.gcviewer.exp.AbstractDataWriter;
import com.tagtraum.perf.gcviewer.model.GCEvent;
import com.tagtraum.perf.gcviewer.model.GCModel;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Writes the model using the toString()-methode of {@link GCEvent}.
 *
 * Date: Feb 1, 2002
 * Time: 9:58:11 AM
 * @author <a href="mailto:hs@tagtraum.com">Hendrik Schreiber</a>
 */
public class PlainDataWriter extends AbstractDataWriter {

    public PlainDataWriter(OutputStream out) {
        super(out);
    }

    /**
     * Writes the model and flushes the internal PrintWriter.
     */
    public void write(GCModel model) throws IOException {
        model.getAllEvents().forEach(i -> out.write(i.toString()));
        out.flush();
    }

}
