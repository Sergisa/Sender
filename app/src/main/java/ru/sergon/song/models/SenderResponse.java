package ru.sergon.song.models;

import com.google.gson.annotations.SerializedName;



public class SenderResponse {

    @SerializedName("Message")
    private String message;
    @SerializedName("status")
    private String status;
    @SerializedName("response")
    private Post[] postResponse;

    public String getStatus(){
        return this.status;
    }
    public String getMessage(){
        return this.message;
    }

    public Post[] getResponse() {
        return postResponse;
    }

    public static class Post {
        @SerializedName("type")
        private int type;

        @SerializedName("id")
        private String id;

        @SerializedName("name_type")
        private String name_type;

        @SerializedName("id_type")
        private String id_type;

        @SerializedName("lang_class")
        private String lang_class;

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
                int id_type
            ){
            this.title = title;
            this.text = text;
            this.type = id_type;

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
        public String getText() {
            return text;
        }
        public String getTitle() {
            return title;
        }
        public String getLine() {
            return line;
        }
        /*public String[] getLines(){
            if(this.line!=null) {
                return this.line.split(",");
            }else{
                return null;
            }
        }*/
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

        public void setType(int id_type){
            this.type = id_type;
        }

        public void setLink(String link) {
            this.link = link;

        }


        public void setTag(String s) {
            this.tag = s;
        }
        public void setLanguageName(String name){
            this.name_type=name;
        }
        public void setLanguageCode(String code){
            this.lang_class=code;
        }
    }
}
