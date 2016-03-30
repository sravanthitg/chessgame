package com.aop.chessgame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This is the board we use to display to user
 * 
 * @author rajesh
 *
 */
public class ChessBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MoveListener moveListener;
	private JButton[] buttons;
	private Icon[] icons;
	private int srcIndex;
	private String rootPath;

	public ChessBoard(String rootPath) {
		this.setRootPath(rootPath);
		this.setLayout(new GridLayout(8, 8));
		createButtons();
		loadIcons();
	}

	private void loadIcons() {
		icons = new Icon[12];
		CoinType[] coinTypes = CoinType.values();
		for (int x = 0; x < 6; x++) {
			icons[x] = loadIcon(coinTypes[x].name() + "_white");
			icons[6 + x] = loadIcon(coinTypes[x].name() + "_black");
		}
	}

	private Icon loadIcon(String string) {
		return new ImageIcon(getIconPath(string));
	}

	private String getIconPath(String string) {
		return getRootPath() + "\\" + string + ".png";
	}

	private String getRootPath() {
		return rootPath;
	}

	private void createButtons() {
		buttons = new JButton[64];
		for (int x = 0; x < 64; x++) {
			buttons[x] = new JButton();
			buttons[x].setBackground(getColor(x));
			buttons[x].addActionListener((e) -> buttonClicked(e));
			this.add(buttons[x]);
		}
	}

	private void buttonClicked(ActionEvent e) {
		int index = getIndex((JButton) e.getSource());
		if (srcIndex != -1) {
			this.moveListener.moveRequested(srcIndex, index);
			this.srcIndex = -1;
		} else {
			srcIndex = index;
		}
	}

	private int getIndex(JButton source) {
		for (int x = 0; x < 64; x++) {
			if (buttons[x] == source) {
				return x;
			}
		}
		return -1;
	}

	private Color getColor(int x) {
		return x % 2 == 0 ? Color.WHITE : Color.BLACK;
	}

	public void setCoin(int x, Coin coin) {
		if (x > 63) {
			return;
		}
		if (coin == null) {
			buttons[x].setIcon(null);
		} else {
			Icon icon = getIcon(coin.getType(), coin.isWhite());
			buttons[x].setIcon(icon);
		}
	}

	private Icon getIcon(CoinType type, boolean white) {
		if (white) {
			return icons[type.ordinal()];
		} else {
			return icons[6 + type.ordinal()];
		}
	}

	public void setMoveListener(MoveListener moveListener) {
		this.moveListener = moveListener;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

}
