package com.roton.assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
		
	@GetMapping("/topping")
	public ArrayList topping() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment","root","admin1");
		Statement stmt=con.createStatement();
		String fatchquery="select * from assignment.toppings";
		ResultSet rs=stmt.executeQuery(fatchquery);
		ArrayList<Object> list=new ArrayList<Object>();
		while(rs.next()) {
			HashMap hm=new HashMap();
			hm.put("Topping Name: ",rs.getString("toppingname"));
			hm.put("PizzaPrice :",rs.getInt("price"));
			list.add(hm);
		}
		return list;
	}
	
	@GetMapping("/specialpizzalist")
	public ArrayList specialPizzaList() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment","root","admin1");
		String query="select * from assignment.specialpizzalist";
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		ArrayList list=new ArrayList();
		while(rs.next()) {
			HashMap hm=new HashMap();
			hm.put("Pizza Name: ",rs.getString("pizzaname"));
			hm.put("PizzaPrice :",rs.getInt("price"));
			list.add(hm);
		}
		
		return list;
	}
	
	@PutMapping("/specialpizzaorder")
	public String specialPizzaOder(HttpServletRequest req) throws SQLException, ClassNotFoundException {
		String customerid =req.getParameter("customerid");
		int customer_id=Integer.parseInt(customerid);	
		String pizza_name=req.getParameter("pizzaname");
		String paraprice =req.getParameter("price");
		int price=Integer.parseInt(paraprice);		
		String size=req.getParameter("size");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment","root","admin1");
		String query="select * from assignment.specialpizzalist";
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		ArrayList list=new ArrayList();
//		while(rs.next()) {
//			if(rs.getString("pizzaname")==pizza_name) {
//				price=rs.getInt("price");
//				break;
//			}
//		}
//		if(size=="small") {
//			price=(int)(price*0.75);
//		}
//		if(size=="small") {
//			price=(int)(price*1.25);
//		}
		String query1="insert into assignment.order(customerid ,pizzaname, size, price) values(?, ? ,?, ?)";
		PreparedStatement pstmt=con.prepareStatement(query1);
		pstmt.setInt(1, customer_id);
		pstmt.setString(2, pizza_name);
		pstmt.setString(3, size);
		pstmt.setInt(4, price);
		int rs1=pstmt.executeUpdate();
		if(rs1>=1) {
			return "added successfully";
		}
		return "something went wrong";
	}
	
	@GetMapping("/orderlist")
	public ArrayList orderList() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment","root","admin1");
		Statement stmt=con.createStatement();
		String fatchquery="select * from assignment.order";
		ResultSet rs=stmt.executeQuery(fatchquery);
		ArrayList<Object> list=new ArrayList<Object>();
		while(rs.next()) {
			HashMap hm=new HashMap();
			hm.put("pizza Name: ",rs.getString("pizzaname"));
			hm.put("Price :",rs.getInt("price"));
			list.add(hm);
		}
		return list;
	}
	
	
	
}
