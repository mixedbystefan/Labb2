package backend;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.List;


import javax.annotation.Resource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

@WebServlet("/Servlet")

public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBUtility dBUtility;
	
	int count =1;
	Boolean Validmess = true;
	String _command;
	
	@Resource(name="jdbc/Hyresforeningen")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException 
	
	{
		super.init();
		try {dBUtility = new DBUtility(dataSource);}
		catch (Exception e) {throw new ServletException(e);}
	}
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			_command = request.getParameter("command");
			
			if (_command == null) {_command = "VALIDATE";}
			
			switch (_command) 
			{
			
			
			case "VALIDATE":
				listEmptyA(request, response);
				
				// Ska vara denn kod
				/*if (Validmess == false) 
				{
					listEmptyA(request, response);
					_command="LOAD_EMPTY_A";
				}
				else {doPost(request, response);}
				listEmptyA(request, response);*/
				break;
			case "LIST":
				listTenants(request, response);
				break;
			
			case "LIST_EMPTY_A":
				listEmptyA(request, response);
				break;
				
			case "LIST_A":
				listA(request, response);
				break;
			
			case "ADD" :
				addTenant(request, response);
				break;
				
			case "ADD_FROM_ID" :
				addTenant_from_id(request, response);
				break;	
				
			case "LOAD" :
				loadTenant(request, response);
				break;
				
			case "LOAD_A" :
				loadA(request, response);
				break;
				
			case "UPDATE" :
				updateTenant(request, response);
				break;
				
			case "UPDATE_APARTMENT" :
				updateApartment(request, response);
				break;
				
			case "INFO_APARTMENT" :
				infoApartment(request, response);
				break;
				
			case "DELETE" :
				deleteTenant(request, response);
				break;
				
			
				default:
						
					addTenant_from_id(request, response);
			
			}
			
			listTenants(request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	
	

	

	private void loadA(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String theA_ID = request.getParameter("A_ID");
		String error_mess = request.getParameter("ERROR_MESS");
		String from = request.getParameter("from");
		
		Apartment theApartment = dBUtility.getEmptyA(theA_ID);
		
		request.setAttribute("THE_E_APARTMENT", theApartment);
		request.setAttribute("ERROR_MESS", error_mess);
		request.setAttribute("FROM", from);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-apartment-form.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loadA(HttpServletRequest request, HttpServletResponse response,String error) throws Exception {
		String theA_ID = request.getParameter("A_ID");
		String from = request.getParameter("from");
		String error_mess = error;
		Apartment theApartment = dBUtility.getEmptyA(theA_ID);
		
		request.setAttribute("THE_E_APARTMENT", theApartment);
		request.setAttribute("ERROR_MESS", error_mess);
		request.setAttribute("FROM", from);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-apartment-form.jsp");
		dispatcher.forward(request, response);
		
	}
	
	

	private void listA(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		List<Apartment> Apartments = dBUtility.getAllApartments();
		
		request.setAttribute("A_LIST", Apartments);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-all-apartments.jsp");
		dispatcher.forward(request, response);
		
	}

	private void infoApartment(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String apartmentID = request.getParameter("A_ID");
		List<Tenant> apartmentInfo = dBUtility.getApartmetInfo(apartmentID);
		for(Tenant ten: apartmentInfo) { System.out.println(ten);}
		request.setAttribute("INFOAPARTMENT", apartmentInfo);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/apartment-info.jsp");
		dispatcher.forward(request, response);
		
	}

	private void listEmptyA(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Apartment> emptyApartments = dBUtility.getApartments();
		
		request.setAttribute("EMPTY_A_LIST", emptyApartments);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-empty-apartments.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listTenants(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
			List<Tenant> tenants = dBUtility.getTenants();
			
			request.setAttribute("TENANT_LIST", tenants);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-tenants.jsp");
			dispatcher.forward(request, response);	
			
	}

	private void deleteTenant(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		String theTenantID = request.getParameter("tenantID");
		dBUtility.deleteTenant(theTenantID);	
	}
	private void updateApartment(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String from = request.getParameter("from");
		int id = Integer.parseInt(request.getParameter("A_ID"));
		
		
		try 
		{
			double rent = Double.parseDouble(request.getParameter("rent"));
			double bofond = Double.parseDouble(request.getParameter("bofond"));
			String fridge = request.getParameter("fridge");
			String freezer = request.getParameter("freezer");
			String stove = request.getParameter("stove");
			String notes = request.getParameter("notes");
			
			Apartment tempApartment = new Apartment (id, rent, bofond, fridge, freezer, stove, notes);
			
			dBUtility.updateApartment(tempApartment);
			
			if (from.equalsIgnoreCase("empty_a")) 
			{
				listEmptyA(request, response);
			}
			
			if (from.equalsIgnoreCase("all_a")) 
			{
				listA(request, response);
			}
			listEmptyA(request, response);
			
		} catch (Exception e) {
			
			request.setAttribute("THE_E_APARTMENT", id);
			
			loadA(request, response,"Otillåtet värde i ett eller flera fält!" );
			
		}
		
		
		
		

		
	}
	

	private void loadTenant(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String theTenantID = request.getParameter("tenantID");
		Tenant theTenant = dBUtility.getTenant(theTenantID);
		request.setAttribute("THE_TENANT", theTenant);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-tenant-form.jsp");
		dispatcher.forward(request, response);
	}
	
	
	

	
	private void updateTenant(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	
		int id = Integer.parseInt(request.getParameter("tenantID"));
		int apartmentNumber = Integer.parseInt(request.getParameter("apartmentNumber"));
		String firstName = request.getParameter("firstName");
		String lastName= request.getParameter("lastName");
		String ss_number= request.getParameter("ss_number");
		String mobile= request.getParameter("mobile");
		String email= request.getParameter("email");
		String _from= request.getParameter("_from");
		String _until= request.getParameter("_until");
		if (_until.equalsIgnoreCase("")) {_until = null;}
		String notes= request.getParameter("notes");
		
		Tenant tempTenant = new Tenant(id, apartmentNumber, firstName, lastName, ss_number,mobile, email, _from,_until, notes);
		
		
		
		dBUtility.updateTenant(tempTenant);
		
		
		listTenants(request, response);
	}
	
private void addTenant(HttpServletRequest request, HttpServletResponse response) throws Exception 
	
	{
		
		int apartmentNumber = Integer.parseInt(request.getParameter("apartmentNumber"));
		String firstName = request.getParameter("firstName");
		String lastName= request.getParameter("lastName");
		String ss_number= request.getParameter("ss_number");
		String mobile= request.getParameter("mobile");
		String email= request.getParameter("email");
		String _from= request.getParameter("_from");
		String _until= request.getParameter("_until");
		String notes= request.getParameter("notes");
		
		
		Tenant tempTenant = new Tenant(apartmentNumber, firstName, lastName, ss_number,mobile, email, _from,_until, notes);
		dBUtility.addTenant(tempTenant);
		
		listTenants(request, response);	
	}

private void addTenant_from_id(HttpServletRequest request, HttpServletResponse response) throws Exception 

{
	
	int emptyA_ID = Integer.parseInt(request.getParameter("emptyA_ID"));
	LocalDateTime date = LocalDateTime.now();
	String _today = date.toString();
	String today = _today.substring(0, 10);
	
	request.setAttribute("THE_APARTMENT", emptyA_ID);
	request.setAttribute("DATE", today);
	
	RequestDispatcher dispatcher = request.getRequestDispatcher("/add-tenant-form_from_ID.jsp");
	dispatcher.forward(request, response);
	listTenants(request, response);	
}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		response.setContentType("text/html");  
		String validation;
	  
		String _username = request.getParameter("_username");
		String _password = ((ServletRequest) request).getParameter("_password");
		
		
		
			
			
			 if(dBUtility.validateAdmin(_username, _password)){  
				 _command = "LIST_EMPTY_A";
				 
				
				 try {
					listEmptyA(request, response);
					Validmess = false;
				} catch (Exception e) {
					
					e.printStackTrace();
				}	
				 
			    }  
			    else
			    {  
			    	
			    	
			    	validation = "Fel användarnamn eller lösenord";
			    	
			        request.setAttribute("VALID", validation);
			        RequestDispatcher rd=request.getRequestDispatcher("login.jsp");  
			        rd.forward(request,response);  
			    }  
		
	}
	
	


}
