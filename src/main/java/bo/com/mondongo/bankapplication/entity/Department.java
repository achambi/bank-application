package bo.com.mondongo.bankapplication.entity;

public enum Department {
    LA_PAZ("01"),
    ORURO("02"),
    POTOSI("03"),
    PANDO("04"),
    SANTA_CRUZ("05"),
    BENI("06"),
    COCHABAMBA("07"),
    TARIJA("08"),
    CHUQUISACA("09");

    private String value;

    Department(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


