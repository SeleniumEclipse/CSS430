

import java.util.*;
import java.io.*;

public class Shell extends Thread
{
	Set< Integer > idLog;

	public Shell ( )
	{
		idLog = new HashSet<>();
	}

	public void run()
	{
		for(int line = 1; ; line++) 
		{
			String cmdLine = "";
			do
			{
				StringBuffer input = new StringBuffer();
				SysLib.cout("shell[" + line + "]%");
				SysLib.cin(input);
				cmdLine = new String(input);

			} while(cmdLine.length() == 0);

			String[] args = SysLib.stringToArgs(cmdLine);

			if(args.length < 1) 
			{
				continue;
			}

			if(args[0].equals("exit")) 
			{
				break;
			}

			String[] allCmds = cmdLine.split(";");
			for(String cmd : allCmds) 
			{
				runCmd(cmd);
			}
		}
		SysLib.exit();
	}

	public void runCmd(String cmd) 
	{
		String[] differentCmds = cmd.split("&");

		for(String currCmd : differentCmds) 
		{
			int id = SysLib.exec(SysLib.stringToArgs(currCmd));
			if( id < 0) 
			{
				return;
			} else 
			{
				idLog.add(id);
			}
		}

		while(!idLog.isEmpty()) 
		{
			int retId = SysLib.join();
			if(retId == -1) 
			{
				idLog.clear();
				return;
			}
			else if(idLog.contains(retId)) 
			{
				idLog.remove(retId);
			}
		}

		return;
	} 

}