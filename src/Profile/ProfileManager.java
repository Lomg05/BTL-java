package Profile;

import java.io.*;
import java.util.*;

public class ProfileManager {
    private static final String PROFILES_DIR = "profiles";
    private static final String PROFILES_FILE = "profiles.dat";
    private HashMap<String, ProfileData> profiles;
    private ProfileData currentProfile;

    public ProfileManager() {
        profiles = new HashMap<>();
        loadAllProfiles();
    }

    public void createProfile(String profileName) throws IOException {
        if (profiles.containsKey(profileName)) {
            throw new IOException("Profile '" + profileName + "' đã tồn tại!");
        }
        ProfileData newProfile = new ProfileData(profileName);
        profiles.put(profileName, newProfile);
        saveAllProfiles();
    }

    public ProfileData loadProfile(String profileName) {
        if (!profiles.containsKey(profileName)) {
            return null;
        }
        currentProfile = profiles.get(profileName);
        currentProfile.setLastPlayedAt(System.currentTimeMillis());
        return currentProfile;
    }

    public void saveProfile(ProfileData profile) throws IOException {
        if (profile != null) {
            profiles.put(profile.getProfileName(), profile);
            saveAllProfiles();
        }
    }

    public void deleteProfile(String profileName) throws IOException {
        if (profiles.containsKey(profileName)) {
            profiles.remove(profileName);
            saveAllProfiles();
            if (currentProfile != null && currentProfile.getProfileName().equals(profileName)) {
                currentProfile = null;
            }
        }
    }

    public List<ProfileData> getAllProfiles() {
        return new ArrayList<>(profiles.values());
    }

    public ProfileData getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(ProfileData profile) {
        this.currentProfile = profile;
    }

    public boolean doesProfileExist(String profileName) {
        return profiles.containsKey(profileName);
    }

    private void loadAllProfiles() {
        try {
            ensureProfilesDirectory();
            File file = new File(PROFILES_DIR, PROFILES_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    profiles = (HashMap<String, ProfileData>) ois.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi tải profiles: " + e.getMessage());
            profiles = new HashMap<>();
        }
    }

    private void saveAllProfiles() {
        try {
            ensureProfilesDirectory();
            File file = new File(PROFILES_DIR, PROFILES_FILE);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(profiles);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu profiles: " + e.getMessage());
        }
    }

    private void ensureProfilesDirectory() {
        File dir = new File(PROFILES_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
