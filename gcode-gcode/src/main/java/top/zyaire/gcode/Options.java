package top.zyaire.gcode;

/**
 * The Options class holds information given by the user that affect the creation of gcode file from svg.
 */
public class Options {
    private float moveDepth = 2;
    private float workDepth = 0;
    private Integer laserPower = 1000;//这个是M03S100中的S
    private String units = "mm";
    private float feed = 4000;
    private boolean laser = true;

    /**
     * Instantiates a new Options.
     */
    public Options() {
        this.units = "mm";
    }

    /**
     * Gets move depth. A Z-height value used for the movement from a one path to another when the machine
     * isn't cutting/drawing
     *
     * @return the move depth
     */
    public float getMoveDepth() {
        return moveDepth;
    }

    /**
     * Sets move depth.  A Z-height value used for the movement from a one path to another when the machine
     * isn't cutting/drawing
     *
     * @param moveDepth the move depth
     */
    public void setMoveDepth(float moveDepth) {
        this.moveDepth = moveDepth;
    }

    /**
     * Gets work depth.  A Z-height value used for the movement when the machine
     * is cutting/drawing
     *
     * @return the work depth
     */
    public float getWorkDepth() {
        return workDepth;
    }

    /**
     * Sets work depth.   A Z-height value used for the movement when the machine
     * is cutting/drawing
     *
     * @param workDepth the work depth
     */
    public void setWorkDepth(float workDepth) {
        this.workDepth = workDepth;
    }

    /**
     * Gets units. mm or inch
     *
     * @return the units
     */
    public String getUnits() {
        return units;
    }

    /**
     * Sets units.  mm or inch
     *
     * @param units the units
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /**
     * Gets feed. The speed used when cutting/drawing
     *
     * @return the feed
     */
    public float getFeed() {
        return feed;
    }

    /**
     * Sets feed.The speed used when cutting/drawing
     *
     * @param feed the feed
     */
    public void setFeed(float feed) {
        this.feed = feed;
    }

    /**
     * 判断是否是激光雕刻机，如果是的话，用M03S1000替代G0Z-1
     * @return: boolean
     * @date: 2022/2/8 10:47
     */
    public boolean isLaser() {
        return laser;
    }

    /**
     * 设置适用于激光雕刻
     * @param laser:
     * @return: void
     * @date: 2022/2/8 10:48
     */
    public void setLaser(boolean laser) {
        this.laser = laser;
    }

    public int getLaserPower() {
        return this.laserPower;
    }

    public void setLaserPower(int laserPower) {
        this.laserPower = laserPower;
    }
}
