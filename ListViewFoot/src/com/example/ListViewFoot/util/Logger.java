/**

*/
package com.example.ListViewFoot.util;


import com.example.ListViewFoot.BuildConfig;

/**
* @author MichaelHuang
* the log system for the app
*/
public class Logger {

    private static String TagName="ListViewFootProject:";
	/**
	 * Add warn info into the log file
	 * @param info
	 * @author MichaelHuang
	 */
	public static void warn(String info){
        if(BuildConfig.DEBUG){
            android.util.Log.w(TagName, info);
        }

	}

	/**
	 * Add normal info into the log file
	 * @param info
	 * @author MichaelHuang
	 */
	public static void info(String info){
        if(BuildConfig.DEBUG){
            android.util.Log.i(TagName, info);
        }

	}

	/**
	 * Add error info into the log file
	 * @param info
	 * @author MichaelHuang
	 */
	public static void error(String info){
        if(BuildConfig.DEBUG)android.util.Log.e(TagName, info);

	}

	/**
	 * Add debug info into the log file
	 * @param info
	 * @author MichaelHuang
	 */
	public static void debug(String info){
        if(BuildConfig.DEBUG)android.util.Log.d(TagName, info);

	}


}
