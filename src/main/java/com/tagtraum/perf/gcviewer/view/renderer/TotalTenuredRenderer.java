package com.tagtraum.perf.gcviewer.view.renderer;

import com.tagtraum.perf.gcviewer.model.AbstractGCEvent;
import com.tagtraum.perf.gcviewer.model.GCEvent;
import com.tagtraum.perf.gcviewer.model.GCModel;
import com.tagtraum.perf.gcviewer.view.ModelChart;
import com.tagtraum.perf.gcviewer.view.ModelChartImpl;

import java.awt.*;

/**
 * Renders total size of tenured heap.
 *
 * <br>Date: Jun 2, 2005
 * <br>Time: 3:31:21 PM<br>
 * @author <a href="mailto:hs@tagtraum.com">Hendrik Schreiber</a>
 * @author <a href="mailto:gcviewer@gmx.ch">Joerg Wuethrich</a>
 */
public class TotalTenuredRenderer extends PolygonChartRenderer {
    public static final Paint DEFAULT_LINEPAINT = Color.MAGENTA;
    public static final Paint DEFAULT_FILLPAINT = new GradientPaint(0, 0, Color.MAGENTA, 0, 0, Color.WHITE);

    public TotalTenuredRenderer(ModelChartImpl modelChart) {
        super(modelChart);
        setFillPaint(DEFAULT_FILLPAINT);
        setLinePaint(DEFAULT_LINEPAINT);
        setDrawPolygon(true);
        setDrawLine(true);
    }

    public Polygon computePolygon(ModelChart modelChart, GCModel model) {
        ScaledPolygon polygon = createMemoryScaledPolygon();
        polygon.addPoint(0.0d, 0.0d);
        double lastTotal = 0;
        for (AbstractGCEvent<?> abstractGCEvent : model.getSTWEvents()) {
            if (abstractGCEvent instanceof GCEvent) {
                GCEvent event = (GCEvent) abstractGCEvent;
                GCEvent tenured = event.getTenured();
                if (hasMemoryInformation(event) && tenured != null) {
                    if (polygon.npoints == 1) {
                        // first point needs to be treated different from the rest,
                        // because otherwise the polygon would not start with a vertical line at 0,
                        // but with a slanting line between 0 and after the first pause
                        polygon.addPoint(0, (double)tenured.getTotal());
                    }
                    polygon.addPoint(tenured.getTimestamp() - model.getFirstPauseTimeStamp() + event.getPause(),
                            tenured.getTotal());
                    lastTotal = tenured.getTotal();
                }
            }
        }
        polygon.addPointNotOptimised(model.getRunningTime(), lastTotal);
        polygon.addPointNotOptimised(model.getRunningTime(), 0.0d);
        return polygon;
    }
}
