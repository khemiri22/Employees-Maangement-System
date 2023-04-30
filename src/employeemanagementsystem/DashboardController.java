package employeemanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane parent_form;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_EmployeeDateMember;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_EmployeeFirstName;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_EmployeeGender;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_EmployeeID;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_EmployeeLastName;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_EmployeePhone;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_EmployeePosition;

    @FXML
    private TextField addEmployee_employeeFirstName;

    @FXML
    private ComboBox<?> addEmployee_employeeGender;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private ImageView addEmployee_employeeImage;

    @FXML
    private TextField addEmployee_employeeLastName;

    @FXML
    private TextField addEmployee_employeePhone;

    @FXML
    private ComboBox<?> addEmployee_employeePosition;

    private String addEmployee_employeePositionString;
    private String addEmployee_employeeGenderString;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private TableView<EmployeeData> addEmployee_tableView;

    @FXML
    private AnchorPane employeeManager_form;

    @FXML
    private Button logout_btn;


    @FXML
    private Label username;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    private Image image;

    public boolean checkEmployeeExist(String id){
        String check = "SELECT * FROM employee WHERE employeeId = ?";
        try{
            connect=Database.connectDb();
            prepare = connect.prepareStatement(check);
            prepare.setString(1,id);
            result = prepare.executeQuery();
            if (result.next()){
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public void addEmployeeAdd() throws Exception {
        Alert alert;
        if (StringValidation.verifyStringEmpty(addEmployee_employeeID.getText()) || StringValidation.verifyStringEmpty(addEmployee_employeeFirstName.getText())
                || StringValidation.verifyStringEmpty(addEmployee_employeeLastName.getText()) || addEmployee_employeeGender.getSelectionModel().getSelectedItem() == null
                || StringValidation.verifyStringEmpty(addEmployee_employeePhone.getText()) || addEmployee_employeePosition.getSelectionModel().getSelectedItem() == null
                || CurrentData.path == null || CurrentData.path == "")
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
        }else {
            if (!StringValidation.isNumber(addEmployee_employeeID.getText()))
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("ID must be a Number");
                alert.showAndWait();
            }
            else if (!StringValidation.verifyStringLength(addEmployee_employeeFirstName.getText()) || !StringValidation.verifyStringLength(addEmployee_employeeLastName.getText()))
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The FirstName And LastName must contain at least 3 caracters and not more than 15 caracters");
                alert.showAndWait();
            }else if (!StringValidation.isAllLetters(addEmployee_employeeFirstName.getText()) || !StringValidation.isAllLetters(addEmployee_employeeLastName.getText()))
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The FirstName And LastName must contain Only Letters");
                alert.showAndWait();
            }else if (!StringValidation.isNumber(addEmployee_employeePhone.getText()))
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The Phone number must contain only numbers");
                alert.showAndWait();
            }else if (!StringValidation.verifyPhoneLength(addEmployee_employeePhone.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The Phone number must contain exactly 8 numbers");
                alert.showAndWait();
            }
            else {
                try{
                    boolean exist = checkEmployeeExist(addEmployee_employeeID.getText());
                    if (exist){
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Employee ID " + addEmployee_employeeID.getText() + " is already exist!");
                        alert.showAndWait();
                    }else {
                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        String sql = "INSERT INTO employee " +
                                "(employeeId ,firstName,lastName,gender,phone,position,image,date) VALUES (?,?,?,?,?,?,?,?)";
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1,addEmployee_employeeID.getText());
                        prepare.setString(2,addEmployee_employeeFirstName.getText());
                        prepare.setString(3,addEmployee_employeeLastName.getText());
                        prepare.setString(4,(String) addEmployee_employeeGender.getSelectionModel().getSelectedItem());
                        prepare.setString(5,addEmployee_employeePhone.getText());
                        prepare.setString(6,(String) addEmployee_employeePosition.getSelectionModel().getSelectedItem());
                        String uri = CurrentData.path;
                        uri = uri.replace("\\","\\\\");
                        prepare.setString(7,uri);
                        prepare.setString(8,String.valueOf(sqlDate));
                        prepare.executeUpdate();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Confirmation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Added!");
                        alert.showAndWait();
                        addEmployeeShowListData();
                        addEmployeeReset();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void addEmployeeUpdate() throws Exception {
        Alert alert;
        if (StringValidation.verifyStringEmpty(addEmployee_employeeID.getText()))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the ID Field");
            alert.showAndWait();
        }else{
            if (!StringValidation.isNumber(addEmployee_employeeID.getText()))
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("ID must be a Number");
                alert.showAndWait();
            }
            else if (!StringValidation.verifyStringLength(addEmployee_employeeFirstName.getText()) || !StringValidation.verifyStringLength(addEmployee_employeeLastName.getText()))
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The FirstName And LastName must contain at least 3 caracters and not more than 15 caracters");
                alert.showAndWait();
            }else if (!StringValidation.isAllLetters(addEmployee_employeeFirstName.getText()) || !StringValidation.isAllLetters(addEmployee_employeeLastName.getText()))
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The FirstName And LastName must contain Only Letters");
                alert.showAndWait();
            }else if (!StringValidation.isNumber(addEmployee_employeePhone.getText()))
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The Phone number must contain only numbers");
                alert.showAndWait();
            }else if (!StringValidation.verifyPhoneLength(addEmployee_employeePhone.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The Phone number must contain exactly 8 numbers");
                alert.showAndWait();
            }else {
                boolean exist = checkEmployeeExist(addEmployee_employeeID.getText());
                if(!exist){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID " + addEmployee_employeeID.getText() + " does not exist!");
                    alert.showAndWait();
                }
                else{
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure tou want to update the employee with ID " + addEmployee_employeeID.getText()+" ?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if(option.get().equals(ButtonType.OK))
                    {
                        String sql = "UPDATE employee SET firstName = ?,lastName = ?,gender = ?,phone = ?,position =?, image = ? ,date = ? WHERE employeeId = ?";
                        connect = Database.connectDb();
                        try{
                            prepare = connect.prepareStatement(sql);
                            prepare.setString(1,addEmployee_employeeFirstName.getText());
                            prepare.setString(2,addEmployee_employeeLastName.getText());
                            prepare.setString(4,addEmployee_employeePhone.getText());
                            if(addEmployee_employeePosition.getSelectionModel().getSelectedItem() != null){
                                addEmployee_employeePositionString=(String)addEmployee_employeePosition.getSelectionModel().getSelectedItem();
                            }
                            if(addEmployee_employeeGender.getSelectionModel().getSelectedItem() != null){
                                addEmployee_employeeGenderString=(String)addEmployee_employeeGender.getSelectionModel().getSelectedItem();
                            }
                            prepare.setString(3,addEmployee_employeeGenderString);
                            prepare.setString(5,addEmployee_employeePositionString);
                            String uri = CurrentData.path;
                            uri = uri.replace("\\","\\\\");
                            Date date = new Date();
                            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                            prepare.setString(6,uri);
                            prepare.setString(7,String.valueOf(sqlDate));
                            prepare.setString(8,addEmployee_employeeID.getText());
                            prepare.executeUpdate();
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Employee with ID " + addEmployee_employeeID.getText()+" was updated successfully");
                            alert.showAndWait();
                            addEmployeeShowListData();
                            addEmployeeReset();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    private ObservableList<EmployeeData> addEmployeeList;
    public void addEmployeeDelete() throws Exception {
        Alert alert;
        if (StringValidation.verifyStringEmpty(addEmployee_employeeID.getText()))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the ID Field");
            alert.showAndWait();
        }
        else if (!StringValidation.isNumber(addEmployee_employeeID.getText()))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("ID must be a Number");
            alert.showAndWait();
        }
        else {
            boolean exist = checkEmployeeExist(addEmployee_employeeID.getText());
            if(!exist){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Employee ID " + addEmployee_employeeID.getText() + " does not exist!");
                alert.showAndWait();
            }
            else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure tou want to delete the employee with ID " + addEmployee_employeeID.getText()+" ?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK))
                {
                    String sql = "DELETE FROM employee WHERE employeeId = ?";
                    connect = Database.connectDb();
                    try{
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1,addEmployee_employeeID.getText());
                        prepare.executeUpdate();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Employee with ID " + addEmployee_employeeID.getText()+" was deleted successfully");
                        alert.showAndWait();
                        addEmployeeShowListData();
                        addEmployeeReset();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void addEmployeeReset(){
        addEmployee_employeeID.setText("");
        addEmployee_employeeFirstName.setText("");
        addEmployee_employeeLastName.setText("");
        addEmployee_employeePhone.setText("");
        addEmployee_employeeGender.getSelectionModel().clearSelection();
        addEmployee_employeePosition.getSelectionModel().clearSelection();
        addEmployee_employeeImage.setImage(null);
        CurrentData.path="";
    }
    public void AddEmployee_search(){
        FilteredList<EmployeeData> filteredList = new FilteredList<>(addEmployeeList,e ->true);
        addEmployee_tableView.setItems(filteredList);
        addEmployee_search.textProperty().addListener((observable,oldValue,newValue) -> {
            filteredList.setPredicate(predicateEmployeeData ->{
                if(newValue ==null || newValue.isEmpty()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if(predicateEmployeeData.getEmployeeId().toString().contains(searchKey)){
                    return true;
                } else if (predicateEmployeeData.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
                }
                else if (predicateEmployeeData.getLastName().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateEmployeeData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateEmployeeData.getPhone().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateEmployeeData.getPosition().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateEmployeeData.getDate().toString().contains(searchKey)) {
                    return true;
                }
                    return false;
            });
        });

    }
    private String[] positionList = {"Web Developer (Back End)", "Web Developer (Front End)", "Mobile Developer","Scrum Master","Marketer Coordinator"};

    public void addEmployeePositionList(){
        List<String> listP = new ArrayList<String>(5);
        for (String data : positionList){
            listP.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listP);
        addEmployee_employeePosition.setItems(listData);
    }
    private String[] genderList = {"Male","Female"};

    public void addEmployeeGenderList(){
        List<String> listG = new ArrayList<String>(2);
        for (String data : genderList){
            listG.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listG);
        addEmployee_employeeGender.setItems(listData);
    }
    public void addEmployeeInsertImage(){
        FileChooser open = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        open.getExtensionFilters().add(imageFilter);
        File file = open.showOpenDialog(parent_form.getScene().getWindow());
        if(file != null){
            CurrentData.path=file.getAbsolutePath();
            image = new Image(file.toURI().toString(),101,116,false,true);
            addEmployee_employeeImage.setImage(image);
        }
    }
    public ObservableList<EmployeeData> addEmployeeListData(){
        ObservableList<EmployeeData> dataList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";
        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()){
                EmployeeData employee = new EmployeeData(result.getInt("employeeId"),result.getString("firstName"),result.getString("lastName"),result.getString("gender"),result.getString("phone"),result.getString("position"),result.getString("image"),result.getDate("date"));
                dataList.add(employee);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataList;
    }

    public void addEmployeeShowListData(){
        addEmployeeList = addEmployeeListData();
        addEmployee_col_EmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        addEmployee_col_EmployeeFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployee_col_EmployeeLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployee_col_EmployeeGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_EmployeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addEmployee_col_EmployeePosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_col_EmployeeDateMember.setCellValueFactory(new PropertyValueFactory<>("date"));
        addEmployee_tableView.setItems(addEmployeeList);
        addEmployee_employeePosition.setPromptText("Choose");
        addEmployee_employeeGender.setPromptText("Choose");
    }
    public void addEmployeeSelect(){
        EmployeeData employee = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();
        if(num-1<-1)
            return;
        addEmployee_employeeID.setText(String.valueOf(employee.getEmployeeId()));
        addEmployee_employeeFirstName.setText(String.valueOf(employee.getFirstName()));
        addEmployee_employeeLastName.setText(String.valueOf(employee.getLastName()));
        addEmployee_employeePhone.setText(String.valueOf(employee.getPhone()));
        CurrentData.path=employee.getImage();
        String uri = "file:"+employee.getImage();
        image = new Image(uri,101,116,false,true);
        addEmployee_employeeImage.setImage(image);
        addEmployee_employeeGenderString = employee.getGender();
        addEmployee_employeePositionString = employee.getPosition();
        addEmployee_employeePosition.setPromptText("Choose");
        addEmployee_employeeGender.setPromptText("Choose");
    }
    public void displayUsername(){
        username.setText(CurrentData.username.toUpperCase());
    }

    private double x=0;
    private double y=0;

    public void logout(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you  sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        try{
            if(option.get().equals(ButtonType.OK)){
                logout_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                root.setOnMousePressed((MouseEvent event) ->{
                    x=event.getSceneX();
                    y= event.getSceneY();
                });
                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });
                root.setOnMouseReleased((MouseEvent event)->{
                    stage.setOpacity(1);
                });
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)parent_form.getScene().getWindow();
        stage.setIconified(true);
    }


    @Override   
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEmployeeShowListData();
        displayUsername();
        addEmployeePositionList();
        addEmployeeGenderList();
        AddEmployee_search();
    }
}
