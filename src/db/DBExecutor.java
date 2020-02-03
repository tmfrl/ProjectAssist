package db;

import util.DBUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class DBExecutor {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.println("쿼리를 실행할 타입을 선택하세요.");
        System.out.println("[1]쿼리 입력  [2]파일");

        String type = sc.nextLine();

        while(true) {
            switch (type) {
                case "1" :
                    System.out.println("Please Enter Query.");

                    String readQuery = sc.nextLine();
                    System.out.println("!!!"+readQuery);

                    ExecuteQuery(readQuery);

                    break;
                case "2" :
                    System.out.println("파일안에 실행 쿼리는 라인피드로 구분하여 실행합니다.");
                    System.out.println("파일명을 입력하세요.");

                    String readQueryFile = sc.nextLine();

                    ExecuteQueryFile(readQueryFile);

                    break;

                default :
                    System.out.println("잘못 입력했습니다. 다시 입력해주세요.\n");

                    type = sc.nextLine();

                    break;
            }
        }
    }

    private static void ExecuteQuery(String readQuery) {
        readQuery = readQuery.replaceAll(";", "");

        Connection con = DBUtil.getConnection();

        if(readQuery.startsWith("select") || readQuery.startsWith("SELECT")) {
            try {
                File queryResultFile = new File("./query/result.txt");
                FileOutputStream fos = new FileOutputStream(queryResultFile);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(readQuery);
                ResultSetMetaData rsmd = rs.getMetaData();

                LinkedList<String> columnNameList = DBUtil.getColumnNames(rsmd);

                int currentRowNum = 1;

                while (rs.next()) {
                    System.out.println("############### [" + currentRowNum + "] ###############");
                    fos.write(("############### [" + currentRowNum++ + "] ###############").getBytes());

                    for (int i = 0; i < columnNameList.size(); i++) {
                        String columnName = columnNameList.get(i);

                        System.out.println(columnName + " = [" + rs.getString(columnName) + "]");
                        fos.write((columnName + " = [" + rs.getString(columnName) + "]").getBytes());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

        }
    }

    private static void ExecuteQueryFile(String readQueryFile) {
    }

}
