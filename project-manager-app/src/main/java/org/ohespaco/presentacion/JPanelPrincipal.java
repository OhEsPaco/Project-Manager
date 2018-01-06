package org.ohespaco.presentacion;

import javax.swing.JPanel;
import java.awt.Color;



import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.ohespaco.persistencia.CurrentSession;
import org.ohespaco.dominio.Proyecto;
import org.ohespaco.dominio.Tarea;
import org.ohespaco.dominio.Usuario;
import org.jdesktop.swingx.JXDatePicker;
import org.ohespaco.dominio.GestorProyectos;
import org.ohespaco.dominio.GestorTareas;
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
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Date;
import java.util.Calendar;

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
import javax.swing.JTree;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerDateModel;

public class JPanelPrincipal extends JPanel {
	private JPanel panelsur = null;
	private JLabel lblUsuariobottombar = null;
	private JLabel lblLogintimebottombar = null;
	private Component horizontalStrut = null;
	private JTable table;
	private JList listaproyectos;
	private JTextField txtTitulo;
	private JTextField txtFechacreacion;
	private JTextArea dtrpnEditordescripcion;
	private JTree treeTareas;
	private JTextField txtNombretarea;
	private JTextArea descripcionTarea;
	private JTextField txtTags;
	private JTextField txtEquipo;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField txtTareanombre;
	private JXDatePicker pickerInicio;
	private JXDatePicker pickerFinal;
	private JComboBox comboBoxEstadoTarea;
	private JSpinner spinnerPrioridad;
	private DefaultMutableTreeNode nodo_anterior;

	/**
	 * Create the panel.
	 */
	public JPanelPrincipal() {

		// setBackground((new Color(46, 47, 51)));
		setLayout(new BorderLayout(0, 0));

		// panelIZ.add(list);

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
				////////// NUEVO PROYECTO//////////////////////////////////////////////////
				// .setModel(GestorUsuarios.getInstancia("").getDefaultList());
				GestorProyectos.getInstancia("").crearProyecto("Nuevo proyecto...", "Proyecto creado recientemente.");
				//limpiarTodo();
				listaproyectos.setSelectedIndex(listaproyectos.getModel().getSize()-1);
				
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
						limpiarTodo();
					}

				} else {
					JOptionPane.showMessageDialog(frame, "Primero tienes que seleccionar un proyecto.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		mnArchivo.add(mntmEliminarProyecto);

		JMenuItem mntmGuardarProyecto = new JMenuItem("Guardar todo");
		mntmGuardarProyecto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Proyecto project = (Proyecto) listaproyectos.getSelectedValue();
				int index = 0;
				// SI NO HAY PROYECTO SELECCIONADO GUARDAR EL RESTO DE COSAS
				
				if (project != null) {

					// Guardar proyecto visible
					index = listaproyectos.getSelectedIndex();
					project.setNombre(txtTitulo.getText());
					project.setDescripcion(dtrpnEditordescripcion.getText());
					GestorProyectos.getInstancia("").editarProyecto(project);

					// Guardar tarea visible
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeTareas.getLastSelectedPathComponent();
					Tarea task;
					try {
						task = (Tarea) node.getUserObject();
						if (task != null) {
							// editarTarea(String uuid, String uuid_tarea_padre, String nombre, Date
							// fecha_creacion, Date fecha_fin,
							// String etiquetas, String comentarios, int prioridad, int estado)
							GestorTareas.getInstancia("").editarTarea(task.getUuid(), txtTareanombre.getText(),
									pickerInicio.getDate(), pickerFinal.getDate(), txtTags.getText(),
									descripcionTarea.getText(), (int) spinnerPrioridad.getValue(),
									comboBoxEstadoTarea.getSelectedIndex());

						}
					} catch (java.lang.ClassCastException ey) {

					}
				} else {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
					JOptionPane.showMessageDialog(frame, "No hay proyecto seleccionado.", "Seleccionar proyecto.",
							JOptionPane.ERROR_MESSAGE);
				}
				GestorProyectos.getInstancia("").guardarProyectos();
				GestorTareas.getInstancia("").guardarTareas();
				// listaproyectos.setModel(GestorProyectos.getInstancia("").getDefaultList());
				// listaproyectos.setSelectedIndex(index);
				/*
				 * if(project!=null) {
				 * treeTareas.setModel(GestorTareas.getInstancia("").getTree("Tareas",
				 * project.getUuid())); }
				 */

			}

		});
		mnArchivo.add(mntmGuardarProyecto);

		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);

		JMenu mnTareas = new JMenu("Tareas");
		menuBar.add(mnTareas);
		
		JMenuItem mntmBorrarTarea = new JMenuItem("Borrar tarea");
		mntmBorrarTarea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeTareas.getLastSelectedPathComponent();
				Tarea task;

				//borrar tarea
				try {
					task = (Tarea) nodo_anterior.getUserObject();
					
					
					String mensage = "¿Seguro que quieres eliminar la tarea " + task.getNombre() + " y todas sus tareas hijas?";
					Object[] options = { "Borrar", "No borrar" };

					int n = JOptionPane.showOptionDialog(topFrame, mensage, "Confirmacion", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
							options, // the titles of buttons
							options[1]); // default button title

					if (n == JOptionPane.YES_OPTION) {
						GestorTareas.getInstancia("").borrarTarea(task);
						treeTareas.setModel(GestorTareas.getInstancia("").actualizarTree("Tareas", task.getUuid_proyecto()));
						limpiarTareas();
						
					}
					
					
				} catch (java.lang.ClassCastException | java.lang.NullPointerException ee) {
				
					JOptionPane.showMessageDialog(topFrame, "No hay ninguna tarea seleccionada.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
				
				
				
			}
		});
		
		JMenuItem mntmAadirTarea = new JMenuItem("Añadir tarea");
		mntmAadirTarea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				Proyecto project = (Proyecto) listaproyectos.getSelectedValue();
				if(project!=null) {
				//	crearTarea(String uuid_tarea_padre, String uuid_proyecto, String nombre, Date fecha_creacion,
					//Date fecha_fin, String etiquetas, String comentarios, int prioridad, int estado) 
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeTareas.getLastSelectedPathComponent();
					Tarea task;
					try {
						task = (Tarea) node.getUserObject();
						String uuid_padre=task.getUuid();
						
						//System.out.println("----"+task.getNombre());
						System.out.println(uuid_padre);
						GestorTareas.getInstancia("").crearTarea(uuid_padre,project.getUuid(), "Nueva tarea", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), "Nueva tarea", "Tarea creada recientemente...", 0, 0);
						//System.out.println("----"+task.getNombre());
						//limpiarTareas();
						System.out.println("----"+task.getNombre()+"--"+task.getUuid());
						//GestorTareas.getInstancia("").imprimirHash();
						treeTareas.setModel(GestorTareas.getInstancia("").actualizarTree("Tareas",project.getUuid()));
						GestorTareas.getInstancia("").imprimirHash();
						
						
						//System.out.println("----"+task.getNombre());
					} catch (java.lang.ClassCastException | java.lang.NullPointerException ee) {
					
						
						GestorTareas.getInstancia("").crearTarea(project.getUuid(), "Nueva tarea", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), "", "Tarea creada recientemente...", 0, 0);
						limpiarTareas();
						treeTareas.setModel(GestorTareas.getInstancia("").actualizarTree("Tareas", project.getUuid()));
					}
					
					
			}else {
					
				}
				
				
				
				
				
			}
		});
		mnTareas.add(mntmAadirTarea);
		mnTareas.add(mntmBorrarTarea);

		JMenu mnPersonas = new JMenu("Personas");
		menuBar.add(mnPersonas);

		JMenuItem mntmListarPersonas = new JMenuItem("Gestionar personas");
		mntmListarPersonas.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				////////////////////// Lanza un gestor de personas//////////
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
		panelsur.setBackground(new Color(112, 154, 208));
		panelsur.setForeground(Color.BLACK);
		add(panelsur, BorderLayout.SOUTH);
		panelsur.addComponentListener(new ResizeListener());
		panelsur.setLayout(new GridLayout(0, 2, 0, 0));
		lblUsuariobottombar = new JLabel("Usuario_bottombar");
		lblUsuariobottombar.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuariobottombar.setForeground(Color.WHITE);
		lblUsuariobottombar.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelsur.add(lblUsuariobottombar);

		lblLogintimebottombar = new JLabel("Logintime_bottombar");
		lblLogintimebottombar.setForeground(Color.WHITE);
		lblLogintimebottombar.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLogintimebottombar.setHorizontalAlignment(SwingConstants.CENTER);
		panelsur.add(lblLogintimebottombar);

		JSplitPane mainSplit = new JSplitPane();
		mainSplit.setDividerLocation(150);
		add(mainSplit, BorderLayout.CENTER);

		JPanel split_izq = new JPanel();
		mainSplit.setLeftComponent(split_izq);
		split_izq.setLayout(new BorderLayout(0, 0));
		listaproyectos = new JList(GestorProyectos.getInstancia("").getDefaultList());
		listaproyectos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				Proyecto project = (Proyecto) listaproyectos.getSelectedValue();
				if (project != null) {
					txtTitulo.setText(project.getNombre());
					txtFechacreacion.setText("" + project.getFecha_creacion());
					dtrpnEditordescripcion.setText(project.getDescripcion());
					treeTareas.setModel(GestorTareas.getInstancia("").actualizarTree("Tareas", project.getUuid()));
					//Reset tarea
					limpiarTareas();
				}

			}
		});
		listaproyectos.setForeground(Color.BLACK);
		listaproyectos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		listaproyectos.setBackground(Color.WHITE);
		listaproyectos.setFixedCellHeight(40);
		// list.setFixedCellWidth(150);
		listaproyectos.setCellRenderer(getRenderer());

		listaproyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane_1 = new JScrollPane(listaproyectos);

		scrollPane_1.setViewportView(listaproyectos);

		split_izq.add(scrollPane_1);

		JSplitPane split_der = new JSplitPane();

		split_der.setOneTouchExpandable(true);
		split_der.setResizeWeight(0.6);
		// splitPane_1.setDividerLocation(200);
		// splitPane_1.setResizeWeight(.5d);
		mainSplit.setRightComponent(split_der);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOneTouchExpandable(true);
		splitPane_2.setResizeWeight(0.001);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		//
		// splitPane_2.setResizeWeight(.5d);

		// splitPane_2.setDividerLocation(150);
		split_der.setRightComponent(splitPane_2);

		JPanel panelEquipo = new JPanel();
		panelEquipo.setMaximumSize(new Dimension(600, 600));
		// panelEquipo.setMinimumSize(new Dimension(500,200));
		splitPane_2.setLeftComponent(panelEquipo);
		panelEquipo.setLayout(new BorderLayout(0, 0));

		txtEquipo = new JTextField();
		txtEquipo.setHorizontalAlignment(SwingConstants.CENTER);
		txtEquipo.setEditable(false);
		txtEquipo.setText("Equipo");
		panelEquipo.add(txtEquipo, BorderLayout.NORTH);
		txtEquipo.setColumns(10);

		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setResizeWeight(0.06);
		splitPane_4.setDividerLocation(150);
		panelEquipo.add(splitPane_4, BorderLayout.CENTER);

		JScrollPane scrollPane_5 = new JScrollPane();
		splitPane_4.setLeftComponent(scrollPane_5);

		JList list = new JList();
		scrollPane_5.setViewportView(list);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(112, 154, 208)));
		splitPane_4.setRightComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_separador = new JPanel();
		panel_3.add(panel_separador, BorderLayout.SOUTH);
		panel_separador.setLayout(new BoxLayout(panel_separador, BoxLayout.Y_AXIS));

		Component rigidArea = Box.createRigidArea(new Dimension(3, 3));
		panel_separador.add(rigidArea);

		JButton btnExpulsar = new JButton("Expulsar");
		btnExpulsar.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_separador.add(btnExpulsar);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(3, 3));
		panel_separador.add(rigidArea_1);

		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		panel_7.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new GridLayout(3, 3, 5, 3));

		JLabel lblNewLabel_3 = new JLabel("Nombre");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(lblNewLabel_3);

		textField = new JTextField();
		textField.setEditable(false);
		panel_6.add(textField);
		textField.setColumns(10);

		Component horizontalStrut_8 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_8);

		JLabel lblNewLabel_5 = new JLabel("Apellidos");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(lblNewLabel_5);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		panel_6.add(textField_2);
		textField_2.setColumns(10);

		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_6);

		JLabel lblNewLabel_4 = new JLabel("Rol");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(lblNewLabel_4);

		textField_1 = new JTextField();
		panel_6.add(textField_1);
		textField_1.setColumns(10);

		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_7);

		JPanel panel_8 = new JPanel();

		panel_7.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));

		JLabel lblFotoequipo = new JLabel("");
		lblFotoequipo.setHorizontalAlignment(SwingConstants.CENTER);
		lblFotoequipo.setIcon(
				new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/logo.png"))
						.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
		panel_8.add(lblFotoequipo, BorderLayout.CENTER);
		JPanel panelTareas = new JPanel();
		splitPane_2.setRightComponent(panelTareas);
		panelTareas.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panelTareas.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		txtNombretarea = new JTextField();
		txtNombretarea.setEditable(false);
		txtNombretarea.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombretarea.setText("Tareas");
		panel.add(txtNombretarea, BorderLayout.NORTH);
		txtNombretarea.setColumns(10);
		txtNombretarea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

		JPanel panel_1 = new JPanel();

		panel_1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		JLabel lblNewLabel = new JLabel("Inicio");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblNewLabel);

		pickerInicio = new JXDatePicker();
		pickerInicio.setDate(Calendar.getInstance().getTime());
		panel_1.add(pickerInicio);

		comboBoxEstadoTarea = new JComboBox();
		((JLabel) comboBoxEstadoTarea.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		comboBoxEstadoTarea.setModel(new DefaultComboBoxModel(new String[] { "Activa", "Completa", "Tardia" }));
		panel_1.add(comboBoxEstadoTarea);

		panel.add(panel_1, BorderLayout.SOUTH);

		panel_1.setLayout(new GridLayout(2, 5));
		panel_1.setMinimumSize(new Dimension(1, 50));
		JButton btnNewButton_2 = new JButton("Personas");
		panel_1.add(btnNewButton_2);

		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_4);

		JLabel lblNewLabel_1 = new JLabel("Final");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblNewLabel_1);
		pickerFinal = new JXDatePicker();
		pickerFinal.setDate(Calendar.getInstance().getTime());
		panel_1.add(pickerFinal);

		JButton btnNewButton = new JButton("Calendario");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
			}
		});
		panel_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Eliminar");
		panel_1.add(btnNewButton_1);

		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_5);

		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setDividerLocation(150);
		splitPane_3.setOneTouchExpandable(true);
		panel.add(splitPane_3, BorderLayout.CENTER);

		JScrollPane scrollPane_2 = new JScrollPane();
		splitPane_3.setLeftComponent(scrollPane_2);

		treeTareas = new JTree();
		treeTareas.setModel(null);
		treeTareas.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeTareas.getLastSelectedPathComponent();
				Tarea task;

				// guardar tarea anterior
				try {
					task = (Tarea) nodo_anterior.getUserObject();
					if (task != null) {
						GestorTareas.getInstancia("").editarTarea(task.getUuid(), txtTareanombre.getText(),
								pickerInicio.getDate(), pickerFinal.getDate(), txtTags.getText(),
								descripcionTarea.getText(), (int) spinnerPrioridad.getValue(),
								comboBoxEstadoTarea.getSelectedIndex());
					}
				} catch (java.lang.ClassCastException | java.lang.NullPointerException ee) {

				}

				try {
					task = (Tarea) node.getUserObject();
					if (task != null) {
						txtTareanombre.setText(task.getNombre());
						spinnerPrioridad.setValue(task.getPrioridad());
						pickerInicio.setDate(task.getFecha_creacion());
						pickerFinal.setDate(task.getFecha_fin());
						descripcionTarea.setText(task.getComentarios());
						comboBoxEstadoTarea.setSelectedIndex(task.getEstado());
						txtTags.setText(task.getEtiquetas());
					}
				} catch (java.lang.ClassCastException | java.lang.NullPointerException ee) {

				}

				nodo_anterior = node;
			}
		});
		/*
		 * tree.setModel(new DefaultTreeModel( new DefaultMutableTreeNode("JTree") { { }
		 * } ));
		 */
		scrollPane_2.setViewportView(treeTareas);

		JScrollPane scrollPane_4 = new JScrollPane();
		splitPane_3.setRightComponent(scrollPane_4);

		JPanel panel_4 = new JPanel();
		scrollPane_4.setViewportView(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		descripcionTarea = new JTextArea();
		panel_4.add(descripcionTarea, BorderLayout.CENTER);

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));

		JLabel lblTags = new JLabel("  Tags");
		panel_5.add(lblTags);

		txtTags = new JTextField();
		panel_5.add(txtTags);
		txtTags.setColumns(10);

		JLabel lblPrioridad = new JLabel("Prioridad: ");
		panel_5.add(lblPrioridad);

		spinnerPrioridad = new JSpinner();
		spinnerPrioridad.setModel(new SpinnerNumberModel(0, 0, 99, 1));
		panel_5.add(spinnerPrioridad);

		JPanel panel_9 = new JPanel();
		panel_4.add(panel_9, BorderLayout.NORTH);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));

		JLabel lblNombre = new JLabel("Nombre: ");
		panel_9.add(lblNombre);

		txtTareanombre = new JTextField();
		panel_9.add(txtTareanombre);
		txtTareanombre.setColumns(10);

		JScrollPane scrollPane_3 = new JScrollPane();

		panelTareas.add(scrollPane_3, BorderLayout.WEST);

		JPanel panelGeneral = new JPanel();
		split_der.setLeftComponent(panelGeneral);
		panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));

		JPanel panel_2 = new JPanel();
		panelGeneral.add(panel_2, BorderLayout.NORTH);
		panel_2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));

		JTextField lblNewLabel_2 = new JTextField("Descripción del proyecto");
		lblNewLabel_2.setEditable(false);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_2);

		JPanel panelNor = new JPanel();
		panel_2.add(panelNor);
		// panelNor.setBackground(Color.WHITE);
		panelNor.setMaximumSize(new Dimension(10000, 100));
		panelNor.setLayout(new BoxLayout(panelNor, BoxLayout.X_AXIS));

		Component horizontalStrut_2 = Box.createHorizontalStrut(3);
		panelNor.add(horizontalStrut_2);

		JLabel lblTitulo = new JLabel("Titulo: ");
		lblTitulo.setForeground(Color.BLACK);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelNor.add(lblTitulo);

		txtTitulo = new JTextField();

		txtTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTitulo.setForeground(Color.BLACK);
		txtTitulo.setBackground(Color.WHITE);
		panelNor.add(txtTitulo);
		txtTitulo.setColumns(10);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panelNor.add(horizontalStrut_1);

		JLabel lblCreacion = new JLabel("Creacion: ");
		lblCreacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCreacion.setForeground(Color.BLACK);
		panelNor.add(lblCreacion);

		txtFechacreacion = new JTextField();
		txtFechacreacion.setEditable(false);
		txtFechacreacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtFechacreacion.setForeground(Color.BLACK);
		txtFechacreacion.setText("                  ");

		// txtFechacreacion.setMinimumSize(new Dimension(150,25));
		txtFechacreacion.setMaximumSize(new Dimension(200, 25));
		panelNor.add(txtFechacreacion);

		Component horizontalStrut_3 = Box.createHorizontalStrut(3);
		// txtDescripcin.setColumns(10);
		/*
		 * dtrpnEditordescripcion = new JTextArea(); dtrpnEditordescripcion.setFont(new
		 * Font("Tahoma", Font.BOLD, 11)); dtrpnEditordescripcion.setLineWrap(true);
		 * //Makes the text wrap to the next line
		 * dtrpnEditordescripcion.setWrapStyleWord(true); //Makes the text wrap full
		 * words, not just letters // dtrpnEditordescripcion.setText(gettysburgAddress);
		 * //dtrpnEditordescripcion.setWrapStyleWord(true);
		 * dtrpnEditordescripcion.setForeground(VERDEGUAY); Color bgColor =new Color(46,
		 * 47, 51);
		 * 
		 * 
		 * UIDefaults defaults = new UIDefaults();
		 * defaults.put("EditorPane[Enabled].backgroundPainter", bgColor);
		 * dtrpnEditordescripcion.putClientProperty("Nimbus.Overrides", defaults);
		 * dtrpnEditordescripcion.putClientProperty("Nimbus.Overrides.InheritDefaults",
		 * true); dtrpnEditordescripcion.setBackground(bgColor);
		 */

		// panelGeneral.add(dtrpnEditordescripcion);

		JScrollPane scrollPane = new JScrollPane();
		panelGeneral.add(scrollPane, BorderLayout.CENTER);

		dtrpnEditordescripcion = new JTextArea();
		dtrpnEditordescripcion = new JTextArea();
		dtrpnEditordescripcion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		dtrpnEditordescripcion.setLineWrap(true); // Makes the text wrap to the next line
		dtrpnEditordescripcion.setWrapStyleWord(true); // Makes the text wrap full words, not just letters
		// dtrpnEditordescripcion.setText(gettysburgAddress);
		// dtrpnEditordescripcion.setWrapStyleWord(true);
		dtrpnEditordescripcion.setForeground(Color.BLACK);
		Color bgColor = new Color(46, 47, 51);

		UIDefaults defaults = new UIDefaults();
		defaults.put("EditorPane[Enabled].backgroundPainter", bgColor);
		dtrpnEditordescripcion.putClientProperty("Nimbus.Overrides", defaults);
		dtrpnEditordescripcion.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		dtrpnEditordescripcion.setBackground(Color.WHITE);
		scrollPane.setViewportView(dtrpnEditordescripcion);
		Dimension maximumSize = new Dimension(9999999, 25);
		Dimension maximumSizeDate = new Dimension(100, 100);

		// tabbedPane.addTab("New tab", null, table, null);

	}

	private ListCellRenderer<? super String> getRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				listCellRendererComponent
						.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(112, 154, 208)));
				return listCellRendererComponent;
			}
		};
	}
	public void limpiarTodo() {
		limpiarDetalles();
		limpiarTareas();
	}
	public void limpiarDetalles() {
		txtTitulo.setText("");
		txtFechacreacion.setText("");
		dtrpnEditordescripcion.setText("");
	}
	public void limpiarTareas() {
		txtTareanombre.setText("");
		spinnerPrioridad.setValue(0);
		pickerInicio.setDate(Calendar.getInstance().getTime());
		pickerFinal.setDate(Calendar.getInstance().getTime());
		descripcionTarea.setText("");
		comboBoxEstadoTarea.setSelectedIndex(0);
		txtTags.setText("");
	}

	class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			if (CurrentSession.getInstancia().getUser() != null) {
				lblUsuariobottombar.setText("Bienvenido, " + CurrentSession.getInstancia().getUser().getNombre());
				lblLogintimebottombar.setText("Ultimo login: " + CurrentSession.getInstancia().getLogin_time());
			}

			/*
			 * System.out.println("Cambio de tamaño"); if(panelsur!=null) {
			 * 
			 * if( panelsur.getWidth()>700&& panelsur.getHeight()>700) {
			 * lblUsuariobottombar.setFont(new Font("Tahoma", Font.BOLD, 14));
			 * lblLogintimebottombar.setFont(new Font("Tahoma", Font.BOLD, 14)); }else {
			 * lblLogintimebottombar.setFont(new Font("Tahoma", Font.BOLD, 11));
			 * lblUsuariobottombar.setFont(new Font("Tahoma", Font.BOLD, 11)); }
			 * 
			 * 
			 * 
			 * 
			 * }
			 */
		}
	}
}
