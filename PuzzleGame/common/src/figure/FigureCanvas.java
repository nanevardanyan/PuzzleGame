package figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Company: WeDooApps
 * Date: 12/27/15
 * <p/>
 * Created by Adam Madoyan.
 */
public class FigureCanvas extends JPanel {
	public static final int DEFAULT_MARGIN = 50;
	public static final Color DEFAULT_BORDER_COLOR = new Color(250, 244, 161);

	protected List<Figure> figures = new ArrayList<>();

	private boolean isSelected;

	private int mX;
	private int mY;

	private int currentWidth;
	private int currentHeight;

	BorderResizeMode borderResizeMode = BorderResizeMode.NO_RESIZE;

	private CanvasBorder border;

	public int getBorderLeft() {
		return border.getX();
	}

	public int getBorderRight() {
		return border.getX() + border.getWidth();
	}

	public int getBorderTop() {
		return border.getY();
	}

	public int getBorderBottom() {
		return border.getY() + border.getHeight();
	}


	public FigureCanvas() {
		this(DEFAULT_MARGIN, DEFAULT_MARGIN, 0, 0, DEFAULT_BORDER_COLOR);
	}

	public FigureCanvas(int borderX, int borderY, int borderWidth, int borderHeight, Color borderColor) {
		border = new CanvasBorder(borderX, borderY, borderWidth, borderHeight, this, borderColor);

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//              System.out.println(this.getClass().getName());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				mousePressedPerformed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouseReleasedPerformed(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseDraggedPerformed(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseMovedPerformed(e);
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				canvasResizedPerformed(e);
			}
		});
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	protected void setmX(int mX) {
		this.mX = mX;
	}

	protected void setmY(int mY) {
		this.mY = mY;
	}

	private void canvasResizedPerformed(ComponentEvent e) {

		if (border.getWidth() == 0) {
			border.setWidth(getWidth() - border.getX() * 2);
		} else {
			border.setWidth(border.getWidth() + getWidth() - currentWidth);
		}

		if (border.getHeight() == 0) {
			border.setHeight(getHeight() - border.getY() * 2);
		} else {
			border.setHeight(border.getHeight() + getHeight() - currentHeight);

		}

		currentWidth = getWidth();
		currentHeight = getHeight();
	}

	public void add(Figure figure) {
		if (figure != null) {
			figures.add(figure);
			repaint();
		}
	}

	public void removeAllFigures() {
	    this.figures.clear();
		repaint();
	}

	public void remove() {
		if (isSelected) {
			Figure figure = getSelected();
			figures.remove(figure);
			figure.stop();
			repaint();
		}
	}

	public Figure getSelected() {
		int size = figures.size();
		return isSelected && size > 0 ? figures.get(size - 1) : null;
	}

	public void start() {
		if (isSelected) {
			getSelected().start();
		}
	}

	public void stop() {
		if (isSelected) {
			getSelected().stop();
		}
	}

	public void pause() {
		if (isSelected) {
			getSelected().pause();
		}
	}

	public void resume() {
		if (isSelected) {
			getSelected().resume();
		}
	}

	@Override
	public void paint(Graphics g) {

		g.clearRect(0, 0, 3000, 3000);
		border.draw(g);

		for (Figure figure : figures) {
			figure.draw(g);
		}
	}

	private void select(int x, int y) {
		isSelected = false;
		for (int i = figures.size() - 1; i >= 0; i--) {
			Figure figure = figures.get(i);
			if (figure.isBelong(x, y)) {
				figures.add(figures.remove(i));
				isSelected = true;
				return;
			}
		}
	}

	protected void mouseReleasedPerformed(MouseEvent e) {

	}

	protected void mousePressedPerformed(MouseEvent e) {
		select(e.getX(), e.getY());
		if (isSelected) {
			repaint();
		}
		mX = e.getX();
		mY = e.getY();
	}

	protected void mouseDraggedPerformed(MouseEvent e) {

		if (borderResizeMode != BorderResizeMode.NO_RESIZE) {
			switch (borderResizeMode) {
				case RESIZE_RIGHT_BOTTOM:
					border.setWidth(e.getX() - border.getX());
					border.setHeight(e.getY() - border.getY());
					break;
				case RESIZE_RIGHT:
					border.setWidth(e.getX() - border.getX());
					break;
				case RESIZE_LEFT:
					border.setWidth(border.getX() + border.getWidth() - e.getX());
					border.setX(e.getX());
					break;
				case RESIZE_BOTTOM:
					border.setHeight(e.getY() - border.getY());
					break;
				case RESIZE_TOP:

					border.setHeight(border.getY() + border.getHeight() - e.getY());
					border.setY(e.getY());
					break;
				case RESIZE_RIGHT_TOP:

						border.setWidth(e.getX() - border.getX());
						border.setHeight(border.getY() + border.getHeight() - e.getY());
						border.setY(e.getY());

					break;
			}
			repaint();
		}

		if (isSelected) {
			getSelected().move(e.getX() - mX, e.getY() - mY);
			repaint();
		}
		mX = e.getX();
		mY = e.getY();
	}

	protected void mouseMovedPerformed(MouseEvent e) {
		checkBorders(e);

		mX = e.getX();
		mY = e.getY();
	}

	private void checkBorders(MouseEvent e) {
		checkBorders(e.getX(), e.getY());
	}

	private void checkBorders(int x, int y) {
		if (onBorderRightBottom(x, y)) {
			borderResizeMode = BorderResizeMode.RESIZE_RIGHT_BOTTOM;
			setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));

		} else if (onBorderRightTop(x, y)) {
			borderResizeMode = BorderResizeMode.RESIZE_RIGHT_TOP;
			setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
		} else if (onBorderLeftBottom(x, y)) {
			borderResizeMode = BorderResizeMode.RESIZE_LEFT_BOTTOM;
			setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
		} else if (onBorderLeftTop(x, y)) {
			borderResizeMode = BorderResizeMode.RESIZE_LEFT_TOP;
			setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
		} else if (onBorderLeft(x)) {
			borderResizeMode = BorderResizeMode.RESIZE_LEFT;
			setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		} else if (onBorderTop(y)) {
			borderResizeMode = BorderResizeMode.RESIZE_TOP;
			setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
		} else if (onBorderBottom(y)) {
			borderResizeMode = BorderResizeMode.RESIZE_BOTTOM;
			setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
		} else if (onBorderRight(x)) {
			borderResizeMode = BorderResizeMode.RESIZE_RIGHT;
			setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		} else {
			borderResizeMode = BorderResizeMode.NO_RESIZE;
			setCursor(Cursor.getDefaultCursor());
		}
	}

	private boolean onBorderLeft(int x) {
		return x >= getBorderLeft() - 5 && x < getBorderLeft() + 5;

	}

	private boolean onBorderRight(int x) {
		return x >= getBorderRight() - 5 && x < getBorderRight() + 5;
	}

	private boolean onBorderTop(int y) {
		return y >= getBorderTop() - 5 && y < getBorderTop() + 5;

	}

	private boolean onBorderBottom(int y) {
		return y >= getBorderBottom() - 5 && y < getBorderBottom() + 5;
	}

	private boolean onBorderRightBottom(int x, int y) {
		return onBorderRight(x) && onBorderBottom(y);
	}

	private boolean onBorderLeftTop(int x, int y) {
		return onBorderLeft(x) && onBorderTop(y);
	}

	private boolean onBorderLeftBottom(int x, int y) {
		return onBorderLeft(x) && onBorderBottom(y);
	}

	private boolean onBorderRightTop(int x, int y) {
		return onBorderRight(x) && onBorderTop(y);
	}


	enum BorderResizeMode {
		NO_RESIZE(0, "noResize"),
		RESIZE_LEFT(1, "resizeLeft"),
		RESIZE_RIGHT(2, "resizeRight"),
		RESIZE_RIGHT_BOTTOM(3, "resizeRightBottom"),
		RESIZE_LEFT_TOP(4, "resizeLeftTop"),
		RESIZE_RIGHT_TOP(5, "resizeRightTop"),
		RESIZE_LEFT_BOTTOM(6, "resizeLeftBottom"),
		RESIZE_TOP(7, "resizeTop"),
		RESIZE_BOTTOM(8, "resizeBottom"),;


		BorderResizeMode(int value, String name) {
			this.value = value;
			this.name = name;
		}

		private final String name;
		private final int value;

	}

	public static class CanvasBorder extends Rectangle {

		public static final int DEFAULT_SIZE = 200;
		private int minimumSize = DEFAULT_SIZE;

		@Override
		public void setWidth(int width) {
			if (minimumSize >= width)
				return;
			super.setWidth(width);
		}

		@Override
		public void setHeight(int height) {
			if (minimumSize >= height)
				return;
			super.setHeight(height);
		}

		@Override
		public void setY(int y) {
			if (getY() + getHeight() - y < minimumSize) {
				return;
			}
			super.setY(y);
		}

		public void setMinimumSize(int newSize) {
			if (newSize <= DEFAULT_SIZE) return;
			this.minimumSize = newSize;
		}

		public CanvasBorder(int x, int y, int width, int height, FigureCanvas canvas) {
			super(x, y, width, height, canvas);
		}

		public CanvasBorder(int x, int y, int width, int height, FigureCanvas canvas, Color color) {
			super(x, y, width, height, canvas, color);
		}
	}


}