package ru.sergisa.sender;

public class DataLoader {
    private static DataLoader dataLoaderInstance;
    private DataLoader(){

    }
    public static void initInstance(){
        if(dataLoaderInstance == null){
            dataLoaderInstance = new DataLoader();
        }
    }
    public static DataLoader getDataLoaderInstance() {
        return dataLoaderInstance;

    }
}
