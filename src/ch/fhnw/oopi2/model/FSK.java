package ch.fhnw.oopi2.model;

/**
 * Created by Ajanth on 09.06.2016.
 */
public enum FSK {
    FSK_0(0), FSK_6(6), FSK_12(12), FSK_16(16), FSK_18(18);

    private int value;

    private FSK(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
