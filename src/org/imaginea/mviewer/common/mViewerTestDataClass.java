package org.imaginea.mviewer.common;


public class mViewerTestDataClass {
	BaseClass baseClass = new BaseClass();
	String hostLocator = baseClass.getLocator("hostLocator");
	String portLocator = baseClass.getLocator("portLocator");
	String userNameLocator = baseClass.getLocator("userNameLocator");
	String passwordLocator = baseClass.getLocator("passwordLocator");
	String dataBasesLocator = baseClass.getLocator("dataBasesLocator");
	String connectButtonLocator = baseClass.getLocator("connectButtonLocator");
	String homeLocator = baseClass.getLocator("homeLocator");
	String serverStaticsLocator = baseClass.getLocator("serverStaticsLocator");
	String mongoGraphsLocator = baseClass.getLocator("mongoGraphsLocator");
	String helpLocator = baseClass.getLocator("helpLocator");
	String troubleshootLocator = baseClass.getLocator("troubleshootLocator");
	String disconnectLocator = baseClass.getLocator("disconnectLocator");
	String hostLableLocator = baseClass.getLocator("hostLableLocator");
	String userLableLocator = baseClass.getLocator("userLableLocator");
	String showconsoleLocator = baseClass.getLocator("showconsoleLocator");
	String dbNewButtonLocator = baseClass.getLocator("dbNewButtonLocator");
	
	
	
	public Object[][] loginPageelementLocatorValues() {
		return new Object[][] { { hostLocator, "127.0.0.1" },
				{ portLocator, "27017" }, { userNameLocator, "" },
				{ passwordLocator, "" }, { dataBasesLocator, "" } };

	}
	public Object[][] loginPageelementLocatorValues1() {
		return new Object[][] { 
				new Object[]{  homeLocator },
				new Object[]{  serverStaticsLocator },
				new Object[]{  mongoGraphsLocator },
				new Object[]{  helpLocator },
				new Object[]{  troubleshootLocator },
				new Object[]{  disconnectLocator },
				new Object[]{  hostLableLocator },
				new Object[]{  userLableLocator },
				new Object[]{  showconsoleLocator },
				new Object[]{  dbNewButtonLocator },
		};

	}
}
