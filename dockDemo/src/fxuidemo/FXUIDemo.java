package fxuidemo;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.builders.ColorAdjustBuilder;
import javafx.builders.RectangleBuilder;
import javafx.builders.TimelineBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Sergey Grinev
 */


// Для работы с JavaFX необходимо наследовать класс Application
public class FXUIDemo extends Application {

	public static void main(String[] args) {
		// это наша точка старта -- этот метод поднимает FX стек и загружает туда наше приложение
		Application.launch(args);
	}

	private HBox taskbar;

	@Override
	public void start(Stage stage) {

		// переданный в параметре объект stage является нашим окном
		stage.setTitle("FX Demo");
		stage.setFullScreen(true);

		// здесь мы создаём сцену, которая является содержимым окна и layout manager для неё
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 720, 550, Color.LIGHTGRAY);
		stage.setScene(scene);

		// создадим отдельный layout для иконок-кнопок
		taskbar = new HBox(10);
		taskbar.setAlignment(Pos.CENTER);
		taskbar.setPadding(new Insets(10, 30, 50, 30));
		taskbar.setPrefHeight(150);

		// создаём view
		view = new StackPane();
		view.getChildren().add(new Text("Hello from JavaFX..."));

		// добавляем их сцену
		root.setCenter(view);
		root.setBottom(taskbar);

		// добавляем кнопки и их обработчики их нажатия
		// 1. Media
		mediaPlayer = new MediaPlayer(
				new Media("http://webcast-west.sun.com/oow2010.flv"));

		taskbar.getChildren().add(createButton("icon-0.png", new Runnable() {

			public void run() {
				changeView(new MediaView(mediaPlayer));
				mediaPlayer.play();
			}
		}));

		// 2. График
		taskbar.getChildren().add(createButton("icon-1.png", new Runnable() {

			public void run() {
				// оси координат
				NumberAxis xAxis = new NumberAxis();
				NumberAxis yAxis = new NumberAxis();
				// график
				LineChart<Number, Number> chart = new LineChart<Number, Number>(
						xAxis, yAxis);
				chart.setTitle("Basic LineChart");
				xAxis.setLabel("X Axis");
				yAxis.setLabel("Y Axis");
				// набор случайных данных
				XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
				series.setName("Random Data");
				Random random = new Random();
				for (int i = 0; i < 10 + random.nextInt(20); i++) {
					series.getData().add(
							new XYChart.Data<Number, Number>(10 * i + 5,
									random.nextDouble() * 120));
				}
				chart.getData().add(series);
				changeView(chart);
			}
		}));

		// 3. Accordion
		taskbar.getChildren().add(createButton("icon-2.png", new Runnable() {

			public void run() {
				Accordion accordion = new Accordion();
				for (int i = 0; i <= 4; i++) {
					TitledPane t1 = new TitledPane(new Label("Image " + i),
							new ImageView(new Image(getClass().getResource(
									"icon-" + i + ".png").toString())));
					accordion.getPanes().add(t1);
				}
				changeView(accordion);
			}
		}));

		// 4. Браузер
		taskbar.getChildren().add(createButton("icon-3.png", new Runnable() {

			public void run() {
				WebView web = new WebView(new WebEngine("http://habrahabr.ru"));
				changeView(web);
			}
		}));

		// 5. CSS & binding
		taskbar.getChildren().add(createButton("icon-4.png", new Runnable() {

			public void run() {
				// создаём список
				ListView listView = new ListView();
				// заполняем его стилями
				listView.setItems(FXCollections.observableArrayList(
						"-fx-background-color: green;",
						"-fx-background-color: linear (0%,0%) to (100%,100%) stops (0.0,aqua) (1.0,red);",
						"-fx-background-color: transparent;",
						"-fx-opacity: 0.3;",
						"-fx-opacity: 1;"));
				// через binding связываем стиль панели задач с выбранным элементом списка
				taskbar.styleProperty().bind(
						listView.getSelectionModel().selectedItemProperty());
				changeView(listView);
			}
		}));

		stage.setVisible(true);
	}

	private StackPane view;
	private MediaPlayer mediaPlayer;

	private void changeView(Node node) {
		view.getChildren().clear();
		mediaPlayer.stop();
		view.getChildren().add(node);
	}

	private static final double SCALE = 1.3; // коэффициент увеличения
	private static final double DURATION = 300; // время анимации в мс

	private Node createButton(String iconName, final Runnable action) {
		// загружаем картинку
		final ImageView node = new ImageView(
				new Image(getClass().getResource(iconName).toString()));

		// создаём анимацию увеличения картинки
		final ScaleTransition animationGrow = new ScaleTransition(
				Duration.valueOf(DURATION), node);
		animationGrow.setToX(SCALE);
		animationGrow.setToY(SCALE);

		// и уменьшения
		final ScaleTransition animationShrink = new ScaleTransition(
				Duration.valueOf(DURATION), node);
		animationShrink.setToX(1);
		animationShrink.setToY(1);

		// добавляем эффект отраженичя
		final Reflection effect = new Reflection();
		node.setEffect(effect);

		// создаём эффект затемнения
		final ColorAdjust effectPressed = ColorAdjustBuilder.create().brightness(
				-0.5).build();

		// обработчик нажатия мыши
		node.setOnMouseReleased(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				// в обработчике нажатия применяем эффект. Тут имеется следующая тонкость: это уже второй эффект для кнопки,
				// поэтому мы его выставляем не напрямую, а через input первого эффекта
				effect.setInput(effectPressed);
				// создаём Timeline, который через 300 мс отключит затемнение.
				TimelineBuilder.create().keyFrames(
						new KeyFrame(Duration.valueOf(300),
								new EventHandler<ActionEvent>() {

									public void handle(ActionEvent event) {
										effect.setInput(null);
									}
								})).build().play();
				action.run();
			}
		});

		// при наведении курсора мы запускаем анимацию увеличения кнопки
		node.setOnMouseEntered(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				node.toFront();
				animationShrink.stop();
				animationGrow.playFromStart();
			}
		});
		// когда курсор сдвигается -- запускаем анимацию уменьшения
		node.setOnMouseExited(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				animationGrow.stop();
				animationShrink.playFromStart();
			}
		});

		return node;
	}
}
