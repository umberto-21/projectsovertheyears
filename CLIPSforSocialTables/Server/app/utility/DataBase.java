package utility;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import microservices.battleship.persistence.dao.sql.SqlDaoFactoryImpl;
import play.mvc.Result;


import static play.mvc.Http.Context.Implicit.response;
import static play.mvc.Http.HeaderNames.CACHE_CONTROL;
import static play.mvc.Http.HeaderNames.CONTENT_TYPE;
import static play.mvc.Http.HeaderNames.ETAG;
import static play.mvc.Results.ok;


public class DataBase {


    private String executeQuery(String query){
        Connection connection = null;
        try{
            connection = SqlDaoFactoryImpl.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
           //play.Logger.info("eseguo " + query);
            String htmlStringResult = resultSetToStringHTML(resultSet);
            return htmlStringResult;

        }catch(MySQLIntegrityConstraintViolationException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(connection!=null){
                    connection.close();
                }//if
            }catch(SQLException e){
                e.printStackTrace();
            }//catch
        }//finally
        return "";
    }

    private void executeUpdate(String query){
        Connection connection = null;
        try{
            connection = SqlDaoFactoryImpl.createConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
           //play.Logger.info("eseguo " + query);

        }catch(MySQLIntegrityConstraintViolationException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(connection!=null){
                    connection.close();
                }//if
            }catch(SQLException e){
                e.printStackTrace();
            }//catch
        }//finally
    }

    public Result resetDB(){
/*        String [] queryArray = {
                "DELETE FROM BattleshipAttacks;",
        "DELETE FROM BattleshipCells;",
        "DELETE FROM BattleshipMatches;",
        "DELETE FROM BattleshipNumberOfShots;",
        "DELETE FROM BattleshipPositions;",
        "DELETE FROM BattleshipShipComponents;",
        "DELETE FROM BattleshipShips;",
        "DELETE FROM Beacons;",
        "DELETE FROM GameScores;",
        "DELETE FROM Games;",
        "DELETE FROM Locals;",
        "DELETE FROM Profiles;",
        "DELETE FROM Sessions;",
        "DELETE FROM Tables;",
        "DELETE FROM TeamLeaders;",
        "DELETE FROM Teams;"

        };*/

        String [] queryArray = {
                "CALL populateReset;",
                "CALL populateDefault;"
        };

        for(String query : queryArray){
            executeUpdate(query);
        }

        return seeAll();
    }

    public Result seeAll(){
        response().setHeader(CACHE_CONTROL, "max-age=0");
        String [] queryArray = {
        "SELECT * FROM `GameScores`;",
        "SELECT * FROM `Games`;",
        "SELECT * FROM `BattleshipShipComponents`;",
        "SELECT * FROM `BattleshipNumberOfShots`;",
        "SELECT * FROM `BattleshipShips`;",
        "SELECT * FROM `BattleshipPositions`;",
        "SELECT * FROM `BattleshipCells`;",
        "SELECT * FROM `Tables`;",
        "SELECT * FROM `Locals`;",
        "SELECT * FROM `BattleshipAttacks`;",
        "SELECT * FROM `Beacons`;",
        "SELECT * FROM `Sessions`;",
        "SELECT * FROM `Profiles`;",
        "SELECT * FROM `TeamLeaders`;",
        "SELECT * FROM `BattleshipMatches`;",
        "SELECT * FROM `Teams`;"
        };
        String [] tableTitle = {
                "GameScores",
                "Games",
                "BattleshipShipComponents",
                "BattleshipNumberOfShots",
                "BattleshipShips",
                "BattleshipPositions",
                "BattleshipCells",
                "Tables",
                "Locals",
                "BattleshipAttacks",
                "Beacons",
                "Sessions",
                "Profiles",
                "TeamLeaders",
                "BattleshipMatches",
                "Teams",
        };

        String resultString = "<!DOCTYPE html>\n";

        for(int i = 0; i<queryArray.length; ++i){
            String partialResult = executeQuery(queryArray[i]);
            resultString = resultString.concat("<div> <h1>" + tableTitle[i] + "</h1>" + partialResult + "</div>");
        }

        return ok(resultString).as("text/html");
    }

    private String resultSetToStringHTML(ResultSet resultSet){
        String resultString = "";
        if(resultSet == null){
           //play.Logger.info("resultSet == null");
            return resultString;
        }
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int countColumn = resultSetMetaData.getColumnCount();
            resultString = resultString.concat("<table border=1>\n");
            resultString = resultString.concat("<t>");
            for (int i=1; i<=countColumn; i++) {
                resultString = resultString.concat("<th>");
                resultString = resultString.concat(resultSetMetaData.getColumnLabel(i));
            }
            resultString = resultString.concat("</tr>\n");
            while (resultSet.next()) {
                resultString = resultString.concat("<tr>");
                for (int i=1; i<=countColumn; i++) {
                    resultString = resultString.concat("<td>");
                    String rowString = resultSet.getString(i);
                    if(rowString != null){
                       //play.Logger.info("rowString = " + rowString);
                        resultString = resultString.concat(rowString);
                    } else{

                       //play.Logger.info("rowString == null");
                    }
                }
                resultString = resultString.concat("</tr>\n");
            }
            resultString = resultString.concat("</table>\n");


/*            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = null;
                    columnValue = resultSet.getString(i);
                    resultString = resultString.concat(columnValue + " " + rsmd.getColumnName(i));
                }
                resultString = resultString.concat("\n");
            }*/

        } catch (SQLException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            resultString = errors.toString();
        }
        return  resultString;
    }
}
