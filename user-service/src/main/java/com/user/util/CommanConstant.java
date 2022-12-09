package com.user.util;

public enum CommanConstant {
	CREATE("CREATE"),
	UPDATE("EDIT");
	
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private CommanConstant(String name) {
		this.name = name;
	}
	
}
