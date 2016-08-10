package reflectionexplorer.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import reflectionexplorer.reflection.Reflection;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpenFile;
	private JLabel lblClassName;
	private JTextField textField_1;
	private String filePath = "";

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	
	public GUI() {
		super("Reflection Explorer");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 496);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmOpenFile = new JMenuItem("Open File");
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fileChoose = new JFileChooser();
				fileChoose.showOpenDialog(null);
				String path = fileChoose.getSelectedFile().getAbsolutePath();
				filePath = path;
				textField.setText(path);

			}
		});
		mnFile.add(mntmOpenFile);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		final DefaultListModel<String> listModel = new DefaultListModel<String>();

		JLabel lblLoaded = new JLabel("Loaded - ");

		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);

		lblClassName = new JLabel("Class Name - ");

		textField_1 = new JTextField();
		textField_1.setColumns(10);

		JButton btnGetData = new JButton("Get Data");
		btnGetData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String className = textField_1.getText();
				System.out.println(className);
				Class<?> clazz = Reflection.loadClass(new File(filePath),
						className);
				if (clazz != null)
					System.out.println(clazz.toString());
				listModel.addElement("METHODS - ");
				for (Method m : clazz.getDeclaredMethods()) {
					String params = "";
					for (Class<?> s : m.getParameterTypes()) {
						String aString = s.getName().toString();
						params += aString + " ";
					}
					listModel.addElement(m.getName() + "| Parameter Types: "
							+ params + "| Return Type: " + m.getReturnType());
				}
				listModel.addElement("FIELDS - ");
				for (Field f : clazz.getDeclaredFields()) {
					listModel.addElement(f.getName());
				}
				listModel.addElement("CONSTRUCTORS - ");
				for (Constructor<?> c : clazz.getDeclaredConstructors()) {
					listModel.addElement(c.getName());
				}
				listModel.addElement("CLASSES - ");
				for (Class<?> c : clazz.getDeclaredClasses()) {
					if (c.isInterface()) {
						listModel.addElement("INTERFACE: " + c.getName());
					} else if (c.isEnum()) {
						listModel.addElement("ENUM: " + c.getName());
					} else if (c.isAnnotation()) {
						listModel.addElement("ANNOTATION: " + c.getName());
					} else {
						listModel.addElement(c.getName());
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		
		//Terribly messy GUI builder code.  In retrospect this was unnecessary.
		
		gl_contentPane.setHorizontalGroup(gl_contentPane
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addGap(7)
								.addComponent(lblLoaded)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(textField,
										GroupLayout.PREFERRED_SIZE, 245,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED,
										59, Short.MAX_VALUE)
								.addComponent(lblClassName)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField_1,
										GroupLayout.PREFERRED_SIZE, 126,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnGetData).addGap(30))
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addComponent(scrollPane,
										GroupLayout.PREFERRED_SIZE, 366,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(310, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																gl_contentPane
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				textField,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lblClassName)
																		.addComponent(
																				textField_1,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				btnGetData))
														.addComponent(lblLoaded))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE, 386,
												Short.MAX_VALUE)
										.addContainerGap()));

		JList<String> list = new JList<String>(listModel);
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);
	}
}
