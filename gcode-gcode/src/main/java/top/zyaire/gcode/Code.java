package top.zyaire.gcode;

/**
 * 本目录参考开源项目https://github.com/koodistrom/gcodeCreator/
 * The enum Code represents codes given to cnc machines.
 */
public enum Code {

    /**
     * G 00 code. rapid move
     */
    G00, //rapid move
    /**
     * G 01 code. line to
     */
    G01, //line to
    /**
     * G 02 code. clockwise arc
     */
    G02, //clockwise arc
    /**
     * G 03 code. ccw arc
     */
    G03, //ccw arc
    /**
     * G 20 code. metric
     */
    G20, //metric//单位寸
    /**
     * G 21 code. imperial
     */
    G21, //imperial//默认单位毫米

    /**
     * M03 开启激光
     */
    M03,

    /**
     * M05 关闭激光
     */
    M05
}
