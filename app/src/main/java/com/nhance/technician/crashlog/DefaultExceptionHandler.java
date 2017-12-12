/*
Copyright (c) 2009 nullwire aps

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

Contributors: 
Mads Kristiansen, mads.kristiansen@nullwire.com
Glen Humphrey
Evan Charlton
Peter Hewitt
 */

package com.nhance.technician.crashlog;

import android.content.Context;
import android.content.SharedPreferences;

import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.util.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/*
* Date Author Changes
* 04-Jan-16 Javeed Created
*/
public class DefaultExceptionHandler implements UncaughtExceptionHandler {

	private UncaughtExceptionHandler defaultExceptionHandler;
	private Context context;

	private static final String TAG = DefaultExceptionHandler.class.getName();
	private String crashLOGIdentifierData;
	private String folderPath;

	// constructor
	public DefaultExceptionHandler(String crashLOGIdentifierData, UncaughtExceptionHandler pDefaultExceptionHandler, Context context, String folderPath)
	{
		this.crashLOGIdentifierData = crashLOGIdentifierData;
		defaultExceptionHandler = pDefaultExceptionHandler;
		this.context = context;
		this.folderPath = folderPath;
	}

	// Default exception handler
	public void uncaughtException(Thread t, Throwable e) {
		// Here you should have a more robust, permanent record of problems
		
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		try {

			FileWriter fileWriter = new FileWriter(getFile(),true);
			BufferedWriter bos = new BufferedWriter(fileWriter);
			StringBuffer sb = new StringBuffer();
			sb.append(crashLOGIdentifierData);
			sb.append(result.toString());
			bos.write(sb.toString());
			bos.flush();
			// Close up everything
			bos.close();
		} catch (Exception ebos) {
			// Nothing much we can do_antarctida about this - the game is over
			ebos.printStackTrace();
		}
		/*finally {
			clearStackAndLaunchDefault();
		}*/
		LOG.d(TAG, result.toString());	    
		//call original handler  
		defaultExceptionHandler.uncaughtException(t, e);
	}


	private String getNewFileName(String fileName) {

		int lastFileId = 2;
		
		char[] fileNameArray = fileName.toCharArray();
		
		int startIndex = 0;
		int endIndex = fileName.length()-ApplicationConstants.CRASH_LOG_FILES_EXTENSION.length();
		
		for(int i = 0; i < fileNameArray.length; i++)
		{
			if(fileNameArray[i] == '_')
			{
				startIndex = i;
			}
		}
		
		startIndex++;
		
		LOG.i(TAG, "startIndex: "+startIndex);
		LOG.i(TAG, "endIndex: "+endIndex);
		
		lastFileId = Integer.parseInt(fileName.substring(startIndex, endIndex));
		LOG.i(TAG, "lastFileId: "+lastFileId);
		lastFileId++;
		LOG.i(TAG, "New File Id: "+lastFileId);
		fileName = folderPath+"/"+ApplicationConstants.CRASH_LOG_FILES_NAME+ExceptionHandler.APP_VERSION.replace(".", "_")+"_"+String.valueOf(lastFileId)+ApplicationConstants.CRASH_LOG_FILES_EXTENSION;
        LOG.i(TAG, "New File Name: "+fileName);
		return fileName;
	}
	
	private File getFile() {
		
		String fileName = null;
		File file = null;
		
		/*try{
			Util.createPathIfNecessary(ApplicationConstants.CRASH_LOG_FILES_PRE_PATH, ApplicationConstants.CRASH_LOG_FILES_FOLDER);
		}catch(Exception e)
		{
			e.printStackTrace();
		}*/
		
		SharedPreferences preferences = context.getSharedPreferences(ApplicationConstants.CRASH_LOG_FILE_NAME, Context.MODE_PRIVATE);
		fileName = preferences.getString(ApplicationConstants.CRASH_LOG_FILES_NAME, null);
		
		if(fileName == null)
		{
			fileName = folderPath+"/"+ApplicationConstants.CRASH_LOG_FILES_NAME+ExceptionHandler.APP_VERSION.replace(".", "_")+"_1"+ApplicationConstants.CRASH_LOG_FILES_EXTENSION;
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(ApplicationConstants.CRASH_LOG_FILES_NAME, fileName);
			editor.commit();
		}
		else
		{
			String currentVersionNo = ExceptionHandler.APP_VERSION.replace(".", "_");
			if(!fileName.contains(currentVersionNo))
			{
				fileName = folderPath+"/"+ApplicationConstants.CRASH_LOG_FILES_NAME+ExceptionHandler.APP_VERSION.replace(".", "_")+"_1"+ApplicationConstants.CRASH_LOG_FILES_EXTENSION;
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(ApplicationConstants.CRASH_LOG_FILES_NAME, fileName);
				editor.commit();
			}
		}
		
		file = new File(fileName);

		if(file.exists())
		{
			long size = file.length();
			LOG.d(TAG, "Size of crash log file: "+size);
			int sizeStatus = Util.getSizeInFormat(size);
			LOG.d(TAG, "Size status of crash log file: "+sizeStatus);
			if(sizeStatus == ApplicationConstants.MB_DATA)
			{
				fileName = getNewFileName(fileName);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(ApplicationConstants.CRASH_LOG_FILES_NAME, fileName);
				editor.commit();
				file = new File(fileName);
			}
		}
		
		return file;
	}
}