package co.scndfndtion.commands.ruby;

/**
 * Runs a script in the remote system, using runner.rb
 * @author alfonso.ayala
 *
 */
public class RubyLocalWrapper extends RubyWrapper {
	public void setUp() {
		super.setUp();
		addRequiredParam("scriptName");
		addParam("scriptName",  "pingwin.rb");
	}
	@Override
	protected String parse(String rawOutput) {
		// TODO Auto-generated method stub
		return null;
	}
}
