import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class BindingDemo2 {

	public static void main2(String[] args) {
		Account a1 = new Account();
		Account a2 = new Account();

		a2.balanceProperty().bind(a1.balanceProperty());
		a1.setBalance(15);
		System.err.println("a2 balance: " + a2.getBalance());
	}


	public static void main(String[] args) {
		Account a1 = new Account();
		Account a2 = new Account();
		Account a3 = new Account();

		NumberBinding total = a1.balanceProperty().add(a2.balanceProperty()).add(a3.balanceProperty());
		a1.setBalance(10);
		a2.setBalance(20);
		System.err.println("total: " + total.getValue());
	}


	private static class Account {
		private DoubleProperty balanceProperty = new SimpleDoubleProperty();

		public double getBalance() {
			return balanceProperty.get();
		}

		public void setBalance(double value) {
			balanceProperty.set(value);
		}

		public DoubleProperty balanceProperty() {
			return balanceProperty;
		}
	}

}





