package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;

@Controller
//@RequestMapping("/")
public class HelloController {

    int amountPage = 0;

	@RequestMapping( value="/", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, HttpServletRequest request) {
        String currentPage = request.getParameter("Page");
        ArrayList<Box> arrL;
        if ( currentPage != null ) {
            arrL = getDataList(Integer.parseInt(currentPage) - 1);
            model.addAttribute("Cpage", Integer.parseInt(currentPage));
        }
        else {
            arrL = getDataList(0);
            model.addAttribute("Cpage", 1);
        }
        model.addAttribute("arrL", arrL);
        model.addAttribute("msg", "Information");
        model.addAttribute("Apage", amountPage);
//        model.addAttribute("message", database());
		return "hello";
	}

    @RequestMapping(value="/result.html",method = RequestMethod.POST)
    public String resultPage(ModelMap model, @RequestParam("message") String message, @RequestParam("name") String name) {
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

                String sql = "insert into box (box_message, box_name)values('" + message + "', '"+ name +"')";
                stmt.executeUpdate(sql);

            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        System.out.println("Goodbye!");
        return "redirect:/";
    }

    private ArrayList getDataList( int currentPage ){
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

                String index = (currentPage*max)+"";
                ResultSet r = stmt.executeQuery("select * from box order by box_id DESC LIMIT "+index+","+max);
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

    private void calPageNumber( int all, double limit ){
        amountPage = (int)Math.ceil(all/limit);
        System.out.println(amountPage);
    }


}
