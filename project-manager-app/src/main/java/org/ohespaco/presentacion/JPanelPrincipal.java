package org.ohespaco.presentacion;

import javax.swing.JPanel;
import java.awt.Color;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.ohespaco.persistencia.CurrentSession;
import org.ohespaco.dominio.Proyecto;
import org.ohespaco.dominio.Usuario;
import org.ohespaco.dominio.GestorProyectos;
import org.ohespaco.dominio.GestorUsuarios;

import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JLabel;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSplitPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class JPanelPrincipal extends JPanel {
private JPanel panelsur=null;
private JLabel lblUsuariobottombar=null;
private JLabel lblLogintimebottombar=null;
private Component horizontalStrut=null;
private JTable table;
private JList listaproyectos;
private JTextField txtTitulo;
private JLabel txtFechacreacion;
private JTextField txtDescripcin;
private 	JTextArea dtrpnEditordescripcion ;
private static final Color VERDEGUAY=new Color(46, 189, 89);
private static final Color GRISGUAY=new Color(46, 47, 51);
	/**
	 * Create the panel.
	 */
	public JPanelPrincipal() {
		
		
	
	
		setBackground((new Color(46, 47, 51)));
		setLayout(new BorderLayout(0, 0));
		
		
		//panelIZ.add(list);
		
		JPanel panelnor = new JPanel();
		panelnor.setBackground(Color.WHITE);
		add(panelnor, BorderLayout.NORTH);
		panelnor.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelnor.add(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(mnArchivo);
		
		JMenuItem mntmNuevoProyecto = new JMenuItem("Nuevo proyecto");
		mntmNuevoProyecto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//////////NUEVO PROYECTO//////////////////////////////////////////////////
				//.setModel(GestorUsuarios.getInstancia("").getDefaultList());
				GestorProyectos.getInstancia("").crearProyecto("Nuevo proyecto...", "Proyecto creado recientemente.");
			}
		});
		mnArchivo.add(mntmNuevoProyecto);
		
		JMenuItem mntmEliminarProyecto = new JMenuItem("Eliminar proyecto");
		mntmEliminarProyecto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Proyecto project = (Proyecto) listaproyectos.getSelectedValue();

				Component frame = null;
				if (project != null) {
						String mensage = "¿Seguro que quieres eliminar el proyecto " + project.getNombre() + "?";
						Object[] options = { "Borrar", "No borrar" };

						int n = JOptionPane.showOptionDialog(frame, mensage, "Confirmacion", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
								options, // the titles of buttons
								options[1]); // default button title

						if (n == JOptionPane.YES_OPTION) {
							GestorProyectos.getInstancia("").borrarProyecto(project);
							listaproyectos.setModel(GestorProyectos.getInstancia("").getDefaultList());
							//limpiar();
						}

					

				}else {
					JOptionPane.showMessageDialog(frame, "Primero tienes que seleccionar un proyecto.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JMenuItem mntmGuardarProyecto = new JMenuItem("Guardar proyecto");
		mntmGuardarProyecto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Proyecto project=(Proyecto)listaproyectos.getSelectedValue();
				
				if(project!=null) {
					int index=listaproyectos.getSelectedIndex();
					project.setNombre(txtTitulo.getText());
					project.setDescripcion(dtrpnEditordescripcion.getText());
					GestorProyectos.getInstancia("").editarProyecto(project);
					listaproyectos.setModel(GestorProyectos.getInstancia("").getDefaultList());
					listaproyectos.setSelectedIndex(index);
					
				}else {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
					JOptionPane.showMessageDialog(frame, "No hay proyecto seleccionado.", "Seleccionar proyecto.",
							JOptionPane.ERROR_MESSAGE);
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
			
		});
		mnArchivo.add(mntmGuardarProyecto);
		mnArchivo.add(mntmEliminarProyecto);
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		JMenu mnTareas = new JMenu("Tareas");
		menuBar.add(mnTareas);
		
		JMenu mnPersonas = new JMenu("Personas");
		menuBar.add(mnPersonas);
		
		JMenuItem mntmListarPersonas = new JMenuItem("Gestionar personas");
		mntmListarPersonas.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mousePressed(MouseEvent e) {
			//////////////////////Lanza un gestor de personas//////////
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
				
				PersonasFrame pers = new PersonasFrame(topFrame, true);
				JDialog jd = new JDialog(pers, "Dialogo modal", Dialog.ModalityType.DOCUMENT_MODAL);
				
				pers.setVisible(true);
			}
		});
		mnPersonas.add(mntmListarPersonas);
		
		JMenu mnVer = new JMenu("Ver");
		menuBar.add(mnVer);
		
		JMenu mnSesin = new JMenu("Sesión");
		menuBar.add(mnSesin);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
	    panelsur = new JPanel();
	    panelsur.setBackground(new Color(46, 189, 89));
	    panelsur.setForeground(Color.BLACK);
		add(panelsur, BorderLayout.SOUTH);
		panelsur.addComponentListener(new ResizeListener());
	    panelsur.setLayout(new GridLayout(0, 2, 0, 0));
	    lblUsuariobottombar = new JLabel("Usuario_bottombar");
	    lblUsuariobottombar.setHorizontalAlignment(SwingConstants.CENTER);
	    lblUsuariobottombar.setForeground(Color.BLACK);
	    lblUsuariobottombar.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelsur.add(lblUsuariobottombar);
		
		
		
		lblLogintimebottombar = new JLabel("Logintime_bottombar");
		lblLogintimebottombar.setForeground(Color.BLACK);
		lblLogintimebottombar.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLogintimebottombar.setHorizontalAlignment(SwingConstants.CENTER);
		panelsur.add(lblLogintimebottombar);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(150);
		add(splitPane, BorderLayout.CENTER);
		
		JPanel split_izq = new JPanel();
		splitPane.setLeftComponent(split_izq);
		split_izq.setLayout(new BorderLayout(0, 0));
	    listaproyectos = new JList(GestorProyectos.getInstancia("").getDefaultList());
	    listaproyectos.addListSelectionListener(new ListSelectionListener() {
	    	public void valueChanged(ListSelectionEvent e) {
	    		
	    		Proyecto project = (Proyecto) listaproyectos.getSelectedValue();
				if (project != null) {
					txtTitulo.setText(project.getNombre());
					txtFechacreacion.setText(""+project.getFecha_creacion());
					dtrpnEditordescripcion.setText(project.getDescripcion());
					
				}
	    		
	    		
	    		
	    		
	    		
	    		
	    		
	    		
	    		
	    		
	    	}
	    });
		listaproyectos.setForeground(VERDEGUAY);
		listaproyectos.setFont(new Font("Tahoma", Font.BOLD, 11));
		listaproyectos.setBackground(GRISGUAY);
		listaproyectos.setFixedCellHeight(40);
		//list.setFixedCellWidth(150);
		listaproyectos.setCellRenderer(getRenderer());
		
		
		
		
		
		listaproyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane_1 = new JScrollPane(listaproyectos);
		
		//scrollPane_1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		scrollPane_1.setViewportBorder(new MatteBorder(1, 1, 1, 1,new Color(46, 189, 89)));
		scrollPane_1.setBackground(new Color(46, 189, 89));
		scrollPane_1.setViewportView(listaproyectos);
		
		
		
		
		split_izq.add(scrollPane_1);
		
		JSplitPane splitPane_1 = new JSplitPane();
		//splitPane_1.setDividerLocation(200);
		//splitPane_1.setResizeWeight(.5d);
		splitPane.setRightComponent(splitPane_1);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
	//	
		//splitPane_2.setResizeWeight(.5d);


		//splitPane_2.setDividerLocation(150);
		splitPane_1.setRightComponent(splitPane_2);
		
		JPanel panelEquipo = new JPanel();
		splitPane_2.setLeftComponent(panelEquipo);
		
		JPanel panelTareas = new JPanel();
		splitPane_2.setRightComponent(panelTareas);
		
		JPanel panelGeneral = new JPanel();
		splitPane_1.setLeftComponent(panelGeneral);
		panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));
		
		JPanel panelNor = new JPanel();
		panelNor.setBackground(new Color(46, 47, 51));
		panelNor.setMaximumSize( new Dimension(10000, 100));
		panelGeneral.add(panelNor);
		panelNor.setLayout(new BoxLayout(panelNor, BoxLayout.X_AXIS));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(3);
		panelNor.add(horizontalStrut_2);
		
		JLabel lblTitulo = new JLabel("Titulo: ");
		lblTitulo.setForeground(Color.LIGHT_GRAY);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelNor.add(lblTitulo);
		
		txtTitulo = new JTextField();
		txtTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		txtTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtTitulo.setForeground(VERDEGUAY);
		txtTitulo.setBackground(GRISGUAY);
		panelNor.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panelNor.add(horizontalStrut_1);
		
		JLabel lblCreacion = new JLabel("Creacion: ");
		lblCreacion.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCreacion.setForeground(Color.LIGHT_GRAY);
		panelNor.add(lblCreacion);
		
		txtFechacreacion = new JLabel();
		txtFechacreacion.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechacreacion.setForeground(VERDEGUAY);
		txtFechacreacion.setText("                  ");
		
		//txtFechacreacion.setMinimumSize(new Dimension(150,25));
	//	txtFechacreacion.setMaximumSize(new Dimension(150,25));
		panelNor.add(txtFechacreacion);
		
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(3);
		//panelNor.add(horizontalStrut_3);
		
		txtDescripcin = new JTextField();
		txtDescripcin.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtDescripcin.setForeground(Color.LIGHT_GRAY);
		txtDescripcin.setBackground(new Color(46, 47, 51));
		txtDescripcin.setEditable(false);
		txtDescripcin.setHorizontalAlignment(SwingConstants.CENTER);
		txtDescripcin.setText("Descripción");
		txtDescripcin.setMaximumSize(new Dimension(2147483647, 25));
		panelGeneral.add(txtDescripcin);
		txtDescripcin.setColumns(10);
		/* dtrpnEditordescripcion = new JTextArea();
		 dtrpnEditordescripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		 dtrpnEditordescripcion.setLineWrap(true); //Makes the text wrap to the next line
		 dtrpnEditordescripcion.setWrapStyleWord(true); //Makes the text wrap full words, not just letters
		// dtrpnEditordescripcion.setText(gettysburgAddress);
		 //dtrpnEditordescripcion.setWrapStyleWord(true);
		 dtrpnEditordescripcion.setForeground(VERDEGUAY);
		 Color bgColor =new Color(46, 47, 51);
		

		  UIDefaults defaults = new UIDefaults();
		  defaults.put("EditorPane[Enabled].backgroundPainter", bgColor);
		  dtrpnEditordescripcion.putClientProperty("Nimbus.Overrides", defaults);
		  dtrpnEditordescripcion.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		  dtrpnEditordescripcion.setBackground(bgColor);*/
		 
		 
		 
		//panelGeneral.add(dtrpnEditordescripcion);
		
		JScrollPane scrollPane = new JScrollPane();
		panelGeneral.add(scrollPane);
		
	    dtrpnEditordescripcion = new JTextArea();
	    dtrpnEditordescripcion = new JTextArea();
		 dtrpnEditordescripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		 dtrpnEditordescripcion.setLineWrap(true); //Makes the text wrap to the next line
		 dtrpnEditordescripcion.setWrapStyleWord(true); //Makes the text wrap full words, not just letters
		// dtrpnEditordescripcion.setText(gettysburgAddress);
		 //dtrpnEditordescripcion.setWrapStyleWord(true);
		 dtrpnEditordescripcion.setForeground(VERDEGUAY);
		 Color bgColor =new Color(46, 47, 51);
		

		  UIDefaults defaults = new UIDefaults();
		  defaults.put("EditorPane[Enabled].backgroundPainter", bgColor);
		  dtrpnEditordescripcion.putClientProperty("Nimbus.Overrides", defaults);
		  dtrpnEditordescripcion.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		  dtrpnEditordescripcion.setBackground(bgColor);
		scrollPane.setViewportView(dtrpnEditordescripcion);
		Dimension maximumSize= new Dimension(9999999,25);
		Dimension maximumSizeDate= new Dimension(100,100);
		
		
		
		
		
		
		
		//tabbedPane.addTab("New tab", null, table, null);

	}
	  private ListCellRenderer<? super String> getRenderer() {
	        return new DefaultListCellRenderer(){
	            @Override
	            public Component getListCellRendererComponent(JList<?> list,
	                    Object value, int index, boolean isSelected,
	                    boolean cellHasFocus) {
	                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
	                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0,VERDEGUAY));
	                return listCellRendererComponent;
	            }
	        };
	    }
	class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
         if(CurrentSession.getInstancia().getUser()!=null) {
        	 lblUsuariobottombar.setText("Bienvenido, "+CurrentSession.getInstancia().getUser().getNombre());
        	 lblLogintimebottombar.setText("Ultimo login: "+CurrentSession.getInstancia().getLogin_time());
         }
        	
        	/*  System.out.println("Cambio de tamaño");
           if(panelsur!=null) {
        	 
        	  if( panelsur.getWidth()>700&& panelsur.getHeight()>700) {
        		  lblUsuariobottombar.setFont(new Font("Tahoma", Font.BOLD, 14));
        		  lblLogintimebottombar.setFont(new Font("Tahoma", Font.BOLD, 14));
        	  }else {
        		  lblLogintimebottombar.setFont(new Font("Tahoma", Font.BOLD, 11));
        		  lblUsuariobottombar.setFont(new Font("Tahoma", Font.BOLD, 11));
        	  }
        	  
        	
        	
        	  
           }*/
        }
}
}
