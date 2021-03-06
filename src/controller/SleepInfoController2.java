package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Staff;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class SleepInfoController2 implements Initializable {
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    

    @FXML
    private Text textChange;

    @FXML
    private TextField enterIdField;

    @FXML
    private TextField enterNameField;

    @FXML
    private TextField enterCourseField;

    @FXML
    private TextField enterAssignmentsField;

    @FXML
    private Button backButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button enterButton;
    
     @FXML
    private Button deleteButton;

    @FXML
    private Label deleteIDLabel;

    @FXML
    private TextField deleteIDField;
    
    @FXML
    private TextField enterOfficeHoursField;

    @FXML
    void backToMain(ActionEvent event) throws IOException{
//instead of going back to "previous scene", this reloads the mainView so the table has the most up-to-date information
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
        // load the ui elements
        Parent mainView = loader.load();
        // load the scene
        Scene tableViewScene = new Scene(mainView);
        //access the detailedControlled and call a method
        mainViewController mainController = loader.getController();
        // pass current scene to return
        Scene currentScene = ((Node) event.getSource()).getScene();
        //This line gets the Stage information
        Stage stage = (Stage) currentScene.getWindow();
        stage.setScene(tableViewScene);
        stage.show();
    }
    
    Staff selectedModel;
    Scene previousScene;
    
        // ----------------------------------- below this line is stuff needed to make add when the enter button is pressed --------------------------------
    @FXML
    public void enter(ActionEvent event) {
        
      int id = Integer.parseInt(enterIdField.getText());
        
      String course = enterCourseField.getText();
        
      String lastname = enterNameField.getText();
        
      String assignments = enterAssignmentsField.getText();
      
      String officehours = enterOfficeHoursField.getText();
        
        // create a staff instance
        Staff staff = new Staff();
        
        // set properties
        staff.setId(id);
        staff.setCourse(course);
        staff.setLastname(lastname);
        staff.setAssignments(assignments);
        staff.setOfficehours(officehours);
        // save this staff to database by calling Create operation        
        create(staff);   
    }
    
    @FXML
    void enterDelete(ActionEvent event) {

    }

    @FXML
    void enterUpdate(ActionEvent event) {
     
      int id = Integer.parseInt(enterIdField.getText());
        
      String course = enterCourseField.getText();
        
      String lastname = enterNameField.getText();
        
      String assignments = enterAssignmentsField.getText();
      
      String officehours = enterOfficeHoursField.getText();
       
        // create a staff instance
        Staff staff = new Staff();
       
        // set properties
       staff.setId(id);
       staff.setCourse(course);
       staff.setLastname(lastname);
       staff.setAssignments(assignments);
       staff.setOfficehours(officehours);
      //  save this staff to database by calling Create operation        
        update(staff);
    }
 // -------------------------------------------------------------------------------------------------------------------------------------
   
    public void create(Staff staff) {
        try {
            // begin transaction
            manager.getTransaction().begin();
            
            // sanity check
            if (staff.getId() != null) {
                
                // create student
                manager.persist(staff);
                
                // end transaction
                manager.getTransaction().commit();
                
                System.out.println(staff.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void update(Staff model) {
        try {

            Staff existingStaff = manager.find(Staff.class, model.getId());

            if (existingStaff != null) {
                // begin transaction
                manager.getTransaction().begin();
                
                // update all atttributes
                existingStaff.setCourse(model.getCourse());
                existingStaff.setLastname(model.getLastname());
                existingStaff.setAssignments(model.getAssignments());
                existingStaff.setOfficehours(model.getOfficehours());
                // end transaction
                manager.getTransaction().commit();
                System.out.println(existingStaff.toString() + " is updated");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void initData(Staff model) {
        selectedModel = model;
        enterCourseField.setText(model.getCourse());
        enterNameField.setText(model.getLastname());
        enterAssignmentsField.setText(model.getAssignments());
        enterOfficeHoursField.setText(model.getOfficehours());
    }
    
    EntityManager manager;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = (EntityManager) Persistence.createEntityManagerFactory("Group1FXMLPU").createEntityManager();
    }    
}
