package javawulf.view.gamemenu;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javawulf.scoreboard.ScoreBoardImpl;
import javawulf.scoreboard.Scoreboard;
import javawulf.view.GamePanel;

import javax.swing.Box;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GameMenuPanel is used to display the GUI.
 */
public class GameMenuPanel extends JPanel {

    private static int scaleX;
    private static int scaleY;
    private static int menuBorders;
    private static int scoreboardBorders;

    private static final int MAX_BUTTON_WIDTH = 800;
    private static final int MAX_BUTTON_HEIGHT = 120;
    private static final int MENU_OFFSET = 5;
    private static final int COLS_RESULTS = 3;
    private final JFrame frame;

    /**
     * Sets the size of the window and creates the menu.
     * @throws InterruptedException 
     */
    
    public GameMenuPanel() throws InterruptedException {
        scaleX = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
        scaleY = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
        menuBorders = scaleX / MENU_OFFSET;
        scoreboardBorders = scaleX / 7;
        frame = new JFrame("JavaWulf");
        createMenuGUI(frame);
    }

    /**
     * Create the buttons of the game menu.
     * @param frame Is the frame where the menu is shown
     */
    public static void createMenuGUI(final JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(scaleY, scaleX));
        frame.setSize(new Dimension(scaleY, scaleX));
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        showMenu(frame);

        frame.setVisible(true);
    }

    private static void showMenu(final JFrame frame) {

        JPanel menu = new JPanel(new GridLayout(2, 2));
        JButton startButton = new JButton("PLAY");
        JButton leaderboardButton = new JButton("Leaderboard");
        JButton guideButton = new JButton("Guide");
        JButton exitButton = new JButton("Exit");

        // To limit Max button sixing
        Dimension maxButtonSize = new Dimension(MAX_BUTTON_WIDTH, MAX_BUTTON_HEIGHT);
        startButton.setMaximumSize(maxButtonSize);
        leaderboardButton.setMaximumSize(maxButtonSize);
        guideButton.setMaximumSize(maxButtonSize);
        exitButton.setMaximumSize(maxButtonSize);

        // Start Game
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
                try {
                    frame.getContentPane().removeAll();
                    frame.add(new GamePanel(frame));
                    frame.setSize(GamePanel.TILESIZE * GamePanel.MAX_SCREEN_COL, GamePanel.TILESIZE * GamePanel.MAX_SCREEN_ROW);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (final Exception exceptionViewImpl) {
                    exceptionViewImpl.printStackTrace();
                }
            }
        });
        // Show LeaderBoard
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                showLeaderboard(frame);
            }
        });
        // Show Guide
        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Press the UP key to move up\n"
                    + "Press the DOWN key to move down\n" + "Press the LEFT key to move left\n"
                    + "Press the RIGHT key to move right\n" + "Press the COMMA (,) key to attack\n");
            }
        });
        // Exit 
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame,
                        "Sure you want to quit?",
                        "Confirm exit",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        menu.add(startButton);
        menu.add(leaderboardButton);
        menu.add(guideButton);
        menu.add(exitButton);
        frame.add(menu, BorderLayout.CENTER);
        frame.add(Box.createVerticalStrut(menuBorders), BorderLayout.NORTH);
        frame.add(Box.createVerticalStrut(menuBorders), BorderLayout.SOUTH);
        frame.add(Box.createHorizontalStrut(menuBorders), BorderLayout.WEST);
        frame.add(Box.createHorizontalStrut(menuBorders), BorderLayout.EAST);
    }

    private static void showLeaderboard(final JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        Scoreboard scoreboard = new ScoreBoardImpl();
        scoreboard.loadScoreBoardFromFile();
        JPanel scoreBoardJPanel;
        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        JPanel legendPanel = new JPanel(new GridLayout(1, COLS_RESULTS));
        JLabel titleLabel = new JLabel("LeaderBoard", SwingConstants.CENTER);
        JLabel legendNicknameLabel = new JLabel("Nickname", SwingConstants.CENTER);
        JLabel legendScoreLabel = new JLabel("Score", SwingConstants.CENTER);
        JLabel legendWonLabel = new JLabel("Did you win?", SwingConstants.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                frame.getContentPane().removeAll();
                showMenu(frame);
                frame.setVisible(true);
            }
        });

        if (!scoreboard.getAllScores().isEmpty()) {
            scoreBoardJPanel = new JPanel(new GridLayout(scoreboard.getAllScores().size(), COLS_RESULTS));
            scoreboard.getAllScores().forEach(score -> {
            JLabel nameLabel = new JLabel(score.getUserName());
            JLabel scoreLabel = new JLabel(Integer.toString(score.getScore()), SwingConstants.CENTER);
            JLabel wonLabel = new JLabel(score.getWon() ? "yes" : "no", SwingConstants.CENTER);
            scoreBoardJPanel.add(nameLabel);
            scoreBoardJPanel.add(scoreLabel);
            scoreBoardJPanel.add(wonLabel);
            });
        } else {
            scoreBoardJPanel = new JPanel();
            scoreBoardJPanel.add(new JLabel("no results yet!"));
        }

        legendPanel.add(legendNicknameLabel);
        legendPanel.add(legendScoreLabel);
        legendPanel.add(legendWonLabel);
        titlePanel.add(titleLabel);
        titlePanel.add(legendPanel);
        leaderboardPanel.add(titlePanel, BorderLayout.NORTH);
        leaderboardPanel.add(scoreBoardJPanel, BorderLayout.CENTER);
        leaderboardPanel.add(backButton, BorderLayout.SOUTH);
        frame.add(leaderboardPanel, BorderLayout.CENTER);
        frame.add(Box.createVerticalStrut(scoreboardBorders), BorderLayout.NORTH);
        frame.add(Box.createVerticalStrut(scoreboardBorders), BorderLayout.SOUTH);
        frame.add(Box.createHorizontalStrut(scoreboardBorders), BorderLayout.WEST);
        frame.add(Box.createHorizontalStrut(scoreboardBorders), BorderLayout.EAST);

        frame.revalidate();
        frame.repaint();
    }

}
