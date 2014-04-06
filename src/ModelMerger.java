import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ModelMerger extends JFrame {
	private static final long serialVersionUID = 2217557311837L;
	private int mx = 0;
	private int my = 0;
	private long spinTime = System.currentTimeMillis();
	private String file = "";
	private String sample = "";

	public ModelMerger() {
		setTitle("Thread Debugger");
		setSize(450, 240);
		try {
			setIconImage(ImageIO.read(Main.class.getResource("icon.png")));
		} catch (Throwable t) {

		}
		setUndecorated(true);
		setLocation(0, 0);
		setVisible(true);
		Timer schedule = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
		});
		schedule.start();
		JPanel panel = new JPanel();
		panel.setSize(getWidth(), 30);
		add(panel, BorderLayout.NORTH);
		MouseAdapter st = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getX() > getWidth() - 50 && me.getX() < getWidth()
						&& me.getY() <= 30) {
					setVisible(false);
					dispose();
				}
			}

			public void mouseReleased(MouseEvent me) {
				int mmx = me.getX();
				int mmy = me.getY();
				if (mmx > 160 && mmx < 200 && mmy > 50 && mmy < 74) {
					String nn = openFile();
					if (nn != null) {
						boolean openable = false;
						try {
							// System.out.println(nn);
							File f = new File(nn);
							if (f.exists()) {
								openable = true;
								sample = f.getName();
								file = nn;
							}
						} catch (Throwable t) {
							t.printStackTrace();
						}
						if (openable) {
							merge();
						}
					}
				}
			}
		};
		addMouseListener(st);
		panel.addMouseListener(st);
		MouseMotionAdapter yr = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent me) {
				boolean prevState = mouseOver;
				mx = me.getX();
				my = me.getY();
				if (me.getX() > getWidth() - 50 && me.getY() <= 30) {
					mouseOver = true;
				} else {
					mouseOver = false;
				}
				if (prevState != mouseOver)
					repaint();
			}
		};
		addMouseMotionListener(yr);
		panel.addMouseMotionListener(yr);
		new ComponentMover(this, panel);
	}

	boolean mouseOver = false;

	public void paint(Graphics g2) {
		BufferedImage b = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics g = b.getGraphics();
		g.setFont(new Font("Arial", 0, 10));
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.setColor(new Color(50, 50, 50));
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

		// Graphics2D g3 = (Graphics2D)g;
		// g3.setPaint(new LinearGradientPaint(0,0,0,30,new
		// float[]{0.0f,1.0f},new Color[]{new Color(70,70,70),new
		// Color(60,60,60)}));
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, getWidth(), 30);
		// g3.fillRect(0, 0, getWidth(), 30);

		if (!mouseOver)
			g.setColor(new Color(190, 45, 45));
		else
			g.setColor(new Color(190, 45, 45).darker());
		g.fillRect(getWidth() - 50, 0, 50, 30);
		g.setColor(Color.white);
		g.drawString("x", getWidth() - 30, 18);

		String str = "Aeda Adventure Model Merger Tool";
		int le = g.getFontMetrics().stringWidth(str);

		g.drawString(str, (getWidth() - 30) / 2 - (le / 2), 18);

		g.setColor(Color.black);
		g.fillRect(8, 50, 140, 24);
		g.setColor(Color.gray);
		if (mx > 160 && mx < 200 && my > 50 && my < 74)
			g.setColor(Color.gray.darker());
		g.fillRect(160, 50, 40, 24);
		g.setColor(new Color(225, 225, 225));
		g.drawRect(8, 50, 140, 24);
		g.drawRect(160, 50, 40, 24);
		g.setColor(Color.green);
		if (sample != null && !sample.equals("")) {
			String sample2 = sample + "";
			if (sample2.length() > 20)
				sample2 = sample2.substring(0, 20);
			g.drawString(sample2, 14, 65);
		} else
			g.drawString("No file selected.", 14, 65);
		g.setColor(Color.white);
		g.drawString("Select a file to convert:", 8, 40);
		g.drawString("Browse", 163, 65);

		g2.drawImage(b, 0, 0, null);
	}

	private String openFile() {
		FileDialog fileDialog = new FileDialog(this,
				"Select a file to convert.", FileDialog.LOAD);
		fileDialog.setMultipleMode(false);
		fileDialog.setVisible(true);
		return fileDialog.getDirectory() + fileDialog.getFile();
	}

	public void merge() {
		if (file == null || file.equals(""))
			return;

		$XModelData xData = $Loader.LoadModel(file);
		if (xData.FrameModelList.size() < 1)
			return;

		final float scale = 1;
		
		File dest = new File(file.replace(".x", ".amd"));
		if (!dest.exists())
			try {
				dest.createNewFile();
			} catch (IOException e) {
				
			}
		FileWriter fw = null;
		try {
			fw = new FileWriter(dest.getAbsoluteFile());
		}
		catch (Throwable t){
			
		}
		BufferedWriter bw = null;
		if (fw != null)
			bw = new BufferedWriter(fw);
		
		// Load only first framemodel.
		$FrameModel fmodel = ($FrameModel) xData.FrameModelList.elementAt(0);
		for (int i = 0; i < fmodel.MeshObjectList.size(); i++) {
			$MeshObject tmesh = ($MeshObject) fmodel.MeshObjectList
					.elementAt(i);
			ArrayList<P3D> triangles = new ArrayList<P3D>();
			System.out.println(tmesh.faces.length + "," + tmesh.vertexColors.length);
			for (int c = 0; c < tmesh.faces.length; c++) {
				int[] verts = tmesh.faces[c].vertIndex;
				if (verts.length != 3)
					throw new IllegalStateException(
							"Must be expressed in triangles.");
				else {
					$Vertex3f first = tmesh.vertices[verts[0]];
					$Vertex3f second = tmesh.vertices[verts[1]];
					$Vertex3f third = tmesh.vertices[verts[2]];
					P3D v0 = new P3D(first.x*scale, first.y*scale, first.z*scale);
					P3D v1 = new P3D(second.x*scale, second.y*scale, second.z*scale);
					P3D v2 = new P3D(third.x*scale, third.y*scale, third.z*scale);
					if (bw != null) {
						try {
							if (Math.random() < 0.5)
								bw.write("tesselator.color(var.variate(myColor,20));\n");
							else
								bw.write("tesselator.color(var.bright(myColor,20));\n");
							bw.write("tesselator.point("+v0.x+"f*scale,"+v0.y+"f*scale,"+v0.z+"f*scale);\n");
							bw.write("tesselator.point("+v1.x+"f*scale,"+v1.y+"f*scale,"+v1.z+"f*scale);\n");
							bw.write("tesselator.point("+v2.x+"f*scale,"+v2.y+"f*scale,"+v2.z+"f*scale);\n");
							bw.write("\n");
						}catch (Throwable t) {
							
						}
					}
				}
			}
			/*
			 * vertlength += tmesh.vertices.length; meshlength +=
			 * tmesh.faces.length; texlength += tmesh.vertexColors.length;
			 */
		}
		try {
			bw.close();
			fw.close();
		}
		catch (Throwable t) {
			
		}
	}
}