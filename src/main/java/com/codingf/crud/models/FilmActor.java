package com.codingf.crud.models;

import com.codingf.crud.fonctions.Create;
import com.codingf.crud.fonctions.Read;
import com.codingf.crud.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FilmActor implements Tables {

    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String actor_id = "";
        String film_id = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Donnez l'id de l'acteur");
                actor_id = input.nextLine();
                int actor = Integer.parseInt(actor_id);
                break;
            }
            catch (NumberFormatException e) {
                System.err.println("Vous devez rentrer un nombre entier");
            }
        }

        while (true) {
            try {
                System.out.println("Donnez l'id du film dans lequel l'acteur joue");
                film_id = input.nextLine();
                int film = Integer.parseInt(film_id);
                break;
            }
            catch (NumberFormatException e) {
                System.err.println("Vous devez rentrer un nombre entier");
            }
        }

        try {

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM actor WHERE actor_id = '" + actor_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas d'acteur ayant l'id " + actor_id);
                System.err.println("Vous devez d'abord créer l'acteur correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM film WHERE film_id = '" + film_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas de film ayant l'id " + film_id);
                System.err.println("Vous devez d'abord créer le film correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM film_actor");

            while (result.next()) {
                if (result.getString("actor_id").equals(actor_id) && result.getString("film_id").equals(film_id)) {
                    System.err.println("Cet acteur joue déjà dans ce film");
                    return true;
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("actor_id", "film_id");
            columns = String.join(",", column);
            value = Arrays.asList(actor_id, film_id);
            values = String.join("','", value);
            Create.create(con, "film_actor", columns, values);

            result = statement.executeQuery("SELECT first_name, last_name FROM actor WHERE actor_id = '" + actor_id + "'");

            String actor_name = "";

            if (result.next()) {
                String fn = result.getString("first_name");
                String ln = result.getString("last_name");
                actor_name = fn + " " + ln;
            }

            result = statement.executeQuery("SELECT title FROM film WHERE film_id = '" + film_id + "'");

            String film_name = "";

            if (result.next()) {
                film_name = result.getString("title");
            }

            System.out.println(green + "L'acteur " + actor_name + " joue dans le film " + film_name + reset);

            return false;


        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }
    }

}
