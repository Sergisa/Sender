package ru.sergisa.sender.models;

import com.google.gson.annotations.SerializedName;


public class SenderResponse {

    @SerializedName("Message")
    private String message;
    @SerializedName("status")
    private String status;

    @SerializedName("response")
    private Post[] postResponse;

    @SerializedName("types_response")
    private Type[] typeResponse;

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public Post[] getResponse() {
        return postResponse;
    }

    public Type[] getTypes() {
        return typeResponse;
    }

    public static class Type {


        @SerializedName("id")
        private final int id;

        @SerializedName("name_type")
        private String name_type;


        @SerializedName("lang_class")
        private String lang_class;

        @SerializedName("ace_class")
        private String lang_class_ace;

        public Type(
                int id,
                String typeName,
                String lang_class
        ) {
            this.id = id;
            this.lang_class = lang_class;
            this.name_type = typeName;

        }

        public String getLanguageName() {
            return this.name_type;
        }

        public String getLanguageCode() {
            return this.lang_class;
        }

        public int getId() {
            return id;
        }


    }

    public static class Post {
        @SerializedName("id")
        private int id;

        @SerializedName("type")
        private Type type;

        //field for POST queries
        @SerializedName("type_id")
        private int type_id;

        @SerializedName("title")
        private final String title;

        @SerializedName("text")
        private final String text;

        @SerializedName("line")
        private final String line = "";

        @SerializedName("tag")
        private String tag = "";

        @SerializedName("link")
        private String link = "";

        public Post(String title, String text, Type type) {
            this.title = title;
            this.text = text;
            this.type = type;

        }

        public Post(String title, String text, int type_id) {
            this.title = title;
            this.text = text;
            this.type_id = type_id;

        }


        public String getLanguageName() {
            return this.type.name_type;
        }

        public String getLanguageCode() {
            return this.type.lang_class;
        }

        public int getId() {
            return id;
        }

        public Type getType() {
            return this.type;
        }

        public String getText() {
            return text;
        }

        public String getTitle() {
            return title;
        }

        public String getLine() {
            return line;
        }

        public String[] getLines() {
            if (this.line != null) {
                return this.line.split(",");
            } else {
                return null;
            }
        }

        public String getLink() {
            return link;
        }

        public String getTag() {
            return tag;
        }

        public boolean hasLink() {
            return !this.link.equals("");
        }

        public boolean hasTitle() {

            try {
                return !(this.title.equals(""));
            } catch (NullPointerException npe) {
                return false;

            }
        }

        public boolean hasTag() {
            return !this.tag.equals("");
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setLink(String link) {
            this.link = link;

        }


        public void setTypeId(int id) {
            this.type_id = id;
        }

        public void setTag(String s) {
            this.tag = s;
        }

        public void setLanguageName(String name) {
            this.type.name_type = name;
        }

        public void setLanguageCode(String code) {
            this.type.lang_class = code;
        }
    }
}
