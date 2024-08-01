package course.management.enums;

public enum Credit {
    one(1),
    two(2),
    three(3),
    four(4);

    private int id;

    Credit(int i) {
        this.id = i;
    }

    public int getId() {
        return id;
    }
}
