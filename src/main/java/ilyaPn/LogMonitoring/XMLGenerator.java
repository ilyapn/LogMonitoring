package ilyaPn.LogMonitoring;

import ilyaPn.LogMonitoring.Entities.LogdayEntity;
import ilyaPn.LogMonitoring.Entities.LogdaysEntity;
import ilyaPn.LogMonitoring.Entities.UserEntity;
import ilyaPn.LogMonitoring.Entities.UsersEntity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by ilyaP on 21.08.2018.
 */
public class XMLGenerator {

    private LogdaysEntity output = new LogdaysEntity();
    private String fileName;
    private String dirToWrite;

    public XMLGenerator(String fileName, String dirToWrite) {
        this.fileName = fileName;
        this.dirToWrite = dirToWrite;
    }

    public void generatedXmlFileWithStatistic(ArrayList<User> users) {
        StringWriter writer = new StringWriter();
        outputFill(users, output);
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(LogdaysEntity.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(output, writer);
            fileWriter(writer.toString(), fileName);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public void outputFill(ArrayList<User> users, LogdaysEntity output) {
        ArrayList<LogdayEntity> logdayEntities = output.getLogday();
        Map<String, ArrayList<User>> map = splitByTime(users);
        output.setLogday(logdayEntities);
        ArrayList<String> keys = new ArrayList<>(map.keySet());

        for (int i = 0; i < map.size(); i++) {
            LogdayEntity logdayEntity = new LogdayEntity();
            logdayEntity.setDay(keys.get(i));
            logdayEntities.add(logdayEntity);
            UsersEntity usersEntity = new UsersEntity();
            logdayEntity.setUsers(usersEntity);
            ArrayList<User> list = map.get(keys.get(i));
            list.sort(Comparator.comparing(User::getId));
            for (User user : list) {
                UserEntity userEntity = new UserEntity();
                userEntity.setAverage(user.getSumSeconds() / user.getHitCounter());
                userEntity.setURL(user.getURL());
                userEntity.setId(user.getId());
                usersEntity.getUser().add(userEntity);
            }
        }
    }

    public Map<String, ArrayList<User>> splitByTime(ArrayList<User> users) {
        Map<String, ArrayList<User>> sharedLists = new HashMap<>();
        for (User user : users) {
            String dateText = new Date(user.getTimestamp() * 1000).toInstant().atZone(ZoneId.of("UTC")).
                    format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            ArrayList<User> listFromMap = sharedLists.get(dateText);
            if (listFromMap != null) {
                listFromMap.add(user);
            } else {
                ArrayList<User> list = new ArrayList<>();
                list.add(user);
                sharedLists.put(dateText, list);
            }
        }
        return sharedLists;
    }

    private void fileWriter(String text, String fileName) {
        File file = new File(dirToWrite + "\\" + "avg_" + fileName);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
