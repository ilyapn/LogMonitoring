package ilyaPn.LogMonitoring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ilyaP on 18.08.2018.
 */
@Component
public class App implements ApplicationRunner {
    @Value("${folderContainingLogs}")
    String dirToRead;
    @Value("${folderContainingResult}")
    String dirToWrite;
    @Value("${deleteLogFiles}")
    boolean deleteLogFiles;
    private ArrayList<File> fileNameList = new ArrayList<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        File dir = new File(dirToRead);
        while (true) {
            for (String fileName : dir.list()) {
                File logFile = new File(fileName);
                if (!findFileInList(fileNameList, logFile)) {
                    fileNameList.add(logFile);
                    preparedAndHandled(fileName);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private boolean findFileInList(List<File> list, File file) {
        boolean res = false;
        for (File existFile : list) {
            if (existFile.getName().equals(file.getName()))
                res = true;
        }
        return res;
    }

    private void preparedAndHandled(String fileName) {
        File dir = new File(dirToRead);
        StatisticCollector statisticCollector = StatisticCollector.getNewStatisticCollector();
        executorService.execute(() -> {
            File file = new File(dir + "\\" + fileName);
            pars(file, statisticCollector);
            XMLGenerator xmlGenerator = new XMLGenerator(fileName, dirToWrite);
            xmlGenerator.generatedXmlFileWithStatistic(statisticCollector.getUsers());
            if (deleteLogFiles)
                file.delete();
        });

    }

    private void pars(File file, StatisticCollector statisticCollector) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            NodeList list = root.getElementsByTagName("log");
            for (int i = 0; i < list.getLength(); i++) {
                String timestampText = ((Element) list.item(i)).getElementsByTagName("timestamp").item(0).getTextContent();
                long timestamp = Long.parseLong(timestampText);
                String userId = ((Element) list.item(i)).getElementsByTagName("userId").item(0).getTextContent();
                String url = ((Element) list.item(i)).getElementsByTagName("url").item(0).getTextContent();
                String secondsText = ((Element) list.item(i)).getElementsByTagName("seconds").item(0).getTextContent();
                long seconds = Long.parseLong(secondsText);

                statisticCollector.addLog(new Log(timestamp, userId, url, seconds));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
