package top.zyaire.gcode;

import lombok.Data;

/**
 * The Options class holds information given by the user that affect the creation of gcode file from svg.
 */
@Data
public class Options {
    private float moveHeight;
    private float workDepth;
    private Integer laserPower;//这个是M03S100中的S
    private String units;
    private float feed;
    private boolean laser;
    private float curveAprTolerance;
    private int curveSamplingStep;//越大生成的线条越小

    /**
     * Instantiates a new Options.
     */
    public Options() {
        this.moveHeight = 2;
        this.workDepth = 0;
        this.laserPower = 1000;
        this.feed = 4000;
        this.laser = true;
        this.units = "mm";
        this.curveAprTolerance = 0.5f;
        this.curveSamplingStep = 1000;
    }

}
