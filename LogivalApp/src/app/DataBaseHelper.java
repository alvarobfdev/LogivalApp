package app;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;

import models.Pedidos;
import models.PedidosId;

public class DataBaseHelper {
	
	public final static int MYSQL_DB = 1;
	public final static int CTSQL_DB = 2;
	
	private String lastQuery = null;
	private String lastJSON = null;
	private int lastDbType = 2;
	
	private Connection connectionCtsql = null;
	private int dbType = 2;
	Statement statementCtsql = null;

	public DataBaseHelper() {
		
	}
	
	public DataBaseHelper(int dbType) {
		this.dbType = dbType;
	}

	private ArrayList<Map<String, Object>> getObjectArrayList(ResultSet resultQuery) throws SQLException, UnsupportedEncodingException {
		ResultSetMetaData rsmd = resultQuery.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
	
		ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		while(resultQuery.next())
		 {
		   Map<String, Object> row = new HashMap<String, Object>();
		   for (int i=0; i <columnsNumber ; i++)
		    {
			   
			   row.put(rsmd.getColumnName(i+1), resultQuery.getObject(i+1));
		    }
		   	result.add(row);
		 }
		
		return result;
		
	}

	private String ListMapToJson(List<Map<String, Object>> list) {       
	    JSONArray json_arr=new JSONArray();
	    for (Map<String, Object> map : list) {
	        JSONObject json_obj=new JSONObject();
	        int i=1;
	        for (Map.Entry<String, Object> entry : map.entrySet()) {
	            String key = entry.getKey();
	            Object value = entry.getValue();
	        	i++;
	            try {
	                json_obj.put(key,value);
	            } catch (JSONException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }                           
	        }
	        json_arr.put(json_obj);
	    }
	    return json_arr.toString();
	}
	
	private <T> T getObjectClass(ResultSet resultSet, Class<T> classOfT)
		   throws JsonSyntaxException {
		String json = getJsonFromResultSet(resultSet);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.fromJson(json, classOfT);
	}

	public String getJsonFromResultSet(ResultSet rs) {
		String json = "[]";
		if(rs == null && lastJSON != null) {
			json = lastJSON;
		}
		else {
			try {
				ArrayList<Map<String, Object>> list = getObjectArrayList(rs);
				json = ListMapToJson(list);
				lastJSON = json;
				
			} catch (SQLException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(json);
		return json;
	}
	
	public <T> T getObjectClass(String query, Class<T> classOfT) {
		
		
		ResultSet resultQuery = null;
		
		try {
			if(lastQuery == null || !lastQuery.equals(query) || dbType != lastDbType) {
				if(dbType == CTSQL_DB)
					connectionCtsql = CtsqlHandler.getConnection();
				if(dbType == MYSQL_DB)
					connectionCtsql = MySqlHandler.getConnection();
					
				statementCtsql = connectionCtsql.createStatement();
				System.out.println("Query: "+query);
				resultQuery = statementCtsql.executeQuery(query);
				lastQuery = query;
				lastDbType = dbType;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return getObjectClass(resultQuery, classOfT);
		
		
	}
	
	public void setId(Object[] parentObjects, Object[] ids) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		for(int i=0; i<parentObjects.length; i++) {
			Method method = parentObjects[i].getClass().getMethod("setId", ids[i].getClass());
			method.invoke(parentObjects[i], ids[i]);
		}
		
	}
	
	

}