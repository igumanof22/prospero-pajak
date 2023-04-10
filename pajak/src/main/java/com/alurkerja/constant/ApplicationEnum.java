package com.alurkerja.constant;

public class ApplicationEnum {

    public enum Group {
        MAKER,
        CHECKER,
        APPROVER,
        ADMIN;
    }

    public enum Status {
        CREATED("Created"),
        CHECKED("Checked"),
        APPROVED("Approved");

        public final String key;

        Status(String key) {
            this.key = key;
        }
    }
}
