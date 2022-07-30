package DataBase;

import java.util.Scanner;

public class UserRepository {
    private static final Datainitializer data = new Datainitializer();

    //private static final UserPage userpage = new UserPage()

    public static final Scanner scanner = new Scanner(System.in);

    public static final Scanner numberscanner = new Scanner(System.in);

    public static Datainitializer getData() {
        return data;
    }
}
