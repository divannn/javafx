package candle;

import chart.TooltipContent;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;

public abstract class BarBase extends /*Group*/ Region {

	protected String seriesStyleClass;
	protected String dataStyleClass;
	protected Tooltip tooltip = new Tooltip();

	protected BarBase(String seriesStyleClass, String dataStyleClass) {
		this.seriesStyleClass = seriesStyleClass;
		this.dataStyleClass = dataStyleClass;
		//setAutoSizeChildren(false);
		tooltip.setGraphic(new TooltipContent());
	}

	public void setSeriesAndDataStyleClasses(String seriesStyleClass,
			String dataStyleClass) {
		this.seriesStyleClass = seriesStyleClass;
		this.dataStyleClass = dataStyleClass;
		updateStyleClasses();
	}

	public void update(double closeOffset, double highOffset, double lowOffset,
			double candleWidth) {
	}

	public void updateTooltip(double open, double close, double high,
			double low, long date) {
		TooltipContent tooltipContent = (TooltipContent) tooltip.getGraphic();
		tooltipContent.update(open, close, high, low, date);
	}

	protected void updateStyleClasses() {

	}

}
