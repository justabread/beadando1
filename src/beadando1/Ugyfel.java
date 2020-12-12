package beadando1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Ugyfel {
    private final String name;
    private final int ugyfelId;
    private Osztalykezelo o;

    public Ugyfel(int ugyfelId, String name, Osztalykezelo _o) {
        this.name = name;
        this.ugyfelId = ugyfelId;
        this.o = _o;
    }
    
    public int getUgyfelId()
    {
        return this.ugyfelId;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void complaint(){
        System.out.println("Kérjük panaszát írja meg a panasz.kezeles@gmail.com e-mail címre.");
    }
    
    public void newRent(){
        List<String> vehicle=new ArrayList<>();
        String endString = "";
                      
        Scanner in = new Scanner(System.in);
        int userId = 0;
        boolean validEntry;
        do {
            try {
                System.out.println("Adja meg a hozzáadni kívánt bérlő azonosítóját: ");
                Scanner sc = new Scanner(System.in);
                userId = sc.nextInt();
                validEntry = true;
            } catch (InputMismatchException e) {
                validEntry = false;
                System.out.println("Kérem egész számot üssön be!");
            }
        } while (!validEntry);
        
        System.out.println("Adja meg a bérlés kezdeti idejét ÉÉÉÉ-HH-NN formátumban: ");
            String startDate = in.nextLine();
        
        System.out.println("Adja meg a bérlés záró idejét ÉÉÉÉ-HH-NN formátumban: ");
            String endDate = in.nextLine();
        
        System.out.println("Adja meg a bérelt autók rendszámát enterrel elválasztva(írjon STOP-ot ha be akarja fejezni a műveletet): ");
            String rendszam = in.nextLine();
            if(rendszam.equalsIgnoreCase("stop"))
            {
                System.out.println("Művelet megszakítva.");
            }
            while(!rendszam.equalsIgnoreCase("stop"))
            {
                vehicle.add(rendszam.toUpperCase());
                rendszam = in.nextLine();
            }
            
        if(!vehicle.isEmpty())
        {
            endString += userId + ";";
            endString += startDate + ";";
            endString += endDate + ";";
            
            for(int i = 0; i < vehicle.size()-1; i++)
            {
                endString += vehicle.get(i) + ";";
            }
            endString += vehicle.get(vehicle.size()-1);
            
            try {
                FileWriter fw = new FileWriter("berlesFile.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(endString);
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                System.out.println("An error occured while writing to file.");
            }                            
        }
        
        o.berlesRead();
    }
    
    public void editRent(){
        List<String> _vehicle=new ArrayList<>();
        Scanner in = new Scanner(System.in);
        
        String userInput = "";
        int userId = 0;
        boolean validEntry;
        do {
            try {
                System.out.println("Adja meg a hozzáadni kívánt bérlő azonosítóját: ");
                Scanner sc = new Scanner(System.in);
                userId = sc.nextInt();
                validEntry = true;
            } catch (InputMismatchException e) {
                validEntry = false;
                System.out.println("Kérem egész számot üssön be!");
            }
        } while (!validEntry);
        System.out.println("Kívánja a  bérlés kezdeti idejét módosítani?(y-igen)");
        userInput = in.nextLine();
        if(userInput.equalsIgnoreCase("y"))
        {
            System.out.println("Adja meg a bérlés kezdeti idejét ÉÉÉÉ-HH-NN formátumban: ");
            String startDate = in.nextLine();
            o.getBerlesMap().get(userId).setStartDate(LocalDate.parse(startDate));           
        }
        
        System.out.println("Kívánja a  bérlés záró idejét módosítani?(y-igen)");
        userInput = in.nextLine();
        if(userInput.equalsIgnoreCase("y"))
        {
            System.out.println("Adja meg a bérlés záró idejét ÉÉÉÉ-HH-NN formátumban: ");
            String endDate = in.nextLine();
            o.getBerlesMap().get(userId).setEndDate(LocalDate.parse(endDate)); 
        }
        
        System.out.println("Kívánja a bérelt autók rendszámainak listáját módosítani?(y-igen)");
        userInput = in.nextLine();
        if(userInput.equalsIgnoreCase("y"))
        {
            System.out.println("Adja meg a bérelt autók rendszámát enterrel elválasztva(írjon STOP-ot ha be akarja fejezni a műveletet): ");
            String rendszam = in.nextLine();
            if(rendszam.equalsIgnoreCase("stop"))
            {
                System.out.println("Művelet megszakítva.");
            }
            while(!rendszam.equalsIgnoreCase("stop"))
            {
                _vehicle.add(rendszam.toUpperCase());
                rendszam = in.nextLine();
            }
        }
        
        if(!_vehicle.isEmpty())
        {
            o.getBerlesMap().get(userId).setVehicle(_vehicle);
            
            try {
                FileWriter fw = new FileWriter("berlesFile.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                
                o.getBerlesMap().entrySet().forEach(currentBerles->{
                    try {
                        bw.write(currentBerles.getKey() + ";" + currentBerles.getValue().berlesToStringNoPrice());
                        bw.newLine(); 
                    } catch (IOException e) {
                        System.out.println("An error occured while writing to file.");
                    }
                                     
                });                                                                                      
                bw.close();
            } catch (IOException e) {
                System.out.println("An error occured while writing to file.");
            }                            
        }else
        {
            System.out.println("Üres autólistát adott meg!");
        }
    }
    
    public void searchCar() {
        Osztalykezelo o = new Osztalykezelo();
        o.jarmuRead();
        System.out.println("Gyártók:\n");
        List<String> gyartoLista = new ArrayList<>();
        gyartoLista.add(o.getJarmuList().get(0).getBrand());
        for (Jarmu jarmuvek : o.getJarmuList()) {
            boolean voltMar = false;
            for(int k = 0; k < gyartoLista.size(); k++){
                if(gyartoLista.get(k).equals(jarmuvek.getBrand())){
                    voltMar = true;
                }
            }
            if(!voltMar){
                gyartoLista.add(jarmuvek.getBrand());
            }
        }
        for(String s : gyartoLista){
            System.out.println(s);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Kérem válasszon gyártót!");
        String gyarto = sc.nextLine();
        while(!gyartoLista.contains(gyarto)){
            System.out.println("Nincs ilyen gyártó!");
            System.out.println("Kérem próbálja újra!");
            gyarto = sc.nextLine();
        }
        for (Jarmu j : o.getJarmuList()) {
            if(gyarto.equals(j.getBrand())){
                System.out.println("Típus: " + j.getType() + "\tNapidíj: " + j.getPrice() + "\tRendszám: " + j.getLicense());
            }
        }
    }
    
    public void contact(){
        System.out.println("Telefonszám: +36 30 8888888\n"
                + "E-mail cím: auto.info@gmail.com\n"
                + "Cím: 8200. Veszprém, Katona utca 22.");
    }
    
    public String ugyfelToString()
    {
        return this.ugyfelId + "; " + this.name;
    }  
}
