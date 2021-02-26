package com.example.ArticleAI.configurations;

public enum AllowedContentTypes {
    PDF("application/pdf"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    DOC("application/msword"),
    OCTET("application/octet-stream");

    private String displayString;

    public String getDisplayString() {
        return displayString;
    }

    AllowedContentTypes(String displayValue) {
        this.displayString = displayValue;
    }

    public static AllowedContentTypes fromDisplayString(String displayString) {
        for(AllowedContentTypes type : AllowedContentTypes.values()){
            if(type.getDisplayString().equals(displayString)){
                return type;
            }
        }

        return null;
    }
}
