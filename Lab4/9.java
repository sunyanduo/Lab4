import java.sql.Connection;//.jav
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import antlr.collections.List;

import com.opensymphony.xwork2.ActionSupport ;

public class myAction extends ActionSupport{

	final String DriverName = "com.mysql.jdbc.Driver";
	final String url = "jdbc:mysql://127.0.0.1:3306/bookdatabase";
	final String user = "root";
	final String password = "19930903";

	private String searchBookName;
	private String searchAuthorName;
	private String searchAuthorID;
	private String searchAuthorInfoResult; //id
	
	private String deleteBookName;
	
	private String renewBookName;
	
	private String name;
	private String bookinfo;
	
	ResultofBook addInfo = new ResultofBook();
	private String addtitle;
	private String addauthorname;
	private String addauthorage;
	private String addauthorcountry;
	private String addpublisher;
	private String addpublishdate;
	private String addprice;
	
	private String newauthorid;
	private String newpublisher;
	private String newpublishdate;
	private String newprice;

	private String formarauthorid;
	private String formarpublisher;
	private String formarpublishdate;
	private String formarprice;
	
	ArrayList<ResultofBook> searchAuthorInfo =new ArrayList<ResultofBook>();	
	ArrayList<String> BookList = new ArrayList<String>();
	
	public String searchBook (){
			boolean found = false;
		try{
			Class.forName(DriverName);
			Connection conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed()) 
	             System.out.println("Succeeded connecting to the Database!");
	        Statement statement = conn.createStatement();
	        	        
	     	String sql = "select * from bookinfo where title='"+getSearchBookName()+"'";
	        ResultSet Result = statement.executeQuery(sql);
	        System.out.println(sql);
	        while(Result.next()){
	        	found = true;
	        		bookinfo = "ISBN:"+Result.getString("isbn")+";   "+
	        				"TITLE:"+Result.getString("title")+";   "+
	        				"Author ID:"+Result.getString("authorid")+";   "+
	        				"Publisher:"+Result.getString("publisher")+";   "+
	        				"Publish Date:"+Result.getString("publishdate")+";   "+
	        				"Price: "+Result.getString("price");
	        		bookinfo = new String(bookinfo.getBytes("ISO-8859-1"),"GB2312");
	        }
            Result.close();
            conn.close();
            
           } catch(ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!"); 
            e.printStackTrace();
           } catch(SQLException e) {
        	   e.printStackTrace();
           } catch(Exception e) {
            e.printStackTrace();
		}
		if(found) return "success";
        else return "failed";
	}
	
	public String searchAuthor(){
		boolean found = false;
		try{
			Class.forName(DriverName);
			Connection conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed()) 
	             System.out.println("Succeeded connecting to the Database!");
	        
			Statement statement = conn.createStatement();
	     	String sql = "select * from authorinfo where name='"+getSearchAuthorName()+"'";
	        ResultSet Result = statement.executeQuery(sql);
	        while(Result.next()){
	        	found=true;
	        	searchAuthorID = Result.getString("authorid");
	        	searchAuthorInfoResult ="NAME: "+Result.getString("name")+";  AGE: "
	        							+Result.getString("age")+";  COUNTRY: "+Result.getString("country");
	        }
	        
	        if(found){
	        	Statement statement1 = conn.createStatement();
	        	String sql1 = "select * from bookinfo where authorid="+getSearchAuthorID();
	        	ResultSet Result1 = statement1.executeQuery(sql1);
	        	while(Result1.next()){
	        		ResultofBook item = new ResultofBook();
	        		item.setIsbn(Result1.getString("isbn"));
	        		item.setTitle(Result1.getString("title"));
	        		item.setPublisher(Result1.getString("publisher"));
	        		item.setPublishdate(Result1.getString("publishdate"));
	        		item.setPrice(Result1.getString("price"));
	        		searchAuthorInfo.add(item);
	        	}
	        	Result1.close();
	        }
            Result.close();
            conn.close();
           } catch(ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!"); 
            e.printStackTrace();
           } catch(SQLException e) {
        	   e.printStackTrace();
           } catch(Exception e) {
            e.printStackTrace();
		}
		if(found) return "success";
        else return "failed";
	}
	
	public String listBook(){
		boolean found = false;
		try{
			Class.forName(DriverName);
			Connection conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed()) 
	             System.out.println("Succeeded connecting to the Database!");
	        Statement statement = conn.createStatement();
	        
	     	String sql = "select * from bookinfo";
	        ResultSet Result = statement.executeQuery(sql);
            	        
	        while(Result.next()){
	        	found = true;
	        	String item = new String(Result.getString("title"));
	        	BookList.add(item);
	        }
            Result.close();
            conn.close();            
           } catch(ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!"); 
            e.printStackTrace();
           } catch(SQLException e) {
        	   e.printStackTrace();
           } catch(Exception e) {
            e.printStackTrace();
		}
		if(found) return "success";
        else return "failed";
	}

	public String deleteBook(){
		try{
			Class.forName(DriverName);
			Connection conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed()) 
	             System.out.println("Succeeded connecting to the Database!");
	        Statement statement = conn.createStatement();
	     	String sql2 = "delete from bookinfo where title='"+getDeleteBookName()+"'";
	        statement.executeUpdate(sql2);
            conn.close();
           } catch(ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!"); 
            e.printStackTrace();
           } catch(SQLException e) {
            e.printStackTrace();
           } catch(Exception e) {
            e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String addBook(){
		boolean found = false;
		String findAuthorId;
		try{
			Class.forName(DriverName);
			Connection conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed()) 
	             System.out.println("Succeeded connecting to the Database!");
	        
			Statement statement1=conn.createStatement(); //Find Author
			String sql1 = "select * from authorinfo where name='"+getAddauthorname()+"'";
			ResultSet Result1 = statement1.executeQuery(sql1);
			
			while(Result1.next()){
				found = true;
				findAuthorId = Result1.getString("authorid");
				Statement statement2 = conn.createStatement();
				
				addInfo.setAuthorid(findAuthorId);
				addInfo.setPrice(getAddprice());
				addInfo.setPublishdate(getAddpublishdate());
				addInfo.setPublisher(getAddpublisher());
				addInfo.setTitle(getAddtitle());
				
		     	String sql2 = "insert into bookinfo (title,authorid,publisher,publishdate,price) values " +
     			"('"+getAddInfo().getTitle()+"',"+getAddInfo().getAuthorid()+",'"+getAddInfo().getPublisher()+"','"+
     			getAddInfo().getPublishdate()+"',"+getAddInfo().getPrice()+")";
		     	statement2.executeUpdate(sql2);
			}
			Result1.close();
            conn.close();
            
           } catch(ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!"); 
            e.printStackTrace();
           } catch(SQLException e) {
        	   e.printStackTrace();
           } catch(Exception e) {
            e.printStackTrace();
		}
		if(found) return "success";
		else return "failed";

	}
	
	public String addAuthor(){
		try{
			Class.forName(DriverName);
			Connection conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed()) 
	             System.out.println("Succeeded connecting to the Database!");
	       Statement statement1 = conn.createStatement();
	       String sql1 = "insert into authorinfo (name,age,country) values ('"+getAddauthorname()+"',"
	    		   		+getAddauthorage()+",'"+getAddauthorcountry()+"')";
			statement1.executeUpdate(sql1);
			System.out.println(sql1);
            conn.close();
           } catch(ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!"); 
            e.printStackTrace();
           } catch(SQLException e) {
        	   e.printStackTrace();
           } catch(Exception e) {
            e.printStackTrace();
		}
		return "success";
	}
	
	public String renewBook(){
		try{
			Class.forName(DriverName);
			Connection conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed()) 
	             System.out.println("Succeeded connecting to the Database!");
	        Statement statement0 = conn.createStatement();
	        
	        String sql0 = "select * from bookinfo where title='"+getRenewBookName()+"'";
	        ResultSet Result = statement0.executeQuery(sql0);
	        while(Result.next()){
	        	formarauthorid = Result.getString("authorid");
	        	formarprice = Result.getString("price");
	        	formarpublishdate = Result.getString("publishdate");
	        	formarpublisher = Result.getString("publisher");
	        }
	        if(newauthorid.isEmpty()) newauthorid = formarauthorid;
	        if(newpublisher.isEmpty()) newpublisher = formarpublisher;
	        if(newpublishdate.isEmpty()) newpublishdate = formarpublishdate;
	        if(newprice.isEmpty()) newprice = formarprice;
	        
			Statement statement1 = conn.createStatement();
	        
	     	String sql1 = "update bookinfo set authorid='"+newauthorid+"',publisher='"+newpublisher+
	     			"',publishdate='"+newpublishdate+"',price="+newprice+
	     			" where title='"+renewBookName+"'";
	        statement1.executeUpdate(sql1);
            Result.close();
            conn.close();
           } catch(ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!"); 
            e.printStackTrace();
           } catch(SQLException e) {
        	   e.printStackTrace();
           } catch(Exception e) {
            e.printStackTrace();
		}
		return "success";
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSearchBookName() {
		return searchBookName;
	}

	public void setSearchBookName(String searchBookName) {
		this.searchBookName = searchBookName;
	}

	public String getBookinfo() {
		return bookinfo;
	}

	public void setBookinfo(String bookinfo) {
		this.bookinfo = bookinfo;
	}
	
	public String getSearchAuthorName() {
		return searchAuthorName;
	}

	public void setSearchAuthorName(String searchAuthorName) {
		this.searchAuthorName = searchAuthorName;
	}
	
	public ArrayList<ResultofBook> getSearchAuthorInfo() {
		return searchAuthorInfo;
	}

	public void setSearchAuthorInfo(ArrayList<ResultofBook> searchAuthorInfo) {
		this.searchAuthorInfo = searchAuthorInfo;
	}

	public ArrayList<String> getBookList() {
		return BookList;
	}

	public void setBookList(ArrayList<String> bookList) {
		BookList = bookList;
	}

	public String getDeleteBookName() {
		return deleteBookName;
	}

	public void setDeleteBookName(String deleteBookName) {
		this.deleteBookName = deleteBookName;
	}

	public ResultofBook getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(ResultofBook addInfo) {
		this.addInfo = addInfo;
	}

	public String getAddtitle() {
		return addtitle;
	}

	public void setAddtitle(String addtitle) {
		this.addtitle = addtitle;
	}

	public String getAddauthorname() {
		return addauthorname;
	}

	public void setAddauthorname(String addauthorname) {
		this.addauthorname = addauthorname;
	}

	public String getAddpublisher() {
		return addpublisher;
	}

	public void setAddpublisher(String addpublisher) {
		this.addpublisher = addpublisher;
	}

	public String getAddpublishdate() {
		return addpublishdate;
	}

	public void setAddpublishdate(String addpublishdate) {
		this.addpublishdate = addpublishdate;
	}

	public String getAddprice() {
		return addprice;
	}

	public void setAddprice(String addprice) {
		this.addprice = addprice;
	}

	public String getNewauthorid() {
		return newauthorid;
	}

	public void setNewauthorid(String newauthorid) {
		this.newauthorid = newauthorid;
	}

	public String getNewpublisher() {
		return newpublisher;
	}

	public void setNewpublisher(String newpublisher) {
		this.newpublisher = newpublisher;
	}

	public String getNewpublishdate() {
		return newpublishdate;
	}

	public void setNewpublishdate(String newpublishdate) {
		this.newpublishdate = newpublishdate;
	}

	public String getNewprice() {
		return newprice;
	}

	public void setNewprice(String newprice) {
		this.newprice = newprice;
	}

	public String getRenewBookName() {
		return renewBookName;
	}

	public void setRenewBookName(String renewBookName) {
		this.renewBookName = renewBookName;
	}

	public String getFormarauthorid() {
		return formarauthorid;
	}

	public void setFormarauthorid(String formarauthorid) {
		this.formarauthorid = formarauthorid;
	}

	public String getFormarpublisher() {
		return formarpublisher;
	}

	public void setFormarpublisher(String formarpublisher) {
		this.formarpublisher = formarpublisher;
	}

	public String getFormarpublishdate() {
		return formarpublishdate;
	}

	public void setFormarpublishdate(String formarpublishdate) {
		this.formarpublishdate = formarpublishdate;
	}

	public String getFormarprice() {
		return formarprice;
	}

	public void setFormarprice(String formarprice) {
		this.formarprice = formarprice;
	}

	public String getSearchAuthorID() {
		return searchAuthorID;
	}

	public void setSearchAuthorID(String searchAuthorID) {
		this.searchAuthorID = searchAuthorID;
	}

	public String getSearchAuthorInfoResult() {
		return searchAuthorInfoResult;
	}

	public void setSearchAuthorInfoResult(String searchAuthorInfoResult) {
		this.searchAuthorInfoResult = searchAuthorInfoResult;
	}

	public String getAddauthorage() {
		return addauthorage;
	}

	public void setAddauthorage(String addauthorage) {
		this.addauthorage = addauthorage;
	}

	public String getAddauthorcountry() {
		return addauthorcountry;
	}

	public void setAddauthorcountry(String addauthorcountry) {
		this.addauthorcountry = addauthorcountry;
	}
	
}
