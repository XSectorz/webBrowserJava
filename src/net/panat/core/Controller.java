package net.panat.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class Controller {

	private ProgressBar progressBar;
	
	private WebView webView;
	private TextField textField;
	private Label zoomDisplay;
	private Button zoomOutButton;
	private Button zoomInButton;
	private Button refreshBtn;
	private Button nextButton;
	private Button backButton;
	private Button SourceBtn;
	private Button loadSiteBtn;
	
	
	private String currentSite = "https://www.google.com/";
	private WebEngine engine;
	private WebHistory webHistory;
	
	private String mainURL = "https://www.google.com/";
	
	private float screenSize = 1;
	
	public Controller() {
		this.progressBar = new ProgressBar();
		this.SourceBtn = new Button();
		this.nextButton = new Button();
		this.backButton = new Button();
		this.refreshBtn = new Button();
		this.zoomInButton = new Button();
		this.zoomOutButton = new Button();
		this.zoomDisplay = new Label("100%");
		this.textField = new TextField();
		this.loadSiteBtn = new Button();
		this.webView = new WebView();
		this.engine = new WebEngine();
		
		this.engine = webView.getEngine();
		
		this.webView.setPrefHeight(653);
		this.webView.setPrefWidth(1052);
		this.webView.setLayoutY(98);
		
		this.loadSiteBtn.setLayoutX(930);
		this.loadSiteBtn.setLayoutY(25);
		this.loadSiteBtn.setMnemonicParsing(false);
		this.loadSiteBtn.setPrefHeight(25);
		this.loadSiteBtn.setPrefWidth(88);
		this.loadSiteBtn.setText("Search");
		
		this.textField.setLayoutX(145);
		this.textField.setLayoutY(25);
		this.textField.setPrefHeight(25);
		this.textField.setPrefWidth(763);
		
		this.zoomDisplay.setAlignment(Pos.CENTER);
		this.zoomDisplay.setLayoutX(963);
		this.zoomDisplay.setLayoutY(65);
		this.zoomDisplay.setText("100%");
		
		this.zoomOutButton.setLayoutX(922);
		this.zoomOutButton.setLayoutY(61);
		this.zoomOutButton.setMnemonicParsing(false);
		this.zoomOutButton.setPrefHeight(25);
		this.zoomOutButton.setPrefWidth(34);
		this.zoomOutButton.setText("-");
		this.zoomOutButton.setTextAlignment(TextAlignment.CENTER);
		
		this.zoomInButton.setLayoutX(1001);
		this.zoomInButton.setLayoutY(61);
		this.zoomInButton.setMnemonicParsing(false);
		this.zoomInButton.setPrefHeight(25);
		this.zoomInButton.setPrefWidth(34);
		this.zoomInButton.setText("+");
		this.zoomInButton.setTextAlignment(TextAlignment.CENTER);
		
		this.backButton.setLayoutX(14);
		this.backButton.setLayoutY(25);
		this.backButton.setMnemonicParsing(false);
		this.backButton.setText("<");
		
		this.nextButton.setLayoutX(51);
		this.nextButton.setLayoutY(25);
		this.nextButton.setMnemonicParsing(false);
		this.nextButton.setText(">");
		
		this.progressBar.setLayoutX(145);
		this.progressBar.setLayoutY(57);
		this.progressBar.setPrefHeight(9);
		this.progressBar.setPrefWidth(763);
		this.progressBar.setProgress(0.0);
		
		this.SourceBtn.setLayoutX(17);
		this.SourceBtn.setLayoutY(61);
		this.SourceBtn.setMnemonicParsing(false);
		this.SourceBtn.setPrefHeight(25);
		this.SourceBtn.setPrefWidth(111);
		this.SourceBtn.setText("source");
		
		this.refreshBtn.setLayoutX(97);
		this.refreshBtn.setLayoutY(23);
		this.refreshBtn.setMnemonicParsing(false);
		this.refreshBtn.setPrefHeight(17);
		this.refreshBtn.setPrefWidth(33);
		this.refreshBtn.setText("↻");
		
		this.textField.setText(this.mainURL);
		this.engine.load(this.mainURL);
	}
	
	public Label getZoomDisplay() {
		return this.zoomDisplay;
	}
	
	public WebView getWebView() {
		return this.webView;
	}
	
	public Button getLoadSiteButton() {
		return this.loadSiteBtn;
	}
	
	public WebEngine getWebEngine() {
		return this.engine;
	}
	
	public ProgressBar getProgressBar() {
		return this.progressBar;
	}
	
	public String getCurrentSite() {
		return this.currentSite;
	}
	
	public void setCurrentSite(String currentSite) {
		this.currentSite = currentSite;
	}
	
	public Button getSourceBtn() {
		return this.SourceBtn;
	}
	
	public TextField getURLBar() {
		return this.textField;
	}
	
	public void loadSite() throws IOException {
		
	    try { 
	        new URL(this.textField.getText()).toURI();
	        
			this.engine.load(textField.getText());
			this.textField.setText(textField.getText());
			setCurrentSite(textField.getText());
	        
        } catch (Exception e) {
        	System.out.println(this.textField.getText() + " --> BAD REQUEST");
        	this.engine.load("https://www.google.com/search?q=" + textField.getText());
        	this.textField.setText("https://www.google.com/search?q=" + textField.getText());
			setCurrentSite(textField.getText());
	    }
	}
	
	public Button getZoomOutButton() {
		return this.zoomOutButton;
	}
	
	public Button getZoomInButton() {
		return this.zoomInButton;
	}
	
	public Button getRefreshButton() {
		return this.refreshBtn;
	}
	
	public Button getNextButton() {
		return this.nextButton;
	}
	
	public Button getBackButton() {
		return this.backButton;
	}
	
	public void reload() {
		this.engine.reload();
	}
	
	public void zoomIn() {
		if(this.screenSize >= 2) {
			return;
		}
		
		this.screenSize += 0.25;
		setZoomDisplay(this.screenSize);
		webView.setZoom(this.screenSize);
	}
	
	public void zoomOut() {
		if(this.screenSize <= 0.25) {
			return;
		}
		this.screenSize -= 0.25;
		setZoomDisplay(this.screenSize);
		webView.setZoom(this.screenSize);
	}
	
	public void setZoomDisplay(float value) {
		DecimalFormat df = new DecimalFormat("#");
		
		this.zoomDisplay.setText(String.valueOf(df.format(value*100)) + "%");
	}
	
	public void nextPage() {
		this.webHistory = this.engine.getHistory();
		ObservableList<WebHistory.Entry> entry = this.webHistory.getEntries();
		try {
			this.webHistory.go(1);
			this.textField.setText(entry.get(webHistory.getCurrentIndex()).getUrl());
			setCurrentSite(entry.get(webHistory.getCurrentIndex()).getUrl());
		} catch(IndexOutOfBoundsException ex) {
			return;
		}
		
	}
	
	public void viewSource() {
		try {
			URL yahoo = new URL(this.textField.getText());
			BufferedReader in = new BufferedReader( new InputStreamReader(yahoo.openStream()));
			String inputLine = "";
			String srcList = "";
			
			while ((inputLine = in.readLine()) != null) {
				srcList += inputLine;
			}
			
			srcList = srcList.replace("<", "&lt;").replace(">", "&gt;");
			
			String content = "<body>Source Code<br> " + srcList +"</body>";
			this.engine.loadContent(content);
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void backPage() {
		this.webHistory = this.engine.getHistory();
		ObservableList<WebHistory.Entry> entry = this.webHistory.getEntries();
		try {
			this.webHistory.go(-1);
			this.textField.setText(entry.get(webHistory.getCurrentIndex()).getUrl());
			setCurrentSite(entry.get(webHistory.getCurrentIndex()).getUrl());
		} catch(IndexOutOfBoundsException ex) {
			return;
		}
		
	}

}
