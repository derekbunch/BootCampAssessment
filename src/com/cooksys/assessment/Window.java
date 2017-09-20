package com.cooksys.assessment;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.OutputDeviceAssigned;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Window {

	private JFrame frame;

	/**
	 * Launch the application. The main method is the entry point to a Java application. 
	 * For this assessment, you shouldn't have to add anything to this.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. This is the constructor for this Window class.
	 * All of the code here will be executed as soon as a Window object is made.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This is where Window Builder
	 * will generate its code.
	 */
	public void initialize() {
		
		//Create Frame
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Create menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 434, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		JMenuItem itemSave = new JMenuItem("Save");
		JMenuItem itemLoad = new JMenuItem("Load");
		JMenuItem itemExit = new JMenuItem("Exit");
		
		//List models
		DefaultListModel source = new DefaultListModel();
		DefaultListModel destination = new DefaultListModel();
		
		//Save Button (menuBar)
		itemSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					ArrayList<String> save = new ArrayList<>();
					for (int i = 0; i < destination.size(); i++){
						save.add((String)destination.getElementAt(i));
					}
					
					Parts parts = new Parts();
					parts.setPart(save);
					try {
						//Marshaller
						File file = new File("src\\data\\components.xml");
						JAXBContext context = JAXBContext.newInstance(Parts.class);
						Marshaller marshaller = context.createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						marshaller.marshal(parts, file);
					} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		);
		
		
		//Exit Button (menuBar)
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		//Populate menuBar with menu buttons
		menuFile.add(itemSave);
		menuFile.add(itemLoad);
		menuFile.add(itemExit);
		
		//Fill left list with items
		String components[] = {"Case", "Motherboard", "CPU", "RAM", "GPU",
		        "HDD", "PSU"};
		
		for(int i = 0; i < components.length; i++)
        {
            source.addElement(components[i]);
        }
		
		
		//Left panel and JList
		JPanel pnlLeft = new JPanel();
		pnlLeft.setBounds(0, 22, 174, 239);
		frame.getContentPane().add(pnlLeft);
		pnlLeft.setLayout(null);
		JList lstLeft = new JList(source);
		
		lstLeft.setVisibleRowCount(10);
		lstLeft.setFixedCellHeight(20);
		lstLeft.setFixedCellWidth(140);
		lstLeft.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		lstLeft.setBounds(0, 0, 174, 239);
		pnlLeft.add(lstLeft);
		
		//Right panel and JList
		JPanel pnlRight = new JPanel();
		pnlRight.setBounds(258, 22, 174, 239);
		frame.getContentPane().add(pnlRight);
		pnlRight.setLayout(null);
		JList lstRight = new JList(destination);
		
		lstLeft.setVisibleRowCount(10);
		lstLeft.setFixedCellHeight(20);
		lstLeft.setFixedCellWidth(140);
		lstLeft.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		lstRight.setBounds(0, 0, 174, 239);
		pnlRight.add(lstRight);
	
		//Add Button
		JButton btnAdd = new JButton(">>");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Object selectedValue : lstLeft.getSelectedValuesList()){
					destination.addElement(selectedValue);
					source.removeElement(selectedValue);
				}
			}
		});
		btnAdd.setBounds(184, 116, 64, 23);
		frame.getContentPane().add(btnAdd);
		
		//Remove Button
		JButton btnRemove = new JButton("<<");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Object selectedValue : lstRight.getSelectedValuesList()){
					source.addElement(selectedValue);
					destination.removeElement(selectedValue);
				}
			}
		});
		btnRemove.setBounds(184, 140, 64, 23);
		frame.getContentPane().add(btnRemove);
		
		//Load Button (menuBar)
		itemLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
				
				//Unmarshaller
				JAXBContext context = JAXBContext.newInstance(Parts.class);
				Unmarshaller um = context.createUnmarshaller();
				
				File file = new File("src\\data\\components.xml");
				
				//Read XML from file
				Parts part = (Parts) um.unmarshal(file);
				List<String> output = part.getPart();
				
				destination.removeAllElements();
				for (int i = 0; i < output.size() - 1; i++){
					for (int l = 0; l < source.getSize() - 1; l++){
						if (output.get(i).matches((String) source.get(l))) {
							source.removeElement(output.get(i));
					}
					}
					
				destination.addElement(output.get(i));
				}
				} catch (JAXBException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});	
	}
}
