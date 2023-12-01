package javaio.menu;

public abstract class MenuItem {
    public static final int height = 10;
    public final String name;
    public String displayValue;

    public MenuItem(String displayText){
        this.name = displayText;
        updateDisplayValue();
    }

    public abstract void previous();
    public abstract void next();
    public abstract void action();
    public abstract void chosen();
    public abstract void updateDisplayValue();
}
