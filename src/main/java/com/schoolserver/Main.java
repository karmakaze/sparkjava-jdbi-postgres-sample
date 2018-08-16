package com.schoolserver;

import com.schoolserver.controller.SchoolClassController;
import com.schoolserver.controller.SchoolTimeTableController;
import com.schoolserver.controller.StudentController;
import com.schoolserver.service.SchoolClassService;
import com.schoolserver.service.SchoolTimeTableService;
import com.schoolserver.service.StudentService;
import org.sql2o.Sql2o;

import java.util.Optional;
import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

	String dbHost = Optional.ofNullable(System.getenv("OPENSHIFT_MYSQL_DB_HOST")).orElse("127.0.0.1");
	String dbPort = Optional.ofNullable(System.getenv("OPENSHIFT_MYSQL_DB_PORT")).orElse("5432");
	String dbUser = Optional.ofNullable(System.getenv("OPENSHIFT_MYSQL_DB_USERNAME")).orElse("postgres");
	String dbPass = Optional.ofNullable(System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD")).orElse("postgres");
	String dbName = "db_school";

	Sql2o sql2o = new Sql2o("jdbc:postgresql://"+ dbHost + ":" + dbPort + "/" + dbName +"?sslmode=disable", dbUser, dbPass);

        String host = Optional.ofNullable(System.getenv("OPENSHIFT_DIY_IP")).orElse("0.0.0.0");
        int port = Integer.parseInt(Optional.ofNullable(System.getenv("OPENSHIFT_DIY_PORT")).orElse("8080"));

        ipAddress(host);
        port(port);

        new StudentController(new StudentService(sql2o));
        new SchoolClassController(new SchoolClassService(sql2o));
        new SchoolTimeTableController(new SchoolTimeTableService(sql2o));
    }
}
