package dunaeva.polina.finalproject.entity;

public enum RatingType {
    ONE, TWO, THREE, FOUR, FIVE;
    public Integer toInteger() {
        int value = 0;
        switch (this.name()) {
            case "ONE": value = 1; break;
            case "TWO": value = 2; break;
            case "THREE": value = 3; break;
            case "FOUR": value = 4; break;
            case "FIVE": value = 5; break;
        }
        return value;
    }
}
