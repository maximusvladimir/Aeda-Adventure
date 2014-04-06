import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;


public class ComponentMover extends MouseAdapter {
	private Class<?> destinationClass;
	private Component destinationComponent;
	private Component destination;
	private Component source;
	private boolean changeCursor = true;
	private Point pressed;
	private Point location;
	private Cursor originalCursor;
	private boolean autoscrolls;
	private Insets dragInsets = new Insets(0, 0, 0, 0);
	private Dimension snapSize = new Dimension(1, 1);
	
	public ComponentMover() {
	}
	
	public ComponentMover(Class<?> destinationClass, Component... components) {
		this.destinationClass = destinationClass;
		registerComponent(components);
	}
	
	public ComponentMover(Component destinationComponent,
			Component... components) {
		this.destinationComponent = destinationComponent;
		registerComponent(components);
	}

	public boolean isChangeCursor() {
		return changeCursor;
	}

	public void setChangeCursor(boolean changeCursor) {
		this.changeCursor = changeCursor;
	}

	public Insets getDragInsets() {
		return dragInsets;
	}

	public void setDragInsets(Insets dragInsets) {
		this.dragInsets = dragInsets;
	}

	public void deregisterComponent(Component... components) {
		for (Component component : components)
			component.removeMouseListener(this);
	}

	public void registerComponent(Component... components) {
		for (Component component : components)
			component.addMouseListener(this);
	}

	public Dimension getSnapSize() {
		return snapSize;
	}

	public void setSnapSize(Dimension snapSize) {
		this.snapSize = snapSize;
	}

	public void mousePressed(MouseEvent e) {
		source = e.getComponent();
		int width = source.getSize().width - dragInsets.left - dragInsets.right;
		int height = source.getSize().height - dragInsets.top
				- dragInsets.bottom;
		Rectangle r = new Rectangle(dragInsets.left, dragInsets.top, width,
				height);

		if (r.contains(e.getPoint()))
			setupForDragging(e);
	}

	private void setupForDragging(MouseEvent e) {
		source.addMouseMotionListener(this);
		if (destinationComponent != null) {
			destination = destinationComponent;
		} else if (destinationClass == null) {
			destination = source;
		} else
		{
			destination = SwingUtilities.getAncestorOfClass(destinationClass,
					source);
		}

		pressed = e.getLocationOnScreen();
		location = destination.getLocation();

		if (changeCursor) {
			originalCursor = source.getCursor();
			source.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
		if (destination instanceof JComponent) {
			JComponent jc = (JComponent) destination;
			autoscrolls = jc.getAutoscrolls();
			jc.setAutoscrolls(false);
		}
	}

	public void mouseDragged(MouseEvent e) {
		Point dragged = e.getLocationOnScreen();
		int dragX = getDragDistance(dragged.x, pressed.x, snapSize.width);
		int dragY = getDragDistance(dragged.y, pressed.y, snapSize.height);
		destination.setLocation(location.x + dragX, location.y + dragY);
	}

	private int getDragDistance(int larger, int smaller, int snapSize) {
		int halfway = snapSize / 2;
		int drag = larger - smaller;
		drag += (drag < 0) ? -halfway : halfway;
		drag = (drag / snapSize) * snapSize;

		return drag;
	}

	public void mouseReleased(MouseEvent e) {
		source.removeMouseMotionListener(this);

		if (changeCursor)
			source.setCursor(originalCursor);

		if (destination instanceof JComponent) {
			((JComponent) destination).setAutoscrolls(autoscrolls);
		}
	}
}