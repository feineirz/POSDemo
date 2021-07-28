/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GLOBAL;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
/**
 *
 * @author feinz
 */
public class APIData {
    
    public class APIStore {
        public String name;
        public String price;
        public String link;
        public String currency;
        public String currency_symbol;
    }

    public class APIReview {
        public String name;
        public String rating;
        public String title;
        public String review;
        public String date;
    }

    public class APIProduct {
        public String barcode_number;
        public String barcode_formats;
        public String mpn;
        public String model;
        public String asin;
        public String title;
        public String category;
        public String manufacturer;
        public String brand;
        public String contributors;
        public String age_group;
        public String ingredients;
        public String nutrition_facts;
        public String color;
        public String format;
        public String multipack;
        public String size;
        public String length;
        public String width;
        public String height;
        public String weight;
        public String release_date;
        public String description;
        public Object[] features;
        public String[] images;
        public APIStore[] stores;
        public APIReview[] reviews;
    }

    public class RootObject {
        public APIProduct[] products;
    }
	
    public static void getAPIData(String reqBarcode) {
        System.out.println("API request for '"+reqBarcode+"'");
        try {
            URL url = new URL("https://api.barcodelookup.com/v3/products?barcode="+reqBarcode+"&formatted=y&key="+Settings.API_KEY);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = "";
            String data = "";
            while (null != (str= br.readLine())) {
                data+=str;
            }

            Gson g = new Gson();

            RootObject value = g.fromJson(data, RootObject.class);

            String barcode = value.products[0].barcode_number;
            System.out.print("Barcode Number: ");
            System.out.println(barcode);

            String name = value.products[0].title;
            System.out.print("Title: ");
            System.out.println(name);

            System.out.println("Entire Response:");
            System.out.println(data);

        } catch (Exception ex) {
            
            //ex.printStackTrace();
        }

    }
    
}
