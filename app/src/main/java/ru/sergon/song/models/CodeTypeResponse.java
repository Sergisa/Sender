package ru.sergon.song.models;


import com.google.gson.annotations.SerializedName;

public class CodeTypeResponse {

    @SerializedName("Message")
    private String message;
    @SerializedName("status")
    private String status;
    @SerializedName("response")
    private Type[] typeResponse;

    public String getStatus(){
        return this.status;
    }
    public String getMessage(){
        return this.message;
    }

    public Type[] getTypeResponse() {
        return typeResponse;
    }

    public static class Type {


        @SerializedName("id_type")
        private String id;

        @SerializedName("name_type")
        private String name_type;


        @SerializedName("lang_class")
        private String lang_class;

        @SerializedName("ace_class")
        private String lang_class_ace;
        public Type(
                String id,
                String typeName,
                String lang_class
        ){
            this.id = id;
            this.lang_class = lang_class;
            this.name_type = typeName;

        }

        public String getLanguageName(){
            return this.name_type;
        }
        public String getLanguageCode(){
            return this.lang_class;
        }
        public String getId() {
            return id;
        }


    }
}

