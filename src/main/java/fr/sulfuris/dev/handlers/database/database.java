package fr.sulfuris.dev.handlers.database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.common.collect.ImmutableMap;
import com.ptsmods.mysqlw.Database;
import com.ptsmods.mysqlw.query.QueryCondition;
import com.ptsmods.mysqlw.query.SelectResults;
import fr.sulfuris.dev.definers.databaseUser;
import fr.sulfuris.dev.handlers.logger.logger;
import fr.sulfuris.dev.main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class database {

    //'azuriom'@'127.0.0.1' IDENTIFIED BY 'LL6MN$kxT*wQ34B4F&en4YwhT';

    public static String sqlHost = "31.32.183.114";
    public static int sqlPort = 3306;
    public static String sqlName = "azuriom";
    public static String sqlUsername = "azuriom";
    public static String sqlPassword = "LL6MN$kxT*wQ34B4F&en4YwhT";




    public static Database db;

    public static void dbSetup() throws SQLException {
        try {
            logger.log("Connecting to database...");
            Database.loadConnector(Database.RDBMS.MySQL, "8.0.23", new File("mysql-connector-java.jar"), true);
            db = Database.connect(sqlHost, sqlPort, sqlName, sqlUsername, sqlPassword);
            logger.log("Connected to database.");
        } catch (IOException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
}
