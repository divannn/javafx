
package np.com.ngopal.control;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextBox;

/**
 *
 * @author Narayan G. Maharjan
 * @see <a href="http://www.blog.ngopal.com.np"> Blog </a>
 */
public interface IAutoFillTextBox<T> {
    
    /**
     * Keeps the array of String which contains the 
     * words to be matched on typing.
     * @param data 
     */
    void setData(ObservableList<T> data);
    
     /**
     * Give the data containing possible fast matching words
     * @return <a href="http://download.oracle.com/javafx/2.0/api/javafx/collections/ObservableList.html"> ObservableList </a>          
     */
    ObservableList<T> getData();
    
    /**
     * the main listview of the IAutoFillTextBox
     * @return <a href="http://download.oracle.com/javafx/2.0/api/javafx/scene/control/ListView.html"> ListView </a>          
     */
    ListView<T> getListview();
    
     /**
     * the textbox of the IAutoFillTextBox
     * @return <a href="http://download.oracle.com/javafx/2.0/api/javafx/scene/control/ListView.html"> TextView </a>          
     */
    TextBox getTextbox();
    
    /**
     * This defines how many max listcell to be visibled in listview when
     * matched words are occured on typing.
     * @param limit 
     */
    void setListLimit(int limit);
    
    /**
     * this gives the limit of listcell to be visibled in listview
     * @return int
     */
    int getListLimit();
    
    /**
     * This sets the AutoFilterMode which can show as filter type
     * rather than searched type if value is true.
     * @param filter 
     */
    void setFilterMode(boolean filter);
        
    /**
     * 
     * @return boolean value of Filtermode
     */
    boolean getFilterMode();
}
