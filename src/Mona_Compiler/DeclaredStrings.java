//*************************************************************************************
// File:   DeclaredStrings.java                 Date: 9Aug18
// Author: David Sinclair
//
// Class to contain and process the extracted strings from language SL7a
//
//*************************************************************************************

public class DeclaredStrings
{
    String var;
    String value;


    public DeclaredStrings ()
    {
	this.var = "";
	this.value = "";
    }


    public DeclaredStrings (String var, String value)
    {
	this.var = var;
	this.value = value;
    }


    public void print ()
    {
	System.out.println (var + ":" + value);
    }
}
