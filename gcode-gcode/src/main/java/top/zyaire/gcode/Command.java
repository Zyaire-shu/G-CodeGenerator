package top.zyaire.gcode;

import lombok.Data;

import java.util.Locale;

/**
 * The Command class represents command written to gcode file that specifies a operation on CNC-machine.
 * Command holds an enum Code and parameters (coordinates and speeds) and possibly a comment
 */
@Data
public class  Command {
    private Code code;
    private Float x;
    private Float y;
    private Float z;
    private Float i;
    private Float j;
    private Float f;
    private Integer s;
    private String comment;
    private Float scale = 1f;//放大缩小倍率
    private Float xOffset = 0f;//x移动距离
    private Float yOffset = 0f;//y移动距离
    /**
     * Instantiates a new Command.
     *
     * @param code the code
     * @param x    the x coordinate
     * @param y    the y coordinate
     * @param z    the z coordinate
     * @param i    the i x offset for arc centers
     * @param j    the j y offset for arc centers
     * @param f    the f feed speed
     */
    public Command(Code code, Float x, Float y, Float z, Float i, Float j, Float f) {
        this.code = code;
        this.x = x;
        this.y = y;
        this.z = z;
        this.i = i;
        this.j = j;
        this.f = f;
    }

    /**
     * Instantiates a new Command.
     *
     * @param code the code
     * @param x    the x
     * @param y    the y
     * @param i    the i
     * @param j    the j
     */
    public Command(Code code, Float x, Float y, Float i, Float j) {
        this.code = code;
        this.x = x;
        this.y = y;
        this.i = i;
        this.j = j;
    }

    /**
     * Instantiates a new Command.
     *
     * @param code the code
     * @param x    the x
     * @param y    the y
     * @param z    the z
     */
    public Command(Code code, Float x, Float y, Float z) {
        this.code = code;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Instantiates a new Command.
     *
     * @param code the code
     * @param x    the x
     * @param y    the y
     */
    public Command(Code code, Float x, Float y) {
        this.code = code;
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiates a new Command.
     *
     * @param code the code
     * @param zValue    the z
     */
    public Command(Code code, Float zValue) {
        this.code = code;
        this.z = zValue;
    }

    /**
     * Instantiates a new Command.
     *
     * @param comment the comment
     */
    public Command(String comment){
        this.comment = comment;
    }


    /**
     * Instantiates a new Command.
     *
     * @param code the code
     */
    public Command(Code code) {
        this.code = code;
    }


    /**
     * To string returns the command as string in the format that it is written to gcode file:
     * Code (see Code enum) is printed first and each parameter is represented by uppercase letter and the numeric value
     *
     */
    @Override
    public String toString() {
        String str;
        if(code != null){
            str = code + " ";
            if(x!=null){str+="X"+String.format(Locale.ROOT,"%.4f",(x*scale)+xOffset)+" ";}
            if(y!=null){str+="Y"+String.format(Locale.ROOT,"%.4f",(y*scale)+yOffset)+" ";}
            if(z!=null){str+="Z"+String.format(Locale.ROOT,"%.4f",z)+" ";}
            if(i!=null){str+="I"+String.format(Locale.ROOT,"%.4f",(i*scale))+" ";}
            if(j!=null){str+="J"+String.format(Locale.ROOT,"%.4f",(j*scale))+" ";}
            if(f!=null){str+="F"+f+" ";}
            if(s!=null){str+="S"+s+" ";}
        }else{
            str = "("+comment+")";
        }

        return str;
    }
    public static Command toCut(Options options){
        if (options.isLaser()){
            Command down =  new Command(Code.M03);
            down.setS(options.getLaserPower());
            return down;
        }
        return new Command(Code.G01, options.getWorkDepth());
    }
    public static Command toRaise(Options options){
        if (options.isLaser()){
            return new Command(Code.M05);
        }
        return new Command(Code.G00, options.getMoveHeight());
    }
}
