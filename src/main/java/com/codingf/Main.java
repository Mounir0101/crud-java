package com.codingf;

import com.codingf.fonctions.Read;
import com.codingf.interfaces.Tables;
import com.codingf.models.*;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.err.println("Problème de chargement du driver");
        }
        System.out.println("Le driver est chargé");
        String host = "localhost";
        String dbname = "sakila";
        int port = 3306;

        String URL = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        String username = "root";
        String password = "";

        Connection connection = DriverManager.getConnection(URL, username, password);

        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        if (connection == null) {
            System.err.println("Erreur de connexion");
        }
        else {
            System.out.println(green + "Connexion établie" + reset);
        }
        Scanner nb = new Scanner(System.in);
        int table;

        while(true) {

            while (true) {

                DatabaseMetaData databaseMetaData = connection.getMetaData();
                ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
                int iterator = 0;
                while(resultSet.next()) {
                    if (resultSet.getString("TABLE_NAME").equals("film_text") || resultSet.getString("TABLE_NAME").equals("sys_config")) {
                        continue;
                    }
                    else {
                        iterator++;
                        System.out.println(iterator + " : " + resultSet.getString("TABLE_NAME"));
                    }
                }

                //System.out.println("1: Country");
                //System.out.println("2: City");
                System.out.println("16: Quitter");
                System.out.println("Quelle table voulez vous choisir ?");

                String input = nb.nextLine();

                try {
                    table = Integer.parseInt(input);
                    if (table < 1 || table > 16) {
                        System.err.println("Choisissez une action valide");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.err.println("nombre incorrect");
                }

            }

            String tableSelected = "";

            switch (table) {
                case 1 -> tableSelected = "actor";
                case 2 -> tableSelected = "address";
                case 3 -> tableSelected = "category";
                case 4 -> tableSelected = "city";
                case 5 -> tableSelected = "country";
                case 6 -> tableSelected = "customer";
                case 7 -> tableSelected = "film";
                case 8 -> tableSelected = "film_actor";
                case 9 -> tableSelected = "film_category";
                //case 10 -> tableSelected = "film_text";
                case 10 -> tableSelected = "inventory";
                case 11 -> tableSelected = "language";
                case 12 -> tableSelected = "payment";
                case 13 -> tableSelected = "rental";
                case 14 -> tableSelected = "staff";
                case 15 -> tableSelected = "store";
                //case 17 -> tableSelected = "sys_config";
                case 16 -> System.exit(0);
            }

            int choice;

            while (true) {
                System.out.println("1: Créer");
                System.out.println("2: Lire");
                System.out.println("3: Mettre à jour");
                System.out.println("4: Supprimer");
                System.out.println("Que voulez vous faire avec cette table ?");

                String input = nb.nextLine();

                try {
                    choice = Integer.parseInt(input);
                    if (choice < 1 || choice > 4) {
                        System.err.println("Choisissez une action valide");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.err.println("nombre incorrect");
                }
            }

            Scanner input = new Scanner(System.in);
            boolean exists = false;

            switch (choice) {

                case 1:

                    switch (tableSelected) {

                        case "actor":

                            Tables actors = new Actor();
                            if (actors.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "address":

                            Tables adresses = new Address();
                            if (adresses.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "category":

                            Tables categories = new Category();
                            if (categories.create(connection, tableSelected)) {
                                continue;
                            }
                            break;

                        case "country":

                            Tables countries = new Country();
                            if (countries.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "city":

                            Tables cities = new City();

                            if (cities.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "customer":

                            Tables customers = new Customer();

                            if (customers.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "film":

                            Tables films = new Film();

                            if (films.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "film_actor":

                            Tables films_actors = new FilmActor();

                            if (films_actors.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "film_category":

                            Tables films_categories = new FilmCategory();

                            if (films_categories.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "language":

                            Tables languages = new Language();

                            if (languages.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "staff":

                            Tables staffs = new Staff();
                            if (staffs.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "store":

                            Tables stores = new Store();
                            if (stores.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                    }

                    break;

                case 2:

                    Read.read(connection, tableSelected);

            }

        }

    }
}