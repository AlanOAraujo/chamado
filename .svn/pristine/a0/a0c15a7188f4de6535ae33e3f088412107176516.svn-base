package br.com.pagga.chamado.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.pagga.chamado.util.DateUtil;

public abstract class PaggaController {

	protected HttpServletRequest request;

	protected HttpServletResponse response;
	
	protected EntityManager entityManager;
	
	protected Result result;
	
	public PaggaController(){}
	
	public PaggaController(HttpServletRequest request, HttpServletResponse response, Result result, EntityManager entityManager) {
		super();
		this.request = request;
		this.response = response;
		this.result = result;
		this.entityManager = entityManager;
	}

	protected void setSessionAttribute(String key, Object obj){
		request.getSession().setAttribute(key, obj);
	}

	protected Object getSessionAttribute(String key){
		return request.getSession().getAttribute(key);
	}
	
	protected void removeSessionAttribute(String key){
		request.getSession().removeAttribute(key);
	}

	protected JSONBuilder createJSON(){
		return new JSONBuilder();
	}
	
	protected void addHeader(String key, String value) {
		response.addHeader(key, value);
	}
	
	protected void addCookie(String key, String value) {
		response.addCookie(new Cookie(key, value));
	}

	protected class JSONBuilder{

		private Map<String, Object> map = new HashMap<>();

		private boolean success = false;

		private JSONBuilder(){}

		public JSONBuilder put( String key, Object value ){
			map.put(key, value == null ? "" :  value);
			return this;
		}

		public JSONBuilder put( String key, JSONObject jsonObject ){
			map.put(key, jsonObject == null ? new JSONObject() : jsonObject);
			return this;
		}

		public JSONBuilder put( String key, List<JSONObject> jsonObjectList ){
			map.put(key, jsonObjectList == null ? new JSONArrayPagga() : jsonObjectList);
			return this;
		}

		public JSONBuilder put( String key, Date value ){
			map.put(key, value == null ? "" :  value);
			return this;
		}
		
		public JSONBuilder put( String key, String value ){
			map.put(key, value == null ? "" :  value);
			return this;
		}

		public JSONBuilder put( String key, Date value, String pattern ){
			map.put(key, DateUtil.format(value, pattern));
			return this;
		}

		public JSONBuilder message( String message ){
			map.put("message", message);
			return this;
		}

		public JSONBuilder success(){
			this.success = true;
			map.put("success", this.success);
			return this;
		}
		
		public JSONBuilder data( JSONObject jsonObject){
			map.put("data", jsonObject);
			return this;
		}
		
		public JSONBuilder list( JSONArray jsonArray){
			map.put("list", jsonArray);
			return this;
		}
		
		public JSONBuilder success(String message){
			this.success();
			this.message(message);
			return this;
		}
		
		public JSONBuilder message( List<String> messages ){
			map.put("message", messages);
			return this;
		}

		public JSONBuilder failure(){
			this.success = false;
			map.put("success", this.success);
			return this;
		}

		public JSONBuilder failure(String message){
			this.failure();
			this.message(message);
			return this;
		}
		
		public JSONBuilder failure(List<String> messages){
			this.failure();
			this.message(messages);
			return this;
		}
		
		public void serialize(Object obj){
			result.use(Results.json()).withoutRoot().from(obj).serialize();
		}
		
		public void serializeRecursive(Object obj){
			result.use(Results.json()).withoutRoot().from(obj).recursive().serialize();
		}
		
		public JSONObject build(){
			JSONObject jsonObject = new JSONObject(map);
			return jsonObject;
		}

		public void ok(JSONObject jsonObject) {
			write(jsonObject);
		}

		public void ok(JSONArray jsonArray) {
			write(jsonArray);
		}

		public void ok(){
			write(this.build());
		}
		
		public void conflict(){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			write(this.build());
		}
		
		public void serverError(){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			write(this.build());
		}
		
		public void notFound(){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			write(this.build());
		}
		
		public void notFound(JSONArray jsonArray){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			write(jsonArray);
		}
		
		public void created(){
			response.setStatus(HttpServletResponse.SC_CREATED);
			write(this.build());
		}
		
		public void badRequest(){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			write(this.build());
		}
		
		public void unauthorized(){
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			write(this.build());
		}
	}
	
	protected class JSONArrayPagga{
		
		private List<JSONObject> array = new ArrayList<>();
		
		private JSONArrayPagga(){}
		
		public JSONArrayPagga add(JSONObject jsonObject){
			this.array.add(jsonObject);
			return this;
		}
		
		public List<JSONObject> build(){
			return array;
		}
	}
	
	protected JSONArrayPagga createJSONArray(){
		return new JSONArrayPagga();
	}

	private void write( JSONObject jsonObject ){

		response.setContentType("application/json; charset=utf-8");

		PrintWriter pw = null;

		try{
			pw = response.getWriter();
			pw.write(jsonObject.toString());
			pw.flush();
		}
		catch ( Exception ex ){
			ex.getMessage();
		}
		finally{
			IOUtils.closeQuietly(pw);
		}
	}

	private void write( JSONArray jsonArray ){

		response.setContentType("application/json; charset=utf-8");

		PrintWriter pw = null;

		try{
			pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
		}
		catch ( Exception ex ){
			ex.getMessage();
		}
		finally{
			IOUtils.closeQuietly(pw);
		}
	}

}
