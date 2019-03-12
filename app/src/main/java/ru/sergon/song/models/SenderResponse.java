package ru.sergon.song.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SenderResponse {

    @SerializedName("Message")
    private String message;
    @SerializedName("status")
    private String status;

    @SerializedName("response")
    private Post[] postResponse;

    @SerializedName("types_response")
    private Type[] typeResponse;

    public String getStatus(){
        return this.status;
    }
    public String getMessage(){
        return this.message;
    }

    public Post[] getResponse() {
        return postResponse;
    }
    public Type[] getTypes() {
        return typeResponse;
    }

    public static class Type {


        @SerializedName("id_type")
        private int id;

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
        public int getId() {
            return id;
        }


    }
    public static class Post {
        @SerializedName("type")
        private Type type;

        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        @SerializedName("text")
        private String text;

        @SerializedName("line")
        private String line;

        @SerializedName("tag")
        private String tag;

        @SerializedName("link")
        private String link;

        public Post(
                String title,
                String text,
                Type type
            ){
            this.title = title;
            this.text = text;
            this.type = type;

        }


        public String getLanguageName(){
            return this.type.name_type;
        }
        public String getLanguageCode(){
            return this.type.lang_class;
        }
        public int getId() {
            return id;
        }
        public Type getType(){return this.type;}
        public String getText() {
            return text;
        }
        public String getTitle() {
            return title;
        }
        public String getLine() {
            return line;
        }
        public String[] getLines(){
            if(this.line!=null) {
                return this.line.split(",");
            }else{
                return null;
            }
        }
        public String getLink() {
            return link;
        }
        public String getTag() {
            return tag;
        }
        public boolean hasLink(){
            return !this.link.equals("");
        }
        public boolean hasTitle(){
            return !(this.title.equals(""));
        }
        public boolean hasTag() {
            return !this.tag.equals("");
        }

        public void setType(Type type){
            this.type = type;
        }
        public void setId(int id){this.id = id;}
        public void setLink(String link) {
            this.link = link;

        }


        public void setTag(String s) {
            this.tag = s;
        }
        public void setLanguageName(String name){
            this.type.name_type=name;
        }
        public void setLanguageCode(String code){
            this.type.lang_class=code;
        }
    }
}
