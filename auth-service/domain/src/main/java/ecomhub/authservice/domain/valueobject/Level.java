package ecomhub.authservice.domain.valueobject;

import ecomhub.authservice.common.exception.concrete.valueobject.level.InvalidLevelException;

public record Level(int value) {
    public Level {
        if (value < 0)
            throw new InvalidLevelException();
    }

    public boolean greaterThan(Level level) {
        return this.value > level.value;
    }
}
