package TeacherOperate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("unused")
public class Course {
	
	JFrame courseWindow;
	JTable table;
	DefaultTableModel model;
	DefaultTableCellRenderer center;
	JScrollPane scrollPane;
	Connection con;
	Statement stmt;
	String id;
	public static boolean COURSE = false;
	
	@SuppressWarnings("serial")
	public Course(String id) throws Exception{
		
		this.id = id;
		
		COURSE = true;
		courseWindow = new JFrame("教授课程");
		courseWindow.setLayout(null);
		courseWindow.setResizable(false);
		courseWindow.setUndecorated(true);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image image = kit.getImage("src/Picture/picture3.JPG");       
        courseWindow.setIconImage(image);
		courseWindow.setSize(850,520);	
		
		//连接数据库
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/nrc";
		String user = "root";
		String password = "0721";
		con = DriverManager.getConnection(url, user, password);
		stmt = con.createStatement();
		
		String str1 = "SELECT * FROM TeacherList WHERE ID = '" + this.id + "';";
		ResultSet rs = stmt.executeQuery(str1);
		String name = null;
		while(rs.next()) {
			name = rs.getString("name");
		}
		String str = "SELECT COUNT(*) FROM CourseList WHERE teacher = '" + name + "';";
		String str2 = "SELECT * FROM CourseList WHERE teacher = '" + name + "';";
		rs = stmt.executeQuery(str);
		int num = 0;  //判断有多少行来创建合适的二维数组
		while(rs.next()) {
			num = rs.getInt(1);
		}		
		String p[][] = new String[num][2];
		rs = stmt.executeQuery(str2);
		int cnt = 0;
		//导入数据
		while(rs.next()) {
			String nameString = rs.getString("name");
			String schoolClassString = rs.getString("schoolClass");
			p[cnt][0] = nameString;
			p[cnt][1] = schoolClassString;
			cnt++;
		}
		
		String []columnNames = {"课程名称","教授班级"};
		String[][] tableValues = p;
		model = new DefaultTableModel(tableValues,columnNames);
		center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		table = new JTable(model) {
			public boolean isCellEditable(int row, int column) { 
			 	 return false;
			  }
		};
		table.setDefaultRenderer(Object.class, center);
		scrollPane = new JScrollPane(table);
		table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 30));
		table.setRowHeight(30); 

		courseWindow.add(scrollPane);
		
		table.setFont(new Font("华康方圆体W7(P)",Font.PLAIN, 15));
		table.getTableHeader().setFont(new Font("华康方圆体W7(P)",Font.PLAIN, 15));

		scrollPane.setBounds(0, 0, 850, 520);
		
		courseWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		courseWindow.validate();
		courseWindow.setVisible(false);
		
	}
	
}