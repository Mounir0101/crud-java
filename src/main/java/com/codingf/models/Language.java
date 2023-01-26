package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.fonctions.Read;
import com.codingf.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Language implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String language = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);

            System.out.println("Quel langue voulez vous ajouter ?");
            language = input.nextLine();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM language");

            while (result.next()) {
                if (result.getString("name").equals(language)) {
                    System.err.println("Cette langue existe déjà");
                    return true;
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("name");
            columns = String.join(",", column);
            value = Arrays.asList(language);
            values = String.join("','", value);
            Create.create(con, "language", columns, values);

            System.out.println(green + "La langue " + language + " a bien été ajouté à la table language" + reset);

            return false;

        }

        catch (SQLException e) {
            System.err.println("Erreur " + e);
            return true;
        }
    }

    @Override
    public void read(Connection con, String table) {

        try {
            Statement stmt = con.createStatement();

            ResultSet country_table = stmt.executeQuery("SELECT * FROM " + table);

            System.out.println();

            List<String> column_list = new ArrayList<>();

            column_list.add("language_id");
            column_list.add("name");
            column_list.add("last_update");

            Read.read(con, table, country_table, column_list);
        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }

    }
}
