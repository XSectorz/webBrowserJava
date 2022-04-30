package net.panat.core;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.stage.Stage;

public class core extends Application {
	
	
	public static StageButton stateSite = StageButton.VIEW_SITE_MODE;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Controller controller = new Controller();
		
		Pane pane = new Pane();
		pane.setPrefHeight(751);
		pane.setPrefWidth(1052);
		
		
		controller.getProgressBar().progressProperty().bind(controller.getWebEngine().getLoadWorker().progressProperty());
		
		pane.getChildren().addAll(controller.getWebView(),controller.getURLBar(),
				controller.getLoadSiteButton(),controller.getRefreshButton(),controller.getZoomInButton(),
				controller.getZoomOutButton(),controller.getZoomDisplay(),controller.getBackButton(),
				controller.getNextButton(),controller.getSourceBtn(),controller.getProgressBar());
		
		Scene scene = new Scene(pane,1052,751);
		
		WebEngine engine = controller.getWebView().getEngine();
		
		engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                controller.getURLBar().setText(engine.getLocation());
            }
        });
		
		controller.getZoomOutButton().setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	controller.zoomOut();
		    }
		});
		
		controller.getZoomInButton().setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	controller.zoomIn();
		    }
		});
		
		controller.getRefreshButton().setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	controller.reload();
		    }
		});
		
		controller.getNextButton().setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	controller.nextPage();
		    }
		});
		
		controller.getBackButton().setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	controller.backPage();
		    }
		});
		
		controller.getURLBar().setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	try {
					controller.loadSite();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }
		});
		
		controller.getSourceBtn().setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	if(stateSite.equals(StageButton.VIEW_SITE_MODE)) {
		    		stateSite = StageButton.VIEW_SOURCE_MODE;
			    	controller.viewSource();
		    	} else {
		    		stateSite = StageButton.VIEW_SITE_MODE;
		    		controller.getURLBar().setText(controller.getCurrentSite());
		    		try {
						controller.loadSite();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    	}
		    }
		});
		
		engine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {
	    	WebHistory history = controller.getWebView().getEngine().getHistory();
	        ObservableList<WebHistory.Entry> entries = history.getEntries();
	        
	        Stage stages = (Stage) controller.getURLBar().getScene().getWindow();
	        
		    if (newValue == Worker.State.SUCCEEDED) {
		        
		        stages.setTitle(entries.get(history.getCurrentIndex()).getTitle());
		        
		        stages.setTitle(controller.getWebView().getEngine().getTitle());
		    } else if (newValue == Worker.State.FAILED) {
		        stages.setTitle("Failed to load site");
		        stages.setTitle(controller.getWebView().getEngine().getTitle());
		    }
		});
		
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
