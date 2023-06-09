package module;

public class Mouse {
    String name;

    public Mouse(String name) {
        this.name = name;
        System.out.println("mouse " + name + " is tring to find a way to escape from our maze!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
