package mmir2764.repository;

import mmir2764.model.MenuDataModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class MenuRepository {
    private List<MenuDataModel> listMenu;

    private void readMenu(){
        ClassLoader classLoader = MenuRepository.class.getClassLoader();
        String filename = "data/menu.txt";
        File file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
        this.listMenu= new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while((line=br.readLine())!=null){
                MenuDataModel menuItem=getMenuItem(line);
                listMenu.add(menuItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MenuDataModel getMenuItem(String line){
        MenuDataModel item;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        String name= st.nextToken();
        double price = Double.parseDouble(st.nextToken());
        item = new MenuDataModel(name, 0, price);
        return item;
    }

    public List<MenuDataModel> getMenu(){
        readMenu();//create a new menu for each table, on request
        return listMenu;
    }

}