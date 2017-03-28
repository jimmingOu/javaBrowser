/**
 * hollowworld!
 */
package Net;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.CookieStore;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
/**
 * @author user
 *
 */
public class MyBrowser extends JFrame{

  /**
   * @param args
   * @throws IOException 
   */
  //private WebView webview = new WebView();  
  private JPanel JMenuPaneel = new JPanel();   
  private JButton JBackBtn = new JButton("<<");
  private JButton JFowardBtn = new JButton(">>");
  private JButton JHomeBtn = new JButton("HOME");
  private JButton JSearchBtn = new JButton("GO");
  private JButton JRefreshBtn = new JButton("Refresh");
  private JTextField JUrlText = new JTextField("https://www.mobile01.com",100);  
  private JFXPanel JBrowserPanel = new JFXPanel();
  private ArrayList<String> UrlHistory = new ArrayList<String>();
  private void goHistoryPage(int type){
    WebHistory history = webview.getEngine().getHistory();   
    int currentIndex = history.currentIndexProperty().get();
    if(type == -1 && currentIndex == 0)
      return ;
    if(type == 1 && currentIndex == history.getMaxSize()-1)
      return ; 
    Platform.runLater( new Runnable(){

      @Override
      public void run() {
        // TODO Auto-generated method stub
        history.go(type);
        
      } 
    } );
    
  }
  private void init(){
    setVisible(true);
    setBounds(0,0,1500,1500);    
    setLayout(new BorderLayout());
    JMenuPaneel.setLayout(new FlowLayout());   
    JMenuPaneel.add(JBackBtn);
    JMenuPaneel.add(JFowardBtn);
    JMenuPaneel.add(JRefreshBtn);
    JMenuPaneel.add(JHomeBtn); 
    JMenuPaneel.add(JUrlText);  
    JMenuPaneel.add(JSearchBtn);
    JBackBtn.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        goHistoryPage(-1);
      }
      
    });
    JFowardBtn.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
         goHistoryPage(1);
      }
      
    });    
    JSearchBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        runBrowser(JUrlText.getText());
      }      
    });
    JUrlText.addFocusListener(new FocusListener(){

  
      @Override
      public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub
        JUrlText.setSelectedTextColor(Color.GRAY);
        JUrlText.selectAll();  
      }

      @Override
      public void focusLost(FocusEvent e) {
        // TODO Auto-generated method stub
        
      }
      
    }); 
    
    JUrlText.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.MOUSE_EVENT_MASK == 16)
          JSearchBtn.doClick();
      }
      
    }); 
    JUrlText.addKeyListener(new KeyListener(){

      @Override
      public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub 
        if( e.getKeyCode() == 8 )
          return ;
        int curIndex = JUrlText.getSelectionStart();
        for(String str : UrlHistory){
          if( str.indexOf(JUrlText.getText()) != -1 ){
            JUrlText.setText(str);
            JUrlText.setSelectionStart(curIndex);
          }
        }
        
      }
      
    });
    add(JMenuPaneel, BorderLayout.NORTH);
    add(JBrowserPanel,BorderLayout.CENTER);
    runBrowser(JUrlText.getText());

    
  }
  WebView webview;
  private void runBrowser(String url){
    Platform.runLater(new Runnable(){

      @Override
      public void run() {
        // TODO Auto-generated method stub 
        webview = new WebView();  
        webview.getEngine().load(url);
        JBrowserPanel.setScene(new Scene(webview));
        //JUrlText.setText(webview.getEngine().getLocation()); 
        //System.out.println(webview.getEngine().getUserStyleSheetLocation());
        UrlHistory.add(url);
      }
      
    });    
  }
  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    MyBrowser browser = new MyBrowser();
    browser.init();
  }

}
