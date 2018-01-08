/*
Copyright (c) 2017
Francisco Manuel Garcia Sanchez-Belmonte
Adrian Bustos Marin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package org.ohespaco.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jdesktop.swingx.JXDatePicker;
import org.ohespaco.dominio.GestorEquipo;
import org.ohespaco.dominio.GestorMensajes;
import org.ohespaco.dominio.GestorMiembrosTareas;
import org.ohespaco.dominio.GestorProyectos;
import org.ohespaco.dominio.GestorTareas;
import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.dominio.MiembroEquipo;
import org.ohespaco.dominio.Proyecto;
import org.ohespaco.dominio.Tarea;
import org.ohespaco.dominio.Usuario;
import org.ohespaco.persistencia.CurrentSession;

public class JPanelPrincipal extends JPanel {
	private JPanel panelsur = null;
	private JLabel lblUsuariobottombar = null;
	private JLabel lblLogintimebottombar = null;
	private JList listaproyectos;
	private JTextField txtTitulo;
	private JTextField txtFechacreacion;
	private JTextArea dtrpnEditordescripcion;
	private JTree treeTareas;
	private JTextField txtNombretarea;
	private JTextPane descripcionTarea;
	private JTextField txtTags;
	private JTextField txtEquipo;
	private JTextField nombreMiembro;
	private JTextField rolMiembro;
	private JTextField apellidosMiembro;
	private JTextField txtTareanombre;
	private JXDatePicker pickerInicio;
	private JXDatePicker pickerFinal;
	private JComboBox comboBoxEstadoTarea;
	private JSpinner spinnerPrioridad;
	private DefaultMutableTreeNode nodo_anterior;
	private JLabel lblFotoequipo;
	private JList listEquipo;

	public JPanelPrincipal() {

		setLayout(new BorderLayout(0, 0));

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

				GestorProyectos.getInstancia("").crearProyecto("Nuevo proyecto...", "Proyecto creado recientemente.");

				listaproyectos.setSelectedIndex(listaproyectos.getModel().getSize() - 1);

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
							JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

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

		mnArchivo.addSeparator();
		JMenuItem mntmGuardarProyecto = new JMenuItem("Guardar todo");
		mntmGuardarProyecto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Proyecto project = (Proyecto) listaproyectos.getSelectedValue();
				if (project != null) {

					listaproyectos.getSelectedIndex();
					project.setNombre(txtTitulo.getText());
					project.setDescripcion(dtrpnEditordescripcion.getText());
					GestorProyectos.getInstancia("").editarProyecto(project);

					DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeTareas.getLastSelectedPathComponent();
					Tarea task;
					try {
						task = (Tarea) node.getUserObject();
						if (task != null) {

							GestorTareas.getInstancia("").editarTarea(task.getUuid(), txtTareanombre.getText(),
									pickerInicio.getDate(), pickerFinal.getDate(), txtTags.getText(),
									descripcionTarea.getText(), (int) spinnerPrioridad.getValue(),
									comboBoxEstadoTarea.getSelectedIndex());

						}
					} catch (java.lang.ClassCastException | java.lang.NullPointerException ey) {

					}
				} else {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
					JOptionPane.showMessageDialog(frame, "No hay proyecto seleccionado.", "Seleccionar proyecto.",
							JOptionPane.ERROR_MESSAGE);
				}
				GestorProyectos.getInstancia("").guardarProyectos();
				GestorTareas.getInstancia("").guardarTareas();
				GestorEquipo.getInstancia("").guardarEquipos();
				GestorMensajes.getInstancia("").guardarmensajes();
				GestorMiembrosTareas.getInstancia("").guardarTareas();

			}

		});
		mnArchivo.add(mntmGuardarProyecto);
		mnArchivo.addSeparator();
		mnArchivo.add(mntmEliminarProyecto);

		JMenu mnInsertar = new JMenu("Insertar");
		menuBar.add(mnInsertar);

		JMenuItem mntmInsertarImagen = new JMenuItem("Insertar imagen");
		mntmInsertarImagen.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				insertActionPerformed();

			}
		});
		mnInsertar.add(mntmInsertarImagen);

		JMenu mnTareas = new JMenu("Tareas");
		menuBar.add(mnTareas);

		JMenuItem mntmBorrarTarea = new JMenuItem("Borrar tarea");
		mntmBorrarTarea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
				treeTareas.getLastSelectedPathComponent();
				Tarea task;

				try {
					task = (Tarea) nodo_anterior.getUserObject();

					String mensage = "¿Seguro que quieres eliminar la tarea " + task.getNombre()
							+ " y todas sus tareas hijas?";
					Object[] options = { "Borrar", "No borrar" };

					int n = JOptionPane.showOptionDialog(topFrame, mensage, "Confirmacion", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

					if (n == JOptionPane.YES_OPTION) {
						GestorTareas.getInstancia("").borrarTarea(task);
						treeTareas.setModel(
								GestorTareas.getInstancia("").actualizarTree("Tareas", task.getUuid_proyecto()));
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
				if (project != null) {

					DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeTareas.getLastSelectedPathComponent();
					Tarea task;
					try {
						task = (Tarea) node.getUserObject();
						String uuid_padre = task.getUuid();

						System.out.println(uuid_padre);
						GestorTareas.getInstancia("").crearTarea(uuid_padre, project.getUuid(), "Nueva tarea",
								Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), "Nueva tarea",
								"Tarea creada recientemente...", 0, 0);

						System.out.println("----" + task.getNombre() + "--" + task.getUuid());

						treeTareas.setModel(GestorTareas.getInstancia("").actualizarTree("Tareas", project.getUuid()));

					} catch (java.lang.ClassCastException | java.lang.NullPointerException ee) {

						GestorTareas.getInstancia("").crearTarea(project.getUuid(), "Nueva tarea",
								Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), "",
								"Tarea creada recientemente...", 0, 0);
						limpiarTareas();
						treeTareas.setModel(GestorTareas.getInstancia("").actualizarTree("Tareas", project.getUuid()));
					}

				} else {

				}

			}
		});
		mnTareas.add(mntmAadirTarea);
		mnTareas.addSeparator();
		mnTareas.add(mntmBorrarTarea);

		JMenu mnPersonas = new JMenu("Personas");
		menuBar.add(mnPersonas);

		JMenuItem mntmListarPersonas = new JMenuItem("Gestionar personas");
		mntmListarPersonas.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				Proyecto project = (Proyecto) listaproyectos.getSelectedValue();
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
				JFramePersonas pers;
				if (project != null) {
					pers = new JFramePersonas(topFrame, true, listEquipo, project.getUuid());
					limpiarEquipo();
				} else {
					pers = new JFramePersonas(topFrame, true, listEquipo, null);
				}

				new JDialog(pers, "Dialogo modal", Dialog.ModalityType.DOCUMENT_MODAL);

				pers.setVisible(true);
			}
		});
		mnPersonas.add(mntmListarPersonas);
		mnPersonas.addSeparator();
		JMenuItem mntmMensajes = new JMenuItem("Enviar mensajes");
		mntmMensajes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
				JFrameEnviarMensaje msg = new JFrameEnviarMensaje(topFrame, true);
				new JDialog(msg, "Dialogo modal", Dialog.ModalityType.DOCUMENT_MODAL);
				msg.setVisible(true);

			}
		});
		mnPersonas.add(mntmMensajes);

		JMenuItem mntmBandejaDeEntrada = new JMenuItem("Bandeja de entrada");
		mntmBandejaDeEntrada.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
				JFrameBandejaDeEntrada msg = new JFrameBandejaDeEntrada(topFrame, true);
				new JDialog(msg, "Dialogo modal", Dialog.ModalityType.DOCUMENT_MODAL);
				msg.setVisible(true);
			}
		});
		mnPersonas.add(mntmBandejaDeEntrada);

		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);

		panelsur = new JPanel();
		panelsur.setBackground(new Color(181, 181, 181));
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
			@Override
			public void valueChanged(ListSelectionEvent e) {

				Proyecto project = (Proyecto) listaproyectos.getSelectedValue();
				if (project != null) {
					txtTitulo.setText(project.getNombre());
					txtFechacreacion.setText("" + project.getFecha_creacion());
					dtrpnEditordescripcion.setText(project.getDescripcion());
					treeTareas.setModel(GestorTareas.getInstancia("").actualizarTree("Tareas", project.getUuid()));
					listEquipo.setModel(GestorEquipo.getInstancia("").getMiembrosEquipoProyecto(project.getUuid()));

					limpiarTareas();
					limpiarEquipo();
				}

			}
		});

		listaproyectos.setFont(new Font("Tahoma", Font.PLAIN, 11));

		listaproyectos.setFixedCellHeight(40);
		listaproyectos.setCellRenderer(getRenderer());

		listaproyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane_1 = new JScrollPane(listaproyectos);

		scrollPane_1.setViewportView(listaproyectos);

		split_izq.add(scrollPane_1);

		JSplitPane split_der = new JSplitPane();

		split_der.setOneTouchExpandable(true);
		split_der.setResizeWeight(0.6);

		mainSplit.setRightComponent(split_der);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOneTouchExpandable(true);
		splitPane_2.setResizeWeight(0.001);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);

		split_der.setRightComponent(splitPane_2);

		JPanel panelEquipo = new JPanel();
		panelEquipo.setMaximumSize(new Dimension(600, 600));
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

		listEquipo = new JList();
		listEquipo.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				MiembroEquipo miem = (MiembroEquipo) listEquipo.getSelectedValue();

				if (miem != null) {
					Usuario user = GestorUsuarios.getInstancia("").getUserByUuid(miem.getUuid_usuario());
					nombreMiembro.setText(user.getNombre());
					apellidosMiembro.setText(user.getApellidos());
					try {
						lblFotoequipo.setIcon(new ImageIcon(new javax.swing.ImageIcon(user.getFoto()).getImage()
								.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
					} catch (Exception ex) {
						lblFotoequipo.setIcon(new ImageIcon(
								new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/logo.png"))
										.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
					}
					rolMiembro.setText(miem.getRol());
				}

			}
		});
		scrollPane_5.setViewportView(listEquipo);

		JPanel panel_3 = new JPanel();
		splitPane_4.setRightComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_separador = new JPanel();
		panel_3.add(panel_separador, BorderLayout.SOUTH);
		panel_separador.setLayout(new GridLayout(1, 4, 0, 0));

		Component horizontalStrut_10 = Box.createHorizontalStrut(20);
		panel_separador.add(horizontalStrut_10);

		JButton btnExpulsarPersona = new JButton("Expulsar persona");
		btnExpulsarPersona.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				MiembroEquipo miembro = (MiembroEquipo) listEquipo.getSelectedValue();
				if (miembro != null) {

					String mensage = "¿Seguro que quieres eliminar al miembro del equipo?";
					Object[] options = { "Expulsar", "No expulsar" };

					int n = JOptionPane.showOptionDialog(null, mensage, "Confirmacion", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

					if (n == JOptionPane.YES_OPTION) {
						GestorEquipo.getInstancia("").eliminarAsociacion(miembro);
						Proyecto project = (Proyecto) listaproyectos.getSelectedValue();
						listEquipo.setModel(GestorEquipo.getInstancia("").getMiembrosEquipoProyecto(project.getUuid()));
						limpiarEquipo();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Primero tienes que seleccionar una persona.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		panel_separador.add(btnExpulsarPersona);

		JButton btnAddEquipo = new JButton("Añadir personas");
		panel_separador.add(btnAddEquipo);
		btnAddEquipo.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnAddEquipo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Proyecto project = (Proyecto) listaproyectos.getSelectedValue();
				if (project != null) {
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
					JFrameGestionarEquipo ff = new JFrameGestionarEquipo(topFrame, true, project.getUuid(), listEquipo);

					new JDialog(ff, "Dialogo modal", Dialog.ModalityType.DOCUMENT_MODAL);
					ff.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Primero tienes que seleccionar un proyecto.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		Component horizontalStrut_9 = Box.createHorizontalStrut(20);
		panel_separador.add(horizontalStrut_9);

		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		panel_7.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new GridLayout(4, 3, 3, 3));

		JLabel lblNewLabel_3 = new JLabel("Nombre");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(lblNewLabel_3);

		nombreMiembro = new JTextField();
		nombreMiembro.setEditable(false);
		panel_6.add(nombreMiembro);
		nombreMiembro.setColumns(10);

		Component horizontalStrut_8 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_8);

		JLabel lblNewLabel_5 = new JLabel("Apellidos");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(lblNewLabel_5);

		apellidosMiembro = new JTextField();
		apellidosMiembro.setEditable(false);
		panel_6.add(apellidosMiembro);
		apellidosMiembro.setColumns(10);

		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_6);

		JLabel lblNewLabel_4 = new JLabel("Rol");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(lblNewLabel_4);

		rolMiembro = new JTextField();
		rolMiembro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				MiembroEquipo miembro = (MiembroEquipo) listEquipo.getSelectedValue();
				if (miembro != null) {
					miembro.setRol(rolMiembro.getText());
					GestorEquipo.getInstancia("").editarAsociacion(miembro);
				}

			}
		});
		panel_6.add(rolMiembro);
		rolMiembro.setColumns(10);

		Component horizontalStrut_11 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_11);

		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_7);

		Component horizontalStrut_13 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_13);

		Component horizontalStrut_12 = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut_12);

		JPanel panel_8 = new JPanel();

		panel_7.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));

		lblFotoequipo = new JLabel("");
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

		comboBoxEstadoTarea.setModel(new DefaultComboBoxModel(new String[] { "Activa", "Completa", "Tardia" }));
		panel_1.add(comboBoxEstadoTarea);

		panel.add(panel_1, BorderLayout.SOUTH);

		panel_1.setLayout(new GridLayout(2, 5));
		panel_1.setMinimumSize(new Dimension(1, 50));
		JButton btnNewButton_2 = new JButton("Personas");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Proyecto project = (Proyecto) listaproyectos.getSelectedValue();

				if (project != null) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeTareas.getLastSelectedPathComponent();
					Tarea task;

					try {
						task = (Tarea) node.getUserObject();
						if (task != null) {

							JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
							JFrameGestionarPersonasTarea ff = new JFrameGestionarPersonasTarea(topFrame, true,
									project.getUuid(), task.getUuid());

							new JDialog(ff, "Dialogo modal", Dialog.ModalityType.DOCUMENT_MODAL);
							ff.setVisible(true);

						}
					} catch (java.lang.ClassCastException | java.lang.NullPointerException ee) {

					}

				} else {
					JOptionPane.showMessageDialog(null, "Primero tienes que seleccionar un proyecto.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
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
			@Override
			public void actionPerformed(ActionEvent e) {
				Proyecto project = (Proyecto) listaproyectos.getSelectedValue(); 
				if (project != null) {
					JFrameVistaCalendario cal=new JFrameVistaCalendario(project.getUuid());
					cal.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Primero tienes que seleccionar un proyecto.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			       
			}
		});
		panel_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Eliminar");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
				treeTareas.getLastSelectedPathComponent();
				Tarea task;

				try {
					task = (Tarea) nodo_anterior.getUserObject();

					String mensage = "¿Seguro que quieres eliminar la tarea " + task.getNombre()
							+ " y todas sus tareas hijas?";
					Object[] options = { "Borrar", "No borrar" };

					int n = JOptionPane.showOptionDialog(topFrame, mensage, "Confirmacion", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

					if (n == JOptionPane.YES_OPTION) {
						GestorTareas.getInstancia("").borrarTarea(task);
						treeTareas.setModel(
								GestorTareas.getInstancia("").actualizarTree("Tareas", task.getUuid_proyecto()));
						limpiarTareas();

					}

				} catch (java.lang.ClassCastException | java.lang.NullPointerException ee) {

					JOptionPane.showMessageDialog(topFrame, "No hay ninguna tarea seleccionada.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
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
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeTareas.getLastSelectedPathComponent();
				Tarea task;

				guardar_tarea_anterior();

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

		scrollPane_2.setViewportView(treeTareas);

		JScrollPane scrollPane_4 = new JScrollPane();
		splitPane_3.setRightComponent(scrollPane_4);

		JPanel panel_4 = new JPanel();
		scrollPane_4.setViewportView(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		descripcionTarea = new JTextPane();

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

		panelNor.setMaximumSize(new Dimension(10000, 100));
		panelNor.setLayout(new BoxLayout(panelNor, BoxLayout.X_AXIS));

		Component horizontalStrut_2 = Box.createHorizontalStrut(3);
		panelNor.add(horizontalStrut_2);

		JLabel lblTitulo = new JLabel("Titulo: ");

		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelNor.add(lblTitulo);

		txtTitulo = new JTextField();

		txtTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		panelNor.add(txtTitulo);
		txtTitulo.setColumns(10);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panelNor.add(horizontalStrut_1);

		JLabel lblCreacion = new JLabel("Creacion: ");
		lblCreacion.setFont(new Font("Tahoma", Font.PLAIN, 14));

		panelNor.add(lblCreacion);

		txtFechacreacion = new JTextField();
		txtFechacreacion.setEditable(false);
		txtFechacreacion.setFont(new Font("Tahoma", Font.PLAIN, 14));

		txtFechacreacion.setText("                   ");

		txtFechacreacion.setMaximumSize(new Dimension(200, 25));
		panelNor.add(txtFechacreacion);

		Box.createHorizontalStrut(3);

		JScrollPane scrollPane = new JScrollPane();
		panelGeneral.add(scrollPane, BorderLayout.CENTER);

		dtrpnEditordescripcion = new JTextArea();
		dtrpnEditordescripcion = new JTextArea();
		dtrpnEditordescripcion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		dtrpnEditordescripcion.setLineWrap(true);
		dtrpnEditordescripcion.setWrapStyleWord(true);

		scrollPane.setViewportView(dtrpnEditordescripcion);
		new Dimension(9999999, 25);
		new Dimension(100, 100);

	}

	private ListCellRenderer<? super String> getRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				listCellRendererComponent
						.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(181, 181, 181)));
				return listCellRendererComponent;
			}
		};
	}

	public void limpiarTodo() {
		limpiarDetalles();
		limpiarTareas();
		limpiarEquipo();
		limpiarEquipoList();
	}

	public void limpiarEquipo() {
		rolMiembro.setText("");
		nombreMiembro.setText("");
		apellidosMiembro.setText("");
		lblFotoequipo.setIcon(
				new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/logo.png"))
						.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
	}

	public void limpiarEquipoList() {
		DefaultListModel listModel = (DefaultListModel) listEquipo.getModel();
		listModel.removeAllElements();

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

	private void guardar_tarea_anterior() {
		Tarea task;

		try {
			task = (Tarea) nodo_anterior.getUserObject();
			if (task != null) {
				GestorTareas.getInstancia("").editarTarea(task.getUuid(), txtTareanombre.getText(),
						pickerInicio.getDate(), pickerFinal.getDate(), txtTags.getText(), descripcionTarea.getText(),
						(int) spinnerPrioridad.getValue(), comboBoxEstadoTarea.getSelectedIndex());
			}
		} catch (java.lang.ClassCastException | java.lang.NullPointerException ee) {

		}

	}

	private void insertActionPerformed() {
		JFileChooser jf = new JFileChooser();

		int option = jf.showOpenDialog(this);

		if (option == JFileChooser.APPROVE_OPTION) {
			File file = jf.getSelectedFile();
			if (isImage(file)) {

				descripcionTarea.insertIcon(new ImageIcon(file.getAbsolutePath()));
			} else {
				JOptionPane.showMessageDialog(this, "El archivo no es una imagen.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean isImage(File file) {
		String name = file.getName();
		return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".gif");
	}

	class ResizeListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			if (CurrentSession.getInstancia().getUser() != null) {
				lblUsuariobottombar.setText("Bienvenido, " + CurrentSession.getInstancia().getUser().getNombre());
				lblLogintimebottombar.setText("Ultimo login: " + CurrentSession.getInstancia().getLogin_time());
			}

		}
	}
}
