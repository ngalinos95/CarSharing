package carsharing;


import java.sql.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws ClassNotFoundException {
        // set the JDBC DRIVER and the url path
          String JDBC_DRIVER = "org.h2.Driver";
          String url = "./src/carsharing/db/"+"carsharing";
         try {
             // STEP 1: Register JDBC driver
             Class.forName(JDBC_DRIVER);
             //STEP 2: Open a connection
            System.out.println("Connecting to database...");
             Connection conn= DriverManager.getConnection("jdbc:h2:"+url);
             conn.setAutoCommit(true);
             //STEP 3a:Create the table COMPANY with the proper statement and execute the statement
            System.out.println("Creating table in given database...");
             Statement st = conn.createStatement();
             String sql =  "CREATE TABLE IF NOT EXISTS COMPANY " +
                     "(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                     "NAME VARCHAR(255) NOT NULL UNIQUE)";
             st.executeUpdate(sql);
             //STEP 3b:Create the table CAR with the proper statement and execute the statement
             sql="CREATE TABLE IF NOT EXISTS CAR " +
                     "(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                     "NAME VARCHAR(255) NOT NULL UNIQUE, "+
                     "COMPANY_ID INT NOT NULL, "+
             "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";
             st.executeUpdate(sql);
             //STEP 3c:Create the table CUSTOMER with the proper statement and execute the statement
             sql="CREATE TABLE IF NOT EXISTS CUSTOMER " +
                     "(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                     "NAME VARCHAR(255) NOT NULL UNIQUE, "+
                     "RENTED_CAR_ID INT , "+
                     "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";
             st.executeUpdate(sql);

             //Create the menu
             //Scanner for the user input
             int exitInput=1;
             int secondInput=0;
             String companyName;
             Scanner sc=new Scanner(System.in);
             while(exitInput!=0) {
             if(secondInput==0){
             //Print the main menu
             System.out.println();
             System.out.println("1. Log in as a manager");
             System.out.println("2. Log in as a customer");
             System.out.println("3. Create a customer");
             System.out.println("0. Exit");
             exitInput=sc.nextInt();}
                 if(exitInput==0){
                     break;
                 }else if(exitInput==1){
                     System.out.println();
                     System.out.println("1. Company list");
                     System.out.println("2. Create a company");
                     System.out.println("0. Back");
                     secondInput=sc.nextInt();
                     /*sql="DELETE FROM CAR";
                     st.executeUpdate(sql);
                     sql="ALTER TABLE CAR ALTER COLUMN ID RESTART WITH "+1;
                     st.executeUpdate(sql);

                      */
                     if(secondInput==0){
                         //USER chooses 0.Back
                         continue;

                     }else if(secondInput==1){
                         //USER chooses 1.Company List
                         sql="SELECT "+
                                 "* "+
                             "FROM "+"COMPANY " +
                                 "ORDER BY ID ASC";

                         ResultSet rs=st.executeQuery(sql);
                         boolean empty=true;
                         System.out.println();
                         System.out.println("Choose the company:");
                         while (rs.next()) {
                             empty=false;
                             int numColumns = rs.getMetaData().getColumnCount();
                                 for (int i = 1; i <= numColumns; i++) {
                                     System.out.print(rs.getString(i));
                                     if (i < numColumns) {
                                         System.out.print(". ");
                                     }
                                 }
                                 System.out.println();
                             }
                         if(empty){
                             System.out.println("The company list is empty!");
                             System.out.println();

                         }else{
                             System.out.println("0. Back");
                             int companyID= sc.nextInt();
                             //Print the companyMenu
                                 if(companyID!=0) {
                                     int companyMenu = 1;
                                     while (companyMenu != 0){
                                     sql = "SELECT NAME FROM COMPANY WHERE ID = " + companyID;
                                     rs = st.executeQuery(sql);
                                     rs.next();
                                         System.out.println();
                                         System.out.println(rs.getString("NAME") + " company");
                                         System.out.println("1. Car list");
                                         System.out.println("2. Create a car");
                                         System.out.println("0. Back");
                                         companyMenu = sc.nextInt();
                                         if (companyMenu != 0) {
                                             if (companyMenu == 1) {
                                                 empty = true;
                                                 sql = "SELECT " +
                                                         "NAME " +
                                                         "FROM " + "CAR " +
                                                         "WHERE COMPANY_ID= " + companyID +
                                                         "ORDER BY ID ASC";
                                                 rs = st.executeQuery(sql);
                                                 int y=1;
                                                 while (rs.next()) {

                                                     empty = false;
                                                     int numCol = rs.getMetaData().getColumnCount();
                                                     for (int i = 1; i <= numCol; i++) {
                                                         System.out.println(y+". "+rs.getString(i));

                                                     }
                                                     y++;

                                                 }
                                                 if (empty) {
                                                     System.out.println("The car list is empty!");
                                                     System.out.println();
                                                 }
                                             }else if(companyMenu==2){
                                                 System.out.println();
                                                 System.out.println("Enter the car name:");
                                                 sc.nextLine();
                                                 String carName=sc.nextLine();
                                                 sql="INSERT INTO "+"CAR " +"(NAME,COMPANY_ID) "+
                                                         "VALUES ('"+carName+"', '"+companyID+"')";
                                                 st.executeUpdate(sql);
                                                 System.out.println("The car was added!");

                                             }

                                         }
                                     }
                                 }

                         }

                     }else {
                         //USER chooses 2.Create company
                         System.out.println("Enter the company name:");
                         sc.nextLine();
                         companyName=sc.nextLine();
                         sql="INSERT INTO "+"COMPANY " +"(NAME) "+
                                 "VALUES ('"+companyName+"')";
                         st.executeUpdate(sql);
                         System.out.println("The company was created!");
                     }
                 }else if(exitInput==2){
                     //user chooses to login as a costumer
                     boolean empty = true;
                     sql="SELECT ID, NAME "+
                             "FROM "+"CUSTOMER " +
                             "ORDER BY ID ASC";
                     ResultSet ms=st.executeQuery(sql);
                     System.out.println();
                     System.out.println("Choose a customer:");
                         while(ms.next()){
                             empty=false;
                             int numCol = ms.getMetaData().getColumnCount();
                             for (int i = 1; i <= numCol; i++) {
                                 System.out.print(ms.getString(i));
                                 if (i < numCol) {
                                     System.out.print(". ");
                                 }
                             }
                             System.out.println();
                         }
                         if(empty){
                             System.out.println("The customer list is empty!");
                         }else {
                             System.out.println("0. Back");
                             int costumerID = sc.nextInt();
                             if (costumerID != 0) {
                                 int costumerCar=4;
                                 while(costumerCar!=0){
                                     System.out.println();
                                     System.out.println("1. Rent a car");
                                     System.out.println("2. Return a rented car");
                                     System.out.println("3. My rented car");
                                     System.out.println("0. Back");

                                     costumerCar = sc.nextInt();
                                     //car options by the user input
                                     if (costumerCar == 1) {
                                         sql = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID= " + costumerID;
                                         ms = st.executeQuery(sql);
                                         ms.next();
                                         ms.getString("RENTED_CAR_ID");

                                         if (!ms.wasNull()) {
                                             System.out.println();
                                             System.out.println("You've already rented a car!");
                                         } else {
                                             sql = "SELECT " +
                                                     "* " +
                                                     "FROM " + "COMPANY " +
                                                     "ORDER BY ID ASC";

                                             ms = st.executeQuery(sql);
                                             empty = true;
                                             System.out.println();
                                             System.out.println("Choose the company:");
                                             while (ms.next()) {
                                                 empty = false;
                                                 int numColumns = ms.getMetaData().getColumnCount();
                                                 for (int i = 1; i <= numColumns; i++) {
                                                     System.out.print(ms.getString(i));
                                                     if (i < numColumns) {
                                                         System.out.print(". ");
                                                     }
                                                 }
                                                 System.out.println();
                                             }
                                             if (empty) {
                                                 System.out.println("The company list is empty!");
                                                 System.out.println();

                                             } else {
                                                 System.out.println("0. Back");
                                                 int companyID = sc.nextInt();
                                                 System.out.println();
                                                 System.out.println("Choose a car:");
                                                 empty = true;
                                                 sql = "SELECT CAR.ID, CAR.NAME, CAR.COMPANY_ID  FROM CAR LEFT JOIN CUSTOMER ON CAR.ID = CUSTOMER.RENTED_CAR_ID WHERE CUSTOMER.NAME IS NULL";
                                                 ms = st.executeQuery(sql);
                                                 int y = 1;
                                                 while (ms.next()) {
                                                     empty = false;
                                                     int numCol = ms.getMetaData().getColumnCount();
                                                     for (int i = 1; i <= numCol; i++) {
                                                         System.out.println(y + ". " + ms.getString(i));

                                                     }
                                                     y++;

                                                 }
                                                 if (empty) {
                                                     System.out.println("The car list is empty!");
                                                     System.out.println();
                                                 }
                                                 System.out.println("0.Back");

                                                 int carID=sc.nextInt();
                                                 if(carID!=0){
                                                    sql="SELECT NAME FROM CAR WHERE ID= "+carID;
                                                     ms=st.executeQuery(sql);
                                                     ms.next();
                                                     System.out.println();
                                                     System.out.println("You rented '"+ms.getString("NAME")+"'");
                                                     sql="UPDATE CUSTOMER SET RENTED_CAR_ID="+carID+" WHERE ID=" +costumerID;
                                                     st.executeUpdate(sql);


                                                 }



                                             }



                                         }

                                     } else if (costumerCar == 2) {
                                         sql = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID= " + costumerID;
                                         ms = st.executeQuery(sql);
                                         ms.next();
                                         ms.getString("RENTED_CAR_ID");

                                         if (ms.wasNull()) {
                                             System.out.println();
                                             System.out.println("You didn't rent a car!");
                                         } else {
                                             sql="UPDATE CUSTOMER SET RENTED_CAR_ID=NULL WHERE ID=" +costumerID;
                                             st.executeUpdate(sql);
                                             System.out.println("You've returned a rented car!");

                                         }


                                     } else if (costumerCar == 3) {
                                         sql = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID= " + costumerID;
                                         ms = st.executeQuery(sql);
                                         ms.next();
                                         String carD=ms.getString("RENTED_CAR_ID");

                                         if (ms.wasNull()) {
                                             System.out.println();
                                             System.out.println("You didn't rent a car!");
                                         } else {
                                             System.out.println();
                                             System.out.println("Your rented car:");
                                             sql = "SELECT NAME, COMPANY_ID FROM CAR WHERE ID="+carD;
                                             ResultSet rs=st.executeQuery(sql);
                                             rs.next();
                                             System.out.println(rs.getString("NAME"));
                                             System.out.println("Company:");
                                             sql = "SELECT NAME FROM COMPANY WHERE ID=" + rs.getString("COMPANY_ID");
                                             rs=st.executeQuery(sql);
                                             rs.next();
                                             System.out.println(rs.getString("NAME"));
                                         }


                                     }

                                 }
                             }
                         }


                 }
                 else if(exitInput==3){
                     //user chooses create a costumer
                     System.out.println();
                     System.out.println("Enter the customer name:");
                     sc.nextLine();
                     String costumerName=sc.nextLine();
                     sql="INSERT INTO "+"CUSTOMER " +"(NAME,RENTED_CAR_ID) "+
                             "VALUES ('"+costumerName+"',NULL)";
                     st.executeUpdate(sql);
                     System.out.println("The customer was added!");

                 }
             }
             //exiting the while loop when user chooses 0. Exit

             //STEP 4: Close the statement and the connection
             st.close();
            conn.close();
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
    }
}
