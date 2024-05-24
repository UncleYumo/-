# JavaFX基础操作指南

> @author uncle_yumo
>
> @school Wuxi University
>
> @description 用于解析在本项目（CRMS-DEV）开发中涉及的部分操作

#### JavaFX主程序结构

```java
// App.java : 程序的主页面
package online.revolicise.demo;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch(args); // 作用是启动JavaFX的GUI程序，并调用start()方法；
    }

    @Override
    public void start(Stage primaryStage) throws Exception { // 程序的入口方法，在这里创建GUI组件并显示
        primaryStage.setTitle("JavaFX App"); // 设置窗口标题, 并显示窗口
        primaryStage.show(); // 显示窗口
    }

    @Override
    public void init() throws Exception { // 可用于创建数据库连接、初始化数据等
    }

    @Override
    public void stop() throws Exception { // 可用于释放资源、关闭数据库连接等
    }
}
```

```java
// Main.java 启动App的主类
package online.revolicise.demo;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------------- 程序开始运行 --------------");

        // launch the application
        Application.launch(App.class);

        System.out.println("-------------- 程序运行结束 --------------");
    }
}
```

```java
// module-info.java ：模块配置文件
module online.revolicise.demo { // 所定义的模块名称
    requires javafx.controls; // 提供JavaFX控件支持
    requires javafx.fxml; // 提供FXML视图支持

    // 提供JavaFX图形界面支持，包括Canvas、Scene、Stage、Image、Color、Font、Text、GraphicsContext等
    requires javafx.graphics; // 导入JavaFX图形模块，用于JavaFX UI编程
    requires java.sql; // 导入Java数据库连接模块，用于JDBC（Java数据库连接）
    requires java.desktop; // 导入Java桌面模块，用于AWT（Abstract Window Toolkit）
    requires java.naming; // 导入Java命名和目录模块，用于JNDI（Java命名和目录接口）
    exports online.revolicise.demo; // 导出当前模块的API接口，供其他模块使用
}
```

---

#### 添加运行时JVM参数

1. 修改Main.java的运行参数，这在IDEA中比较简单，按照图中的顺序点击Main -> Edit Configurations

2. 在 Vm options 一栏中点击图中圈出的位置，输入以下内容

   ```xml-dtd
   --module-path
   /Users/xxx/Downloads/javafx-sdk-11.0.2/lib  //在这里填入之前下载的javafx-sdk的位置
   --add-modules
   javafx.controls,javafx.fxml
   ```

   如果你的jdk11以及之后的版本，添加上面的内容即可

   如果你使用的是jdk14，则需要另外增加一条命令，即

   ```xml-dtd
   	--module-path
   	/Users/xxx/Downloads/javafx-sdk-11.0.2/lib  //在这里填入之前下载的javafx-sdk的位置
   	--add-modules
   	javafx.controls,javafx.fxml
   	--add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED1
   ```

---

#### Stage常用方法（顶级窗口）

> Stage（舞台），它代表了一个顶级窗口，是[JavaFX](https://so.csdn.net/so/search?q=JavaFX&spm=1001.2101.3001.7020)应用程序的主要容器;
>
> Stage可以包含多个场景（Scene），每个场景可以包含各种用户界面元素（如按钮、文本框等）;
>
> Stage提供了许多方法和属性，用于设置窗口的外观、行为和交互;

1. setTitle("Title")

2. getIcons().add(image);

   ```java
       try {
               Image e = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ImageFile/system.png")));
               primaryStage.getIcons().add(e);
           } catch (Exception e1) {
               System.out.println("Image not found");
           }
   ```

   ```java
   // 第二种方法，我用这个老报错，上面的没事
   primaryStage.getIcon().add(new Image("Image_Path"));
   ```

   

3. setResiiziable(bool); // true：窗口可改变大小，false：不可改变大小

4. set(X、Y、Width、Height); // 设置初始坐标和宽高

5. show(); / close();

6. initStyle(StagleStyle.DECORATED); //设置窗口的样式（枚举类型，共四种）

7. initModality(Modality.NONE); // 窗口模态，非模态时其他窗口可使用

   APPLICATION_MODAL; // 仅当前窗口可使用

   WINDOW_MODAL; // 仅禁用父窗口

8. initOwner(Stage parentWindow); // 设置父窗口为parentWindow

---

#### Stage事件（event）

1. 关闭窗口时确认

   ```java
           Platform.setImplicitExit(false); // 取消操作系统默认的退出行为
           primaryStage.setOnCloseRequest(event -> {
               event.consume(); // 取消窗口关闭事件
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // 创建确认对话框
               alert.setTitle("确认"); // 设置标题
               alert.setHeaderText("当前应用可能还在运行!!!"); // 设置头部文本
               alert.setContentText("你确定要退出程序?"); // 设置内容文本
               alert.showAndWait(); // 如果没有后文，需要添加此句
   
               Optional<ButtonType> isOK = alert.showAndWait(); // 显示确认对话框
               if(isOK.get() == ButtonType.OK) {
                   System.exit(0); // 退出程序
               }
   ```

---

#### Scene常用方法（场景）

1. 初始化场景并插入容器

   ```java
   AnchorPane root = new AnchorPane(); // 创建容器root
   Scene scene = new Scene(root,500,500); // 创建场景，并设置大小
   ```

2. 改变Scene（场景）内的鼠标图标

   ```java
   scene.setCursor(new ImageCursor(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imgFilePath)))));
   ```

---

#### 转到指定网站

```java
getHostServices().showDocument("URL");
```

---

#### Node类使用方法

> Node类为抽象类，使用前需要重写方法

---

#### Lable（标签）组件常用方法

1. 初始化标签

   ```java
   Label label = new Label("labelName");
   ```

2. 设置背景颜色

   ```java
   label.setStyle("-fx-background-color: red;");
   ```

3. 设置边框粗细和颜色

   ```java
   label.setStyle("-fx-border-color:blue; -fx-border-width: 3px");
   ```

4. 设置标签背景大小

   ```java
   label.setPreWidth(200);
   label.setPreHeight(400);
   ```

5. 设置文字居中

   ```java
   label.setAlignment(Pos.CENTER);
   ```

6. 设置标签是否显示

   ```java
   label.setVisible(true); // 此时为显示
   ```

7. 其他方法

   ```java
   label.setStyle("-fx-font-size: 14px; -fx-border-color: blue; -fx-border-width: 2px;"); // 设置字体大小
   label.setAlignment(Pos.BASELINE_CENTER); // 设置文字对齐方式
   label.setOpacity(1); // 设置透明度
   label.setRotate(90); // 设置旋转角度
   label.setScaleX(2); // 设置水平缩放比例
   label.setScaleY(2); // 设置垂直缩放比例
   label.setTranslateX(100); // 设置水平位移
   label.setTranslateY(100); // 设置垂直位移
   ```

---

#### 组件设置颜色

```java
Circle circle = new Circle(); // 创建一个圆对象
// web方法
circle.setFill(Color.web("#fffff"));
// rgb方法
circle.setFill(Color.rgb(255,0,0));
// 静态常量
circle.setFill(Color.BLUE);
```

---

#### 组件设置字体

```java
Label label = new Label("Label");
label.setFont(new Font(30)); // 设置size为30
// 静态方法
label.setFont(Font.font("微软内置字体名",FontWeight.BOLD,30)); 
// 加载第三方字体
Font.loadFont(String urlStr,doublesize); // 导入字库链接
```

```java
// 导入外部字库文件

// 加载本地字体文件
InputStream fontInputStream = new FileInputStream("path/to/fontFile.ttf");
Font customFont = Font.loadFont(fontInputStream, 20);
// 将加载的字体应用到文本
Text text = new Text("Hello, World!");
text.setFont(customFont);
```

---

#### 图片类（Class Image）

> 支持四种格式：.bmp | .gif | .jpeg | .png

1. 初始化图片对象

   ```java
   Image image01 = new Image("文件流、URL"); // 构造方法参数可为文件流或图片URL
   ```

2. 展示图片

   ```java
   // 添加图片至root
   root.getChildren().add(new ImageView(image01));
   ```

---

#### UI事件的属性绑定

1. 绑定Circle中心位置为Scene中心

   ```java
   Circle circle = new Circle();
   circle.setCenterX(250); // 设置圆心的x坐标
   circle.setCenterY(250); // 设置圆心的y坐标
   circle.setRadius(100); // 设置圆的半径
   circle.setStroke(Color.BLACK); // 设置边框颜色
   circle.setFill(Color.WHITE); // 设置填充颜色
   
   Scene scene = new Scene(root, 500, 500);
   
   circle.centerXProperty().bind(scene.widthProperty().divide(2)); // 设置圆心的x坐标绑定场景宽度的一半
   circle.centerYProperty().bind(scene.heightProperty().divide(2)); // 设置圆心的y坐标绑定场景高度的一半
   ```

2. 属性设置监听器  `addListener()`

   监听圆心的坐标变化

   ```java
   circle.centerXProperty().addListener((obs, oldVal, newVal) -> {
       System.out.println("Circle centerX changed to " + newVal);
   }); // 监听圆心的x坐标变化
   
   
   circle.centerYProperty().addListener((obs, oldVal, newVal) -> {
       System.out.println("Circle centerY changed to " + newVal);
   }); // 监听圆心的y坐标变化
   ```

3. 监听鼠标的位置变化

   ```java
   /**
    * 应用程序启动方法
    */
   System.out.println("App start method called");
   
   // 创建一个 Label 用于显示鼠标位置信息
   Label label_mouseLocation = new Label();
   
   // 创建一个 AnchorPane 作为根节点
   AnchorPane root = new AnchorPane();
   
   // 创建一个 Scene，并设置根节点和大小
   Scene scene = new Scene(root, 500, 500);
   
   // 监听鼠标移动事件
   scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
       
           /**
        * 设置鼠标移动事件监听器
        * 创建了一个名为 EventHandler 的匿名类，并将其实例化后作为参数传递      * 给了 setOnMouseMoved 方法。
        * 这个匿名类实现了 EventHandler 接口，并指定泛型为 MouseEvent，      * 表示它能够处理鼠标事件。
        * 处理鼠标移动事件
        * @param mouseEvent 鼠标事件对象
        */
       @Override
       public void handle(MouseEvent mouseEvent) {
           // 更新 Label 上的鼠标位置信息
           label_mouseLocation.setText(
               "Mouse location: " + mouseEvent.getX() + "," + mouseEvent.getY()
           );
       }
   });
   
   // 将 Label 添加到根节点中
   root.getChildren().add(label_mouseLocation);
   
   // 设置主舞台的场景，并显示
   primaryStage.setScene(scene);
   primaryStage.show();
   }
   ```

4. 按钮点击事件

   第一种：

   ```java
   Button button01 = new Button("buttonName");
   button01.setLayoutX(200);
   button01.setLayoutY(200); // 设置按钮点击事件（箭头函数
   button01.setOnAction(event -> { // 点击后执行的方法
       // event
   })
   ```

   第二种：

   ```java
   button.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
   		// event
       }
   });
   ```

5. 键盘点击事件

   -  键盘按下

   ```java
   setOnKeyPressed(new EvenHander<KeyEvent>() {
       @Override
       public void handle(ActionEvent event) {
       // event
       }
   });
   ```

   - 

   ```java
   scene.setOnKeyTyped(event -> {
       String character = event.getCharacter(); // 获取输入的字符
       System.out.println("Key typed: " + character);
       // 处理用户输入的字符
   });
   ```

   - 键盘松开

   ```java
   setOnKeyReleased();
   ```

6. 文本框拖入文件时，显示其文件路径

   ```java
   // 处理文本框拖入事件
   textField.setOnDragOver(event -> {
       Dragboard dragboard = event.getDragboard();         
       // 获取拖放数据板
       if (dragboard.hasFiles()) {                             
           // 检查数据板中是否包含文件
           String getPath = dragboard.getFiles().get(0).getAbsolutePath();
           // 获取被拖入文件的绝对路径
           textField.setText(getPath);
           // 在文本框中显示文件的绝对路径
       }
   });
   ```

7. 获取文本框内容

   ```java
   String text;
   TextField textField = new TextField();
   text = textField.getText();
   System.out.println(text);
   ```

8. 文本框内回车事件

   ```java
   /**
   在JavaFX中，文本框的默认行为是在用户按下回车键时触发setOnAction中指定的事件处理器。这是JavaFX文本框的内建行为，无需额外的代码。
   */
   textField.setOnAction(event -> { // 处理文本框回车事件
       String text = textField.getText();
       System.out.println(text);
   });
   ```


9. 按下ESC键退出程序

   ```java
   // 1.按下ESC键退出程序
   scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
       @Override
       public void handle(KeyEvent event) {
           if (event.getCode() == KeyCode.ESCAPE) {
               Platform.exit();
               System.out.println("App exit");
               System.exit(0);
           }
       }
   });
   ```

---

#### FXML布局文件

1. 导入fxml文件

   ```java
   // Pane: 所有GUI组件的父容器，可以包含其他组件，如BorderPane、StackPane等
   Pane root = FXMLLoader.load(getClass().getResource("demo_layout.fxml"));
   ```

2. 设置事件

   2.1 创建controller的类

   2.2 fxml中声明controller

   ```java
   fx:controller="online.revolicise.demo.DemoController"
   ```

   2.3 组件中设置id，并设置按钮事件

   ```xml-dtd
   <AnchorPane xmlns="http://javafx.com/javafx"
               xmlns:fx="http://javafx.com/fxml"
               fx:controller="online.revolicise.demo.DemoController" <!-- 声明controller 并锚定class位置" -->
               prefHeight="400.0" prefWidth="600.0">
       <children>
   <Label fx:id="label_fx" text="JavaFX Demo" layoutX="150" layoutY="200"> <!-- 添加fx:id="label" -->
               <font>
                   <Font size="30.0" />
               </font>
           </Label>
           <Button fx:id="button_fx" text="向上移动" layoutX="150" layoutY="260" onAction="#onUp"> <!-- 添加fx:id="button" 与点击事件onAction="#onUp" -->
               <font>
                   <Font size="20.0" />
               </font>
           </Button>
       </children>
   </AnchorPane>
   ```

   2.4 编写Controller类

   ```java
   package online.revolicise.demo;
   
   import javafx.fxml.FXML;
   import javafx.scene.control.Button;
   import javafx.scene.control.Label;
   
   public class DemoController {
       @FXML // 声明一个FXML标签
       public Label label_fx; // 声明一个标签，当标签名与fx:id一致时，自动绑定
   
       @FXML
       public Button button_fx;
   
       public void onUp() { // 定义一个方法，当按钮被点击时，自动调用
           label_fx.setLayoutY(label_fx.getLayoutY() - 5);
           System.out.println("up");
       }
   }
   ```

---

#### Scene Builder插件使用

---

#### Controller类的使用

1. initialize()方法

   ```java
   package online.revolicise.demo;
   
   import javafx.application.Platform;
   import javafx.fxml.FXML;
   import javafx.scene.control.Button;
   import javafx.scene.control.TextField;
   
   public class DemoController {
       @FXML
       public TextField textField_input;
       public void getInput() {}
       
       // initialize()方法调用（构造函数）
       public void initialize() {
           System.out.println("initialize()方法被调用");
           System.out.println("初始化完成");
       }
   }
   ```

2. 在主类中获得Controller类的引用

   2.1 主类代码逻辑

   ```java
   // 创建FXMLLoader对象
   FXMLLoader fxmlLoader = new FXMLLoader();
   
   // 设置加载FXML文件的路径
   fxmlLoader.setLocation(getClass().getResource("hello.fxml"));
   
   // 加载并解析FXML文件内容，返回根节点
   Parent root = fxmlLoader.load();
   
   // 获取Controller对象
   Hello controller = fxmlLoader.getController();
   
   // 调用Controller对象的label_hello属性并设置文本内容
   controller.label_hello.setText("修改Controller内组件的内容");
   ```

---

#### 数据库操作

1. 读取数据并展示在TableView

   1.1 创建TableView组件

   ```xml-dtd
           <TableView fx:id="tableView" layoutY="101.0" prefHeight="299.0" prefWidth="604.0">
               <columns>
                   <TableColumn fx:id="tableView_id" prefWidth="75.0" text="id" />
                   <TableColumn fx:id="tableView_name" prefWidth="75.0" text="name" />
                   <TableColumn fx:id="tableView_birth" prefWidth="75.0" text="birth" />
               </columns>
           </TableView>
   ```

   1.2 在Controller类中链接组件，并编写展示数据的代码

   ```java
   /**
    * DemoController类是一个JavaFX的控制器类，用于管理与GUI界面相关的逻辑操作。
    */
   package online.revolicise.demo;
   
   /**
    * 导入所需的类和包
    */
   import online.revolicise.demo.Student; // 导入Student类
   import *; // 略
   
   public class DemoController {
       /**
        * 使用@FXML注解来标记FXML文件中的控件，使其能够在代码中被引用
        */
       @FXML
       public TableView<Student> tableView; // 学生信息表格视图
       @FXML
       public TableColumn<Student,Integer> tableView_id; // 表格列：学生ID
       @FXML
       public TableColumn<Student,String> tableView_name; // 表格列：学生姓名
       @FXML
       public TableColumn<Student,Date> tableView_birth; // 表格列：学生生日
       
       MySQL_Connection connection = new MySQL_Connection(); // 创建MySQL_Connection对象
   
       }
       /**
        * 初始化方法，会在FXML文件加载之后自动调用
        */
       public void initialize() {
           connection.conn = connection.getConnection(); // 获取数据库连接
           String[] idList = connection.outputTable("student","id"); // 获取学生ID列表
           String[] nameList = connection.outputTable("student","name"); // 获取学生姓名列表
           String[] birthList = connection.outputTable("student","birth"); // 获取学生生日列表
   
           ObservableList<Student> initList = FXCollections.observableArrayList(); // 创建一个可观察的学生列表
           tableView_id.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id")); // 设置表格列的数值工厂，以获取学生ID
           tableView_name.setCellValueFactory(new PropertyValueFactory<Student,String>("name")); // 设置表格列的数值工厂，以获取学生姓名
           tableView_birth.setCellValueFactory(new PropertyValueFactory<Student,Date>("birth")); // 设置表格列的数值工厂，以获取学生生日
           for(int i=0;i<idList.length;i++) { // 遍历学生列表
               if(idList[i]==null){ // 如果学生ID为空则停止遍历
                   System.out.println("表格数据读取完毕");
                   break;
               }
               initList.add(new Student(Integer.parseInt(idList[i]),nameList[i], Date.valueOf(birthList[i]))); // 将学生信息添加到initList中
           }
           tableView.setItems(initList); // 将initList设置为表格视图的数据源
       }
   }
   ```

#### 用线程处理事件

1. 键盘事件修改Controller类中的按钮组件名

   ```java
   if (event.getCode() == KeyCode.R) {
       Thread thread = new Thread(()-> {
           String newValue = "线程启动！";
           Platform.runLater(()-> {
               controller.button_hello.setText(newValue);
               System.out.println("R pressed , button text changed to " + newValue);
           });
       });
       thread.start();
   }
   ```

   