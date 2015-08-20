package com.tcs.itcsmlcp;

public class Info {
	
	//private variables
	int _id;
	String _name;
	String _loc;
	String _email;
	
	// Empty constructor
	public Info(){
		
	}
	// constructor
	public Info(int id, String name, String loc,String email){
		this._id = id;
		this._name = name;
		this._loc = loc;
		this._email = email;
	}
	
	// constructor
	public Info(String name, String loc){
		this._name = name;
		this._loc = loc;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting loc
	public String getLoc(){
		return this._loc;
	}
	
	// setting loc
	public void setLoc(String loc){
		this._loc = loc;
	}
	// getting email
	public String getEmail(){
		return this._email;
	}
	
	// setting email
	public void setEmail(String email){
		this._email = email;
	}
}
