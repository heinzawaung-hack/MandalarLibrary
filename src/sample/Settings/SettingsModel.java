package sample.Settings;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import sample.Tools.Message;

import java.io.*;

public class SettingsModel {
     int withoutFee;
     float fees;
     String userName;
     String password;

    public static final String file = "userData.json";

    public SettingsModel() {
        withoutFee = 7;
        fees = 2;
        userName = "root";
        setPassword("root");
    }

    public int getWithoutFee() {
        return withoutFee;
    }

    public void setWithoutFee(int withoutFee) {
        this.withoutFee = withoutFee;
    }

    public float getFees() {
        return fees;
    }

    public void setFees(float fees) {
        this.fees = fees;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha512Hex(password);
    }

    public static void initSetting() throws IOException {
        SettingsModel settingsModel = new SettingsModel();
        Writer writer = null;
        try {
            writer = new FileWriter(file);
            Gson gson = new Gson();
            gson.toJson(settingsModel,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                writer.close();
            }
        }
    }

    public static SettingsModel getInstance() throws IOException {
        File file = new File("/home/heinzawaung/IntelliJIDEAProjects/Library/userData.json");
        SettingsModel settingsModel = new SettingsModel();
        try {
            FileReader fileReader = new FileReader(file);
            Gson gson = new Gson();
            settingsModel = gson.fromJson(fileReader,SettingsModel.class);
        } catch (FileNotFoundException e) {
            //If not exists init setting , for the first time
            initSetting();
            e.printStackTrace();
        }
        return settingsModel;
    }

    public static void writeModel(SettingsModel settingsModel) throws IOException {
        Writer writer = null;
        try {
            writer = new FileWriter(file);
            Gson gson = new Gson();
            gson.toJson(settingsModel,writer);
            Message message = new Message();
            message.infoMessage("Success","Settings Updated");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                writer.close();
            }
        }
    }
}
