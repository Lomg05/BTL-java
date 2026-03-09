package ui;

import Game.Game;
import Game.GameController;
import GameState.GameState;
import Profile.ProfileData;
import Profile.ProfileManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class ProfileSelectionUI {
    private ProfileManager profileManager;
    private GameController gameController;
    private GameState gameState;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_SPACING = 10;
    private int selectedIndex = -1;
    private String inputText = "";
    private boolean isCreatingProfile = false;
    private String errorMessage = "";
    private long errorMessageTime = 0;
    private BufferedImage background;
    private BufferedImage background_button;
    private Font font;

    // Button rectangles
    private Rectangle newProfileButton;
    private Rectangle deleteProfileButton;
    private Rectangle backButton;
    private Rectangle playButton;

    public ProfileSelectionUI(ProfileManager profileManager, GameController gameController) {
        this.profileManager = profileManager;
        this.gameController = gameController;
        this.gameState = null;
        loadResources();
        initializeButtons();
    }

    private void loadResources() {
        try {
            background = LoadSave.loadImage(LoadSave.MENU_BACKGROUND);
            background_button = LoadSave.loadImage(LoadSave.MENU_BUTTON_BACKGROUND);
            font = LoadSave.LoadFont(LoadSave.FONT);
        } catch (Exception e) {
            System.err.println("Error loading resources: " + e.getMessage());
        }
    }

    private void initializeButtons() {
        int centerX = Game.GAME_WIDTH / 2;
        int startY = Game.GAME_HEIGHT - 80;
        
        newProfileButton = new Rectangle(centerX - BUTTON_WIDTH / 2 - 160, startY, BUTTON_WIDTH, BUTTON_HEIGHT);
        deleteProfileButton = new Rectangle(centerX + 160 - BUTTON_WIDTH / 2, startY, BUTTON_WIDTH, BUTTON_HEIGHT);
        playButton = new Rectangle(centerX - BUTTON_WIDTH / 2 - 160, startY - BUTTON_HEIGHT - BUTTON_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT);
        backButton = new Rectangle(centerX + 160 - BUTTON_WIDTH / 2, startY - BUTTON_HEIGHT - BUTTON_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public void update() {
        if (System.currentTimeMillis() - errorMessageTime > 3000) {
            errorMessage = "";
        }
    }

    public void render(Graphics g) {
        // Draw background
        if (background != null) {
            g.drawImage(background, 0, 0,
                    (int) (background.getWidth() * Game.GAME_SCALE * 1.3),
                    (int) (background.getHeight() * Game.GAME_SCALE * 1.3), null);
        }

        // Draw title
        g.setFont(font != null ? font.deriveFont(50f) : new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        String title = "Select Profile";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, Game.GAME_WIDTH / 2 - titleWidth / 2, 50);

        // Draw profiles list
        drawProfilesList(g);

        // Draw buttons
        drawButton(g, newProfileButton, "New Profile");
        drawButton(g, playButton, "Play");
        drawButton(g, deleteProfileButton, "Delete");
        drawButton(g, backButton, "Back");

        // Draw input dialog if creating profile
        if (isCreatingProfile) {
            drawInputDialog(g);
        }

        // Draw error message
        if (!errorMessage.isEmpty()) {
            g.setFont(font != null ? font.deriveFont(20f) : new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.RED);
            int textWidth = g.getFontMetrics().stringWidth(errorMessage);
            g.drawString(errorMessage, Game.GAME_WIDTH / 2 - textWidth / 2, Game.GAME_HEIGHT - 120);
        }
    }

    private void drawProfilesList(Graphics g) {
        List<ProfileData> profiles = profileManager.getAllProfiles();
        int startY = 120;
        g.setFont(font != null ? font.deriveFont(24f) : new Font("Arial", Font.PLAIN, 24));

        if (profiles.isEmpty()) {
            g.setColor(Color.GRAY);
            String noProfiles = "No profiles available";
            int textWidth = g.getFontMetrics().stringWidth(noProfiles);
            g.drawString(noProfiles, Game.GAME_WIDTH / 2 - textWidth / 2, startY + 50);
        } else {
            for (int i = 0; i < profiles.size(); i++) {
                ProfileData profile = profiles.get(i);
                int y = startY + i * 50;
                
                if (selectedIndex == i) {
                    g.setColor(new Color(100, 150, 255));
                    g.fillRect(50, y - 30, Game.GAME_WIDTH - 100, 40);
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.WHITE);
                }

                String profileText = (i + 1) + ". " + profile.toString();
                g.drawString(profileText, 70, y);

                // Draw profile info
                g.setFont(font != null ? font.deriveFont(16f) : new Font("Arial", Font.PLAIN, 16));
                g.setColor(Color.LIGHT_GRAY);
                String info = "Play Time: " + formatPlayTime(profile.getPlayTime());
                g.drawString(info, 80, y + 20);
                g.setFont(font != null ? font.deriveFont(24f) : new Font("Arial", Font.PLAIN, 24));
            }
        }
    }

    private void drawInputDialog(Graphics g) {
        int dialogWidth = 400;
        int dialogHeight = 150;
        int x = (Game.GAME_WIDTH - dialogWidth) / 2;
        int y = (Game.GAME_HEIGHT - dialogHeight) / 2;

        // Draw semi-transparent background
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        // Draw dialog
        g.setColor(new Color(50, 50, 50));
        g.fillRect(x, y, dialogWidth, dialogHeight);
        g.setColor(Color.WHITE);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        ((Graphics2D) g).drawRect(x, y, dialogWidth, dialogHeight);

        // Draw text
        g.setFont(font != null ? font.deriveFont(24f) : new Font("Arial", Font.PLAIN, 24));
        g.setColor(Color.WHITE);
        String label = "Enter Profile Name:";
        int labelWidth = g.getFontMetrics().stringWidth(label);
        g.drawString(label, x + (dialogWidth - labelWidth) / 2, y + 35);

        // Draw input field
        g.setColor(Color.WHITE);
        ((Graphics2D) g).drawRect(x + 20, y + 50, dialogWidth - 40, 30);
        g.setColor(Color.BLACK);
        g.fillRect(x + 21, y + 51, dialogWidth - 42, 28);
        g.setColor(Color.WHITE);
        g.drawString(inputText + "_", x + 30, y + 70);

        // Draw instructions
        g.setFont(font != null ? font.deriveFont(14f) : new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("ENTER to confirm, ESC to cancel", x + 20, y + 130);
    }

    private void drawButton(Graphics g, Rectangle button, String text) {
        g.setColor(new Color(80, 120, 180));
        g.fillRect(button.x, button.y, button.width, button.height);
        g.setColor(Color.WHITE);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        ((Graphics2D) g).drawRect(button.x, button.y, button.width, button.height);

        g.setFont(font != null ? font.deriveFont(18f) : new Font("Arial", Font.BOLD, 18));
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, button.x + (button.width - textWidth) / 2, button.y + 35);
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (!isCreatingProfile) {
            if (newProfileButton.contains(x, y)) {
                isCreatingProfile = true;
                inputText = "";
            } else if (playButton.contains(x, y)) {
                playSelectedProfile();
            } else if (deleteProfileButton.contains(x, y)) {
                deleteSelectedProfile();
            } else if (backButton.contains(x, y)) {
                GameState.state = GameState.MENU;
            } else {
                // Select profile
                List<ProfileData> profiles = profileManager.getAllProfiles();
                int startY = 120;
                for (int i = 0; i < profiles.size(); i++) {
                    int y_pos = startY + i * 50;
                    if (x > 50 && x < Game.GAME_WIDTH - 50 && y > y_pos - 30 && y < y_pos + 10) {
                        selectedIndex = i;
                        break;
                    }
                }
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (isCreatingProfile) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                createNewProfile();
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                isCreatingProfile = false;
                inputText = "";
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (inputText.length() > 0) {
                    inputText = inputText.substring(0, inputText.length() - 1);
                }
            } else if (e.getKeyChar() >= 32 && e.getKeyChar() <= 126) {
                if (inputText.length() < 20) {
                    inputText += e.getKeyChar();
                }
            }
        }
    }

    private void createNewProfile() {
        if (inputText.trim().isEmpty()) {
            showError("Profile name cannot be empty!");
            return;
        }
        
        try {
            profileManager.createProfile(inputText.trim());
            isCreatingProfile = false;
            inputText = "";
            selectedIndex = profileManager.getAllProfiles().size() - 1;
        } catch (IOException ex) {
            showError(ex.getMessage());
        }
    }

    private void playSelectedProfile() {
        if (selectedIndex < 0 || selectedIndex >= profileManager.getAllProfiles().size()) {
            showError("Please select a profile!");
            return;
        }
        
        List<ProfileData> profiles = profileManager.getAllProfiles();
        ProfileData selectedProfile = profiles.get(selectedIndex);
        profileManager.loadProfile(selectedProfile.getProfileName());
        gameController.startGameWithProfile();
    }

    private void deleteSelectedProfile() {
        if (selectedIndex < 0 || selectedIndex >= profileManager.getAllProfiles().size()) {
            showError("Please select a profile to delete!");
            return;
        }
        
        List<ProfileData> profiles = profileManager.getAllProfiles();
        ProfileData selectedProfile = profiles.get(selectedIndex);
        
        try {
            profileManager.deleteProfile(selectedProfile.getProfileName());
            selectedIndex = -1;
            showError("Profile deleted!");
        } catch (IOException ex) {
            showError("Error deleting profile: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        errorMessage = message;
        errorMessageTime = System.currentTimeMillis();
    }

    private String formatPlayTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        
        if (hours > 0) {
            return hours + "h " + minutes + "m";
        } else if (minutes > 0) {
            return minutes + "m";
        } else {
            return seconds + "s";
        }
    }
}
