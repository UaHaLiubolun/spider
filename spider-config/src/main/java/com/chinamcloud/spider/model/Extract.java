package com.chinamcloud.spider.model;


import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class Extract {

    private List<String> value;

    private String filed;

    public static enum  Type {
        XPath("XPath"), Regex("Regex"), Css("Css"), JsonPath("JsonPath");

        @JsonCreator
        public static Type getType(String value) {
            for (Type tmp : values()) {
                if (tmp.getValue().equalsIgnoreCase(value)) {
                    return tmp;
                }
            }
            return null;
        }


        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    };

    private Type type = Type.XPath;

    public static enum Source {
        Html("Html"), Url("Url"), RawHtml("RawHtml"), RawText("RawText");

        @JsonCreator
        public static Source getSource(String value) {
            for (Source tmp : values()) {
                if (tmp.getValue().equalsIgnoreCase(value)) {
                    return tmp;
                }
            }
            return null;
        }

        private String value;

        Source(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private Source source = Source.Html;

    private boolean multi = false;

    private boolean notNull = false;

    private DataConversion dataConversion = null;

    public DataConversion getDataConversion() {
        return dataConversion;
    }

    public void setDataConversion(DataConversion dataConversion) {
        this.dataConversion = dataConversion;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }
}
