package com.jstrgames.sharepoint.query;

import static org.junit.Assert.*;
import static com.jstrgames.sharepoint.query.Where.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.junit.Before;
import org.junit.Test;

public class TestNotEquals {
	private static final String SCENARIO1_FIELD_NAME = "foo";
	private static final String SCENARIO1_FIELD_VALUE = "bar";
	private static final String EXPECTED_SCENARIO1_XML = "<Neq><FieldRef Name=\"foo\"/><Value Type=\"Text\">bar</Value></Neq>";
	
	private static final String SCENARIO2_FIELD_NAME = "foobar";
	private static final String SCENARIO2_FIELD_VALUE = "2011-02-07T00:00:00Z";
	private static final String EXPECTED_SCENARIO2_XML = 
			"<Neq><FieldRef Name=\"foobar\"/><Value Type=\"DateTime\">" +
			SCENARIO2_FIELD_VALUE + 
			"</Value></Neq>";
	
	private XmlObject root;
	
	@Before
	public void setup() {
		this.root = XmlObject.Factory.newInstance();
	}
	
	@Test
	public void testScenario1_DefaultTypeFieldGeneratedXml() {
		final XmlCursor xmlCursor = root.newCursor();
		xmlCursor.toNextToken();
		
		final Field field = new Field(SCENARIO1_FIELD_NAME, SCENARIO1_FIELD_VALUE);
		final Expression expression = ne(field);
		expression.write(xmlCursor);
		
		final String actualXml = root.xmlText();
		assertNotNull(actualXml);
		assertEquals(EXPECTED_SCENARIO1_XML, actualXml);
	}
	
	@Test
	public void testScenario2_DateTimeTypeFieldGeneratedXml() {
		final XmlCursor xmlCursor = root.newCursor();
		xmlCursor.toNextToken();
		final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); 
				
		try {
			final Field field = new Field(SCENARIO2_FIELD_NAME, 
										  parser.parse(SCENARIO2_FIELD_VALUE));
			final Expression expression = ne(field);
			expression.write(xmlCursor);
			
			final String actualXml = root.xmlText();
			assertNotNull(actualXml);
			assertEquals(EXPECTED_SCENARIO2_XML, actualXml);
			
		} catch (ParseException e) {
			fail("failed to parse test date!");
		}

	}

}
