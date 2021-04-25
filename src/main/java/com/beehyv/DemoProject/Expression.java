package com.beehyv.DemoProject;

import javax.persistence.Embeddable;

@Embeddable
public class Expression {

	private String expression;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
