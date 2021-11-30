package com.bilgeadam.marathon.resources;

import java.util.ResourceBundle;
public class Resources {
	
	private static Resources instance = null;
	
	
	
		private Resources() {
		super();
		}

		public static Resources getInstance() {
			if(instance == null) {
				instance = new Resources();
			}
			return instance;				
		}

		private static final String MYBUNDLE = Resources.class.getPackageName() + ".tsv";
		private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(MYBUNDLE);
		
		public String getPath(String key) {
			return RESOURCE_BUNDLE.getString(key);
		}
}
