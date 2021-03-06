package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@Controller
//@RequestMapping("/")
public class HelloController {

    int amountPage = 0;
    String error = "";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, HttpServletRequest request) {
        String currentPage = request.getParameter("Page");
        ArrayList<Box> arrL;
        if (currentPage != null) {
            arrL = getDataList(Integer.parseInt(currentPage) - 1);
            model.addAttribute("Cpage", Integer.parseInt(currentPage));
        } else {
            arrL = getDataList(0);
            model.addAttribute("Cpage", 1);
        }
        model.addAttribute("arrL", arrL);
        model.addAttribute("msg", "Information");
        model.addAttribute("Apage", amountPage);
        model.addAttribute("error", error);
//        model.addAttribute("message", database());
        return "hello";
    }

    @RequestMapping(value = "/result.html", method = RequestMethod.POST)
    public String resultPage(ModelMap model, @RequestParam("message") String message, @RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("name:"+name+" , msg:"+message);
        if ( isValidate(request, response) && paramNotEqual(name,message) ) {
            insertQuery(message,name);
            this.error = "";
        }
        else if ( !paramNotEqual(name,message) ){
            this.error = "error : name and message must be difference";
        }
        else if ( !isValidate(request,response)  ){
            this.error = "error : no captcha";
        }
        return "redirect:/";
    }

    private void insertQuery( String message, String name ){
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "";
        String password = "";
        Connection connection;

        System.out.println("Connecting database...");

        try {
            connection = DriverManager.getConnection(url, username, password);
            try {
                System.out.println("Database connected!");

                Statement stmt = connection.createStatement();

                String sql = "insert into box (box_message, box_name)values('" + message + "', '" + name + "')";
                stmt.executeUpdate(sql);

            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        System.out.println("Goodbye!");
    }

    private boolean paramNotEqual( String name, String message ) {
        if( name.equals(message) ){
            return false;
        }
        return true;
    }

    private ArrayList getDataList(int currentPage) {
        ArrayList<Box> pageList = new ArrayList<Box>();

        String url = "jdbc:mysql://localhost:3306/test";
        String username = "";
        String password = "";
        Connection connection;

        System.out.println("Connecting database...");

        try {
            connection = DriverManager.getConnection(url, username, password);
            try {
                System.out.println("Database connected!");
                Statement stmt = connection.createStatement();

                ResultSet countRow = stmt.executeQuery("select count(box_id) from box");
                countRow.next();
                int numRow = countRow.getInt("count(box_id)");
                System.out.println(numRow);
                int max = 10;
                calPageNumber(numRow, max);

                String index = (currentPage * max) + "";
                ResultSet r = stmt.executeQuery("select * from box order by box_id DESC LIMIT " + index + "," + max);
                Box box = null;
                while (r.next()) {
                    box = new Box();
                    box.setId(r.getInt("box_id"));
                    box.setMsg(r.getString("box_message"));
                    box.setName(r.getString("box_name"));
                    pageList.add(box);
                }


            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        return pageList;
    }

    private void calPageNumber(int all, double limit) {
        amountPage = (int) Math.ceil(all / limit);
        System.out.println(amountPage);
    }

    protected boolean isValidate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get reCAPTCHA request param
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        System.out.println(gRecaptchaResponse);
        return VerifyReCaptcha.verify(gRecaptchaResponse);
    }
}

