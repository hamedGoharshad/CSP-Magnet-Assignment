package ir.magnet;

import aima.core.search.csp.Variable;

public class MagnetVariable extends Variable {


    public MagnetVariable(int id) {
        super(String.valueOf(id));
    }
}

enum Value {
    EMPTY, POSITIVE, NEGATIVE;

    public boolean isOpposite(Value value) {
        return (this == POSITIVE && value == NEGATIVE ||
                this == NEGATIVE && value == POSITIVE);
    }

    @Override
    public String toString() {
        return switch (this) {
            case EMPTY -> "0";
            case POSITIVE -> "+";
            case NEGATIVE -> "-";
        };
    }
}
