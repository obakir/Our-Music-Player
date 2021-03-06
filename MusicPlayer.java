/*
 * Authors: Amar Bakir, Tolby Lew
 * Software Methodology
 * Assignment 1: Song Library
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MusicPlayer extends JFrame{

	public static int songCount = 0;
	JButton add, edit, delete, confirm, clear;
	JLabel nLabel, artLabel, albLabel, yLabel;
	JTextField name, artist, album, year;
	JTextArea userMessage;
	JList<String> songList;
	DefaultListModel<String> model;
	static ArrayList<String> songDB = new ArrayList<String>();
	String delims = "/";
	
	public MusicPlayer(String title){
		super(title);
		
		// use FlowLayout
		setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		

		// Set up the Panels
		JPanel listPanel = new JPanel(new GridLayout(0,1));
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 15 ));
		JPanel textPanel = new JPanel(new GridLayout(0, 1, 0, 5));
		
		//AddListbox
		model = new DefaultListModel<String>();
		songList = new JList<String>();
		songList.setModel(model);
		songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		songList.setSelectedIndex(0);
		//songList.addListSelectionListener(this);
		songList.setVisibleRowCount(15);
	    JScrollPane listScrollPane = new JScrollPane(songList);
	    listPanel.add(listScrollPane);
	    listPanel.setPreferredSize(new Dimension (500, 200));
	    
		//Add Buttons
		add = new JButton("Add");
		buttonPanel.add(add);
		edit = new JButton("Edit");
		buttonPanel.add(edit);
		delete = new JButton("Delete");
		buttonPanel.add(delete);
		confirm = new JButton("Confirm");
		confirm.setEnabled(false);
		buttonPanel.add(confirm);
		clear = new JButton("Clear");
		buttonPanel.add(clear);
		
		//Add TextFields
		name = new JTextField("Name", 10);
		nLabel = new JLabel("Name");
		textPanel.add(nLabel);		
		textPanel.add(name);
		artLabel = new JLabel("Artist");
		artist = new JTextField("Artist", 10);
		textPanel.add(artLabel);
		textPanel.add(artist);
		albLabel = new JLabel("Album");
		album = new JTextField("Album", 10);
		textPanel.add(albLabel);
		textPanel.add(album);
		yLabel = new JLabel("Year");
		year = new JTextField("Year", 10);
		textPanel.add(yLabel);
		textPanel.add(year);
		
		//Add JLabel
	    
		userMessage = new JTextArea();
		userMessage.setEditable(false);
		userMessage.setLineWrap(true);
		userMessage.setBackground(this.getBackground());
		listPanel.add(userMessage);
		userMessage.setText("Song description");
		userMessage.setPreferredSize(new Dimension (500, 50));
		userMessage.setMaximumSize(new Dimension (500, 50));
		userMessage.setMinimumSize(new Dimension (500, 50));
		
		//add the panels
		add(listPanel);
		add(textPanel);
		add(buttonPanel);
		
		try {
        	File file = new File("songlist.txt");
			if(!file.exists()) {
				file.createNewFile();
				return;
			}
			String in;
	        BufferedReader input = new BufferedReader(new FileReader(file));
	        while ((in = input.readLine()) != null) {
	        	model.addElement(in.substring(0,in.indexOf("/")));
				songDB.add(in);
		        songCount++;
	        }
	        songList.setSelectedIndex(0);
		} catch (IOException x) {
			x.printStackTrace();
		}
		
		name.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	name.setText("");
	        }

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
	    });
		
		artist.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	artist.setText("");
	        }

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
	    });
		
		album.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	album.setText("");
	        }

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
	    });
		
		year.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	year.setText("");
	        }

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	    });
		
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder();
				if (name.getText().trim().isEmpty() || artist.getText().trim().isEmpty()) {
					return;
				}
				sb.append(name.getText().trim() + "/");
				sb.append(artist.getText().trim() + "/");
				if (!album.getText().trim().isEmpty()) {
					sb.append(album.getText().trim() + "/");
				} else {
					sb.append("N|A/");
				}
				if (!year.getText().trim().isEmpty()) {
					sb.append(year.getText().trim());
				} else {
					sb.append("N|A");
				}
				
				String[] tokens = sb.toString().split(delims);
				String[] currentEntry;
				String[] temp;
				if (songDB.size() == 0 || songCount == 0) {
					name.setText(null);
					artist.setText(null);
					album.setText(null);
					year.setText(null);
					model.addElement((sb.toString()).substring(0,sb.indexOf("/")));
					songDB.add(sb.toString());
					songList.setSelectedIndex(0);
			        songCount++;
			        return;
				}
				int i = 0;
				for (i = 0; i < songDB.size(); i++) {
					currentEntry = songDB.get(i).split(delims);
					if (tokens[0].equalsIgnoreCase(currentEntry[0]) && tokens[1].equalsIgnoreCase(currentEntry[1])) {
						userMessage.setText("That song already exists in the list!");
						return;
					} else {
						if(tokens[0].compareToIgnoreCase(currentEntry[0]) > 0) {
							continue;
						} else if (tokens[0].equalsIgnoreCase(currentEntry[0]) && tokens[1].compareToIgnoreCase(currentEntry[1]) > 0) {
							continue;
						} else {
							name.setText(null);
							artist.setText(null);
							album.setText(null);
							year.setText(null);
							//model.add(i, sb.toString());
							//model.add(i, (sb.toString()).substring(0,sb.indexOf("/")));
							model.add(i, (sb.toString()).substring(0,sb.indexOf("/")));
							songDB.add(i, sb.toString());
							songList.setSelectedIndex(i);
							//System.out.println(songDB.get(songList.getSelectedIndex()));
					        songCount++;
					        return;
						}
					}
				}
				if (i == songDB.size()) {
					name.setText(null);
					artist.setText(null);
					album.setText(null);
					year.setText(null);
					//model.addElement(sb.toString());
					model.addElement((sb.toString()).substring(0,sb.indexOf("/")));
					songDB.add(sb.toString());
					songList.setSelectedIndex(songDB.size() - 1);
			        songCount++;
			        return;
				}
			}
		});
		
		edit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				confirm.setEnabled(true);
			}
		});
		
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				songDB.remove(songList.getSelectedIndex());
				model.remove(songList.getSelectedIndex());
				songList.setSelectedIndex(0);
		        songCount--;
			}
		});
		
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder();
				if (name.getText().trim().isEmpty() || artist.getText().trim().isEmpty()) {
					confirm.setEnabled(false);
					return;
				}
				sb.append(name.getText().trim() + "/");
				sb.append(artist.getText().trim() + "/");
				if (!album.getText().trim().isEmpty()) {
					sb.append(album.getText().trim() + "/");
				} else {
					sb.append("N|A/");
				}
				if (!year.getText().trim().isEmpty()) {
					sb.append(year.getText().trim());
				} else {
					sb.append("N|A");
				}
				
				String[] tokens = sb.toString().split(delims);
				String[] currentEntry;
				String[] temp;
				if (songDB.size() == 0 || songCount == 0) {
					name.setText(null);
					artist.setText(null);
					album.setText(null);
					year.setText(null);
					//model.addElement(sb.toString());
					model.addElement((sb.toString()).substring(0,sb.indexOf("/")));
					songDB.add(sb.toString());
			        songCount++;
			        songDB.remove(songList.getSelectedIndex());
					model.remove(songList.getSelectedIndex());
					songList.setSelectedIndex(0);
			        songCount--;
			        confirm.setEnabled(false);
			        return;
				}
				int i = 0;
				for (i = 0; i < songDB.size(); i++) {
					currentEntry = songDB.get(i).split(delims);
					if (tokens[0].equalsIgnoreCase(currentEntry[0]) && tokens[1].equalsIgnoreCase(currentEntry[1])) {
						userMessage.setText("That song already exists in the list!");
						confirm.setEnabled(false);
						return;
					} else {
						if(tokens[0].compareToIgnoreCase(currentEntry[0]) > 0) {
							continue;
						} else if (tokens[0].equalsIgnoreCase(currentEntry[0]) && tokens[1].compareToIgnoreCase(currentEntry[1]) > 0) {
							continue;
						} else {
							name.setText(null);
							artist.setText(null);
							album.setText(null);
							year.setText(null);
							//model.add(i, sb.toString());
							model.add(i, (sb.toString()).substring(0,sb.indexOf("/")));
							songDB.add(i, sb.toString());
					        songCount++;
					        songDB.remove(songList.getSelectedIndex());
							model.remove(songList.getSelectedIndex());
							songList.setSelectedIndex(0);
					        songCount--;
					        confirm.setEnabled(false);
					        return;
						}
					}
				}
				if (i == songDB.size()) {
					name.setText(null);
					artist.setText(null);
					album.setText(null);
					year.setText(null);
					//model.addElement(sb.toString());
					model.addElement((sb.toString()).substring(0,sb.indexOf("/")));
					songDB.add(sb.toString());
			        songCount++;
			        songDB.remove(songList.getSelectedIndex());
					model.remove(songList.getSelectedIndex());
					songList.setSelectedIndex(0);
			        songCount--;
			        confirm.setEnabled(false);
			        return;
				}
			}
		});
		
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				name.setText(null);
				artist.setText(null);
				album.setText(null);
				year.setText(null);
				confirm.setEnabled(false);
			}
		});
		
		songList.addListSelectionListener(new ListSelectionListener() {
	        //@Override
	        public void valueChanged(ListSelectionEvent arg0) {
	        	if (songDB.isEmpty()) {
            		userMessage.setText("Songlist is empty!");
            		return;
            	}
	        	int i = songList.getSelectedIndex();
	        	if (i != -1) {
	        		if(i < songDB.size()) {
	        			String[] tokens = songDB.get(i).split(delims);
	                	userMessage.setText("Artist: " + tokens[1] + " Album: " + tokens[2] + " Year: " + tokens[3]);
	        		}
	        	}
	        }
		});
	}
	
	public static void main(String[] args){
		MusicPlayer mp = new MusicPlayer("Our Library");
		//mp.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mp.setDefaultCloseOperation(HIDE_ON_CLOSE);
		mp.setSize(750,250);
		mp.setResizable(false);
		mp.setLocationRelativeTo(null);
		mp.setVisible(true);
		
		mp.addComponentListener(new ComponentAdapter() {
        	@Override
	        public void componentHidden(ComponentEvent e) {
		        try {
		        	File file = new File("songlist.txt");
					if(!file.exists()) {
						file.createNewFile();
					}
			        BufferedWriter output = new BufferedWriter(new FileWriter(file));
			        for (int i = 0; i < songDB.size(); i++) {
			        	output.write(songDB.get(i));
			        	output.newLine();
			        }
			        output.close();
				} catch (IOException x) {
					x.printStackTrace();
				}
		        ((JFrame)(e.getComponent())).dispose();
        	}
		});
	}
}
