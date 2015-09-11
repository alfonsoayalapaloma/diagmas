package co.scndfndtion.commands.def;

/**
 * Parameter class to exchange info with the runner script
 * @author alfonso.ayala
 *
 */
public class ExeParameter {
	public String user;
	public String pass;
	public String command;
	public String deviceIp;
	public String targetIp;
	public String date;
	public String directory;
	public String protocol;
	public String sourceIp;
	public String vrf;
	public String port;
	public String ont;
	public String cmdtype;
	public String brand;

	/**
	 * Objeto parametro enviado al script.
	 * @param user
	 * @param pass
	 * @param command
	 * @param deviceIp
	 * @param date
	 * @param directory
	 * @param protocol
	 * @param targetIp
	 * @param sourceIp
	 * @param vrf TODO
	 * @param port 
	 * @param ont 
	 */
	public ExeParameter(final String user, 
			final String pass, 
			final String command,
			final String deviceIp, 
			final String date, 
			final String directory, 
			final String protocol, 
			final String targetIp, 
			final String sourceIp, 
			final String vrf, 
			final String port, 
			final String ont) {
		this.user = user;
		this.pass = pass;
		this.command = command;
		this.deviceIp = deviceIp;
		
		this.date = date;
		this.directory = directory;
		this.protocol = protocol;
		this.targetIp = targetIp;
		this.sourceIp = sourceIp;
		this.vrf      = vrf;
	}
	
	public ExeParameter(final String user, 
			final String pass, 
			final String command,
			final String deviceIp, 
			final String date, 
			final String directory, 
			final String protocol, 
			final String targetIp, 
			final String sourceIp, 
			final String vrf, 
			final String port, 
			final String ont,
			final String brand) {
		this.user = user;
		this.pass = pass;
		this.command  = command;
		this.deviceIp = deviceIp;
		
		this.date     = date;
		this.directory = directory;
		this.protocol = protocol;
		this.targetIp = targetIp;
		this.sourceIp = sourceIp;
		this.vrf      = vrf;
		this.brand    = brand;
	}
	
	public String toString(){
		return "parameterObject:" +
				" gsrIp="+deviceIp
				+" targetIp="+ targetIp
				+" sourceIp="+ sourceIp
				+" vrf="+ vrf
				+" command=\""+ command + "\""
				;
	}
}