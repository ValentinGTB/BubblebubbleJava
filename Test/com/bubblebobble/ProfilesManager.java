package com.bubblebobble;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bubblebobble.models.ProfileModel;

public class ProfilesManager {

    private static ProfilesManager instance;
    private ProfilesManager() {}

    public static ProfilesManager getInstance() {
        if (instance == null) {
            instance = new ProfilesManager();
        }

        return instance;
    }

    private ProfileModel getDefaultProfile() {
        // create a default profile
        ProfileModel profile = new ProfileModel();
        profile.setNickname("Drake");
        profile.setAvatar("Drake.png");
        profile.setSavePath(Constants.PROFILES_URL + "drake.txt");
        return profile;
    }

    private ProfileModel getProfile(String profileFile) {
        ProfileModel profile = null;

        // load profile
        try {
            profile = ProfileModel.loadFromFile(Constants.PROFILES_URL + profileFile);
        } catch (Exception exception) {
            System.out.println("Impossibile caricare il profilo " + profileFile);
        }

        return profile;
    }

    public List<ProfileModel> getProfiles() {
        File profilesDir = new File(Constants.PROFILES_URL);
        List<ProfileModel> profiles = new ArrayList<ProfileModel>();

        // crea la cartella dei profili nel caso non ci sia
        if (!profilesDir.exists()) {
            profilesDir.mkdirs();
        }

        // carica tutti i profili memorizzati
        for (File profileFile : new File(Constants.PROFILES_URL).listFiles()) {
            profiles.add(getProfile(profileFile.getName()));
        }

        // se non ho profili, ne creo uno di default
        if (profiles.isEmpty()) {
            profiles.add(getDefaultProfile());
        }

        return profiles;
    }
}
