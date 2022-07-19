package fr.sulfuris.dev.data;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.common.collect.ImmutableMap;
import com.ptsmods.mysqlw.Database;
import com.ptsmods.mysqlw.query.QueryCondition;
import com.ptsmods.mysqlw.query.SelectResults;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class DatabaseUser {
    private static String usersTable = "users";
    private static String[] userDrfiner = new String[]{"id", "name", "email", "email_verified_at", "password", "role_id", "money", "game_id", "avatar", "access_token", "two_factor_secret", "two_factor_recovery_codes", "last_login_ip", "last_login_at", "is_banned", "remember_token", "created_at", "updated_at", "deleted_at"};

    public static String sqlHost = "31.32.183.114";
    public static int sqlPort = 3306;
    public static String sqlName = "azuriom";
    public static String sqlUsername = "azuriom";
    public static String sqlPassword = "LL6MN$kxT*wQ34B4F&en4YwhT";
    public static Database db;

    public static void dbSetup() throws SQLException {
        try {
            System.out.println("Connecting to database...");
            Database.loadConnector(Database.RDBMS.MySQL, "8.0.23", new File("mysql-connector-java.jar"), true);
            db = Database.connect(sqlHost, sqlPort, sqlName, sqlUsername, sqlPassword);
            System.out.println("Connected to database.");
        } catch (IOException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
    public static DatabaseUser getFromName(String user){
        SelectResults results = db.select(usersTable, "*", QueryCondition.equals("name", user));
        if(results.iterator().hasNext()){
            SelectResults.SelectResultRow row = results.iterator().next();
            return new DatabaseUser(
                    String.valueOf(row.get("id")),
                    String.valueOf(row.get("name")),
                    String.valueOf(row.get("email")),
                    String.valueOf(row.get("email_verified_at")),
                    String.valueOf(row.get("password")),
                    String.valueOf(row.get("role_id")),
                    String.valueOf(row.get("money")),
                    String.valueOf(row.get("game_id")),
                    String.valueOf(row.get("avatar")),
                    String.valueOf(row.get("access_token")),
                    String.valueOf(row.get("two_factor_secret")),
                    String.valueOf(row.get("two_factor_recovery_codes")),
                    String.valueOf(row.get("last_login_ip")),
                    String.valueOf(row.get("last_login_at")),
                    String.valueOf(row.get("is_banned")),
                    String.valueOf(row.get("remember_token")),
                    String.valueOf(row.get("created_at")),
                    String.valueOf(row.get("updated_at")),
                    String.valueOf(row.get("deleted_at")));
        }
        return null;
    }
    
    public static void registerUser(int id, String name, String email, String password, int roleId, long money, String gameId, String avatar, String accessToken, String twoFactorSecret, String twoFactorRecoveryCodes, String lastLoginIp, String lastLoginAt, int isBanned, String rememberToken, String createdAt, String updatedAt, String deletedAt) {
        db.insert(usersTable, userDrfiner, new String[]{String.valueOf(id), name, email, "NULL", BCrypt.withDefaults().hashToString(12, password.toCharArray()), String.valueOf(1), String.valueOf(money), gameId, avatar, accessToken, twoFactorSecret, twoFactorRecoveryCodes, lastLoginIp, lastLoginAt, String.valueOf(isBanned), rememberToken, createdAt, updatedAt, deletedAt});
        System.out.println("Added azuriom " + name + " to database.");
    }
    
    public static Boolean doesUserexist(String name) {
        return db.select("SELECT * FROM " + usersTable + " WHERE name = ?", new String[]{name}).iterator().hasNext();
    }
    
    public static void updateUser(String name, String selectValue, Object value){
        db.update(usersTable, ImmutableMap.<String, Object>builder().put(selectValue, value).build(), QueryCondition.equals("name", name));
    }
    private String id;
    private String name;
    private String email;
    private String email_verified_at;
    private String password;
    private String roleId;
    private Float money;
    private String gameId;
    private String avatar;
    private String accessToken;
    private String twoFactorSecret;
    private String twoFactorRecoveryCodes;
    private String lastLoginIp;
    private String lastLoginAt;
    private String isBanned;
    private String rememberToken;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public DatabaseUser(String id, String name, String email, String email_verified_at, String password, String roleId, String money, String gameId, String avatar, String accessToken, String twoFactorSecret, String twoFactorRecoveryCodes, String lastLoginIp, String lastLoginAt, String isBanned, String rememberToken, String createdAt, String updatedAt, String deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.password = password;
        this.roleId = roleId;
        this.money = Float.valueOf(money);
        this.gameId = gameId;
        this.avatar = avatar;
        this.accessToken = accessToken;
        this.twoFactorSecret = twoFactorSecret;
        this.twoFactorRecoveryCodes = twoFactorRecoveryCodes;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginAt = lastLoginAt;
        this.isBanned = isBanned;
        this.rememberToken = rememberToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String toString(){
        return "id: " + id + "\n" +
                "name: " + name + "\n" +
                "email: " + email + "\n" +
                "email_verified_at: " + email_verified_at + "\n" +
                "password: " + password + "\n" +
                "roleId: " + roleId + "\n" +
                "money: " + money + "\n" +
                "gameId: " + gameId + "\n" +
                "avatar: " + avatar + "\n" +
                "accessToken: " + accessToken + "\n" +
                "twoFactorSecret: " + twoFactorSecret + "\n" +
                "twoFactorRecoveryCodes: " + twoFactorRecoveryCodes + "\n" +
                "lastLoginIp: " + lastLoginIp + "\n" +
                "lastLoginAt: " + lastLoginAt + "\n" +
                "isBanned: " + isBanned + "\n" +
                "rememberToken: " + rememberToken + "\n" +
                "createdAt: " + createdAt + "\n" +
                "updatedAt: " + updatedAt + "\n" +
                "deletedAt: " + deletedAt + "\n";
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        updateUser(this.name, "name", name);
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
        updateUser(this.name, "email", email);
    }
    public String getEmail_verified_at() {
        return email_verified_at;
    }
    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
        updateUser(this.name, "email_verified_at", email_verified_at);
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
        updateUser(this.name, "password", password);
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
        updateUser(this.name, "roleId", roleId);
    }
    public Float getMoney() {
        return money;
    }
    public void setMoney(Float money) {
        this.money = money;
        updateUser(this.name, "money", money);
    }
    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
        updateUser(this.name, "gameId", gameId);
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
        updateUser(this.name, "avatar", avatar);
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        updateUser(this.name, "accessToken", accessToken);
    }
    public String getTwoFactorSecret() {
        return twoFactorSecret;
    }
    public void setTwoFactorSecret(String twoFactorSecret) {
        this.twoFactorSecret = twoFactorSecret;
        updateUser(this.name, "twoFactorSecret", twoFactorSecret);
    }
    public String getTwoFactorRecoveryCodes() {
        return twoFactorRecoveryCodes;
    }
    public void setTwoFactorRecoveryCodes(String twoFactorRecoveryCodes) {
        this.twoFactorRecoveryCodes = twoFactorRecoveryCodes;
        updateUser(this.name, "twoFactorRecoveryCodes", twoFactorRecoveryCodes);
    }
    public String getLastLoginIp() {
        return lastLoginIp;
    }
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
        updateUser(this.name, "last_login_ip", lastLoginIp);
    }
    public String getLastLoginAt() {
        return lastLoginAt;
    }
    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        updateUser(this.name, "lastLoginAt", lastLoginAt);
    }
    public String getIsBanned() {
        return isBanned;
    }
    public void setIsBanned(String isBanned) {
        this.isBanned = isBanned;
        updateUser(this.name, "isBanned", isBanned);
    }
    public String getRememberToken() {
        return rememberToken;
    }
    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
        updateUser(this.name, "rememberToken", rememberToken);
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        updateUser(this.name, "createdAt", createdAt);
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        updateUser(this.name, "updatedAt", updatedAt);
    }
    public String getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        updateUser(this.name, "deletedAt", deletedAt);
    }
}
