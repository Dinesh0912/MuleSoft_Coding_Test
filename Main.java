package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static final String DB_NAME = "Movies.db";
    private static final Scanner scanner = new Scanner(System.in);
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Dinesh\\IdeaProjects\\TestDB\\" + DB_NAME;

    public static final String TABLE_MOVIES = "movies";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ACTOR = "actor";
    public static final String COLUMN_ACTRESS = "actress";
    public static final String COLUMN_DIRECTOR = "director";
    public static final String COLUMN_YEAR_OF_RELEASE = "year_of_release";

    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();

            statement.execute("DROP TABLE IF EXISTS " + TABLE_MOVIES);

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_MOVIES +
                    " (" + COLUMN_NAME + " TEXT, " +
                    COLUMN_ACTOR + " TEXT, " +
                    COLUMN_ACTRESS + " TEXT, " +
                    COLUMN_DIRECTOR + " TEXT, " +
                    COLUMN_YEAR_OF_RELEASE + " INTEGER" +
                    ")");

            insertMovie(statement,"Tenet", "Robert Pattinson", "Elizabeth Debicki", "Christopher Nolan", 2020);
            insertMovie(statement,"Inception", "Leonardo DiCaprio", "Elliot Page", "Christopher Nolan", 2010);
            insertMovie(statement,"Interstellar", "Matthew McConaughey", "Anne Hathaway", "Christopher Nolan", 2014);

            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_MOVIES);
            while(results.next()) {
                System.out.println("Movie Name = " + results.getString(COLUMN_NAME));
                System.out.println("Actor = " + results.getString(COLUMN_ACTOR));
                System.out.println("Actress = " + results.getString(COLUMN_ACTRESS));
                System.out.println("Director = " + results.getString(COLUMN_DIRECTOR));
                System.out.println("Year of Release = " + results.getInt(COLUMN_YEAR_OF_RELEASE));
                System.out.println();
            }
            System.out.println("*******************************************************");
            System.out.println("Enter an actor's name: ");
            String actorName = scanner.nextLine();
            results = statement.executeQuery("SELECT * FROM " + TABLE_MOVIES + " WHERE " + COLUMN_ACTOR + "='" + actorName + "'");
            while(results.next()) {
                System.out.println("Movie Name = " + results.getString(COLUMN_NAME));
                System.out.println("Actor = " + results.getString(COLUMN_ACTOR));
                System.out.println("Actress = " + results.getString(COLUMN_ACTRESS));
                System.out.println("Director = " + results.getString(COLUMN_DIRECTOR));
                System.out.println("Year of Release = " + results.getInt(COLUMN_YEAR_OF_RELEASE));
            }
            results.close();

            statement.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertMovie(Statement statement, String name, String actor, String actress, String director, int year) throws SQLException {
        statement.execute("INSERT INTO " + TABLE_MOVIES +
                "(" + COLUMN_NAME + ", " +
                COLUMN_ACTOR + ", " +
                COLUMN_ACTRESS + ", " +
                COLUMN_DIRECTOR + ", " +
                COLUMN_YEAR_OF_RELEASE +
                ") " +
                "VALUES(" + "'" + name + "'" + ", " + "'" + actor + "'" + ", " + "'" + actress + "'" + ", " + "'" + director + "'" + ", " + year + ")");
    }
}
