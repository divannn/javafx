package np.com.ngopal.control;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextBox;

/**
 * This class is main Control class which extends from Control <br>
 * and also implements basic functions of  the IAutoFillTextBox<br>
 * <p/>
 * You can easily utilize the IAutoFillTextBox in your application<br>
 * <p/>
 * e.g <br>
 * <pre>
 *      //..codes
 *      IAutoFillTextBox autobox = new IAutoFillTextBox("helo","prefix","dog","city");
 *      autobox.setLimit(7);
 *      //..add autobox to your scene then the output must be like this:
 * </pre>
 * Output:
 * <br>
 * <img src="http://blog.ngopal.com.np/wp-content/uploads/2011/07/screen.png" align="center"/>
 * <br>
 *
 * @author Narayan G. Maharjan
 * @see <a href="http://www.blog.ngopal.com.np"> Blog </a>
 */
public class AutoFillTextBox<T> extends Control
		implements IAutoFillTextBox<T> {

	private TextBox textbox;
	private ListView<T> listview;
	private ObservableList<T> data = FXCollections.observableArrayList();

	private boolean filterMode;
	private int limit;

	public AutoFillTextBox() {
		init();
	}

	public AutoFillTextBox(ObservableList<T> data) {
		this();
		this.data = data;
	}

	private void init() {
		getStyleClass().setAll("autofill-text");
		textbox = new TextBox();
		listview = new ListView();
		limit = 5;
		filterMode = false;
	}

	public T getItem() {
		return listview.getSelectionModel().getSelectedItem();
	}

	public String getText() {
		return textbox.getText();
	}

	public void addData(T data) {
		this.data.add(data);

	}

	@Override
	public void setData(ObservableList<T> data) {
		this.data = data;
	}

	@Override
	public ObservableList<T> getData() {
		return data;
	}

	@Override
	public ListView<T> getListview() {
		return listview;
	}

	@Override
	public TextBox getTextbox() {
		return textbox;
	}

	@Override
	public void setListLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public int getListLimit() {
		return limit;
	}

	@Override
	public void setFilterMode(boolean filter) {
		filterMode = filter;
	}

	@Override
	public boolean getFilterMode() {
		return filterMode;
	}

}
