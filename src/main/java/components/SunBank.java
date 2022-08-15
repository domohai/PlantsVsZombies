package components;

public class SunBank extends Component {
	public int balance;
	public static int BALANCE;
	
	public SunBank() {
		this.balance = 0;
	}
	
	public SunBank(int balance) {
		this.balance = balance;
	}
	
	@Override
	public void start() {
		SunBank.BALANCE = this.balance;
	}
	
	@Override
	public void update(double dt) {
		this.balance = SunBank.BALANCE;
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
