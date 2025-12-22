package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.level.InvalidLevelException;

public class Level {
    private final int value;

    public Level(int value) {
        if (value < 0)
            throw new InvalidLevelException(value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean greaterThan(Level level) {
        return this.value > level.value;
    }
    public boolean lessThan(Level level) {
        return this.value < level.value;
    }
}
